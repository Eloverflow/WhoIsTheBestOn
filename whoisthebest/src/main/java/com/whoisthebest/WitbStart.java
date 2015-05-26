package com.whoisthebest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import library.DatabaseHandler;

import java.util.HashMap;

/**
 * Created by Jean on 2015-02-03.
 */
public class WitbStart extends Activity {


    static HashMap<String,String> user = new HashMap<String, String>();
    private LoadingProgressDialog cProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.witb_start_layout);

            first_time_check();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Changer le fond losqu'on tourne l'écran
        setContentView(R.layout.witb_start_layout);
    }

    private boolean first_time_check() {

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        /**
         * Hashmap to load data from the Sqlite database
         **/
        user = db.getUserDetails();

        //Dans le futur vérifier si l'usager existe tjs ?
        if((user.get("uname") != null)){
        //new AsyncTaskCustom(this,this.findViewById(android.R.id.content).getRootView()).execute();

            cProgressDialog = new LoadingProgressDialog(this);
            cProgressDialog.setCancelable(true);
            cProgressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(WitbStart.this, WhoIsTheBest.class);
                            startActivity(i);
                            cProgressDialog.dismiss();
                            finish();
                        }
                    });
                }
            }).start();


        }
        else
        {
            this.startActivity(new Intent(this, PageWelcome.class));
        }
        return false;
    }
}
