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

import java.util.ArrayList;

public class ListChallenge extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList<String> web;
    private final ArrayList<Integer> nbrUpVotes;
    private final ArrayList<Integer> nbrDownVotes;
    public ListChallenge(Activity context,
                         ArrayList<String> web, ArrayList<Integer> nbrUpVotes, ArrayList<Integer> nbrDownVotes) {
        super(context, R.layout.list_challenge_unit_layout, web);
        this.context = context;
        this.web = web;
        this.nbrUpVotes = nbrUpVotes;
        this.nbrDownVotes = nbrDownVotes;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        //La ligne custom
        View rowView= inflater.inflate(R.layout.list_challenge_unit_layout, null, false);

        //Ce que tu veux remplacer sur chaque ligne
        TextView txtTitle = (TextView) rowView.findViewById(R.id.challangeName);
        TextView nbrDownVote = (TextView) rowView.findViewById(R.id.downvote_nbr);
        TextView nbrUpVote = (TextView) rowView.findViewById(R.id.upvote_nbr);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        //Ce qu'on met pour remplacer
        txtTitle.setText(web.get(position));
        //nbrDownVote.setText("" +voteNbr[position]);
        //nbrUpVote.setText("" + ((voteNbr[position]+ 2) * 46 - 30));
        nbrUpVote.setText("" +/*(new Random().nextInt(1000 - 5 + 1) + 5) * */nbrUpVotes.get(position));
        nbrDownVote.setText(""  +/*(new Random().nextInt(600 - 5 + 1) + 5) * */nbrDownVotes.get(position));

        Animation animation;
        if(context.findViewById(R.id.f2_button1).isSelected())
        {
            animation = AnimationUtils.loadAnimation(context, R.anim.tab_slidein_righttoleft);
        }
        else if(context.findViewById(R.id.f2_button2).isSelected()) {
            if(position % 2 == 0)
                animation = AnimationUtils.loadAnimation(context, R.anim.tab_slidein_righttoleft);
            else
                animation = AnimationUtils.loadAnimation(context, R.anim.tab_slidein_lefttoright);
        }
        else {
            animation = AnimationUtils.loadAnimation(context, R.anim.tab_slidein_lefttoright);
        }

        rowView.startAnimation(animation);

        //imageView.setImageResource(Drawable.);
        return rowView;
    }
}