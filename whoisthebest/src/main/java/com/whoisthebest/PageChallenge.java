package com.whoisthebest;

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

public class PageChallenge extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener  {

    //Temp :
    String[] challengeString = new String[]{
            "The Ice Bucket",
            "The Put Em in a coffin",
            "The Cinnamon",
            "The Dizzy",
            "The Fast Food",
            "The Sky Diving",
            "The Bridge Jump",
            "The Blindfolded",
            "The Bridge Jump",
            "The Trust Jesus",
            "The Kameamea",
            "The Twerk"};

    Integer[] status = {
            1,
            2,
            2,
            3,
            1,
            3,
            2,
            1,
            3,
            2,
            1,
            1};
    //EndTemp
    ListView list;

    private YouTubePlayer YPlayer;
    private static final String YoutubeDeveloperKey = "AIzaSyDg_QKCvDCZo9UucfqtbFnUmUwkhXhRiq8";
    private static final int RECOVERY_DIALOG_REQUEST = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Petite effet de transition
        //overridePendingTransition(R.anim.bottom_up, 0);
        setContentView(R.layout.page_challenge_layout);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.videoView1);
        youTubeView.initialize(YoutubeDeveloperKey, this);

        if(this.getIntent().getExtras() != null){
            ((TextView)this.findViewById(R.id.fragmentPagesTittle)).setText(this.getIntent().getExtras().get("challenge").toString());
        }

        //On crée notre custom adapter de ListView
        ListChallengeEntry adapter = new ListChallengeEntry(this, challengeString, status);

        //On trouve la ListView à l'id donné dans notre Layout et on y applique notre Adapter
        list=(ListView)this.findViewById(R.id.listView);
        list.setAdapter(adapter);

    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YoutubeDeveloperKey, this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.videoView1);
    }

    @Override
    public void onInitializationSuccess(Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        YPlayer = player;
 /*
 * Now that this variable YPlayer is global you can access it
 * throughout the activity, and perform all the player actions like
 * play, pause and seeking to a position by code.
 */
        if (!wasRestored) {
            YPlayer.cueVideo("UfLjm6kqpeE");
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