package com.tristenallen.watersource.controller;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.model.PurityReport;
import com.tristenallen.watersource.model.ReportHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jahziel on 3/28/17.
 * Activity for creating viewing the purity reports at the selected location.
 */
public class ViewLocationPurityReportsActivity extends AppCompatActivity {
    private final ReportHelper reportHelper = ReportHelper.getInstance();
    private final double[] location = new double[2];
    private final ArrayList<String> monthYearVC = new ArrayList<>();
    @SuppressWarnings({"FeatureEnvy", "OverlyLongMethod"})
    // feature envy smell occurs because of onCreate() handling the bulk of work
    // long method is due to onCreate having to handle bulk of work
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlocationpurityreports);
        ListView listView = (ListView) findViewById(R.id.locationPurityReportListView);
        Button viewHistographButton = (Button) findViewById(R.id.viewHistographButton);
        Button addNewPurityReportButton = (Button) findViewById(R.id.addNewReportButton);
        @SuppressWarnings("ChainedMethodCall") // required by android
        double[] extrasFromInfoWindow = getIntent().getDoubleArrayExtra("Location");
        Iterable<PurityReport> rawPurityReports = new ArrayList<>(reportHelper.getPurityReports(this));
        List<PurityReport> purityReports = new ArrayList<>();

        List<String> purityReportStrings = new ArrayList<>();

        for (PurityReport x : rawPurityReports) {
            Location l = x.getLocation();
            if ((l.getLatitude() == extrasFromInfoWindow[0]) &&
                    (l.getLongitude() == extrasFromInfoWindow[1])) {
                purityReports.add(x);
            }
        }

        PurityReport sample = purityReports.get(0);
        Location locationRaw = sample.getLocation();
        location[0] = locationRaw.getLatitude();
        location[1] = locationRaw.getLongitude();
        //int count = 0; //for testing
        for (PurityReport p : purityReports) {
            purityReportStrings.add(p.toString());
            Date leDate =  p.getTimestamp();
            Calendar cal = Calendar.getInstance();
            cal.setTime(leDate);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            month++; // required due to 0 indexing of months
            //month = month + count; // for testing
            int virusPPM = p.getVirusPPM();
            int contPPM = p.getContaminantPPM();
            monthYearVC.add(month + ":" + year + ":" + virusPPM + ":" + contPPM);
            //count++; for testing
        }
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, purityReportStrings));

        viewHistographButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSelect = new Intent(getApplicationContext(), SelectYearVCActivity.class);
                goToSelect.putExtra("monthYearVC", monthYearVC);
                startActivity(goToSelect);
            }
        });

        addNewPurityReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAnother = new Intent(getApplicationContext(), SubmitPurityReportActivity.class);
                addAnother.putExtra("location", location);
                startActivity(addAnother);
            }
        });


    }
}
