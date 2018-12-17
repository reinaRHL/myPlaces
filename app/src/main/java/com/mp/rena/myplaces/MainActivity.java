package com.mp.rena.myplaces;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;
    private RecyclerView rv;
    Button searchBtn;

    public void getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        String lat = location.getLatitude()+"";
                        String lng = location.getLongitude()+"";
                        Places place = new Places(lat,lng,"addresstest");

                        Bundle extra = new Bundle();
                        extra.putSerializable("place", place);
                        intent.putExtras(extra);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),"failed2",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBtn = findViewById(R.id.searchPlaceBtn);
        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this);


        searchBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                } else{
                    ActivityCompat.requestPermissions(MainActivity.this , new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
            }
        });

        String[] data = new String[5];
        data[0] = "1";
        data[1] = "2";
        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MyAdapter(this, data));
    }
}
