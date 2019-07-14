package net.ddns.scravlon.splitmefull;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class sadNumber extends AppCompatActivity {

    int numberselected;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sad_number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("5B0595AB6319C0E9F4A2D77D06E2B378").build();
        mAdView.loadAd(adRequest);

        final SeekBar numSeek = (SeekBar) findViewById(R.id.numSeek);
        final EditText split_people = (EditText) findViewById(R.id.split_people);
        final Button bsubmit = (Button) findViewById(R.id.split_submit);

        bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(sadNumber.this, sadActivity.class);
                    Bundle b = new Bundle();
                    if (Integer.valueOf(split_people.getText().toString()) > 35){
                        split_people.setText("35");
                        Toast.makeText(sadNumber.this, "Max number is 14!", Toast.LENGTH_SHORT).show();
                    }
                    b.putString("num", String.valueOf(split_people.getText()));
                    intent.putExtra("numbundle", b);
                    startActivity(intent);
                    //finish();
                }
                catch (NumberFormatException nfe) {
                    split_people.setError("Please Enter amount of people");
                }
            }
        });
            //numSeek
        numSeek.setMax(14);
        numSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                bsubmit.setEnabled(true);
                split_people.setText(String.valueOf(i + 2));
                numberselected = i+2;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.number_menu, menu);
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
            //recreate();
            return true;
        }else if (id == R.id.split_exit){
            AlertDialog.Builder builderexit = new AlertDialog.Builder(sadNumber.this);
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
        }else if (id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
