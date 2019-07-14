package net.ddns.scravlon.splitmefull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class introActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_intro);
        InterstitialAd mInterstitialAd;

        mInterstitialAd = new InterstitialAd(introActivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1045433702910438/6014047500");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("5B0595AB6319C0E9F4A2D77D06E2B378").build();
        mInterstitialAd.loadAd(adRequest);
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Begin Game, continue with app
        }


        /*final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        } else{
            Log.d("Comments", "Not");
        } */
        //CheckAndInitAppFirstTime();

        Thread myThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        myThread.start();
    }
    public void CheckAndInitAppFirstTime() {
        final String PREFS_NAME = "TheAppVer";
        final String CHECK_VERSION = "1"; //Required ver...
        final String KEY_NAME = "CheckVersion";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (!settings.getString(KEY_NAME, "0").equals(CHECK_VERSION)) { //the app is being launched for first time, do something or CHECK_VERSION is different //...
             settings.edit().putString(KEY_NAME, CHECK_VERSION).commit(); }
    }
}
