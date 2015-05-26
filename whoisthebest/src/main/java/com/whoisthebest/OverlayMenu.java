package com.whoisthebest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class OverlayMenu extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overlay_menu_layout);
    }

    //On tue l'activité avec effet de transition
    public void killActivity(View v){
        finish();
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void disconnect(View v){
        LogoutDialogClass cdd=new LogoutDialogClass(this);
        cdd.show();
    }

    public void launchTests(View v){
        //not working
    }

    public void onBackPressed() {
        killActivity(null);
    }
}