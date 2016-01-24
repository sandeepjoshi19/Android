package com.example.sandeep.myevents;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class CurrentLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;
    Location location;
    Intent intent;
    @Override
    protected void onStop() {
        super.onStop();
        if(googleApiClient.isConnected())
        {
            googleApiClient.disconnect();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        intent=getIntent();

        googleApiClient=new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"Connection failed",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnected(Bundle bundle) {
        location=LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if( location!=null)
        {
            intent.putExtra("lattitude",location.getLatitude());
            intent.putExtra("longitude",location.getLongitude());
            this.setResult(RESULT_OK,intent);
            finish();
        }
        else
        {
            Toast.makeText(this,"Location not detected",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"connection suspended",Toast.LENGTH_SHORT).show();
        googleApiClient.connect();
    }
}
