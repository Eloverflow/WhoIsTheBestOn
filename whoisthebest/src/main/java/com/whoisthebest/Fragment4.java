package com.whoisthebest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import library.FriendFunctions;

//Fragment Page Friends
//Where you can see the list of your friend or add a friend
public class Fragment4 extends Fragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {

    RelativeLayout mLinearLayout;
    LinearLayout friendExtention;
    EditText friendName;
    SwipeRefreshLayout swipeLayout;
    LoadingProgressDialog cProgressDialog;
    ListView list;
    InputMethodManager imm;
    ImageButton addFriendButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
        if (container == null) {
            return null;
        }

        mLinearLayout = (RelativeLayout) inflater.inflate(R.layout.fragment4_layout, container, false);

        friendExtention = (LinearLayout)mLinearLayout.findViewById(R.id.friendExtention);
        friendName = (EditText) mLinearLayout.findViewById(R.id.friendName);

        mLinearLayout.findViewById(R.id.searchFriendButton).setOnClickListener(this);
        mLinearLayout.findViewById(R.id.addFriendButton).setOnClickListener(this);
        mLinearLayout.findViewById(R.id.buttonRefresh).setOnClickListener(this);

        swipeLayout = (SwipeRefreshLayout)mLinearLayout.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        cProgressDialog = new LoadingProgressDialog(getActivity());
        cProgressDialog.setCancelable(true);
        cProgressDialog.show();

        imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        // Hard coded ?
        addFriendButton = (ImageButton) mLinearLayout.findViewById(R.id.addFriendButton);

        friendName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    addFriendButton.performClick();
                    return true;
                }
                return false;
            }
        });
        // Hard coded ?

        onRefresh();


        /*
        TextView login = (TextView) mlinearLayout.findViewById(R.id.textwelcome);
        login.setText("Welcome Mr. "+ MainActivity.user.get("uname"));*/

		return mLinearLayout;
	}

    @Override
    public void onRefresh() {
        new ProcessLoadListFriend(this.getActivity(), mLinearLayout).execute();
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

    @Override
    public void onClick(View button) {
        if(button.getId() == R.id.searchFriendButton)
        {
            if (friendExtention.getVisibility() == View.VISIBLE)
            {
                collapse(friendExtention);
                imm.hideSoftInputFromWindow(friendName.getWindowToken(), 0);
            }
            else
            {
                expand(friendExtention);
                friendName.setFocusableInTouchMode(true);
                friendName.requestFocus();
                imm.showSoftInput(friendName, InputMethodManager.SHOW_IMPLICIT);
            }
        }
        else if(button.getId() == R.id.buttonRefresh){
            onRefresh();
        }
        else if(button.getId() == R.id.addFriendButton){

            cProgressDialog = new LoadingProgressDialog(getActivity());
            cProgressDialog.setCancelable(true);
            cProgressDialog.show();


            new AsyncTask<Void, Void, Integer>() {

                @Override
                protected Integer doInBackground(Void... voids) {
                    FriendFunctions friendFunction = new FriendFunctions();
                    JSONObject retour = friendFunction.addFriend(WhoIsTheBest.user.get("uid"), friendName.getText().toString());
                    Integer flag = 0;

                    //Have to correct the return from API no success = 1 on success
                    try {
                        if(retour.getInt("success") == 0){
                            flag = 1;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return flag;
                }

                @Override
                protected void onPostExecute(Integer integer) {
                    if(integer == 1)
                    {
                        Toast.makeText(mLinearLayout.getContext(), "This user does not exist or is already your friend", Toast.LENGTH_LONG).show();
                        stopLoading();
                    }
                    else{
                        Toast.makeText(mLinearLayout.getContext(), "Friend request sent", Toast.LENGTH_LONG).show();
                        onRefresh();
                        collapse(friendExtention);
                        friendName.setText("");
                    }

                }
            }.execute();



        }
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
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density)+200);
        v.startAnimation(a);
    }
}
