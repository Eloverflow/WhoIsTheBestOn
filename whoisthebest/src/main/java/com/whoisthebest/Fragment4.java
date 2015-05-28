package com.whoisthebest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import library.FriendFunctions;

public class Fragment4 extends Fragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {


    //Temp :
    String[] friendList = new String[]{
            "William Grognon",
            "William Le Dieu Grec",
            "Lau la Pimp",
            "Chuck Norris",
            "Jean FM 93",
            "Kiki",
            "Guidounne",
            "Luigi Bros.",
            "Mario Bros.",
            "Donkey Kong",
            "Sangoku",
            "Monkey D. Luffy",
            "William Grognon",
            "William Le Dieu Grec",
            "Lau la Pimp",
            "Chuck Norris",
            "Jean FM 93",
            "Kiki",
            "Guidounne",
            "Luigi Bros.",
            "Mario Bros.",
            "Donkey Kong",
            "Sangoku",
            "Monkey D. Luffy"};

    Integer[] status = {
            1,
            2,
            4,
            3,
            1,
            3,
            4,
            1,
            3,
            2,
            4,
            1,
            1,
            2,
            4,
            3,
            1,
            3,
            4,
            1,
            3,
            2,
            4,
            1};
    //EndTemp

    RelativeLayout mLinearLayout;
    LinearLayout friendExtention;
    EditText friendName;
    SwipeRefreshLayout swipeLayout;
    LoadingProgressDialog cProgressDialog;
    ListView friends;
    ListView list;
    Intent myIntent;

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

        swipeLayout = (SwipeRefreshLayout)mLinearLayout.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        onRefresh();


        /*
        TextView login = (TextView) mlinearLayout.findViewById(R.id.textwelcome);
        login.setText("Welcome Mr. "+ MainActivity.user.get("uname"));*/

		return mLinearLayout;
	}

    @Override
    public void onRefresh() {
        new ProcessLoadListFriend(this.getActivity(), mLinearLayout).execute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
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
            }
            else
            {
                expand(friendExtention);
            }
        }
        else if(button.getId() == R.id.addFriendButton){


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
                        Toast.makeText(mLinearLayout.getContext(), "This user does not exist", Toast.LENGTH_LONG).show();
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
