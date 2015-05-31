package com.whoisthebest;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import java.util.ArrayList;

import library.NotificationsFunctions;

/*
 * Service appele lorsque l'alarme est declenchee.
 * Permet de faire les tâche asynchrone en background.
 */
public class AlarmService extends IntentService {
    Exception m_Exp;

    // Gestionnaire de notifications.
    private NotificationManager notifMgr;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    //On effectu la tâche asynchrone ici !
    protected void onHandleIntent(Intent intent) {

        //On associe le NotificationManager à une variable pour pouvoir intéragir avec plus tard.
        this.notifMgr = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        //On va chercher les notifications avec la methode GetNotifs
        ArrayList<String> arrayDeNotif = GetNotifs();

        //On vérifie si des notifications sont retournées
        if(arrayDeNotif != null && arrayDeNotif.size() > 0)
        {
                //Si oui on envoie les notifs au NotificationManger...
                gererEnvoyerNotif(arrayDeNotif);
        }

    }

    protected ArrayList<String> GetNotifs(Void... unused) {
        ArrayList<String> listeNotifs = new ArrayList<String>();

        try {
            NotificationsFunctions notifFunction = new NotificationsFunctions();
            //notifFunction.

        } catch (Exception e) {
            m_Exp = e;
        }
        return listeNotifs;
    }


    @SuppressWarnings("deprecation")
    public void gererEnvoyerNotif(ArrayList<String> nom) {

        String statusBarNotif, titreNotif,texteNotifNom;


        // Texte dans "status bar", titre et texte de la notification.
        if (nom.size() == 1)
        {
            statusBarNotif = "Nouvelle demande d'ami";
            titreNotif = "Demande d'ami";
            texteNotifNom = nom.get(0) + " vous a envoye une requete d'ami";
        }
        else
        {
            statusBarNotif = "Nouvelles demandes d'amis";
            titreNotif = "Demandes d'amis";
            texteNotifNom = "Vous avez recu (" + nom.size() + ") demandes d'amis";
        }

/*
        // Creation d'un nouvelle notification.
        Notification notif = new Notification(R.drawable.geopeche, statusBarNotif, System.currentTimeMillis());
        // Pour faire disparaitre la notification lorsque l'utilisateur la clique.
        notif.flags |= Notification.FLAG_AUTO_CANCEL;

        // Creation d'une intention de retour lorsqu'on clique sur la notification.
        Intent i = new Intent(this, friendsActivity.class);
        // Ajout d'information dans l'intention. -- Nom du demandeur dans extra
        i.putExtra("message", texteNotifNom);
        i.putExtra("selectedTab", "demandes");
        // Creation d'une nouvelle intention en suspens.
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        // Configuration de la notification.
        notif.setLatestEventInfo(this, titreNotif, texteNotifNom, pi);
        // Envoie de la notification.
        this.notifMgr.notify(ID_ALARM, notif);*/

    }
}
