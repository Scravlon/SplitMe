package net.ddns.scravlon.splitmefull;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class DivideActivity extends AppCompatActivity {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("5B0595AB6319C0E9F4A2D77D06E2B378").build();
        mAdView.loadAd(adRequest);

        //initiate UI
        final EditText div_numberpeople = (EditText) findViewById(R.id.divpeople_editText);
        final EditText div_amount = (EditText) findViewById(R.id.divamount_editText);
        final TextView div_divresult = (TextView) findViewById(R.id.div_result);
        final SeekBar numberseek = (SeekBar) findViewById(R.id.div_seek);
        Button dividebut = (Button) findViewById(R.id.button_divide);
        Button divclear = (Button) findViewById(R.id.div_clear);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double amount = Double.parseDouble(div_amount.getText().toString());
                    double people = Integer.parseInt(div_numberpeople.getText().toString());
                    double total = Math.round(amount / people * 100) / 100.00;
                    div_divresult.setText("RM " + String.valueOf(total) + " / person");
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    Snackbar.make(view, "Divided", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Please fill in both number and amount!",
                            Toast.LENGTH_LONG).show();
                    Snackbar.make(view, "Please fill in both number and amount!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });





        numberseek.setMax(33);
        numberseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                div_numberpeople.setText(String.valueOf(i + 2));

                //hide keyboard
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        divclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                div_numberpeople.setText("");
                div_amount.setText("");
                div_divresult.setText("");
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                numberseek.setProgress(0);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        dividebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    double amount = Double.parseDouble(div_amount.getText().toString());
                    double people = Integer.parseInt(div_numberpeople.getText().toString());
                    double total = Math.round(amount / people * 100) / 100.00;
                    div_divresult.setText("RM " + String.valueOf(total) + " / person");
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Please fill in both number and amount!",
                            Toast.LENGTH_LONG).show();
                    div_amount.setError("Please Enter the amount!");
                }
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
            //recreate();
            return true;
        } else if (id == R.id.split_exit){
            AlertDialog.Builder builderexit = new AlertDialog.Builder(DivideActivity.this);
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
        } else if (id == android.R.id.home){
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }



}