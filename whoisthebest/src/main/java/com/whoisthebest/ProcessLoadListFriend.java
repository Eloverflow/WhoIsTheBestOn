package com.whoisthebest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import library.FriendFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jean on 2015-01-26.
 * Async Task to check whether internet connection is working
 */
    public class ProcessLoadListFriend extends AsyncTask<String,String,JSONObject> {
    private View rootView;
    private Context context;
    private LoadingProgressDialog cProgressDialog;


    ListView list;
    ListFriend adapter;
    Intent myIntent;
    View lastView;
    private ArrayList<Integer> statusArray = new ArrayList<>();
    private ArrayList<String> friendStringArray = new ArrayList<>();

    public ProcessLoadListFriend(Context newContext, View newRootView) {
        context = newContext;
        rootView = newRootView;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        cProgressDialog = new LoadingProgressDialog(context);
        cProgressDialog.setCancelable(true);
        //cProgressDialog.show();

        //On trouve la ListView à l'id donné dans notre Layout et on y applique notre Adapter
        list=(ListView)rootView.findViewById(R.id.listView);

    }

    @Override
    protected JSONObject doInBackground(String... args){
        FriendFunctions friendsFunction = new FriendFunctions();
        JSONObject json = friendsFunction.getFriendList(WhoIsTheBest.user.get("uid"));
        return json;

    }
    @Override
    protected void onPostExecute(JSONObject json){

        try {
            if (json != null) {

                JSONArray jArray = json.getJSONArray("list");

                for(int i=0; i<jArray.length(); i++){
                    JSONObject json_data = jArray.getJSONObject(i);

                    statusArray.add(json_data.getInt("status"));
                    friendStringArray.add(json_data.getString("name"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //On crée notre custom adapter de ListView
        adapter = new ListFriend((Activity) rootView.getContext(), friendStringArray, statusArray);

            //Instatiation pour les lignes
            View rowView;
            int i = 0;

            //Effet de départ de ligne de listview
            while((rowView = list.getChildAt(i)) != null)
            {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.slideout_righttoleft);

                rowView.startAnimation(animation);

                i++;

            }

            list.setAdapter(adapter);

        //Pour pouvoir écouter les action dans la ListView
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View friendUnitView,
                                    final int position, long id) {


                //On vérifie si on ajouté un onClickListener au dernier item pressé
                //et on lui enlève la posibilité de déclancher le onClick
                if (lastView != null) {
                    if (friendUnitView.hasOnClickListeners())

                    lastView.setClickable(false);
                    lastView = null;
                }

                //Si un item est pressé on lui applique un onClickListener pour le collapse quand
                //on le presse de nouveau et lui enlève la posibilité de déclancher le onClick
                friendUnitView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.isClickable()) {
                            if (v.findViewById(R.id.button1) != null) {
                                collapse(v.findViewById(R.id.button1));
                                v.findViewById(R.id.button1).setId(R.id.unitExtention);

                                //On enregistre la dernière view
                                lastView = v;
                            }

                            v.setClickable(false);
                        }
                    }
                });

                //On enregistre la dernière view
                lastView = friendUnitView;

                if (parent.findViewById(R.id.button1) != null) {

                    collapse(parent.findViewById(R.id.button1));

                    //parent.findViewById(R.id.button1).setVisibility(View.GONE);
                    parent.findViewById(R.id.button1).setId(R.id.unitExtention);
                }

                expand(friendUnitView.findViewById(R.id.unitExtention));

                //view.findViewById(R.id.unitExtention).startAnimation(anim);
                //view.findViewById(R.id.unitExtention).setVisibility(View.VISIBLE);
                friendUnitView.findViewById(R.id.unitExtention).setId(R.id.button1);
                friendUnitView.findViewById(R.id.buttonInfo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //On click on instancie une nouvelle activity avec notre Challenge Selectionné
                        Intent myIntent = new Intent(v.getContext(), PageChallenge.class);

                        //On ajouter les information à transmettre en Extra
                        //myIntent.putExtra("challenge", challengesStringArray.get(position));

                        //On start l'activité
                        ((Activity) context).startActivityForResult(myIntent, 0);

                        //Petite effet de transition
                        ((Activity) v.getContext()).overridePendingTransition(R.anim.bottom_up, 0);
                        //this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);


                    }
                });

                ImageView imgCheck = (ImageView) friendUnitView.findViewById(R.id.imgCheck);

                if (imgCheck.getTag().equals("blocked") || imgCheck.getTag().equals("friend") || imgCheck.getTag().equals("bestFriend")) {
                    friendUnitView.findViewById(R.id.buttonBlock).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            blockFriend(friendUnitView);
                        }
                    });


                    friendUnitView.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteFriend(friendUnitView);
                        }
                    });
                } else if (imgCheck.getTag().equals("reqSent")) {
                    friendUnitView.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelFriendReq(friendUnitView);
                        }
                    });
                }else if (imgCheck.getTag().equals("reqIn")) {
                    friendUnitView.findViewById(R.id.buttonBlock).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            blockFriend(friendUnitView);
                        }
                    });
                }else if (imgCheck.getTag().equals("deleted")) {
                    friendUnitView.findViewById(R.id.buttonBlock).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addFriend(friendUnitView);
                        }
                    });

                    friendUnitView.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            permaDeleteFriend(friendUnitView);
                        }
                    });
                }

                if (imgCheck.getTag().equals("bestFriend") || imgCheck.getTag().equals("friend")) {
                    friendUnitView.findViewById(R.id.buttonFav).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addFavFriend(friendUnitView);
                        }
                    });
                }

            }
        });


    }

    private void permaDeleteFriend(final View friendUnitView) {
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... voids) {
                TextView friendName = (TextView)friendUnitView.findViewById(R.id.friendName);
                FriendFunctions friendFunction = new FriendFunctions();
                JSONObject retour = friendFunction.permaDeleteFriend(WhoIsTheBest.user.get("uid"), friendName.getText().toString());
                Integer flag = 0;

                //Have to correct the return from API no success = 1 on success
                try {
                    if(retour.getInt("success") == 0){
                        flag = 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if(integer == 1)
                {
                    Toast.makeText(context, "Error in permanently deleting the friend", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Friend permanently deleted", Toast.LENGTH_SHORT).show();
                    collapse(friendUnitView);
                }

            }
        }.execute();
    }

    private void addFriend(final View friendUnitView) {
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... voids) {
                TextView friendName = (TextView)friendUnitView.findViewById(R.id.friendName);
                FriendFunctions friendFunction = new FriendFunctions();
                JSONObject retour = friendFunction.addFriend(WhoIsTheBest.user.get("uid"), friendName.getText().toString());
                Integer flag = 0;

                //Have to correct the return from API no success = 1 on success
                try {
                    if(retour.getInt("success") == 0){
                        flag = 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if(integer == 1)
                {
                    Toast.makeText(context, "Error in sending a new friend request", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "New friend request sent", Toast.LENGTH_SHORT).show();
                    ImageView imgCheck = (ImageView)friendUnitView.findViewById(R.id.imgCheck);
                    imgCheck.setImageResource(android.R.drawable.sym_action_email);
                }

            }
        }.execute();
    }

    private void cancelFriendReq(final View friendUnitView) {
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... voids) {
                TextView friendName = (TextView)friendUnitView.findViewById(R.id.friendName);
                FriendFunctions friendFunction = new FriendFunctions();
                JSONObject retour = friendFunction.cancelRequest(WhoIsTheBest.user.get("uid"), friendName.getText().toString());
                Integer flag = 0;

                //Have to correct the return from API no success = 1 on success
                try {
                    if(retour.getInt("success") == 0){
                        flag = 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if(integer == 1)
                {
                    Toast.makeText(context, "Could not cancel this friend request", Toast.LENGTH_SHORT).show();
                }
                else{
                    ImageView imgCheck = (ImageView)friendUnitView.findViewById(R.id.imgCheck);
                    Toast.makeText(context, "Friend request cancelled", Toast.LENGTH_SHORT).show();
                    imgCheck.setImageResource(android.R.drawable.ic_menu_delete);
                    collapse(friendUnitView);
                }

            }
        }.execute();
    }

    private void addFavFriend(final View friendUnitView) {
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... voids) {
                TextView friendName = (TextView)friendUnitView.findViewById(R.id.friendName);
                FriendFunctions friendFunction = new FriendFunctions();
                JSONObject retour = friendFunction.addFavFriend(WhoIsTheBest.user.get("uid"), friendName.getText().toString());
                Integer flag = 0;

                //Have to correct the return from API no success = 1 on success
                try {
                    if(retour.getInt("success") == 0){
                        flag = 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if(integer == 1)
                {
                    Toast.makeText(context, "Could not had this user to you BF", Toast.LENGTH_SHORT).show();
                }
                else{
                    ImageView imgCheck = (ImageView)friendUnitView.findViewById(R.id.imgCheck);
                    ImageView imgFavorites = (ImageView)friendUnitView.findViewById(R.id.imgFavorites);
                    if(imgCheck.getTag().equals("bestFriend")){
                        Toast.makeText(context, "Degraded Friend", Toast.LENGTH_SHORT).show();
                        imgFavorites.setVisibility(View.GONE);
                        imgCheck.setTag("friend");
                    }
                    else {
                        Toast.makeText(context, "New Best Friend", Toast.LENGTH_SHORT).show();
                        imgFavorites.setVisibility(View.VISIBLE);
                        imgCheck.setTag("bestFriend");
                    }
                }

            }
        }.execute();
    }

    public void blockFriend(final View friendUnitView){
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... voids) {
                TextView friendName = (TextView)friendUnitView.findViewById(R.id.friendName);
                FriendFunctions friendFunction = new FriendFunctions();
                JSONObject retour = friendFunction.blockFriend(WhoIsTheBest.user.get("uid"), friendName.getText().toString());
                Integer flag = 0;

                //Have to correct the return from API no success = 1 on success
                try {
                    if(retour.getInt("success") == 0){
                        flag = 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if(integer == 1)
                {
                    Toast.makeText(context, "Could not block this user", Toast.LENGTH_SHORT).show();
                }
                else{
                    ImageView imgCheck = (ImageView)friendUnitView.findViewById(R.id.imgCheck);
                    ImageView addCheck = (ImageView)friendUnitView.findViewById(R.id.addCheck);
                    if(imgCheck.getTag().equals("reqIn")){
                        Toast.makeText(context, "User blocked", Toast.LENGTH_SHORT).show();
                        imgCheck.setImageResource(android.R.drawable.presence_offline);
                        addCheck.setVisibility(View.GONE);
                        imgCheck.setTag("blocked");
                        collapse(friendUnitView);
                    }
                    else if(imgCheck.getTag().equals("blocked")){
                        Toast.makeText(context, "Friend unblocked", Toast.LENGTH_SHORT).show();
                        imgCheck.setImageResource(R.drawable.icone_check);
                        imgCheck.setTag("friend");
                    }
                    else {
                        Toast.makeText(context, "Friend blocked", Toast.LENGTH_SHORT).show();
                        imgCheck.setImageResource(android.R.drawable.presence_offline);
                        imgCheck.setTag("blocked");
                    }
                }

            }
        }.execute();
    }

    public void deleteFriend(final View friendUnitView){
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... voids) {
                TextView friendName = (TextView)friendUnitView.findViewById(R.id.friendName);
                FriendFunctions friendFunction = new FriendFunctions();
                JSONObject retour = friendFunction.deleteFriend(WhoIsTheBest.user.get("uid"), friendName.getText().toString());
                Integer flag = 0;

                //Have to correct the return from API no success = 1 on success
                try {
                    if(retour.getInt("success") == 0){
                        flag = 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if(integer == 1)
                {
                    Toast.makeText(context, "Could not delete this user", Toast.LENGTH_SHORT).show();
                }
                else{
                    ImageView imgCheck = (ImageView)friendUnitView.findViewById(R.id.imgCheck);
                    if(imgCheck.getTag().equals("deletedTemp")){
                        Toast.makeText(context, "Friend un-deleted", Toast.LENGTH_SHORT).show();
                        imgCheck.setImageResource(R.drawable.icone_check);
                        imgCheck.setTag("friend");
                    }
                    else {
                        Toast.makeText(context, "Friend deleted", Toast.LENGTH_SHORT).show();
                        imgCheck.setImageResource(android.R.drawable.ic_menu_delete);
                        imgCheck.setTag("deletedTemp");
                    }
                }

            }
        }.execute();
    }

    public static void expand(final View v) {
        v.measure(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? AbsListView.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density)+200);
        v.startAnimation(a);
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