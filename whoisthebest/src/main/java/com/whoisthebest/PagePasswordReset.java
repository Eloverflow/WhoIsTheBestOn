package com.whoisthebest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.learn2crack.library.UserFunctions;


//à modifier comme Login et Register
public class PagePasswordReset extends Activity {
    Button resetpass;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_passwordreset_layout);

        resetpass = (Button) findViewById(R.id.respass);
        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetAsync(view);
            }
        });}


    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(this, PageLogin.class));
        this.overridePendingTransition(R.anim.slidein_righttoleft, R.anim.slideout_righttoleft);
        finish();
    }


    public void NetAsync(View view){
        new ProcessNetworkCheck(this,this.findViewById(android.R.id.content).getRootView()).execute();
    }}

