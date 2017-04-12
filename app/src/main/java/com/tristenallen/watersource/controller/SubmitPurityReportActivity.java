package com.tristenallen.watersource.controller;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.database.MyDatabase;
import com.tristenallen.watersource.model.ReportHelper;
import com.tristenallen.watersource.model.AuthHelper;
import com.tristenallen.watersource.model.DataSource;
import com.tristenallen.watersource.model.WaterPurity;

import java.util.Locale;

/**
 * Created by David on 3/14/17.
 * Activity for submitting a new purity report.
 */

@SuppressWarnings("ChainedMethodCall") // all chained calls are due to android
public class SubmitPurityReportActivity extends AppCompatActivity {
    private EditText latField;
    private EditText lngField;
    private EditText virusPPM;
    private EditText contaminantPPM;

    private Spinner waterConditionSpinner;

    private double latDouble;
    private double lngDouble;
    private int virusPPMInt;
    private int contaminantPPMInt;

    private final Location h20Loc = new Location("Water Purity Report Location");
    private final ReportHelper reportHelper = ReportHelper.getInstance();
    private final AuthHelper authHelper = AuthHelper.getInstance();
    private LatLng latLng;

    private boolean badLat;
    private boolean badLng;
    private boolean badVirusPPM;
    private boolean badContaminantPPM;

    private static final double LAT_MAX = 90;
    private static final double LAT_MIN = -90;
    private static final double LONG_MAX = 180;
    private static final double LONG_MIN = -180;

    private DataSource data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new MyDatabase(this);

        setContentView(R.layout.activity_purityreport);
        //init the vars
        latField = (EditText) findViewById(R.id.latitudeTXT);
        lngField = (EditText) findViewById(R.id.longitudeTXT);
        virusPPM = (EditText) findViewById(R.id.virusPPM);
        contaminantPPM = (EditText) findViewById(R.id.contaminantPPM);

        waterConditionSpinner = (Spinner) findViewById(R.id.waterCondition);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button reportSourceButton = (Button) findViewById(R.id.sourceReportButton);

        //populate spinners
        waterConditionSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterPurity.values()));

        // if latLng of a newly added marker is passed in, set latLng to it.
        if (getIntent().hasExtra(MainActivity.ARG_latLng)) {
            latLng = getIntent().getParcelableExtra(MainActivity.ARG_latLng);
            latField.setText(String.format(Locale.US, "%f",latLng.latitude));
            lngField.setText(String.format(Locale.US, "%f",latLng.longitude));
        }

        if (getIntent().hasExtra("location")) {
            double[] arr = getIntent().getDoubleArrayExtra("location");
            latField.setText(String.format(Locale.US, "%f",arr[0]));
            lngField.setText(String.format(Locale.US, "%f",arr[1]));
        }



        reportSourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSubmitSourceActivity = new Intent(getApplicationContext()
                        , SubmitH20SourceReportActivity.class);
                goToSubmitSourceActivity.putExtra(MainActivity.ARG_latLng,latLng);
                startActivity(goToSubmitSourceActivity);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings({"OverlyLongMethod", "OverlyComplexMethod"})
            @Override
            public void onClick(View v) {
                //------------------get all the data out------------------------
                WaterPurity waterPurityData = (WaterPurity) waterConditionSpinner.getSelectedItem();
                try {
                    badLat = false;
                    latDouble = Double.parseDouble(latField.getText().toString());
                }
                catch (NumberFormatException n) {
                    badLat = true;
                }
                try {
                    badLng = false;
                    lngDouble = Double.parseDouble(lngField.getText().toString());
                }
                catch (NumberFormatException n) {
                    badLng = true;
                }
                try {
                    badVirusPPM = false;
                    virusPPMInt = Integer.parseInt(virusPPM.getText().toString());
                }
                catch (NumberFormatException n) {
                    badVirusPPM = true;
                }
                try {
                    badContaminantPPM = false;
                    contaminantPPMInt = Integer.parseInt(contaminantPPM.getText().toString());
                }
                catch (NumberFormatException n) {
                    badContaminantPPM = true;
                }
                //--------------------------------------------------------------

                //--------------additional checks on lat and long---------------
                if (badLng && badLat) {
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter valid latitude/longitude values!";
                    int duration = Toast.LENGTH_LONG;
                    lngField.setText("");
                    latField.setText("");
                    Toast badLng = Toast.makeText(context, error, duration);
                    badLng.show();

                } else if ((latDouble > LAT_MAX) || (latDouble < LAT_MIN) || badLat) {
                    //throw a fit!
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter a number between +/- 90!";
                    int duration = Toast.LENGTH_LONG;
                    latField.setText("");
                    Toast badLatitude = Toast.makeText(context, error, duration);
                    badLatitude.show();
                } else if ((lngDouble > LONG_MAX) || (lngDouble < LONG_MIN) || badLng) {
                    //throw a fit!
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter a number between +/- 180!";
                    int duration = Toast.LENGTH_LONG;
                    lngField.setText("");
                    Toast badLng = Toast.makeText(context, error, duration);
                    badLng.show();
                } else if (badVirusPPM) {
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter valid virus PPM (integer)!";
                    int duration = Toast.LENGTH_LONG;
                    virusPPM.setText("");
                    Toast badLng = Toast.makeText(context, error, duration);
                    badLng.show();
                } else if (badContaminantPPM) {
                    Context context = getApplicationContext();
                    CharSequence error = "Please enter valid comtaminant PPM (integer)!";
                    int duration = Toast.LENGTH_LONG;
                    contaminantPPM.setText("");
                    Toast badLng = Toast.makeText(context, error, duration);
                    badLng.show();
                } else {
                    h20Loc.setLatitude(latDouble);
                    h20Loc.setLongitude(lngDouble);
                    reportHelper.addPurityReport(authHelper.getCurrentUserID(), h20Loc, waterPurityData
                            , virusPPMInt,contaminantPPMInt, data, SubmitPurityReportActivity.this);
                    Context context = getApplicationContext();
                    CharSequence msg = "Report submitted successfully!";
                    int duration = Toast.LENGTH_LONG;
                    Toast completedMsg = Toast.makeText(context, msg, duration);
                    completedMsg.show();
                    Intent goToMainScreen = new Intent(getApplicationContext(), MainActivity.class);
                    goToMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(goToMainScreen);
                }
                //--------------------------------------------------------------
            }
        });
    }
}
