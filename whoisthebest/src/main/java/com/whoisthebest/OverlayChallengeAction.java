package com.whoisthebest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OverlayChallengeAction extends Activity {

    String challenge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overlay_activity_challenge_action_layout);

        if(this.getIntent().getExtras() != null){
            challenge = this.getIntent().getExtras().get("challenge").toString();

            ((TextView)this.findViewById(R.id.fragmentPagesTittle)).setText(challenge);
        }
    }

    //On tue l'activité avec effet de transition
    public void killActivity(View v){
        finish();
        this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void disconnect(View v){
        /*CustomDialogClass cdd=new CustomDialogClass(this);
        cdd.show();*/
    }

    public void seeChallenge(View v){
        //On click on instancie une nouvelle activity avec notre Challenge Selectionné
        Intent myIntent = new Intent(v.getContext(), PageChallenge.class);

        //On ajouter les information à transmettre en Extra
        myIntent.putExtra("challenge", challenge);

        //On start l'activité
        startActivityForResult(myIntent, 0);

        //Petite effet de transition
        ((Activity) v.getContext()).overridePendingTransition(R.anim.bottom_up, 0);

        finish();
        //this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void onBackPressed() {
        killActivity(null);
    }
}