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

import java.util.ArrayList;

public class ListFriend extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList<String> name;
    private final ArrayList<Integer> status;

    public ListFriend(Activity context,
                      ArrayList<String> name, ArrayList<Integer> status) {
        super(context, R.layout.list_friend_unit_layout, name);
        this.context = context;
        this.name = name;
        this.status = status;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        //La ligne custom
        View rowView= inflater.inflate(R.layout.list_friend_unit_layout, null, true);

        //Ce que tu veux remplacer sur chaque ligne
        TextView txtTitle = (TextView) rowView.findViewById(R.id.friendName);

        ImageView imageCheck = (ImageView) rowView.findViewById(R.id.imgCheck);
        ImageView imageFav = (ImageView) rowView.findViewById(R.id.imgFavorites);

        //Ce qu'on met pour remplacer
        txtTitle.setText(name.get(position));

        switch(status.get(position)){

            // Favoris
            case 1:{
                imageFav.setVisibility(View.VISIBLE);
                break;
            }

            // Ami(e)
            case 2:{
                imageCheck.setImageResource(R.drawable.icone_check);
                break;
            }

            // Bloqué
            case 3:{
                imageCheck.setImageResource(android.R.drawable.presence_offline);
                break;
            }

            // Supprimé
            case 4:{
                imageCheck.setImageResource(android.R.drawable.ic_delete);
                break;
            }
        }

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slidein_righttoleft);
        rowView.startAnimation(animation);

        return rowView;
    }
}