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
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
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

public class MapsActivity extends Activity implements View.OnClickListener{

    private String location;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private FrameLayout mapFrameLayout;
    private FrameLayout infoFrameLayout;
    private FrameLayout parseFrameLayout;
    private TextView infoTextView;
    private WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_map);
        Log.d("MapsActivity", "onCreate");
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // ~ FrameLayout
        mapFrameLayout = (FrameLayout) findViewById(R.id.mapFrameLayout);
        infoFrameLayout = (FrameLayout) findViewById(R.id.infoFrameLayout);
        parseFrameLayout = (FrameLayout) findViewById(R.id.parseFrameLayout);
        // ~ TextView
        infoTextView = (TextView) findViewById(R.id.infoTextView);
        // ~ Webview
        webView = (WebView) findViewById(R.id.webView);
        // ~ Btn
        findViewById(R.id.infoBtn).setOnClickListener(this);
        findViewById(R.id.mapBtn).setOnClickListener(this);
        findViewById(R.id.parseBtn).setOnClickListener(this);
        
        Intent receivedIntent = getIntent();
        
        if(Geocoder.isPresent()){
            try {
                location = receivedIntent.getExtras().getString("str");

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
                    infoTextView.setText("Location : [" + location + "]\n" +
                            "Latitude : " + ll.get(0).latitude + " Longitude :" + ll.get(0).longitude);
                }else{
                    infoTextView.setText("Location : [" +location +"] not exist");
                }
                
            } catch (IOException e) {
                // handle the exception
            }
        }

        String uri = receivedIntent.getExtras().getString("uri");
        webView.loadUrl(uri);
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setUpMapIfNeeded(LatLng latLng) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            FragmentManager fm = getFragmentManager();
            mMap = ((MapFragment) fm.findFragmentById(R.id.map)).getMap();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast toast = Toast.makeText(this, "Visited " + location, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        Log.d("MapsActivity", "OnClickEvent");
        
        switch (v.getId()){
            case R.id.infoBtn:
                parseFrameLayout.setVisibility(View.INVISIBLE);
                infoFrameLayout.setVisibility(View.VISIBLE);
                mapFrameLayout.setVisibility(View.INVISIBLE);
                break;
            case R.id.mapBtn:
                parseFrameLayout.setVisibility(View.INVISIBLE);
                infoFrameLayout.setVisibility(View.INVISIBLE);
                mapFrameLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.parseBtn:
                parseFrameLayout.setVisibility(View.VISIBLE);
                infoFrameLayout.setVisibility(View.INVISIBLE);
                mapFrameLayout.setVisibility(View.INVISIBLE);
                break;
        }
        
    }
}
