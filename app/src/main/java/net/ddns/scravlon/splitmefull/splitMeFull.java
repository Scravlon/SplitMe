package net.ddns.scravlon.splitmefull;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static net.ddns.scravlon.splitmefull.R.id.split_discount;



public class splitMeFull extends AppCompatActivity {
    double defsercharge = 1.1;
    double discount = 1;
    int discountori = 0;
    int roundnumber = 10;
    double rounddecimal = 10.0;
    String withzero = "";
    private AdView mAdView;
    String color[] = {"FF0000", "FF4000", "FFFF00", "BFFF00", "40ff00", "00FFFF", "00BFFF",
            "4000FF", "8000FF", "FF0080", "000000", "AAAAAA", "FFFFFF", "996633", "663300", "003300"};




    InterstitialAd mInterstitialAd;
    //1-35 editText
    //+36-70 editText 2
    //+71-105 editText3
    //+106-140 editText4
    //+141-175 Buttons
    //+176-210 delBut
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_me_full);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean alwaysround = sharedPreferences.getBoolean("ROUNDOFF", false);

        if (alwaysround){
            roundnumber = 10;
            rounddecimal = 10.0;
            withzero = "0";
        } else {
            roundnumber = 100;
            rounddecimal = 100.00;
            withzero = "";
        }

        Log.d("RoundNo", String.valueOf(roundnumber));
        Log.d("Boolean", String.valueOf(alwaysround));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("numbundle");
        String numberofp = bundle.getString("num");
        int numberofpeople = Integer.valueOf(numberofp);



        final LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        final LinearLayout llbut = (LinearLayout) findViewById(R.id.llbuttons);
        final LinearLayout ll3 = (LinearLayout) findViewById(R.id.llresult);
        final RelativeLayout rldum = (RelativeLayout) findViewById(R.id.rldummy);
        final RelativeLayout rellalllay = (RelativeLayout) findViewById(R.id.rellalllay);
        final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hori);

        final List<EditText> alledt = new ArrayList<EditText>();
        final List<EditText> alledtaf = new ArrayList<EditText>();
        final List<EditText> alledtaf2 = new ArrayList<EditText>();
        final List<EditText> alledtaf3 = new ArrayList<EditText>();

        final Button btn_split = (Button) findViewById(R.id.split_split);
        final CheckBox check_gst = (CheckBox) findViewById(R.id.check_gst);
        final CheckBox check_service = (CheckBox) findViewById(R.id.check_serv);
        final TextView split_text = (TextView) findViewById(R.id.split_total);
        EditText edt;
        ImageView insertbut;
        ImageView deletebut;
//        Button insertbut;

        //clear everything
        Button btn_clear = (Button) findViewById(R.id.split_clear);
        final int[] thisIsAnIntArray = new int[numberofpeople];

        final int[] consid = {0};
        final int[] consid2 = {0};
        final int[] consid3 = {0};


        for (int ifj = 1; ifj <= numberofpeople; ifj++) {
            TextView txt = new TextView(splitMeFull.this);
            edt = new EditText(splitMeFull.this);
            insertbut = new ImageView(splitMeFull.this);
            deletebut = new ImageView(splitMeFull.this);



            thisIsAnIntArray[ifj-1] = 0;

            alledt.add(edt);
            edt.setId(ifj);
            edt.setHint("Item 1");
            insertbut.setId(ifj + 140);
            deletebut.setId(ifj + 175);
            txt.setTextSize(19);
            edt.setTextSize(18);
            deletebut.setVisibility(View.INVISIBLE);
            edt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())
            );
            //params.setMargins(1, 2, 1, 0);
            txt.setLayoutParams(params);

            //original edit text
           RelativeLayout.LayoutParams paredt = new RelativeLayout.LayoutParams(
                   (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics()),
                   (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
           ));

            if (ifj != 1){
                paredt.addRule(RelativeLayout.BELOW, edt.getId()-1);
            }

            /* insertbut.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()),
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())));
               */
            final RelativeLayout.LayoutParams paredtbut = new RelativeLayout.LayoutParams(
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()),
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
                    ));


            final RelativeLayout.LayoutParams deletepabut = new RelativeLayout.LayoutParams(
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()),
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
                    ));
            deletepabut.addRule(RelativeLayout.RIGHT_OF, insertbut.getId());
            deletepabut.addRule(RelativeLayout.BELOW, insertbut.getId()-141);
            //deletepabut.addRule(RelativeLayout.RIGHT_OF, insertbut.getId()-35);
            paredtbut.addRule(RelativeLayout.RIGHT_OF, edt.getId());
            if (ifj != 1){
                paredtbut.addRule(RelativeLayout.BELOW, edt.getId()-1);
            }
            deletebut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView imgleft = (ImageView) findViewById(v.getId()-35);


                    if (thisIsAnIntArray[v.getId()-176] == 1){
                        thisIsAnIntArray[v.getId()-176] = 0;
                        EditText todelete = (EditText) findViewById(v.getId()-140);
                        EditText tofocus = (EditText) findViewById(v.getId()-175);

                        v.setVisibility(View.INVISIBLE);
                        alledtaf.remove(todelete);
                        rldum.removeView(todelete);
                        rldum.removeView(imgleft);
                        rldum.addView(imgleft, paredtbut);
                        tofocus.requestFocus();
                    } else if (thisIsAnIntArray[v.getId()-176] == 2){
                        thisIsAnIntArray[v.getId()-176] = 1;
                        EditText todelete = (EditText) findViewById(v.getId()-105);
                        EditText tofocus = (EditText) findViewById(v.getId()-140);


                        RelativeLayout.LayoutParams paredtbuttwo = new RelativeLayout.LayoutParams(
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()),
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
                                ));

                        paredtbuttwo.addRule(RelativeLayout.RIGHT_OF, v.getId()-140);
                        // if (edtaf.getId() - 35 != 1){
                        paredtbuttwo.addRule(RelativeLayout.BELOW, v.getId()-176);
                        //imgleft.setVisibility(View.VISIBLE);
                        alledtaf2.remove(todelete);
                        rldum.removeView(todelete);
                        rldum.removeView(imgleft);
                        rldum.addView(imgleft, paredtbuttwo);
                        tofocus.requestFocus();
                    } else if (thisIsAnIntArray[v.getId() - 176] == 3){
                        thisIsAnIntArray[v.getId()-176] = 2;
                        EditText todelete = (EditText) findViewById(v.getId()-70);
                        EditText tofocus = (EditText) findViewById(v.getId()-105);


                        RelativeLayout.LayoutParams paredtbutthree = new RelativeLayout.LayoutParams(
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()),
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
                                ));

                        paredtbutthree.addRule(RelativeLayout.RIGHT_OF, v.getId()-105);

                        // if (edtaf.getId() - 35 != 1){
                        paredtbutthree.addRule(RelativeLayout.BELOW, v.getId()-176);
                        imgleft.setVisibility(View.VISIBLE);
                        alledtaf3.remove(todelete);
                        rldum.removeView(v);
                        rldum.removeView(todelete);
                        rldum.removeView(imgleft);
                        rldum.addView(v, deletepabut);
                        rldum.addView(imgleft, paredtbutthree);
                        tofocus.requestFocus();
                    }
                }
            });
            final ImageView finalDeletebut = deletebut;
            insertbut.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onClick(View v) {
                    EditText edtaf = new EditText(splitMeFull.this);
                    EditText edtaf2 = new EditText(splitMeFull.this);
                    EditText edtaf3 = new EditText(splitMeFull.this);

                    edtaf.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    edtaf2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    edtaf3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


                    RelativeLayout.LayoutParams paredt = new RelativeLayout.LayoutParams(
                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics()),
                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
                            ));

                    if (thisIsAnIntArray[v.getId()-141] == 0){
                        thisIsAnIntArray[v.getId()-141] = 1;
                        finalDeletebut.setVisibility(View.VISIBLE);

                        edtaf.setId(v.getId()-105);
                        edtaf.setHint("Item 2");
                        paredt.addRule(RelativeLayout.RIGHT_OF, v.getId() - 140);
                        //paredt.addRule(RelativeLayout.LEFT_OF, v.getId());
                        paredt.addRule(RelativeLayout.BELOW, v.getId() - 141);
                        RelativeLayout.LayoutParams paredtbutin = new RelativeLayout.LayoutParams(
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()),
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
                                ));

                        //set location OF BUT
                        paredtbutin.addRule(RelativeLayout.RIGHT_OF, edtaf.getId());
                       // if (edtaf.getId() - 35 != 1){
                            paredtbutin.addRule(RelativeLayout.BELOW, edtaf.getId()-36);
                       // }

                        rldum.removeView(v);
                        rldum.addView(v, paredtbutin);

                        alledtaf.add(edtaf);
                        rldum.addView(edtaf, paredt);
                        edtaf.requestFocus();


                    } else if (thisIsAnIntArray[v.getId()-141] == 1){
                        thisIsAnIntArray[v.getId()-141] = 2;
                        edtaf2.setId(v.getId()-70);
                        edtaf2.setHint("Item 3");

                        paredt.addRule(RelativeLayout.RIGHT_OF, v.getId() - 105);
                        //paredt.addRule(RelativeLayout.LEFT_OF, v.getId());
                        paredt.addRule(RelativeLayout.BELOW, v.getId() - 141);
                        alledtaf2.add(edtaf2);
                        rldum.addView(edtaf2, paredt);
                        RelativeLayout.LayoutParams pareddeletein2 = new RelativeLayout.LayoutParams(
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()),
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
                                ));
                        pareddeletein2.addRule(RelativeLayout.RIGHT_OF, edtaf2.getId());
                        pareddeletein2.addRule(RelativeLayout.BELOW, v.getId()-141);
                        //pareddeletein2.addRule(RelativeLayout.BELOW, edtaf2.getId()-71);

                        rldum.removeView(v);

                        rldum.addView(v, pareddeletein2);
                        edtaf2.requestFocus();
                    } else if (thisIsAnIntArray[v.getId()-141] == 2){
                        ImageView imgremove = (ImageView) findViewById(v.getId()+35);
                        thisIsAnIntArray[v.getId()-141] = 3;
                        edtaf3.setId(v.getId()-35);
                        edtaf3.setHint("Item 4");

                        paredt.addRule(RelativeLayout.RIGHT_OF, v.getId() - 70);
                        paredt.addRule(RelativeLayout.BELOW, v.getId() - 141);
                        alledtaf3.add(edtaf3);
                        v.setVisibility(View.INVISIBLE);

                        rldum.addView(edtaf3, paredt);
                        RelativeLayout.LayoutParams pareddeletein3= new RelativeLayout.LayoutParams(
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()),
                                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()
                                ));
                        pareddeletein3.addRule(RelativeLayout.RIGHT_OF, edtaf3.getId());
                        pareddeletein3.addRule(RelativeLayout.BELOW, v.getId()-141);

                        rldum.removeView(imgremove);
                        rldum.addView(imgremove, pareddeletein3);
                        rldum.removeView(v);

                        rldum.addView(v, pareddeletein3);
                        edtaf3.requestFocus();
                    }
                    //paredt.addRule(RelativeLayout.RIGHT_OF, edtaf.findViewById(v.getId()).generateViewId());
                    //tx.setText("Button " + edtaf.findViewById(v.getId()).generateViewId());
                    Log.d("Vid", String.valueOf(v.getId() - 106));
                    //test debug edtaf.setText("gaga " + edtaf.getId());
                    //llbut.removeView(v);



                }
            });
            insertbut.setImageResource(R.drawable.plus);
            deletebut.setImageResource(R.drawable.minus);
            //insertbut.setScaleType(ImageView.ScaleType.FIT_START);
            //insertbut.setAdjustViewBounds(true);
            txt.setTypeface(null, Typeface.BOLD);
            txt.setText("P" + ifj + ": RM");
            txt.setTextColor(Color.parseColor("#" + color[ifj]));
            //ll.setGravity(Gravity.LEFT);
            ll.addView(txt);
            //ll2.addView(edt);
            rldum.addView(edt, paredt);
            rldum.addView(insertbut, paredtbut);
            rldum.addView(deletebut, deletepabut);
            //llbut.addView(insertbut);
        }


        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        final int[] gstcheck = {0};
        final int[] sercheck = {0};

        btn_split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_gst.isChecked()){
                    gstcheck[0] = 1;
                } else {
                    gstcheck[0] = 0;
                }
                if (check_service.isChecked()){
                    sercheck[0] = 1;
                } else{
                    sercheck[0] = 0;
                }

                final AlertDialog alertDialog = new AlertDialog.Builder(splitMeFull.this).create();
                alertDialog.setTitle("SplitMe");
                View mView = getLayoutInflater().inflate(R.layout.confirm_dialog, null);

                final CheckBox sercheckbox = (CheckBox) mView.findViewById(R.id.checkBox_Ser);
                final CheckBox gstcheckbox =  (CheckBox) mView.findViewById(R.id.checkBox_GST);
                final TextView distext = (TextView) mView.findViewById(R.id.textView_discount);
                Button but_cancel = (Button) mView.findViewById(R.id.but_cancel);
                Button but_split = (Button) mView.findViewById(R.id.but_Split);

                distext.setText("Discount: " + discountori +"%");
                if (gstcheck[0] == 1){
                    gstcheckbox.setChecked(true);
                } else {
                    gstcheckbox.setChecked(false);
                }
                if (sercheck[0] == 1){
                    sercheckbox.setChecked(true);
                } else {
                    sercheckbox.setChecked(false);
                }

                but_cancel.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                but_split.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if(gstcheckbox.isChecked()){
                                check_gst.setChecked(true);
                            } else{
                                check_gst.setChecked(false);
                            }
                            if(sercheckbox.isChecked()){
                                check_service.setChecked(true);
                            }else{
                                check_service.setChecked(false);
                            }

                            alertDialog.dismiss();

                            //ll2.findViewById(1);
                            String[] strings = new String[(alledt.size())];
                            //String[] etstrings = new String[alledtaf.size()];
                            String EditTextChecker = "True";
                            String casecheck = "N";
                            float splitresult;
                            float edittextnumber = 0;
                            float totalamount = 0;

                            //Test if edittext empty
                            for (int i = 0; i < alledt.size(); i++) {
                                strings[i] = alledt.get(i).getText().toString();
                                if (strings[i].isEmpty()) {
                                    EditTextChecker = "false";
                                }
                            }
                            for (int i = 0; i < alledtaf.size(); i++) {
                                strings[i] = alledtaf.get(i).getText().toString();
                                if (strings[i].isEmpty()) {
                                    EditTextChecker = "false";
                                }
                            }

                            for (int i = 0; i < alledtaf2.size(); i++) {
                                strings[i] = alledtaf2.get(i).getText().toString();
                                if (strings[i].isEmpty()) {
                                    EditTextChecker = "false";
                                }
                            }

                            for (int i = 0; i < alledtaf3.size(); i++) {
                                strings[i] = alledtaf3.get(i).getText().toString();
                                if (strings[i].isEmpty()) {
                                    EditTextChecker = "false";
                                }
                            }


                            if(check_service.isChecked()){
                                casecheck = "A";
                                if(check_gst.isChecked()){
                                    casecheck = "B";
                                }

                            } else {
                                if (check_gst.isChecked()) {
                                    casecheck = "C";
                                }

                                Log.d("Type", casecheck);
                            }

                            //A = service charge only
                            //B = Service charge + GST
                            //C = GST only
                            //Default = no tax

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())
                            );
                            params.setMargins(1, 3, 1, 0);

                            //Change if to above switch

                            switch (casecheck) {
                                case "A":
                                    if (EditTextChecker == "false") {
                                        Log.d("Status", "Fail");
                                        Toast.makeText(splitMeFull.this, "Please Enter all amount!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        for (int i = 0; i < alledt.size(); i++) {

                                            strings[i] = alledt.get(i).getText().toString();
                                            if (thisIsAnIntArray[i] == 1) {
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString());
                                                consid[0] = consid[0]+ 1;
                                            } else if (thisIsAnIntArray[i] == 2){
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString())
                                                        + Float.valueOf(alledtaf2.get(consid2[0]).getText().toString());
                                                consid[0] = consid[0] + 1;
                                                consid2[0] = consid2[0]+ 1;
                                            } else if (thisIsAnIntArray[i] == 3) {
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString())
                                                        + Float.valueOf(alledtaf2.get(consid2[0]).getText().toString())
                                                        + Float.valueOf(alledtaf3.get(consid3[0]).getText().toString());
                                                consid[0] = consid[0] + 1;
                                                consid2[0] = consid2[0]+ 1;
                                                consid3[0] = consid3[0]+ 1;

                                            } else if (thisIsAnIntArray[i] == 0){
                                                edittextnumber = Float.valueOf(strings[i]);
                                            }
                                            Log.d("Emply", strings[i]);
                                            TextView result = new TextView(splitMeFull.this);
                                            result.setTextSize(18);
                                            //result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                            result.setLayoutParams(params);

                                            splitresult = (float) (edittextnumber * discount * defsercharge);
                                            float splitfinal = roundOff(splitresult);
                                            totalamount += splitfinal;
                                            result.setText(" " + splitfinal + withzero);

                                            ll3.addView(result);
                                            btn_split.setEnabled(false);
                                        }
                                        Toast.makeText(splitMeFull.this, "Split!", Toast.LENGTH_SHORT).show();
                                        fab.hide();
                                        rellalllay.removeView(llbut);
                                        rellalllay.removeView(hsv);

                                    }
                                    break;
                                case "B":
                                    if (EditTextChecker == "false") {
                                        Log.d("Status", "Fail");
                                        Toast.makeText(splitMeFull.this, "Please Enter all amount!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        for (int i = 0; i < alledt.size(); i++) {
                                            strings[i] = alledt.get(i).getText().toString();
                                            if (thisIsAnIntArray[i] == 1) {
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString());
                                                consid[0] = consid[0]+ 1;
                                            } else if (thisIsAnIntArray[i] == 2){
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString())
                                                        + Float.valueOf(alledtaf2.get(consid2[0]).getText().toString());
                                                consid[0] = consid[0] + 1;
                                                consid2[0] = consid2[0]+ 1;
                                            }
                                            else if (thisIsAnIntArray[i] == 0){
                                                edittextnumber = Float.valueOf(strings[i]);
                                            } else if (thisIsAnIntArray[i] == 3){
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString())
                                                        + Float.valueOf(alledtaf2.get(consid2[0]).getText().toString())
                                                + Float.valueOf(alledtaf3.get(consid3[0]).getText().toString());
                                                consid[0] = consid[0] + 1;
                                                consid2[0] = consid2[0]+ 1;
                                                consid3[0] = consid3[0]+ 1;
                                            }
                                            Log.d("Emply", strings[i]);
                                            TextView result = new TextView(splitMeFull.this);
                                            result.setTextSize(18);
                                            //result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                            result.setLayoutParams(params);
                                            //change this

                                            splitresult = (float) ((edittextnumber * discount * defsercharge) * 1.06);
                                            float splitfinal = roundOff(splitresult);
                                            totalamount += splitfinal;
                                            result.setText(" " + splitfinal + withzero);
                                            ll3.addView(result);
                                            btn_split.setEnabled(false);
                                        }
                                        Toast.makeText(splitMeFull.this, "Split!", Toast.LENGTH_SHORT).show();
                                        fab.hide();
                                        rellalllay.removeView(llbut);
                                        rellalllay.removeView(hsv);

                                    }
                                    break;
                                case "C":
                                    if (EditTextChecker == "false") {
                                        Log.d("Status", "Fail");
                                        Toast.makeText(splitMeFull.this, "Please Enter all amount!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        for (int i = 0; i < alledt.size(); i++) {
                                            strings[i] = alledt.get(i).getText().toString();
                                            if (thisIsAnIntArray[i] == 1) {
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString());
                                                consid[0] = consid[0]+ 1;
                                            } else if(thisIsAnIntArray[i] == 2){
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString()) +
                                                        Float.valueOf(alledtaf2.get(consid2[0]).getText().toString());
                                                consid[0] = consid[0] + 1;
                                                consid2[0] = consid2[0] + 1;
                                            } else if (thisIsAnIntArray[i] == 0){
                                                edittextnumber = Float.valueOf(strings[i]);

                                            } else if (thisIsAnIntArray[i] == 3){
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString()) +
                                                        Float.valueOf(alledtaf2.get(consid2[0]).getText().toString())
                                                + Float.valueOf(alledtaf3.get(consid3[0]).getText().toString());
                                                consid[0] = consid[0] + 1;
                                                consid2[0] = consid2[0] + 1;
                                                consid3[0] = consid3[0] + 1;
                                            }
                                            Log.d("Emply", strings[i]);
                                            TextView result = new TextView(splitMeFull.this);
                                            result.setTextSize(18);
                                            //result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                            result.setLayoutParams(params);
                                            //change this

                                            splitresult = (float) (edittextnumber * discount * 1.06);
                                            float splitfinal = roundOff(splitresult);
                                            totalamount += splitfinal;
                                            result.setText(" " + splitfinal + withzero);
                                            ll3.addView(result);
                                            btn_split.setEnabled(false);
                                        }
                                        Toast.makeText(splitMeFull.this, "Split!", Toast.LENGTH_SHORT).show();
                                        fab.hide();
                                        rellalllay.removeView(llbut);
                                        rellalllay.removeView(hsv);

                                    }
                                    break;
                                default:
                                    if (EditTextChecker == "false") {
                                        Log.d("Status", "Fail");
                                        Toast.makeText(splitMeFull.this, "Please Enter all amount!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        for (int i = 0; i < alledt.size(); i++) {
                                            strings[i] = alledt.get(i).getText().toString();

                                            if (thisIsAnIntArray[i] == 1) {
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString());
                                                consid[0] = consid[0]+ 1;
                                            } else if (thisIsAnIntArray[i] == 2){
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString()) +
                                                        Float.valueOf(alledtaf2.get(consid2[0]).getText().toString());
                                                consid[0] = consid[0] + 1;
                                                consid2[0] = consid2[0] + 1;

                                            } else if(thisIsAnIntArray[i] == 0){
                                                edittextnumber = Float.valueOf(strings[i]);

                                            } else if(thisIsAnIntArray[i] == 3){
                                                edittextnumber = Float.valueOf(strings[i]) + Float.valueOf(alledtaf.get(consid[0]).getText().toString()) +
                                                        Float.valueOf(alledtaf2.get(consid2[0]).getText().toString())
                                                + Float.valueOf(alledtaf3.get(consid3[0]).getText().toString());
                                                consid[0] = consid[0] + 1;
                                                consid2[0] = consid2[0] + 1;
                                                consid3[0] = consid3[0] + 1;

                                            }

                                                    splitresult = (float) (edittextnumber * discount);

                                            //Log.d("Emply", strings[i]);
                                            TextView result = new TextView(splitMeFull.this);
                                            result.setTextSize(18);
                                            //result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                            result.setLayoutParams(params);
                                            //change this
                                            float splitfinal = roundOff(splitresult);
                                            totalamount += splitfinal;
                                            result.setText(" " + splitfinal + withzero);
                                            
                                            ll3.addView(result);
                                            btn_split.setEnabled(false);
                                        }
                                        Toast.makeText(splitMeFull.this, "Split!", Toast.LENGTH_SHORT).show();
                                        fab.hide();
                                        rellalllay.removeView(llbut);
                                        rellalllay.removeView(hsv);

                                    }
                                    break;
                            }



                         //   InputMethodManager inputManager = (InputMethodManager)
                            // getSystemService(Context.INPUT_METHOD_SERVICE);
                         // inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                         //InputMethodManager.HIDE_NOT_ALWAYS);
                            mAdView = (AdView) findViewById(R.id.adView2);
                            AdRequest adRequest = new AdRequest.Builder().addTestDevice("5B0595AB6319C0E9F4A2D77D06E2B378").build();
                            mAdView.loadAd(adRequest);
                            split_text.setText("RM " + Math.round(totalamount * 100) / 100.00 + withzero);

                            //tx.setText(alledtaf.get(1).getText().toString() + " Size");


                        }
                        catch (NumberFormatException nfe) {
                           Log.d("Status", "FAILED");
                        }
                    }

                });


                alertDialog.setView(mView);
                alertDialog.show();
            }
        });

    }

    public float roundOff(float d){
        float d2 = (float) (Math.round(d * roundnumber) / rounddecimal);
        return d2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.split_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        final CheckBox check_service = (CheckBox) findViewById(R.id.check_serv);

        //noinspection SimplifiableIfStatement
        if (id == R.id.split_servicechargep) {
            final AlertDialog alertDialog = new AlertDialog.Builder(splitMeFull.this).create();
            alertDialog.setTitle("Service Charge%");
            View mView = getLayoutInflater().inflate(R.layout.service_dialog, null);
            final EditText etservice = (EditText) mView.findViewById(R.id.et_service);
            Button but_cancel = (Button) mView.findViewById(R.id.button_cancel);
            Button but_ok = (Button) mView.findViewById(R.id.button_enter);

            etservice.requestFocus();

            but_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            but_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        defsercharge = Double.parseDouble(etservice.getText().toString()) / 100 + 1;
                        Toast.makeText(splitMeFull.this, "Service Charge updated!", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                        check_service.setText("Service " + etservice.getText().toString() + "%");

                    }
                    catch (NumberFormatException nfe) {
                        etservice.setError("Please enter the right amount");
                    }
                }

            });


            alertDialog.setView(mView);
            alertDialog.show();
            return true;
        } else if (id == split_discount){

            final AlertDialog alertDialog = new AlertDialog.Builder(splitMeFull.this).create();
            alertDialog.setTitle("Discount%");
            View mView = getLayoutInflater().inflate(R.layout.service_dialog, null);
            final EditText etservice = (EditText) mView.findViewById(R.id.et_service);
            TextView dialogtext = (TextView) mView.findViewById(R.id.dialogtext);
            Button but_cancel = (Button) mView.findViewById(R.id.button_cancel);
            Button but_ok = (Button) mView.findViewById(R.id.button_enter);

            dialogtext.setText("Enter the discount amount!");
            etservice.setHint("Default no discount");

            etservice.requestFocus();

            but_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            but_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        discount = 1 - Double.parseDouble(etservice.getText().toString()) / 100;
                        Toast.makeText(splitMeFull.this, "Discount " + etservice.getText().toString() + "%", Toast.LENGTH_LONG).show();
                        discountori = Integer.valueOf(etservice.getText().toString());

                        alertDialog.dismiss();


                    }
                    catch (NumberFormatException nfe) {
                        etservice.setError("Please enter the right amount");
                    }
                }

            });


            alertDialog.setView(mView);
            alertDialog.show();
            return true;
        } else if (id == R.id.split_exit){
            AlertDialog.Builder builderexit = new AlertDialog.Builder(splitMeFull.this);
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
