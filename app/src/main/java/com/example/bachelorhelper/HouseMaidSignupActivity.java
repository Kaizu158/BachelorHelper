package com.example.bachelorhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class HouseMaidSignupActivity extends AppCompatActivity {

    public static final String UID_EXTRA_TEXT = "com.example.bachelorhelper.UID";

    EditText inputName, inputPhoneNumber, inputAddress, inputNID, inputEmail, inputPassword;

    Button signupButton, backToLoginButton;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;


    DatabaseReference databaseReference;
    HashMap<String, Object> map;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_maid_signup);

        backToLoginButton = findViewById(R.id.maidSignupPageLoginButton);
        backToLoginButton.setOnClickListener(v -> openLoginPageActivity());

        inputName = findViewById(R.id.maidSignupPageNameInput);
        inputPhoneNumber = findViewById(R.id.maidSignupPagePhoneInput);
        inputAddress = findViewById(R.id.maidSignupPageAddressInput);
        inputNID = findViewById(R.id.maidSignupPageNIDInput);
        inputEmail = findViewById(R.id.maidSignupPageEmailInput);
        inputPassword = findViewById(R.id.maidSignupPagePasswordInput);
        signupButton = findViewById(R.id.maidSignupPageSignupButton);


        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        signupButton.setOnClickListener(v -> {
            performAuthentication();
        });

    }

    private void performAuthentication() {

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String name = inputName.getText().toString();
        String phoneNumber = inputPhoneNumber.getText().toString();
        String nid = inputNID.getText().toString();
        String address = inputAddress.getText().toString();


        if(!email.matches(emailPattern)){
            inputEmail.setError("Invalid email address");
        }else if(password.isEmpty() || password.length()< 6){
            inputPassword.setError("Password must not be empty or at least 6 characters long");
        }else{
            progressDialog.setMessage("Registering...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String userID, userType;

                        userType = "House Maid";
                        userID = task.getResult().getUser().getUid();

                        map = new HashMap<>();
                        map.put("Name", name);
                        map.put("Password", password);
                        map.put("Email", email);
                        map.put("NID", nid);
                        map.put("Phone Number", phoneNumber);
                        map.put("Maid Address", address);
                        map.put("User Type", userType);
                        map.put("User ID", userID);

                        databaseReference.child(userID).updateChildren(map);

                        progressDialog.dismiss();

                        sendUserToHouseMaidHomePageActivity(userID);

                        Toast.makeText(HouseMaidSignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();


                    }else{
                        Toast.makeText(HouseMaidSignupActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToHouseMaidHomePageActivity(String uID) {
        Intent intent = new Intent(HouseMaidSignupActivity.this, HouseMaidHomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(UID_EXTRA_TEXT, uID);
        startActivity(intent);
    }

    public void openLoginPageActivity(){
        Intent intent = new Intent(HouseMaidSignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}