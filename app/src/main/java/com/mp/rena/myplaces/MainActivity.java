package com.mp.rena.myplaces;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.deleteAllBtn){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Everything in the list will be deleted and cannot be restored")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            data.clear();
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("no", null)
                    .show();
        } else{
            // do nothing for now
        }

        return true;
    }

    private FusedLocationProviderClient mFusedLocationClient;
    private RecyclerView rv;
    Button searchBtn;
    static ArrayList<Places> data = new ArrayList<>(); // arraylist containing Places objects
    static MyAdapter adapter;

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
                        Toast.makeText(getApplicationContext(),"Failed to get last known location",Toast.LENGTH_SHORT).show();

                        Places place = new Places("34","118","1313 Disneyland Dr, Anaheim, CA 92802, USA");
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        Bundle extra = new Bundle();
                        extra.putSerializable("place", place);
                        intent.putExtras(extra);
                        startActivity(intent);
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

        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, data);
        rv.setAdapter(adapter);
        DividerItemDecoration devider = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(devider);


    }
}
