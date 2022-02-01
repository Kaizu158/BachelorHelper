package com.example.bachelorhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class CustomerHomePageActivity extends AppCompatActivity {

    Button aptSelectButton, maidSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);

        aptSelectButton = findViewById(R.id.customerHomePageAptButton);
        aptSelectButton.setOnClickListener(view -> ApartmentSearchListPage());
        maidSelectButton = findViewById(R.id.customerHomePageMaidButton);
        maidSelectButton.setOnClickListener(view -> maidSearchListPage());
    }

    public void ApartmentSearchListPage(){
        Intent intent = new Intent(this, apartmentListActivity.class);
        startActivity(intent);
    }

    public void maidSearchListPage(){
        Intent intent = new Intent(this, MaidListActivity.class);
        startActivity(intent);
    }
}