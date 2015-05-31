package com.whoisthebest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*
 * BroadcastReceiver pour le declenchement de l'alarme.
 * Active le service qui va faire la tâche asynchrone.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        context.startService(intentService);
    }
}
