package net.ddns.scravlon.splitmefull;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button emailb = (Button)findViewById(R.id.email_but);
        Button suggest = (Button) findViewById(R.id.suggest);
        final String androidOS = Build.VERSION.RELEASE;

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String[] TO = {"haokokyong10@gmail.com"};
                //String[] CC = {"xyz@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("sms:"));
                emailIntent.setType("text/plain");
                //emailIntent.setData(Uri.parse("mailto:"));
                //emailIntent.setType("message/rfc822");


                //emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                //emailIntent.putExtra(Intent.EXTRA_CC, CC);
                //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reports of SplitMe V 1.0.0");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Try out this application SplitMe: https://play.google.com/store/apps/details?id=net.ddns.scravlon.splitmefull" );

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    //finish();
                    Log.i("Finished", "Suggest");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(AboutActivity.this,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        emailb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Send email", "");

                String[] TO = {"haokokyong10@gmail.com"};
                //String[] CC = {"xyz@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                emailIntent.setData(Uri.parse("mailto:"));
                //emailIntent.setType("message/rfc822");


                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                //emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reports of SplitMe V 1.0.0");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Android Version: " + androidOS + "\n Model: " + Build.MODEL + "\n Issue: ");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    //finish();
                    Log.i("Finished sending email", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(AboutActivity.this,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
