package com.example.digitrafficinspectorv0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class page8 extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private ResultReceiver resultReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page8);

        resultReceiver = new AddressResultReceiver(new Handler());

        getCurrentLocation();
    }



    private void getCurrentLocation(){


        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(page8.this).requestLocationUpdates(locationRequest,new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(page8.this).removeLocationUpdates(this);
                if(locationResult!=null && locationResult.getLocations().size()>0){
                    int latestLocationIndex = locationResult.getLocations().size() -1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();


                    Location location = new Location("providerNA");
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    fetchAddressFromLatLong(location);

                } else{

                }

            }


        }, Looper.getMainLooper());


    }

    private void fetchAddressFromLatLong(Location location){
        Intent intent = new Intent(this,fetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER,resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA,location);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver{
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if(resultCode== Constants.SUCCESS_RESULT){
                Toast.makeText(page8.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_LONG).show();
            }else{
//                Toast.makeText(page8.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }

        }
    }
}





//checkNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Task<QuerySnapshot> vehicleQuery = db.collection("Complaints").whereEqualTo("VEHICLE_NUMBER", vehicleNumberInput.getText().toString()).orderBy("DATE_TIME_STAMP", Query.Direction.DESCENDING).limit(1).get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                         pic1=document.getData().get("FRONT_VIEW").toString();
//                                         pic2=document.getData().get("LEFT_VIEW").toString();
//                                         pic3=document.getData().get("BACK_VIEW").toString();
//                                         pic4=document.getData().get("RIGHT_VIEW").toString();
//
//                                        if (pic1 !="null")
//                                        {
//                                            Toast.makeText(registerComplaintPage.this, "Front View Already Present", Toast.LENGTH_SHORT).show();
//                                            frontPic.setText("Front View Already Captured");
//                                        }
//                                        if (pic2 !="null")
//                                        {
//                                            Toast.makeText(registerComplaintPage.this, "Left View Already Present", Toast.LENGTH_SHORT).show();
//                                            leftPic.setText("Left View Already Captured");
//                                        }
//                                        if (pic3 !="null")
//                                        {
//                                            Toast.makeText(registerComplaintPage.this, "Back View Already Present", Toast.LENGTH_SHORT).show();
//                                            backPic.setText("Back View Already Captured");
//                                        }
//                                        if (pic4 !="null")
//                                        {
//                                            Toast.makeText(registerComplaintPage.this, "Right View Already Present", Toast.LENGTH_SHORT).show();
//                                            rightPic.setText("Right View Already Captured");
//                                        }
//                                    }
//                                } else {
//                                    Log.d(TAG, "Error getting documents: ", task.getException());
//                                    Toast.makeText(registerComplaintPage.this, "Problem in searching",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//
//