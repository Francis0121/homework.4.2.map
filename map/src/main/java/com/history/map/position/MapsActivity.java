package com.history.map.position;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.history.map.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends Activity {

    private String location;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_map);
        Log.d("MapsActivity", "oncreate");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(Geocoder.isPresent()){
            try {
                Intent receivedIntent = getIntent();
                location = receivedIntent.getExtras().getString("uri");

                Geocoder gc = new Geocoder(this);
                List<Address> addresses= gc.getFromLocationName(location, 5); // get the found Address Objects

                List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                    }
                }
                
                if(ll.size() > 0){
                    setUpMapIfNeeded(ll.get(0));
                }
            } catch (IOException e) {
                // handle the exception
            }
        }

    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setUpMapIfNeeded(LatLng latLng) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            FragmentManager fm = getFragmentManager();
            mMap = ((MapFragment) fm.findFragmentById(R.id.map)).getMap();
            mMap.addMarker(new MarkerOptions().position(new LatLng(37.56641923090, 126.9778741551)).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast toast = Toast.makeText(this, "Visited " + location, Toast.LENGTH_SHORT);
        toast.show();
    }
}
