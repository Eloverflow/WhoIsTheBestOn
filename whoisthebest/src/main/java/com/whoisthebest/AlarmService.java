package com.whoisthebest;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;

/*
 * Service appele lorsque l'alarme est declenchee.
 * Note: Ce type de service simple permet d'executer une tache asynchrone
 * dans la methode "onHandleIntent"; ce qui est tres pratique pour executer
 * une tache lourde, comme le chargement de donnees sur Internet, sans avoir
 * besoin de se soucier de la gestion d'un "Thread".  Le service sera automatiquement
 * arrete apres l'execution de la methode "onHandleIntent".
 */
public class AlarmService extends IntentService {
    private HttpClient m_ClientHttp = new DefaultHttpClient();
    private final String TAG = this.getClass().getSimpleName();
    Exception m_Exp;
    SharedPreferences sharedpreferences;

    // Identifiant unique pour la notification.
    private static final int ID_ALARM = 12345;

    // Gestionnaire de notifications.
    private NotificationManager notifMgr;



    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // On peut effectuer des taches lourdes ici.

        // Incrementation du compteur du nombre de declenchements de l'alarme.
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        //SharedPreferences.Editor editor = prefs.edit();

        this.notifMgr = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        ArrayList<String> arrayDeNotif = GetNotifs();

        if(arrayDeNotif != null && arrayDeNotif.size() > 0)
        {
                gererEnvoyerNotif(arrayDeNotif);
        }




        //Ici on cre nos notifs (une pour chaque notif dans l'arrayDeNotif)
        //Nos notif doive lancer un intent (lesDemandeDami) (Avec comme pref le nom de l'ami)
        //(lesDemandeDami vont aller chercher dans les pref l'array des demande d'ami





        //editor.putInt("arrayDeNotif", "leContenuDesNotifRetourne");
        //editor.commit();
    }

    protected ArrayList<String> GetNotifs(Void... unused) {
        ArrayList<String> listeNotifs = new ArrayList<String>();

        try {
            sharedpreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            if (sharedpreferences.contains("usrKey"))
            {
                /*URI uri = new URI("http", Varr.getService(), "/notifications" + "/" + sharedpreferences.getString("usrKey", ""), null, null);
                HttpGet requeteGet = new HttpGet(uri);

                String body = m_ClientHttp.execute(requeteGet, new BasicResponseHandler());
                JSONArray jsonArray = new JSONArray(body);
                //listeNotifs = JsonParser.deserialiserJsonListeSites(body);

                if(jsonArray != null){
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject json_data = jsonArray.getJSONObject(i);

                        listeNotifs.add(json_data.get("MemNomDemandeur").toString());
                    }
                }*/

            }
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
