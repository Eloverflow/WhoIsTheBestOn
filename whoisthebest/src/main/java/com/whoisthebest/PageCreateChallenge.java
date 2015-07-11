package com.whoisthebest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class PageCreateChallenge extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Petite effet de transition
        //overridePendingTransition(R.anim.bottom_up, 0);
        setContentView(R.layout.page_create_challenge_layout);

        if(this.getIntent().getExtras() != null){
            ((TextView)this.findViewById(R.id.fragmentPagesTittle)).setText(this.getIntent().getExtras().get("challenge").toString());
        }
    }

    public void finish(View v) {
        //((WebView) findViewById(R.id.videoView1)).destroy();
        finish();
        this.overridePendingTransition(0, R.anim.bottom_down);

    }

    public void onBackPressed() {
        finish(null);
    }


}