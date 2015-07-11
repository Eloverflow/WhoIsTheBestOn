package com.whoisthebest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OverlayPreCreateChallenge extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overlay_pre_create_challenge_layout);
    }

    //On tue l'activité avec effet de transition
    public void killActivity(View v){
        finish();
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void createChallenge(View v){
        //New Windows or new fragment in windows ?
        //Let go for new window

        Intent myIntent = new Intent(this, PageCreateChallenge.class);
        if(this.getIntent().getExtras() != null){
            //On ajouter les information à transmettre en Extra
            myIntent.putExtra("challenge", this.getIntent().getExtras().get("challenge").toString());
        }


        this.startActivity(myIntent);
        this.overridePendingTransition(R.anim.bottom_up, 0);
        finish();
        //context.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }

    public void onBackPressed() {
        killActivity(null);
    }
}