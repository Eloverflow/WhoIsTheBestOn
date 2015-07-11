package com.whoisthebest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.learn2crack.library.DatabaseHandler;
//import com.learn2crack.library.UserFunctions;


public class PageLogin extends Activity {

    Button btnLogin;
    TextView passreset;
    EditText inputEmail;
    EditText inputPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_login_layout);

        inputEmail = (EditText) findViewById(R.id.loginPageFormEmail);
        inputPassword = (EditText) findViewById(R.id.loginPageFormPassword);
        btnLogin = (Button) findViewById(R.id.loginPageButtonLogin);
        passreset = (TextView)findViewById(R.id.passres);
        //((TextView)findViewById(R.id.pageTittle)).setText("Login");

        passreset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PagePasswordReset.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(R.anim.slidein_lefttoright, R.anim.slideout_lefttoright);
                finish();
            }});

        // Hard coded ?
        inputPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    btnLogin.performClick();
                    return true;
                }
                return false;
            }
        });
        // Hard coded ?


/**
 * Login button click event
 * A Toast is set to alert when the Email and Password field is empty
 **/
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (  ( !inputEmail.getText().toString().equals("")) && ( !inputPassword.getText().toString().equals("")) )
                {
                    NetAsync(view);
                }
                else if ( ( !inputEmail.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                }
                else if ( ( !inputPassword.getText().toString().equals("")) )
                {
                    Toast.makeText(getApplicationContext(),
                            "Email field empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Email and Password field are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(this, PageWelcome.class));
        this.overridePendingTransition(R.anim.slidein_righttoleft, R.anim.slideout_righttoleft);
        finish();
    }

    public void NetAsync(View view){
        new ProcessNetworkCheck(this,this.findViewById(android.R.id.content).getRootView()).execute();
    }
}