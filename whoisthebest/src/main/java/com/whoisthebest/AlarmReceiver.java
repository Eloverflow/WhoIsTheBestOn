package com.whoisthebest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/*
 * BroadcastReceiver pour le declenchement de l'alarme.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Recuperation du compteur du nombre de declenchements de l'alarme.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int triggerCount = prefs.getInt("trigger_count", 0);

        //Peut etre ?
        //Je regarde le contenu des pref change par le AlarmService lorsque je l'ai lance

        // Appel du service qui gere le declenchement de l'alarme.
        // Note : Il est essentiel de faire ceci lorsque le traitement
        // a effectuer est lourd car l'execution de "onReceive" doit etre rapide.
        // De plus, il ne faut pas partir une tache asynchrone ici.
        Intent intentService = new Intent(context, AlarmService.class);
        context.startService(intentService);
    }
}
