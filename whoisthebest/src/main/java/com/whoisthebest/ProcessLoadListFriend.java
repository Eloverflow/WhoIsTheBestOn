package com.whoisthebest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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


            //Log.d("test", "test");

            /*//Pour pouvoir écouter les action dans la ListView
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    //On click on instancie une nouvelle activity avec notre Challenge Selectionné
                    myIntent = new Intent(rootView.getContext(), OverlayChallengeAction.class);

                    //On ajouter les information à transmettre en Extra
                    myIntent.putExtra("challenge", challengeString[+ position]);

                    //On start l'activité
                    ((Activity)context).startActivityForResult(myIntent, 0);

                    //Petite effet de transition
                    ((Activity) rootView.getContext()).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            });*/


    }
}