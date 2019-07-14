package net.ddns.scravlon.splitmefull;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity {


    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkFirstRun();


        mAdView = (AdView) findViewById(R.id.adView5);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("5B0595AB6319C0E9F4A2D77D06E2B378").build();
        mAdView.loadAd(adRequest);





        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        //initiate Interface
        Button butsplit = (Button)findViewById(R.id.SplitBut);
        Button butdivide = (Button)findViewById(R.id.DivBut);
        Button butabt = (Button) findViewById(R.id.but_about);
        Button butsad = (Button) findViewById(R.id.but_SaD);

        butsplit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent SplitIntent = new Intent(MainActivity.this, SplitActivity.class);
                                            startActivity(SplitIntent);
                                        }
                                    }
        );


        butdivide.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Intent DivideIntent = new Intent(MainActivity.this, DivideActivity.class);
                                             startActivity(DivideIntent);
                                         }
                                     }
        );

        butabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutintent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutintent);
            }
        });

        butsad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sadint = new Intent(MainActivity.this, sadNumber.class);
                startActivity(sadint);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent aboutintent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(aboutintent);

        }else if (id == R.id.split_exit){
            AlertDialog.Builder builderexit = new AlertDialog.Builder(MainActivity.this);
            builderexit.setMessage("Do you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                            System.exit(1);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builderexit.show();
        } else if (id == R.id.tutorial){
            Intent intfirst = new Intent(MainActivity.this, FIrstActivity.class);
            startActivity(intfirst);
        } else if (id == R.id.action_settings){
            Intent intentsetting = new Intent(MainActivity.this, settingPage.class);
            startActivity(intentsetting);

        }

        return super.onOptionsItemSelected(item);
    }
    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {
            Intent intfirst = new Intent(MainActivity.this, FIrstActivity.class);
            startActivity(intfirst);
            // TODO This is a new install (or the user cleared the shared preferences)

        } else if (currentVersionCode > savedVersionCode) {
            Intent intfirst = new Intent(MainActivity.this, FIrstActivity.class);
            startActivity(intfirst);
            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

}
