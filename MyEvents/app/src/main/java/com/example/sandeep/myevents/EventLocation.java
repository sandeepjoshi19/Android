package com.example.sandeep.myevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventLocation extends FragmentActivity implements OnMapReadyCallback ,GoogleApiClient.OnConnectionFailedListener {
    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        Intent intent=getIntent();
        Toast.makeText(this,String.valueOf(intent.getDoubleExtra("lattitude",0))+String.valueOf(intent.getDoubleExtra("longitude",0)),Toast.LENGTH_SHORT).show();;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intent=getIntent();
        LatLng latLng=new LatLng(intent.getDoubleExtra("lattitude",0),intent.getDoubleExtra("longitude",0));
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Event Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"Make sure you have enabled your internet connection",Toast.LENGTH_LONG).show();
    }
}
