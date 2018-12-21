package com.mp.rena.myplaces;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intent = getIntent();
        Places place = (Places)intent.getSerializableExtra("place");
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng currentLoc = new LatLng(Double.parseDouble(place.lat), Double.parseDouble(place.lng));
        mMap.addMarker(new MarkerOptions().position(currentLoc).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,10));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String lat = String.valueOf(latLng.latitude);
                String lng = String.valueOf(latLng.longitude);
                String address = "Not known";
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    address = addressList.get(0).getAddressLine(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Places place = new Places(lat, lng, address);
                MainActivity.data.add(place);
                MainActivity.adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "saved!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
