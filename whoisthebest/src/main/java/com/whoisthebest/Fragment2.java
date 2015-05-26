package com.whoisthebest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Fragment2 extends Fragment implements OnClickListener,SwipeRefreshLayout.OnRefreshListener {

    RelativeLayout mLinearLayout;
    SwipeRefreshLayout swipeLayout;
    LoadingProgressDialog cProgressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (container == null) {
			return null;
		}

        //On crée notre inflate qu'on va retourner à notre pager adapter
        mLinearLayout = (RelativeLayout) inflater.inflate(R.layout.fragment2_layout, container, false);

        swipeLayout = (SwipeRefreshLayout)mLinearLayout.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        //On ajoute un listener � nos boutons
        mLinearLayout.findViewById(R.id.f2_button1).setOnClickListener(this);
        mLinearLayout.findViewById(R.id.f2_button2).setOnClickListener(this);
        mLinearLayout.findViewById(R.id.f2_button3).setOnClickListener(this);

        //Default Tab
        tab1();

		return mLinearLayout;
	}

    @Override
    public void onClick(View button) {

        // If clicked button is groups, set the layout fragment1.xml
        if(button.getId() == R.id.f2_button1)
        {
            tab1();
        }
        else if(button.getId() == R.id.f2_button2)
        {
            tab2();
        }
        else if(button.getId() == R.id.f2_button3)
        {
            tab3();
        }
    }

    public void tab1(){

        //Si la tab n'est pas déjà sélectionné
        if(!mLinearLayout.findViewById(R.id.f2_button1).isSelected())
        {
            mLinearLayout.findViewById(R.id.f2_button1).setSelected(true);
            mLinearLayout.findViewById(R.id.f2_button2).setSelected(false);
            mLinearLayout.findViewById(R.id.f2_button3).setSelected(false);

            cProgressDialog = new LoadingProgressDialog(getActivity());
            cProgressDialog.setCancelable(true);
            cProgressDialog.show();

            onRefresh();
        }

    }

    public void tab2(){

        //Si la tab n'est pas déjà sélectionné
        if(!mLinearLayout.findViewById(R.id.f2_button2).isSelected())
        {
            mLinearLayout.findViewById(R.id.f2_button1).setSelected(false);
            mLinearLayout.findViewById(R.id.f2_button2).setSelected(true);
            mLinearLayout.findViewById(R.id.f2_button3).setSelected(false);

            cProgressDialog = new LoadingProgressDialog(getActivity());
            cProgressDialog.setCancelable(true);
            cProgressDialog.show();

            onRefresh();
        }

    }

    public void tab3(){

        //Si la tab n'est pas déjà sélectionné
        if(!mLinearLayout.findViewById(R.id.f2_button3).isSelected())
        {
            mLinearLayout.findViewById(R.id.f2_button1).setSelected(false);
            mLinearLayout.findViewById(R.id.f2_button2).setSelected(false);
            mLinearLayout.findViewById(R.id.f2_button3).setSelected(true);

            cProgressDialog = new LoadingProgressDialog(getActivity());
            cProgressDialog.setCancelable(true);
            cProgressDialog.show();

            onRefresh();
        }

    }

    @Override
    public void onRefresh() {
        new ProcessLoadListChallenge(getActivity(), mLinearLayout).execute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cProgressDialog.dismiss();
                swipeLayout.setRefreshing(false);
            }
        }, 600);
    }
}
