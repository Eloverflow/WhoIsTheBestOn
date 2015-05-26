package com.whoisthebest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class FragmentChallengeTarget extends Activity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    ListView list;

    SwipeRefreshLayout swipeLayout;
    LoadingProgressDialog cProgressDialog;
    Button btn1, btn2, btn3;

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Petite effet de transition
        //overridePendingTransition(R.anim.bottom_up, 0);
        setContentView(R.layout.fragment_challenge_target_layout);

        //On crée notre custom adapter de ListView
        /*ListChallengeTarget adapter = new ListChallengeTarget(this, friendList, badge);

        //On trouve la ListView à l'id donné dans notre Layout et on y applique notre Adapter
        list=(ListView)this.findViewById(R.id.listView);
        list.setAdapter(adapter);
*/


        swipeLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //On ajoute un listener � nos boutons
        btn1 = (Button) findViewById(R.id.challFriend_btn1);
        btn2 = (Button) findViewById(R.id.challFriend_btn2);
        btn3 = (Button) findViewById(R.id.challFriend_btn3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        //Default Tab
        tab1();
    }


    @Override
    public void onClick(View button) {
        // If clicked button is groups, set the layout fragment1.xml
        if(button.getId() == R.id.challFriend_btn1)
        {
            tab1();
        }
        else if(button.getId() == R.id.challFriend_btn2)
        {
            tab2();
        }
        else if(button.getId() == R.id.challFriend_btn3)
        {
            tab3();
        }
    }

    public void tab1(){
         //Si la tab n'est pas déjà sélectionné
        if(!btn1.isSelected())
        {
            btn1.setSelected(true);
            btn2.setSelected(false);
            btn3.setSelected(false);

            cProgressDialog = new LoadingProgressDialog(this);
            cProgressDialog.setCancelable(true);
            cProgressDialog.show();

            onRefresh();
        }
    }

    public void tab2(){
        //Si la tab n'est pas déjà sélectionné
        if(!btn2.isSelected())
        {
            btn1.setSelected(false);
            btn2.setSelected(true);
            btn3.setSelected(false);

            cProgressDialog = new LoadingProgressDialog(this);
            cProgressDialog.setCancelable(true);
            cProgressDialog.show();

            onRefresh();
        }
    }

    public void tab3(){
        //Si la tab n'est pas déjà sélectionné
        if(!btn3.isSelected())
        {
            btn1.setSelected(false);
            btn2.setSelected(false);
            btn3.setSelected(true);

            cProgressDialog = new LoadingProgressDialog(this);
            cProgressDialog.setCancelable(true);
            cProgressDialog.show();

            onRefresh();
        }
    }

    public void finish(View v) {
        finish();
        this.overridePendingTransition(0, R.anim.bottom_down);
    }

    public void onBackPressed() {
        finish(null);
    }

    @Override
    public void onRefresh() {
        new ProcessLoadListChallengeTarget(this, findViewById(R.id.fragment_challenge_target_layout)).execute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cProgressDialog.dismiss();
                swipeLayout.setRefreshing(false);
            }
        }, 600);
    }
}