package com.whoisthebest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import library.DatabaseHandler;
import library.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jean on 2015-01-26.
 */
public class ProcessRegister extends AsyncTask<String, String, JSONObject> {

    /**
     *  JSON Response node names.
     **/


    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "uid";
    private static String KEY_FIRSTNAME = "fname";
    private static String KEY_LASTNAME = "lname";
    private static String KEY_USERNAME = "uname";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    private static String KEY_ERROR = "error";

    /**
     * Defining Process dialog
     **/
    private View rootView;
    private Context context;
    private LoadingProgressDialog cProgressDialog;
    EditText inputUsername;
    EditText inputEmail;
    EditText inputPassword;
    Button btnRegister;
    TextView registerErrorMsg;

    public ProcessRegister(Context newContext, View newRootView) {
        context = newContext;
        rootView = newRootView;
        inputUsername = (EditText) rootView.findViewById(R.id.registerPageFormUser);
        inputEmail = (EditText) rootView.findViewById(R.id.registerPageFormEmail);
        inputPassword = (EditText) rootView.findViewById(R.id.registerPageFormPassword);
        btnRegister = (Button) rootView.findViewById(R.id.registerPageButtonRegister);
        registerErrorMsg = (TextView) rootView.findViewById(R.id.errorMsg);
    }

    String email,password,fname,lname,uname;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        inputUsername = (EditText) rootView.findViewById(R.id.registerPageFormUser);
        inputPassword = (EditText) rootView.findViewById(R.id.registerPageFormPassword);
        fname = "";//inputFirstName.getText().toString();
        lname = "";//inputLastName.getText().toString();
        email = inputEmail.getText().toString();
        uname= inputUsername.getText().toString();
        password = inputPassword.getText().toString();
        cProgressDialog = new LoadingProgressDialog(context);
        cProgressDialog.setCancelable(true);
        cProgressDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {


        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.registerUser(fname, lname, email, uname, password);

        return json;


    }
    @Override
    protected void onPostExecute(JSONObject json) {
        /**
         * Checks for success message.
         **/
        try {
            if (json != null) {
                if (json.getString(KEY_SUCCESS) != null) {
                    registerErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS);

                    String red = json.getString(KEY_ERROR);

                    Log.d("test", json.getString(KEY_ERROR));
                    Log.d("test2", json.getString(KEY_SUCCESS));

                    if (Integer.parseInt(res) == 1) {
                        cProgressDialog.setTitle("Getting Data");

                        registerErrorMsg.setText("Successfully Registered");


                        DatabaseHandler db = new DatabaseHandler(context.getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");

                        /**
                         * Removes all the previous data in the SQlite database
                         **/

                        UserFunctions logout = new UserFunctions();
                        logout.logoutUser(context.getApplicationContext());
                        db.addUser(json_user.getString(KEY_FIRSTNAME), json_user.getString(KEY_LASTNAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_USERNAME), json_user.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                        /**
                         * Stores registered data in SQlite Database
                         * Launch Registered screen
                         **/

                        Intent registered = new Intent(context.getApplicationContext(), WhoIsTheBest.class);

                        /**
                         * Close all views before launching Registered screen
                         **/
                        //registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        cProgressDialog.dismiss();
                        context.startActivity(registered);
                        //((Activity)context).overridePendingTransition(R.anim.slidein_lefttoright, R.anim.slideout_lefttoright);
                        ((Activity) context).finish();
                        //Temporarely disabled
                        //Login.this.overridePendingTransition(R.anim.slidein_lefttoright, R.anim.slideout_lefttoright);

                        //context.finish();
                    } else if (Integer.parseInt(red) == 1) {
                        cProgressDialog.dismiss();
                        registerErrorMsg.setText("User already exists");
                    } else if (Integer.parseInt(red) == 3) {
                        cProgressDialog.dismiss();
                        registerErrorMsg.setText("Invalid Email id");
                    }

                } else {
                    cProgressDialog.dismiss();

                    registerErrorMsg.setText("Error occured in registration");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();


        }
    }}