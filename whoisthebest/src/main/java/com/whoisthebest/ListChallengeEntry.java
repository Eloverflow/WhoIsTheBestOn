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
import android.widget.ImageView;
import android.widget.TextView;

public class ListChallengeEntry extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] name;
    private final Integer[] status;

    public ListChallengeEntry(Activity context,
                              String[] name, Integer[] status) {
        super(context, R.layout.list_challenge_entry_unit_layout, name);
        this.context = context;
        this.name = name;
        this.status = status;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        //La ligne custom
        View rowView= inflater.inflate(R.layout.list_challenge_entry_unit_layout, null, true);

        //Ce que tu veux remplacer sur chaque ligne
        TextView txtChallenge = (TextView) rowView.findViewById(R.id.challengeName);
        ImageView imgStatus = (ImageView) rowView.findViewById(R.id.imgStatus);

        //Ce qu'on met pour remplacer
        txtChallenge.setText(name[position]);

        switch (status[position]) {

            // Statut Pending
            case 1:
                imgStatus.setImageResource(android.R.drawable.presence_invisible);
                break;

            // Statut Accepted
            case 2:
                imgStatus.setImageResource(android.R.drawable.presence_online);
                break;

            // Statut Declined
            case 3:
                imgStatus.setImageResource(android.R.drawable.presence_busy);
                break;

            // Statut Done
            case 4:
                imgStatus.setImageResource(R.drawable.icone_check);
                break;
        }


        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fadein);
        if(context.findViewById(R.id.f1_button1) != null)
        {
            if(context.findViewById(R.id.f1_button1).isSelected())
            {
                animation = AnimationUtils.loadAnimation(context, R.anim.tab_slidein_righttoleft);
            }
            else {
                animation = AnimationUtils.loadAnimation(context, R.anim.tab_slidein_lefttoright);
            }
        }

        rowView.startAnimation(animation);

        return rowView;
    }
}