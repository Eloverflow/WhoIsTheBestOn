package com.whoisthebest;

/**
 * Created by Jean on 2015-01-23.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListChallengeTarget extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] name;
    private final Integer[] badge;

    public ListChallengeTarget(Activity context,
                               String[] name, Integer[] badge) {
        super(context, R.layout.list_challenge_friend_unit_layout, name);
        this.context = context;
        this.name = name;
        this.badge = badge;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        //La ligne custom
        View rowView= inflater.inflate(R.layout.list_friend_unit_layout, null, true);

        //Ce que tu veux remplacer sur chaque ligne
        TextView txtTitle = (TextView) rowView.findViewById(R.id.friendName);

        //Ce qu'on met pour remplacer
        txtTitle.setText(name[position]);


        Animation animation;
        if(context.findViewById(R.id.challFriend_btn1).isSelected())
        {
            animation = AnimationUtils.loadAnimation(context, R.anim.tab_slidein_righttoleft);
        }
        else if(context.findViewById(R.id.challFriend_btn2).isSelected()) {
            if(position % 2 == 0)
                animation = AnimationUtils.loadAnimation(context, R.anim.tab_slidein_righttoleft);
            else
                animation = AnimationUtils.loadAnimation(context, R.anim.tab_slidein_lefttoright);
        }
        else {
            animation = AnimationUtils.loadAnimation(context, R.anim.tab_slidein_lefttoright);
        }
        rowView.startAnimation(animation);

        return rowView;
    }
}