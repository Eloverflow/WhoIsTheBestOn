package com.whoisthebest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import library.ChallengesFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jean on 2015-01-26.
 * Async Task to check whether internet connection is working
 */
public class ProcessLoadListChallengeTarget extends AsyncTask<String, String, JSONObject> {
    private View rootView;
    private Context context;
    private LoadingProgressDialog cProgressDialog;
    ArrayList<String> challengesString = new ArrayList<>();
    Integer[] voteArray;

    ListView list;
    ListChallengeTarget adapter;
    Intent myIntent;
    View lastView;

    //Temp :
    String[] friendList = new String[]{
            "William Grognon",
            "William Le Dieu Grec",
            "Lau la Pimp",
            "Chuck Norris",
            "Jean FM 93",
            "Kiki",
            "Guidounne",
            "Luigi Bros.",
            "Mario Bros.",
            "Donkey Kong",
            "Sangoku",
            "Monkey D. Luffy",
            "William Grognon",
            "William Le Dieu Grec",
            "Lau la Pimp",
            "Chuck Norris",
            "Jean FM 93",
            "Kiki",
            "Guidounne",
            "Luigi Bros.",
            "Mario Bros.",
            "Donkey Kong",
            "Sangoku",
            "Monkey D. Luffy"};

    Integer[] badge = {
            1,
            2,
            4,
            3,
            1,
            3,
            4,
            1,
            3,
            2,
            4,
            1,
            1,
            2,
            4,
            3,
            1,
            3,
            4,
            1,
            3,
            2,
            4,
            1};
    //EndTemp

    public ProcessLoadListChallengeTarget(Context newContext, View newRootView) {
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
        ChallengesFunctions challengesFunction = new ChallengesFunctions();
        JSONObject json = challengesFunction.getChallengesList("poppular");
        return json;

    }
    @Override
    protected void onPostExecute(JSONObject json){
            try {
                if (json != null) {

                    JSONArray jArray = json.getJSONArray("list");

                    for(int i=0; i<jArray.length(); i++){
                        JSONObject json_data = jArray.getJSONObject(i);

                        challengesString.add(json_data.getString("name"));

                        /*Log.i("log_tag", "id:" + json_data.getInt("cid") +
                                        ", name:" + json_data.getString("name") +
                                        ", by:" + json_data.getString("created_by_uid") +
                                        ", desc:" + json_data.getString("desc") +
                                        ", date:" + json_data.getString("date")
                        );*/
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //On crée notre custom adapter de ListView
            adapter = new ListChallengeTarget((Activity) rootView.getContext(), friendList, badge);

            //Instatiation pour les lignes
            View rowView;
            int i = 0;

            //Effet de départ de ligne de listview
            while((rowView = list.getChildAt(i)) != null)
            {
                Animation animation;

                if(rootView.findViewById(R.id.challFriend_btn1).isSelected())
                {
                    animation = AnimationUtils.loadAnimation(context, R.anim.tab_slideout_righttoleft);
                }
                else if(rootView.findViewById(R.id.challFriend_btn2).isSelected()) {
                    if(i % 2 == 0)
                        animation = AnimationUtils.loadAnimation(context, R.anim.tab_slideout_righttoleft);
                    else
                        animation = AnimationUtils.loadAnimation(context, R.anim.tab_slideout_lefttoright);
                }
                else {
                    animation = AnimationUtils.loadAnimation(context, R.anim.tab_slideout_lefttoright);
                }
                i++;

                rowView.startAnimation(animation);
            }

            list.setAdapter(adapter);

            //Pour pouvoir écouter les action dans la ListView
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent, final View view,
                                        final int position, long id) {


                    Toast.makeText(context,"Comming soon",Toast.LENGTH_SHORT);

                }
            });

        }
}