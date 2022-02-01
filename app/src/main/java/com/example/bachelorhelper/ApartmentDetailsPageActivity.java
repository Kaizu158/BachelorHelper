package com.example.bachelorhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ApartmentDetailsPageActivity extends AppCompatActivity {

    String aptAddress;
    String aptArea;
    String aptCity;
    String aptOwnerName;
    String aptOwnerAddress;
    String aptOwnerEmail;
    String aptOwnerNID;
    String aptOwnerPhone;

    TextView tvTtle, tvAptAddress, tvOwnerName, tvOwnerPhone, tvOwnerEmail, tvOwnerNID, tvOwnerAddress;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details_page);


        Intent intent = getIntent();

        aptAddress = intent.getStringExtra(AptListSearchResultActivity.APT_ADDRESS_EXTRA_TEXT);
        aptArea = intent.getStringExtra(AptListSearchResultActivity.AREA_EXTRA_TEXT);
        aptCity = intent.getStringExtra(AptListSearchResultActivity.CITY_EXTRA_TEXT);
        aptOwnerName = intent.getStringExtra(AptListSearchResultActivity.OWNER_NAME_EXTRA_TEXT);
        aptOwnerAddress = intent.getStringExtra(AptListSearchResultActivity.OWNER_ADDRESS_EXTRA_TEXT);
        aptOwnerEmail = intent.getStringExtra(AptListSearchResultActivity.OWNER_EMAIL_EXTRA_TEXT);
        aptOwnerNID = intent.getStringExtra(AptListSearchResultActivity.OWNER_NID_EXTRA_TEXT);
        aptOwnerPhone = intent.getStringExtra(AptListSearchResultActivity.OWNER_PHONE_EXTRA_TEXT);



        tvTtle = findViewById(R.id.aptDetailsPageTitle);
        tvAptAddress = findViewById(R.id.aptDetailsPageAddress);
        tvOwnerName = findViewById(R.id.aptDetailsPageOwnerName);
        tvOwnerPhone = findViewById(R.id.aptDetailsPageOwnerPhone);
        tvOwnerEmail = findViewById(R.id.aptDetailsPageOwnerEmail);
        tvOwnerNID = findViewById(R.id.aptDetailsPageOwnerNID);
        tvOwnerAddress = findViewById(R.id.aptDetailsPageOwnerAddress);
        backButton = findViewById(R.id.aptDetailsPageBackButton);



        tvTtle.setText("Apartment Details");
        tvAptAddress.setText("Apartment Location: "+aptAddress);
        tvOwnerName.setText("Apartment Owner Name: "+aptOwnerName);
        tvOwnerPhone.setText("Apartment Owner Phone Number: "+aptOwnerPhone);
        tvOwnerEmail.setText("Apartment Owner Email: "+aptOwnerEmail);
        tvOwnerAddress.setText("Apartment Owner Address: "+aptOwnerAddress);
        tvOwnerNID.setText("Apartment Owner NID: "+aptOwnerNID);

        backButton.setOnClickListener(v -> backToSearchResultPage());


    }

    private void backToSearchResultPage() {
        Intent intent = new Intent(ApartmentDetailsPageActivity.this, AptListSearchResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}