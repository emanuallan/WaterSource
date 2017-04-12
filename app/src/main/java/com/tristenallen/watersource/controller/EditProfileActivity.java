package com.tristenallen.watersource.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.tristenallen.watersource.R;
import com.tristenallen.watersource.model.AuthHelper;
import com.tristenallen.watersource.model.User;

/**
 * Created by David on 2/22/17.
 * Activity for users to edit their profile on the app.
 */

@SuppressWarnings("FeatureEnvy") // feature envy smell occurs because User is a data holder class
public class EditProfileActivity extends AppCompatActivity {
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText titleField;
    private EditText addressField;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        firstNameField = (EditText) findViewById(R.id.fnameTXT);
        lastNameField = (EditText) findViewById(R.id.lnameTXT);
        titleField = (EditText) findViewById(R.id.titleTXT);
        addressField = (EditText) findViewById(R.id.addressTXT);

        AuthHelper authHelper = AuthHelper.getInstance();

        user = authHelper.getCurrentUser();

        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        titleField.setText(user.getTitle());
        addressField.setText(user.getAddress());
    }

    /**
     * Method defining actions once the submit button is pressed.
     * @param view the View required by Android for this method to run.
     */
    @SuppressWarnings({"UnusedParameters", "ChainedMethodCall"})
    //View view is required by android
    //chained calls required by android
    public void onSubmitPressed(View view) {
        user.setFirstName(firstNameField.getText().toString());
        user.setLastName(lastNameField.getText().toString());
        user.setTitle(titleField.getText().toString());
        user.setAddress(addressField.getText().toString());

        Intent goToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(goToMainActivity);
    }

    /**
     * Method defining actions for when back is pressed.
     * @param view the View required by Android for this method to run.
     */
    @SuppressWarnings("UnusedParameters")
    //View view is required by android
    public void onBackPressed( View view) {
        super.onBackPressed();
    }
}
