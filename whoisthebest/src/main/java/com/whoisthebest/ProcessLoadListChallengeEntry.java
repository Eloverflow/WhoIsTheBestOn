package com.whoisthebest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jean on 2015-01-26.
 * Async Task to check whether internet connection is working
 */
public class ProcessLoadListChallengeEntry extends AsyncTask<String,String,Boolean> {
    private View rootView;
    private Context context;
    private LoadingProgressDialog cProgressDialog;

    //Temp :
    String[] challengeString = new String[]{
            "The Ice Bucket",
            "The Put Em in a coffin",
            "The Cinnamon",
            "The Dizzy",
            "The Fast Food",
            "The Put Em in a coffin",
            "The Sky Diving",
            "The Bridge Jump",
            "The Blindfolded",
            "The Bridge Jump",
            "The Put Em in a coffin",
            "The Trust Jesus",
            "The Kameamea",
            "The Twerk"};

    Integer[] status = {
            1,
            2,
            2,
            3,
            1,
            3,
            3,
            2,
            1,
            3,
            2,
            3,
            1,
            1};

    //EndTemp

    ListView list;
    ListChallengeEntry adapter;
    Intent myIntent;

    public ProcessLoadListChallengeEntry(Context newContext, View newRootView) {
        context = newContext;
        rootView = newRootView;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        cProgressDialog = new LoadingProgressDialog(context);
        cProgressDialog.setCancelable(true);
        //cProgressDialog.show();

        //On crée notre custom adapter de ListView
        adapter = new ListChallengeEntry((Activity) rootView.getContext(), challengeString, status);

        //On trouve la ListView à l'id donné dans notre Layout et on y applique notre Adapter
        list=(ListView)rootView.findViewById(R.id.listView);


    }

    @Override
    protected Boolean doInBackground(String... args){
        //Connextion à la BD
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL("http://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(3000);
                urlc.connect();
                Log.d("urlc response:", " "+urlc.getResponseCode());
                if (urlc.getResponseCode() == 200 || urlc.getResponseCode() == 302) {
                    return true;
                }

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;

    }
    @Override
    protected void onPostExecute(Boolean th){

        if(th == true){
            cProgressDialog.dismiss();

            //Instatiation pour les lignes
            View rowView;
            int i = 0;

            //Effet de départ de ligne de listview
            while((rowView = list.getChildAt(i)) != null)
            {
                Animation animation;


                if(rootView.findViewById(R.id.f1_button1).isSelected())
                {
                    animation = AnimationUtils.loadAnimation(context, R.anim.tab_slideout_righttoleft);
                }
                else {
                    animation = AnimationUtils.loadAnimation(context, R.anim.tab_slideout_lefttoright);
                }

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
        else{
            //Peut importe la page pour une async custom il va y avoir un id error_msg normalement
            cProgressDialog.dismiss();
            Toast.makeText(context, "Error in ListChallenge", Toast.LENGTH_SHORT);
        }
    }
}