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

//import com.learn2crack.library.UserFunctions;


//à modifier comme Login et Register
public class PagePasswordReset extends Activity {
    Button resetpass;
    EditText forpas;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_passwordreset_layout);
        forpas = (EditText) findViewById(R.id.forpas);
        resetpass = (Button) findViewById(R.id.respass);
        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetAsync(view);
            }
        });

        // Hard coded ?
        forpas.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    resetpass.performClick();
                    return true;
                }
                return false;
            }
        });
        // Hard coded ?
    }


    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(this, PageLogin.class));
        this.overridePendingTransition(R.anim.slidein_righttoleft, R.anim.slideout_righttoleft);
        finish();
    }


    public void NetAsync(View view){
        new ProcessNetworkCheck(this,this.findViewById(android.R.id.content).getRootView()).execute();
    }}

