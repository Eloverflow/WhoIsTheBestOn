package com.whoisthebest;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import library.DatabaseHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class WhoIsTheBest extends FragmentActivity {

	private PagerAdapter mPagerAdapter;
	ViewPager pager = null;
    static FragmentManager fragManager;
	private int defaultPage = 1;
	private String currentPage = "";
	private Boolean onRestaure = false;
    static HashMap<String,String> user = new HashMap<String, String>();
    Fragment fragment1, fragment2, fragment3, fragment4;
    public static int INTERVAL_ALARM = 30000;

    // Identifiant pour l'intention en suspens de l'alarme.
    public static final int ID_ALARM = 12345;

    // Le gestionnaire d'alarme d'Android.
    private AlarmManager alarmMgr;
    // L'intention lorsque l'alarme se déclenche.
    private Intent alarmIntent;

    private NotificationManager notifMgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        /**
         * Hashmap to load data from the Sqlite database
         **/
        user = db.getUserDetails();

        //Alarme
        // Création de l'intention pour le déclenchement de l'alarme.
        this.alarmIntent = new Intent(this, AlarmReceiver.class);

        // Récupération du gestionnaire d'alarme.
        this.alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        activerAlarme();
        //Alarme


        //Si on vient de d�marer l'applications
		if(savedInstanceState == null){

		    whoIsTheBestFragments();
        }
		//Sinon on resume ou on �tait
		else {
			currentPage = savedInstanceState.getString("currentPage");
			onRestaure = true;
			goBackToGoodPage();
		}
	}

	private void initialisePaging() {

        fragment1 = Fragment.instantiate(this, Fragment1.class.getName());
        fragment2 = Fragment.instantiate(this, Fragment2.class.getName());
        fragment3 = Fragment.instantiate(this, Fragment3.class.getName());
        fragment4 = Fragment.instantiate(this, Fragment4.class.getName());

		//On cr�e un vecteur de fragments et on ajoute nos 3 fragments(Aka Page).
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(fragment1);
		fragments.add(fragment2);
		fragments.add(fragment3);
		fragments.add(fragment4);
		
		//On cr�e notre pageAdapter et pageViwer avec notre vecteur de fragments.
        fragManager = this.getSupportFragmentManager();
        mPagerAdapter = new PagerAdapter(this.getSupportFragmentManager(), fragments);
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setOffscreenPageLimit(3);
		pager.setAdapter(mPagerAdapter);
		
		//Page 2 par d�faut
		pager.setCurrentItem(defaultPage);


        //ViewPager change page event

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                //Log.d("test", "testa");
                //Arrive juste une fois
                //((Fragment1)fragment1).tab1();
                //((Fragment2)fragment2).tab1();
                //((Fragment3)fragment3).tab1();
                //((Fragment4)fragment4).getFragmentManager().executePendingTransactions();


                //On pourrait peut-être vérifier quelle page on est et à quel point il faut modifier quelque chose pour enlever le petit lag graphique
                //Aussi on pourrait comparer le temps en
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((Fragment3)fragment3).tab1();
                            }
                        });
                    }
                }).start();*/
/*
                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction transaction2 = fm2.beginTransaction();
                transaction2.replace(R.id.fragment3Nothing, new Fragment());
                //transaction2.setCustomAnimations(R.anim.fadeout, R.anim.fadeout);
                transaction2.commit();*/
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });


	}

    //Pour lancer notre viewPager (pages principales)
	public void whoIsTheBestFragments(){
		currentPage = "viewPager_layout";
		setContentView(R.layout.viewpager_layout);
		initialisePaging();
	}

	//On affiche notre menu / Va se kill� avec BackButton ou SettingButton
	public void menu(View v){
		Intent myIntent = new Intent(this, OverlayMenu.class);
		this.startActivityForResult(myIntent, 0);
    	overridePendingTransition(R.anim.fadein, R.anim.fadeout);

	}


	@Override
	public void onBackPressed() {
		goBackToGoodPage();
	}

	//Si onRestaure est false c'est que c'est un pr�c�dent, sinon on veut retourner � la page enregistr�
	public void goBackToGoodPage(){

            if(onRestaure)
            whoIsTheBestFragments();
            else {
                if(pager.getCurrentItem() == defaultPage){
                    moveTaskToBack(true);
                }
                else
                    pager.setCurrentItem(1);
            }

		//Si on �t� onRestaure = true on vient juste de restaurer notre page donc onRestaure = false
		if(onRestaure)
		onRestaure = false;
	}


	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	     savedInstanceState.putString("currentPage", currentPage);
	     super.onSaveInstanceState(savedInstanceState);
	}

    /*
         * Permet d'activer l'alarme.
         */
    public void activerAlarme() {

        // Création d'une nouvelle intention en suspens.
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this, ID_ALARM, this.alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        // Activation de l'alarme.
        // Privilégiez l'utilisation de la méthode "setInexactRepeating" au lieu de "setRepeating".
        this.alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME, 0, INTERVAL_ALARM, alarmPendingIntent);

        Toast.makeText(this, "Alarme on bitch", Toast.LENGTH_SHORT).show();

        // Activation du BroadcastReceiver pour le démarrage (boot) de l'appareil (BootReceiver).
        ComponentName receiver = new ComponentName(this, BootReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }


}
