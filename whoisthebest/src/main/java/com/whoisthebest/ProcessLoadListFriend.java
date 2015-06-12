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
import android.widget.ListView;
import android.widget.TextView;

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
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, long id) {


                //On vérifie si on ajouté un onClickListener au dernier item pressé
                //et on lui enlève la posibilité de déclancher le onClick
                if (lastView != null) {
                    if (view.hasOnClickListeners())

                        Log.d("test3", "atatatat");
                    lastView.setClickable(false);
                    lastView = null;
                }

                //Si un item est pressé on lui applique un onClickListener pour le collapse quand
                //on le presse de nouveau et lui enlève la posibilité de déclancher le onClick
                view.setOnClickListener(new View.OnClickListener() {
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
                lastView = view;

                if (parent.findViewById(R.id.button1) != null) {

                    collapse(parent.findViewById(R.id.button1));

                    //parent.findViewById(R.id.button1).setVisibility(View.GONE);
                    parent.findViewById(R.id.button1).setId(R.id.unitExtention);
                }

                expand(view.findViewById(R.id.unitExtention));

                //view.findViewById(R.id.unitExtention).startAnimation(anim);
                //view.findViewById(R.id.unitExtention).setVisibility(View.VISIBLE);
                view.findViewById(R.id.unitExtention).setId(R.id.button1);
                view.findViewById(R.id.buttonInfo).setOnClickListener(new View.OnClickListener() {
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

                view.findViewById(R.id.buttonBlock).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //On click on instancie une nouvelle activity avec notre Challenge Selectionné
                        Intent myIntent = new Intent(v.getContext(), FragmentChallengeTarget.class);

                        //On start l'activité
                        ((Activity) context).startActivityForResult(myIntent, 0);

                        //Petite effet de transition
                        ((Activity) v.getContext()).overridePendingTransition(R.anim.bottom_up, 0);
                        //this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);


                    }
                });


                view.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //On click on instancie une nouvelle activity avec notre Challenge Selectionné
                        Intent myIntent = new Intent(v.getContext(), FragmentChallengeTarget.class);

                        //On start l'activité
                        ((Activity) context).startActivityForResult(myIntent, 0);

                        //Petite effet de transition
                        ((Activity) v.getContext()).overridePendingTransition(R.anim.bottom_up, 0);
                        //this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);


                    }
                });

            }
        });


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