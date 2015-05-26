package com.whoisthebest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*
 * BroadcastReceiver pour le demarrage (boot) de l'appareil.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            // Activation de l'alarme.
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(
                    context,
                    WhoIsTheBest.ID_ALARM,
                    alarmIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            alarmMgr.setRepeating(
                    AlarmManager.ELAPSED_REALTIME,
                    0,
                    WhoIsTheBest.INTERVAL_ALARM,
                    alarmPendingIntent);
        }
    }
}
