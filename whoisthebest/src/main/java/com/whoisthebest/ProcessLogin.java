package com.whoisthebest;

/**
 * Created by Jean on 2015-01-26.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import library.DatabaseHandler;
import library.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Async Task to get and send data to My Sql database through JSON respone.
 **/
public class ProcessLogin extends AsyncTask<String, String, JSONObject> {



    private View rootView;
    private Context context;
    Button btnLogin;
    Button Btnregister;
    TextView passreset;
    EditText inputEmail;
    EditText inputPassword;
    private TextView loginErrorMsg;


    /**
     * Called when the activity is first created.
     */
    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_USERNAME = "uname";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

    private LoadingProgressDialog cProgressDialog;

    String email,password;

    public ProcessLogin(Context newContext, View newRootView) {
        context = newContext;
        rootView = newRootView;
        inputEmail = (EditText) rootView.findViewById(R.id.loginPageFormEmail);
        inputPassword = (EditText) rootView.findViewById(R.id.loginPageFormPassword);
        btnLogin = (Button) rootView.findViewById(R.id.loginPageButtonLogin);
        passreset = (TextView) rootView.findViewById(R.id.passres);
        loginErrorMsg = (TextView) rootView.findViewById(R.id.errorMsg);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        inputEmail = (EditText) rootView.findViewById(R.id.loginPageFormEmail);
        inputPassword = (EditText) rootView.findViewById(R.id.loginPageFormPassword);
        email = inputEmail.getText().toString();
        password = inputPassword.getText().toString();
        cProgressDialog = new LoadingProgressDialog(context);
        cProgressDialog.setCancelable(true);
        cProgressDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.loginUser(email, password);
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        try {
            if (json.getString(KEY_SUCCESS) != null) {

                String res = json.getString(KEY_SUCCESS);

                if(Integer.parseInt(res) == 1){
                    cProgressDialog.setTitle("Getting Data");
                    DatabaseHandler db = new DatabaseHandler(context.getApplicationContext());
                    JSONObject json_user = json.getJSONObject("user");
                    /**
                     * Clear all previous data in SQlite database.
                     **/
                    UserFunctions logout = new UserFunctions();
                    logout.logoutUser(context.getApplicationContext());
                    db.addUser(json_user.getString(KEY_FIRSTNAME),json_user.getString(KEY_LASTNAME),json_user.getString(KEY_EMAIL),json_user.getString(KEY_USERNAME),json_user.getString(KEY_UID),json_user.getString(KEY_CREATED_AT));
                    /**
                     *If JSON array details are stored in SQlite it launches the User Panel.
                     **/
                    Intent witbMain = new Intent(context.getApplicationContext(), WhoIsTheBest.class);
                    //witbMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    cProgressDialog.dismiss();
                    context.startActivity(witbMain);
                    ((Activity)context).finish();


                    //Temporarely Disabled
                    /*overridePendingTransition(R.anim.slidein_lefttoright, R.anim.slideout_lefttoright);
                    *//**
                     * Close Login Screen
                     **//*
                    finish();*/
                }else{

                    cProgressDialog.dismiss();
                    loginErrorMsg.setText("Incorrect username/password");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}