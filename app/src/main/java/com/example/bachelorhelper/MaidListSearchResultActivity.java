package com.example.bachelorhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MaidListSearchResultActivity extends AppCompatActivity {


    public static final String MAID_ADDRESS_EXTRA_TEXT = "com.example.bachelorhelper.maid.address";
    public static final String MAID_SERVICE_EXTRA_TEXT = "com.example.bachelorhelper.maid.service";
    public static final String AREA_EXTRA_TEXT = "com.example.bachelorhelper.area";
    public static final String CITY_EXTRA_TEXT = "com.example.bachelorhelper.city";
    public static final String MAID_NAME_EXTRA_TEXT = "com.example.bachelorhelper.maid.name";
    public static final String MAID_EMAIL_EXTRA_TEXT = "com.example.bachelorhelper.maid.email";
    public static final String MAID_NID_EXTRA_TEXT = "com.example.bachelorhelper.maid.nid";
    public static final String MAID_PHONE_EXTRA_TEXT = "com.example.bachelorhelper.maid.phone";



    String city, area;
    List<String> viewAllMaidAddressOnListviewList;
    List<String> selectedAptInfoList;
    ArrayAdapter arrayAdapter;




    List<String> maidAddressList;
    List<String> maidServiceLocationList;
    List<String> maidAreaList;
    List<String> maidCityList;
    List<String> maidNameList;
    List<String> maidEmailList;
    List<String> aptNIDList;
    List<String> aptPhoneList;



    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maid_list_search_result);

        Intent intent = getIntent();
        city = intent.getStringExtra(MaidListActivity.CITY_EXTRA_TEXT);
        area = intent.getStringExtra(MaidListActivity.AREA_EXTRA_TEXT);

        System.out.println(city);
        System.out.println(area);

        ListView aptSearchResultList = findViewById(R.id.maidListPageSearchResultListView);

        viewAllMaidAddressOnListviewList = new ArrayList<>();
        selectedAptInfoList = new ArrayList<>();

        maidNameList = new ArrayList<>();
        maidServiceLocationList = new ArrayList<>();
        aptNIDList = new ArrayList<>();
        maidEmailList = new ArrayList<>();
        aptPhoneList = new ArrayList<>();
        maidAddressList = new ArrayList<>();
        maidAreaList = new ArrayList<>();
        maidCityList = new ArrayList<>();

        Button backToSearchButton = findViewById(R.id.maidListSearchResultPageBackButton);
        backToSearchButton.setOnClickListener(v -> backToSearchPageActivity());


        databaseReference = FirebaseDatabase.getInstance().getReference("Maid List");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewAllMaidAddressOnListviewList.clear();

                maidNameList.clear();
                aptNIDList.clear();
                maidServiceLocationList.clear();
                maidEmailList.clear();
                aptPhoneList.clear();
                maidAddressList.clear();
                maidAreaList.clear();
                maidCityList.clear();

                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    if(snapshot1.child("City").getValue().toString().equals(city) && snapshot1.child("Area").getValue().toString().equals(area)){
                        viewAllMaidAddressOnListviewList.add(snapshot1.child("Service Location").getValue().toString());

                        maidNameList.add(snapshot1.child("Name").getValue().toString());
                        maidServiceLocationList.add(snapshot1.child("Service Location").getValue().toString());
                        aptNIDList.add(snapshot1.child("Maid NID").getValue().toString());
                        maidEmailList.add(snapshot1.child("Maid Email").getValue().toString());
                        aptPhoneList.add(snapshot1.child("Maid Phone Number").getValue().toString());
                        maidAddressList.add(snapshot1.child("Maid Address").getValue().toString());
                        maidAreaList.add(snapshot1.child("Area").getValue().toString());
                        maidCityList.add(snapshot1.child("City").getValue().toString());
                    }else{
                        openAptDetailsPageActivity();
                    }
                }

                arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, viewAllMaidAddressOnListviewList);
                aptSearchResultList.setAdapter(arrayAdapter);

                aptSearchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        selectedAptInfoList.clear();

                        for(int i = 0; i< viewAllMaidAddressOnListviewList.size(); i++){
                            if(position == i){
                                openAptDetailsPageActivity(maidServiceLocationList.get(i), maidAddressList.get(i), maidAreaList.get(i), maidCityList.get(i), maidNameList.get(i), maidEmailList.get(i), aptNIDList.get(i), aptPhoneList.get(i));
                            }
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Intent intent = new Intent(MaidListSearchResultActivity.this, ApartmentDetailsPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });





    }

    private void backToSearchPageActivity() {
        Intent intent = new Intent(MaidListSearchResultActivity.this, MaidListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openAptDetailsPageActivity() {


    }

    private void openAptDetailsPageActivity(String maidServiceLocation, String maidAddress, String maidArea, String maidCity, String maidName, String maidEmail, String maidNID, String maidPhone) {

        Intent intent = new Intent(MaidListSearchResultActivity.this, MaidDetailsPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(MAID_ADDRESS_EXTRA_TEXT, maidAddress);
        intent.putExtra(MAID_SERVICE_EXTRA_TEXT, maidServiceLocation);
        intent.putExtra(AREA_EXTRA_TEXT, maidArea);
        intent.putExtra(CITY_EXTRA_TEXT, maidCity);
        intent.putExtra(MAID_NAME_EXTRA_TEXT, maidName);
        intent.putExtra(MAID_EMAIL_EXTRA_TEXT, maidEmail);
        intent.putExtra(MAID_NID_EXTRA_TEXT, maidNID);
        intent.putExtra(MAID_PHONE_EXTRA_TEXT, maidPhone);

        startActivity(intent);

    }

}