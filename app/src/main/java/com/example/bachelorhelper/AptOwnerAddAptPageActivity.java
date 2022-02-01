package com.example.bachelorhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AptOwnerAddAptPageActivity extends AppCompatActivity {

    Button addButton;
    TextView customAddress;
    Spinner sp_parent, sp_child;

    String ownerID;

    //arraylist to choose cities
    ArrayList<String> arrayList_parent;
    ArrayAdapter<String> arrayAdapter_parent;

    //for area dropdown
    ArrayList<String> arrayList_Dhaka, arrayList_Chittagong, arrayList_Rajshahi;
    ArrayAdapter<String> arrayAdapter_child;

    String city, area;

    DatabaseReference databaseReference;
    HashMap<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt_owner_add_apt_page);

        customAddress = findViewById(R.id.aptOwnerAddAptPageCustomAddressInput);
        addButton = findViewById(R.id.aptOwnerAddAptPageAddButton);

        //get id from the xml
        sp_parent = (Spinner) findViewById(R.id.sp_parent_aptOwnerAddAptPage);
        sp_child = (Spinner) findViewById(R.id.sp_child_aptOwnerAddAptPage);

        //add on the arraylist
        String [] cities = {"Dhaka", "Chittagong", "Rajshahi"};
        arrayList_parent = new ArrayList<>();
        arrayList_parent.addAll(Arrays.asList(cities));

        //display_cities
        arrayAdapter_parent = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList_parent);

        sp_parent.setAdapter(arrayAdapter_parent);

        //*************    area part     **************
        arrayList_Dhaka = new ArrayList<>();
        String [] dhakaArea = {"Mirpur", "Mohakhali", "Farmgate", "Gulsan", "Badda",
                "Tejgaon", "Khilgaon", "Mohammadpur", "Dhanmondi"};
        arrayList_Dhaka.addAll(Arrays.asList(dhakaArea));

        arrayList_Chittagong = new ArrayList<>();
        String [] chitArea = {"Chittagong Sadar", "Hathazari", "Sitakunda"};
        arrayList_Chittagong.addAll(Arrays.asList(chitArea));

        arrayList_Rajshahi = new ArrayList<>();
        String [] rajArea = {"Rajshahi Sadar", "Bagha", "Putia", "Durgapur"};
        arrayList_Rajshahi.addAll(Arrays.asList(rajArea));

        sp_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (i == 0){
                    city = sp_parent.getSelectedItem().toString();
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item,arrayList_Dhaka);
                }
                if (i == 1){
                    city = sp_parent.getSelectedItem().toString();
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item,arrayList_Chittagong);
                }
                if (i == 2){
                    city = sp_parent.getSelectedItem().toString();
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item,arrayList_Rajshahi);
                }

                sp_child.setAdapter(arrayAdapter_child);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city = "No city selected";
            }
        });

        sp_child.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (i == 0){
                    area = sp_child.getSelectedItem().toString();
                }
                if (i == 1){
                    area = sp_child.getSelectedItem().toString();
                }
                if (i == 2){
                    area = sp_child.getSelectedItem().toString();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                area = "No area selected";
            }
        });

        Intent intent = getIntent();
        ownerID = intent.getStringExtra(LoginActivity.UID_EXTRA_TEXT);

        databaseReference = FirebaseDatabase.getInstance().getReference("Apartment List");

        addButton.setOnClickListener(v -> addDataToDatabase());




    }

    private void addDataToDatabase() {

        map = new HashMap<>();
        String cusAddress = customAddress.getText().toString();

        map.put("Apartment Address", cusAddress);
        map.put("City", city);
        map.put("Area", area);
        map.put("Owner User ID", ownerID);

        FirebaseDatabase.getInstance().getReference("User").child(ownerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("Name").getValue(String.class);
                map.put("Name", name);
                String phone = snapshot.child("Phone Number").getValue(String.class);
                map.put("Owner Phone Number", phone);
                String ownerAddress = snapshot.child("Owner Address").getValue(String.class);
                map.put("Owner Address", ownerAddress);
                String ownerEmail = snapshot.child("Email").getValue(String.class);
                map.put("Owner Email", ownerEmail);
                String ownerNID = snapshot.child("NID").getValue(String.class);
                map.put("Owner NID", ownerNID);
                databaseReference.push().updateChildren(map);
                System.out.println(map);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openAptOwnerHomePageActivity() {
        Intent intent = new Intent(AptOwnerAddAptPageActivity.this, aptOwnerHomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}