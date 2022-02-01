package com.example.bachelorhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SignupOptionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_option);

        Button SignupOptionToCustomerSignup = findViewById(R.id.signupPageCustomerOptionButton);
        SignupOptionToCustomerSignup.setOnClickListener(v -> openCustomerSignupActivity());

        Button SignupOptionToAptOwnerSignup = findViewById(R.id.signupPageAptOwnerOptionButton);
        SignupOptionToAptOwnerSignup.setOnClickListener(v -> openAptOwnerSignupActivity());

        Button SignupOptionToMaidSignup = findViewById(R.id.signupPageMaidOptionButton);
        SignupOptionToMaidSignup.setOnClickListener(v -> openMaidSignupActivity());

    }

    public void openCustomerSignupActivity(){
        Intent intent = new Intent(this, CustomerSignupActivity.class);
        startActivity(intent);
    }

    public void openAptOwnerSignupActivity(){
        Intent intent = new Intent(this, ApartmentOwnerSignupActivity.class);
        startActivity(intent);
    }

    public void openMaidSignupActivity(){
        Intent intent = new Intent(this, HouseMaidSignupActivity.class);
        startActivity(intent);
    }


}