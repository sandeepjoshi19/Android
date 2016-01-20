package com.example.sandeep.myevents;

import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddLocation extends FragmentActivity implements OnMapReadyCallback ,GoogleMap.OnMapClickListener,
        GoogleApiClient.OnConnectionFailedListener,GoogleMap.OnMyLocationButtonClickListener{

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private GoogleMap mMap;
    private Location mLastLocation;;
    GoogleApiClient mGoogleApiClient;
    GoogleApiClient.Builder mgoogleapiclient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mgoogleapiclient==null) {
            mapFragment.getMapAsync(this);
        }
        if (checkPlayServices()) {
            buildGoogleApiClient();
        }
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
       // mMap.setMapType(GoogleMap.G);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        mMap.setMyLocationEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }
    }

    private void displayLocation() {

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            Intent intent=getIntent();
            intent.putExtra("latitude",latitude);
            intent.putExtra("longitude",longitude);
            this.setResult(RESULT_OK, intent);
            Toast.makeText(this, latitude + "," + longitude, Toast.LENGTH_SHORT);
            finish();


        } else {


                    Toast.makeText(this, "(Couldn't get the location. Make sure location is enabled on the device",Toast.LENGTH_SHORT);
        }
    }
    @Override
    public void onMapClick(LatLng latLng) {

    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}