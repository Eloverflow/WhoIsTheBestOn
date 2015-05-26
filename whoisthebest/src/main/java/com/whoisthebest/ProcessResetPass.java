package com.whoisthebest;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import library.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jean on 2015-01-30.
 */
public class ProcessResetPass extends AsyncTask<String, String, JSONObject> {


    private LoadingProgressDialog cProgressDialog;

    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";

    EditText email;
    TextView alert;
    Button resetpass;
    private View rootView;
    private Context context;

    String forgotpassword;


    public ProcessResetPass(Context newContext, View newRootView) {

        context = newContext;
        rootView = newRootView;
        email = (EditText) rootView.findViewById(R.id.forpas);
        alert = (TextView) rootView.findViewById(R.id.errorMsg);
        resetpass = (Button) rootView.findViewById(R.id.respass);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        forgotpassword = email.getText().toString();

        cProgressDialog = new LoadingProgressDialog(context);
        cProgressDialog.setCancelable(true);
        cProgressDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {


        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.forPass(forgotpassword);
        return json;


    }


    @Override
    protected void onPostExecute(JSONObject json) {
        /**
         * Checks if the Password Change Process is sucesss
         **/
        try {
            if (json.getString(KEY_SUCCESS) != null) {
                alert.setText("");
                String res = json.getString(KEY_SUCCESS);
                String red = json.getString(KEY_ERROR);


                if(Integer.parseInt(res) == 1){
                    cProgressDialog.dismiss();
                    alert.setText("A recovery email is sent to you, see it for more details.");



                }
                else if (Integer.parseInt(red) == 2)
                {    cProgressDialog.dismiss();
                    alert.setText("Your email does not exist in our database.");
                }
                else {
                    cProgressDialog.dismiss();
                    alert.setText("Error occured in changing Password");
                }




            }}
        catch (JSONException e) {
            e.printStackTrace();


        }
    }}