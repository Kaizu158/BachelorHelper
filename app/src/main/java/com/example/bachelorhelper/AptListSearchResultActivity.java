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

public class AptListSearchResultActivity extends AppCompatActivity {


    public static final String APT_ADDRESS_EXTRA_TEXT = "com.example.bachelorhelper.apt.address";
    public static final String AREA_EXTRA_TEXT = "com.example.bachelorhelper.area";
    public static final String CITY_EXTRA_TEXT = "com.example.bachelorhelper.city";
    public static final String OWNER_NAME_EXTRA_TEXT = "com.example.bachelorhelper.owner.name";
    public static final String OWNER_ADDRESS_EXTRA_TEXT = "com.example.bachelorhelper.owner.address";
    public static final String OWNER_EMAIL_EXTRA_TEXT = "com.example.bachelorhelper.owner.email";
    public static final String OWNER_NID_EXTRA_TEXT = "com.example.bachelorhelper.owner.nid";
    public static final String OWNER_PHONE_EXTRA_TEXT = "com.example.bachelorhelper.owner.phone";



    String city, area;
    List<String> viewAllAptAddressOnListviewList;
    List<String> selectedAptInfoList;
    ArrayAdapter arrayAdapter;




    List<String> aptAddressList;
    List<String> aptAreaList;
    List<String> aptCityList;
    List<String> aptOwnerNameList;
    List<String> aptOwnerAddressList;
    List<String> aptOwnerEmailList;
    List<String> aptOwnerNIDList;
    List<String> aptOwnerPhoneList;



    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt_list_search_result);

        Intent intent = getIntent();
        city = intent.getStringExtra(apartmentListActivity.CITY_EXTRA_TEXT);
        area = intent.getStringExtra(apartmentListActivity.AREA_EXTRA_TEXT);

        System.out.println(city);
        System.out.println(area);

        ListView aptSearchResultList = findViewById(R.id.aptListPageSearchResultListView);

        viewAllAptAddressOnListviewList = new ArrayList<>();
        selectedAptInfoList = new ArrayList<>();

        aptOwnerNameList = new ArrayList<>();
        aptOwnerNIDList = new ArrayList<>();
        aptOwnerEmailList = new ArrayList<>();
        aptOwnerPhoneList = new ArrayList<>();
        aptOwnerAddressList = new ArrayList<>();
        aptAddressList = new ArrayList<>();
        aptAreaList = new ArrayList<>();
        aptCityList = new ArrayList<>();

        Button backToSearchButton = findViewById(R.id.aptListSearchResultPageBackButton);
        backToSearchButton.setOnClickListener(v -> backToSearchPageActivity());


        databaseReference = FirebaseDatabase.getInstance().getReference("Apartment List");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewAllAptAddressOnListviewList.clear();

                aptOwnerNameList.clear();
                aptOwnerNIDList.clear();
                aptOwnerEmailList.clear();
                aptOwnerPhoneList.clear();
                aptOwnerAddressList.clear();
                aptAddressList.clear();
                aptAreaList.clear();
                aptCityList.clear();

                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    if(snapshot1.child("City").getValue().toString().equals(city) && snapshot1.child("Area").getValue().toString().equals(area)){
                        viewAllAptAddressOnListviewList.add(snapshot1.child("Apartment Address").getValue().toString());

                        aptOwnerNameList.add(snapshot1.child("Name").getValue().toString());
                        aptOwnerNIDList.add(snapshot1.child("Owner NID").getValue().toString());
                        aptOwnerEmailList.add(snapshot1.child("Owner Email").getValue().toString());
                        aptOwnerPhoneList.add(snapshot1.child("Owner Phone Number").getValue().toString());
                        aptOwnerAddressList.add(snapshot1.child("Owner Address").getValue().toString());
                        aptAddressList.add(snapshot1.child("Apartment Address").getValue().toString());
                        aptAreaList.add(snapshot1.child("Area").getValue().toString());
                        aptCityList.add(snapshot1.child("City").getValue().toString());
                    }else{
                        openAptDetailsPageActivity();
                    }
                }

                arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, viewAllAptAddressOnListviewList);
                aptSearchResultList.setAdapter(arrayAdapter);

                aptSearchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        selectedAptInfoList.clear();

                        for(int i = 0; i< viewAllAptAddressOnListviewList.size(); i++){
                            if(position == i){
                                openAptDetailsPageActivity(aptAddressList.get(i), aptAreaList.get(i), aptCityList.get(i), aptOwnerNameList.get(i), aptOwnerAddressList.get(i), aptOwnerEmailList.get(i), aptOwnerNIDList.get(i), aptOwnerPhoneList.get(i));
                            }
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Intent intent = new Intent(AptListSearchResultActivity.this, ApartmentDetailsPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });





    }

    private void backToSearchPageActivity() {
        Intent intent = new Intent(AptListSearchResultActivity.this, apartmentListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openAptDetailsPageActivity() {


    }

    private void openAptDetailsPageActivity(String aptAddress, String aptArea, String aptCity, String aptOwnerName, String aptOwnerAddress, String aptOwnerEmail, String aptOwnerNID, String aptOwnerPhone) {

        Intent intent = new Intent(AptListSearchResultActivity.this, ApartmentDetailsPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(APT_ADDRESS_EXTRA_TEXT, aptAddress);
        intent.putExtra(AREA_EXTRA_TEXT, aptArea);
        intent.putExtra(CITY_EXTRA_TEXT, aptCity);
        intent.putExtra(OWNER_NAME_EXTRA_TEXT, aptOwnerName);
        intent.putExtra(OWNER_ADDRESS_EXTRA_TEXT, aptOwnerAddress);
        intent.putExtra(OWNER_EMAIL_EXTRA_TEXT, aptOwnerEmail);
        intent.putExtra(OWNER_NID_EXTRA_TEXT, aptOwnerNID);
        intent.putExtra(OWNER_PHONE_EXTRA_TEXT, aptOwnerPhone);

        startActivity(intent);

    }

}