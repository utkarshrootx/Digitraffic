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

public class page5 extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private TextView textLatLong,textAddress;
    private ProgressBar progressBar;
    private ResultReceiver resultReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page5);

        resultReceiver = new AddressResultReceiver(new Handler());
        textLatLong = findViewById(R.id.textLatLong);
        progressBar = findViewById(R.id.progressBar);
        textAddress = findViewById(R.id.textAddress);
        findViewById(R.id.butoonGetCurrentLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                )!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                            page5.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION_PERMISSION
                    );

                }else{
                    getCurrentLocation();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
            else{
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation(){

        progressBar.setVisibility(View.VISIBLE);

        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(page5.this).requestLocationUpdates(locationRequest,new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(page5.this).removeLocationUpdates(this);
                if(locationResult!=null && locationResult.getLocations().size()>0){
                    int latestLocationIndex = locationResult.getLocations().size() -1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    textLatLong.setText(
                            String.format(
                                    "Lat: %s \nLong: %s",latitude,longitude
                            )
                    );

                    Location location = new Location("providerNA");
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    fetchAddressFromLatLong(location);

                } else{
                    progressBar.setVisibility(View.GONE);
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
                textAddress.setText(resultData.getString(Constants.RESULT_DATA_KEY));
            }else{
                Toast.makeText(page5.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
}