package com.whoisthebest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jean on 2015-01-26.
 * Async Task to check whether internet connection is working
 */
public class ProcessNetworkCheck extends AsyncTask<String,String,Boolean> {
    private View rootView;
    private Context context;
    private LoadingProgressDialog cProgressDialog;

    public ProcessNetworkCheck(Context newContext, View newRootView) {
        context = newContext;
        rootView = newRootView;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        cProgressDialog = new LoadingProgressDialog(context);
        cProgressDialog.setCancelable(true);
        cProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... args){
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

            //On Vérifie dans quelle page on est pour lancer le bon process
            if(rootView.findViewById(R.id.loginPageButtonLogin) != null || rootView.findViewById(R.id.authPageButtonLogin) != null)
            {
                new ProcessLogin(context, rootView).execute();
            }
            else if(rootView.findViewById(R.id.registerPageButtonRegister) != null){

                new ProcessRegister(context, rootView).execute();
            }
            else if(rootView.findViewById(R.id.respass) != null){

                new ProcessResetPass(context, rootView).execute();
            }
        }
        else{
            //Peut importe la page pour une async custom il va y avoir un id error_msg normalement
            cProgressDialog.dismiss();
            TextView errorMsg = (TextView) rootView.findViewById(R.id.errorMsg);
            errorMsg.setText("Error in Network Connection");
        }
    }
}
