package com.whoisthebest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import library.ChallengesFunctions;
import library.FriendFunctions;

//Fragment Page WhoIsTheBest?(Main)
//Where you can see the list of the different challenge that you can do or send to a friend
public class Fragment2 extends Fragment implements OnClickListener,SwipeRefreshLayout.OnRefreshListener {

    RelativeLayout mLinearLayout;
    SwipeRefreshLayout swipeLayout;
    LoadingProgressDialog cProgressDialog;
    LinearLayout challengeExtention;
    EditText challengeName;
    InputMethodManager imm;
    ImageButton searchChallengeNameButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (container == null) {
			return null;
		}

        //On crée notre inflate qu'on va retourner à notre pager adapter
        mLinearLayout = (RelativeLayout) inflater.inflate(R.layout.fragment2_layout, container, false);
        challengeExtention = (LinearLayout)mLinearLayout.findViewById(R.id.challengeExtention);
        challengeName = (EditText) mLinearLayout.findViewById(R.id.challengeName);
        imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        swipeLayout = (SwipeRefreshLayout)mLinearLayout.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        // Hard coded ?
        searchChallengeNameButton = (ImageButton) mLinearLayout.findViewById(R.id.searchChallengeNameButton);

        challengeName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    searchChallengeNameButton.performClick();
                    return true;
                }
                return false;
            }
        });
        // Hard coded ?


        //On ajoute un listener � nos boutons
        mLinearLayout.findViewById(R.id.f2_button1).setOnClickListener(this);
        mLinearLayout.findViewById(R.id.f2_button2).setOnClickListener(this);
        mLinearLayout.findViewById(R.id.f2_button3).setOnClickListener(this);
        mLinearLayout.findViewById(R.id.searchChallengeButton).setOnClickListener(this);
        mLinearLayout.findViewById(R.id.searchChallengeNameButton).setOnClickListener(this);

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
        else if(button.getId() == R.id.searchChallengeNameButton){
            tabSearch();
        }
        else if(button.getId() == R.id.searchChallengeButton)
        {
            if (challengeExtention.getVisibility() == View.VISIBLE)
            {
                collapse(challengeExtention);
                imm.hideSoftInputFromWindow(challengeName.getWindowToken(), 0);
            }
            else
            {
                expand(challengeExtention);
                challengeName.setFocusableInTouchMode(true);
                challengeName.requestFocus();
                imm.showSoftInput(challengeName, InputMethodManager.SHOW_IMPLICIT);
            }
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

    public void tabSearch(){

        //Si la tab n'est pas déjà sélectionné

            mLinearLayout.findViewById(R.id.f2_button1).setSelected(false);
            mLinearLayout.findViewById(R.id.f2_button2).setSelected(false);
            mLinearLayout.findViewById(R.id.f2_button3).setSelected(false);

            cProgressDialog = new LoadingProgressDialog(getActivity());
            cProgressDialog.setCancelable(true);
            cProgressDialog.show();

            loadChallenge("searchChallenge");

    }

    @Override
    public void onRefresh() {
        loadChallenge("1");
    }

    public void loadChallenge(String listType){
        new ProcessLoadListChallenge(getActivity(), mLinearLayout, listType).execute();
        stopLoading();
    }

    public void stopLoading(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                cProgressDialog.dismiss();
            }
        }, 600);
    }

    public static void expand(final View v) {
        v.measure(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? AbsListView.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) + 200);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) + 200);
        v.startAnimation(a);
    }
}
