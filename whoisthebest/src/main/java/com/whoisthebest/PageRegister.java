package com.whoisthebest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PageRegister extends Activity {

    /**
     * Defining layout items.
     **/

    //EditText inputFirstName;
    //EditText inputLastName;
    EditText inputUsername;
    EditText inputEmail;
    EditText inputPassword;
    Button btnRegister;
    TextView registerErrorMsg;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_register_layout);

        /**
         * Defining all layout items
         **/
        inputUsername = (EditText) findViewById(R.id.registerPageFormUser);
        inputEmail = (EditText) findViewById(R.id.registerPageFormEmail);
        inputPassword = (EditText) findViewById(R.id.registerPageFormPassword);
        btnRegister = (Button) findViewById(R.id.registerPageButtonRegister);
        registerErrorMsg = (TextView) findViewById(R.id.errorMsg);
        //inputFirstName = (EditText) findViewById(R.id.fname);
        //inputLastName = (EditText) findViewById(R.id.lname);

        /**
         * Register Button click event.
         * A Toast is set to alert when the fields are empty.
         * Another toast is set to alert Username must be 5 characters.
         **/

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (  ( !inputUsername.getText().toString().equals("")) && ( !inputPassword.getText().toString().equals("")) && ( !inputEmail.getText().toString().equals("")) )
                {
                    if ( inputUsername.getText().toString().length() > 4 ){
                        NetAsync(view);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Username should be minimum 5 characters", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Hard coded ?
        inputPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    btnRegister.performClick();
                    return true;
                }
                return false;
            }
        });
        // Hard coded ?

    }

    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(this, PageWelcome.class));
        this.overridePendingTransition(R.anim.slidein_lefttoright, R.anim.slideout_lefttoright);
        finish();
    }


    public void NetAsync(View view){
        new ProcessNetworkCheck(this,this.findViewById(android.R.id.content).getRootView()).execute();
    }}


