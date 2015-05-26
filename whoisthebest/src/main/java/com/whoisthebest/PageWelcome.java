package com.whoisthebest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class PageWelcome extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_welcome_layout);

        //On ajoute un listener � nos boutons
        findViewById(R.id.authPageButtonLogin).setOnClickListener(this);
        findViewById(R.id.authPageButtonRegister).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case  R.id.authPageButtonLogin: {
                    this.startActivity(new Intent(this, PageLogin.class));
                    this.overridePendingTransition(R.anim.slidein_lefttoright, R.anim.slideout_lefttoright);
                    finish();
                    break;
                }

                case R.id.authPageButtonRegister: {

                    startActivity(new Intent(this, PageRegister.class));
                    this.overridePendingTransition(R.anim.slidein_righttoleft, R.anim.slideout_righttoleft);
                    finish();
                    break;
                }
            }
    }
}
