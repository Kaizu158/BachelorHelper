package com.example.bachelorhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MaidDetailsPageActivity extends AppCompatActivity {

    String maidAddress;
    String maidArea;
    String maidCity;
    String maidName;
    String maidServiceAddress;
    String maidEmail;
    String maidNID;
    String maidPhone;

    TextView tvTtle, tvMaidServiceAddress, tvMaidName, tvMaidPhone, tvMaidEmail, tvMaidNID, tvMaidAddress;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_details_page);


        Intent intent = getIntent();

        maidAddress = intent.getStringExtra(MaidListSearchResultActivity.MAID_ADDRESS_EXTRA_TEXT);
        maidArea = intent.getStringExtra(MaidListSearchResultActivity.AREA_EXTRA_TEXT);
        maidCity = intent.getStringExtra(MaidListSearchResultActivity.CITY_EXTRA_TEXT);
        maidName = intent.getStringExtra(MaidListSearchResultActivity.MAID_NAME_EXTRA_TEXT);
        maidServiceAddress = intent.getStringExtra(MaidListSearchResultActivity.MAID_SERVICE_EXTRA_TEXT);
        maidEmail = intent.getStringExtra(MaidListSearchResultActivity.MAID_EMAIL_EXTRA_TEXT);
        maidNID = intent.getStringExtra(MaidListSearchResultActivity.MAID_NID_EXTRA_TEXT);
        maidPhone = intent.getStringExtra(MaidListSearchResultActivity.MAID_PHONE_EXTRA_TEXT);



        tvTtle = findViewById(R.id.maidDetailsPageTitle);
        tvMaidServiceAddress = findViewById(R.id.maidDetailsPageServiceLocation);
        tvMaidName = findViewById(R.id.maidDetailsPageMaidName);
        tvMaidPhone = findViewById(R.id.maidDetailsPageMaidPhone);
        tvMaidEmail = findViewById(R.id.maidDetailsPageOwnerEmail);
        tvMaidNID = findViewById(R.id.maidDetailsPageMaidNID);
        tvMaidAddress = findViewById(R.id.maidDetailsPageMaidAddress);

        backButton = findViewById(R.id.maidDetailsPageBackButton);


        tvTtle.setText("Maid Details");
        tvMaidServiceAddress.setText("Maid Service Area: "+ maidServiceAddress);
        tvMaidName.setText("Maid Name: "+ maidName);
        tvMaidPhone.setText("Maid Phone Number: "+ maidPhone);
        tvMaidEmail.setText("Maid Email: "+ maidEmail);
        tvMaidAddress.setText("Maid Address: "+ maidAddress);
        tvMaidNID.setText("Maid NID: "+ maidNID);

        backButton.setOnClickListener(v -> backToSearchResultPage());


    }

    private void backToSearchResultPage() {
        Intent intent = new Intent(MaidDetailsPageActivity.this, MaidListSearchResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}