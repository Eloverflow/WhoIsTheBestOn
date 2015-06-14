package com.whoisthebest;

/**
 * Created by Jean on 2015-01-23.
 */
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import library.FriendFunctions;

public class ListFriend extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList<String> name;
    private final ArrayList<Integer> status;

    public ListFriend(Activity context,
                      ArrayList<String> name, ArrayList<Integer> status) {
        super(context, R.layout.list_friend_unit_layout, name);
        this.context = context;
        this.name = name;
        this.status = status;
    }

    @Override
    public View getView(final int position, final View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        //La ligne custom
        final View rowView= inflater.inflate(R.layout.list_friend_unit_layout, null, true);

        //Ce que tu veux remplacer sur chaque ligne
        final TextView txtTitle = (TextView) rowView.findViewById(R.id.friendName);

        final ImageView imageCheck = (ImageView) rowView.findViewById(R.id.imgCheck);
        final ImageView addCheck = (ImageView) rowView.findViewById(R.id.addCheck);
        ImageView imageFav = (ImageView) rowView.findViewById(R.id.imgFavorites);
        ImageButton buttonFav = (ImageButton) rowView.findViewById(R.id.buttonFav);
        ImageButton buttonDelete = (ImageButton) rowView.findViewById(R.id.buttonDelete);
        ImageButton buttonBlock = (ImageButton) rowView.findViewById(R.id.buttonBlock);


        //Ce qu'on met pour remplacer
        txtTitle.setText(name.get(position));

        switch(status.get(position)){

            // Pending in
            case 0:{
                imageCheck.setImageResource(android.R.drawable.ic_delete);
                addCheck.setImageResource(android.R.drawable.ic_input_add);
                buttonDelete.setVisibility(View.GONE);
                imageCheck.setTag("reqIn");

                //On click effect sur le + de la ligne
                addCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AsyncTask<Void, Void, Integer>() {

                            @Override
                            protected Integer doInBackground(Void... voids) {
                                FriendFunctions friendFunctions = new FriendFunctions();
                                JSONObject retour = friendFunctions.acceptFriend(WhoIsTheBest.user.get("uid"), ((TextView) rowView.findViewById(R.id.friendName)).getText().toString());
                                Integer flag = 0;

                                //Have to correct the return from API no success = 1 on success
                                try {
                                    if (retour.getInt("success") == 0) {
                                        flag = 1;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return flag;
                            }

                            @Override
                            protected void onPostExecute(Integer integer) {
                                if (integer == 1) {
                                    //Toast.makeText(mLinearLayout.getContext(), "This user does not exist", Toast.LENGTH_LONG).show();
                                    //stopLoading();
                                } else {
                                    //rowView.setVisibility(View.GONE);
                                    addCheck.setImageResource(0);
                                    imageCheck.setImageResource(R.drawable.icone_check);
                                    Toast.makeText(getContext(), "Friend added", Toast.LENGTH_LONG).show();

                                    //Toast.makeText(mLinearLayout.getContext(), "Friend request sent", Toast.LENGTH_LONG).show();
                                    //onRefresh();
                                    //friendName.setText("");
                                }

                            }
                        }.execute();

                    }
                });

                imageCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AsyncTask<Void, Void, Integer>() {

                            @Override
                            protected Integer doInBackground(Void... voids) {
                                FriendFunctions friendFunctions = new FriendFunctions();
                                JSONObject retour = friendFunctions.deleteFriend(WhoIsTheBest.user.get("uid"), ((TextView) rowView.findViewById(R.id.friendName)).getText().toString());
                                Integer flag = 0;

                                //Have to correct the return from API no success = 1 on success
                                try {
                                    if (retour.getInt("success") == 0) {
                                        flag = 1;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return flag;
                            }

                            @Override
                            protected void onPostExecute(Integer integer) {
                                if (integer == 1) {
                                    //Toast.makeText(mLinearLayout.getContext(), "This user does not exist", Toast.LENGTH_LONG).show();
                                    //stopLoading();
                                } else {
                                    //rowView.setVisibility(View.GONE);
                                    collapse(rowView);
                                    Toast.makeText(getContext(), "Friend declined", Toast.LENGTH_LONG).show();

                                    //Toast.makeText(mLinearLayout.getContext(), "Friend request sent", Toast.LENGTH_LONG).show();
                                    //onRefresh();
                                    //friendName.setText("");
                                }

                            }
                        }.execute();

                    }
                });
                break;
            }
            // Favoris
            case 1:{
                imageFav.setVisibility(View.VISIBLE);
                buttonFav.setVisibility(View.VISIBLE);
                imageCheck.setTag("bestFriend");
                break;
            }

            // Ami(e)
            case 2:{
                imageCheck.setImageResource(R.drawable.icone_check);
                buttonFav.setVisibility(View.VISIBLE);
                imageCheck.setTag("friend");
                break;
            }

            // Pending out
            case 3: {
                imageCheck.setImageResource(android.R.drawable.sym_action_email);
                imageCheck.setTag("reqSent");
                buttonBlock.setVisibility(View.GONE);
                break;
            }

            // Bloqué
            case 4:{
                imageCheck.setImageResource(android.R.drawable.presence_offline);
                imageCheck.setTag("blocked");
                break;
            }

            // Supprimé
            case 5:{
                imageCheck.setImageResource(android.R.drawable.ic_menu_delete);
                buttonBlock.setImageResource(android.R.drawable.ic_input_add);
                imageCheck.setTag("deleted");
                break;
            }



        }

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slidein_righttoleft);
        rowView.startAnimation(animation);

        return rowView;
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density)+200);
        v.startAnimation(a);
    }
}