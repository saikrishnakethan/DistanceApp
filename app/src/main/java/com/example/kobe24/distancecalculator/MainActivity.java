package com.example.kobe24.distancecalculator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import java.util.List;
import android.util.Log;

public class MainActivity extends Activity implements LocationListener{
    protected LocationManager mLocationManager;
    protected LocationListener locationListener;
    protected Context context;
    double dist=0.0;
    long ans=0;
    Location prev;
    Location bestLocation=null;
    TextView txtLat;
//    TextView txtLat1,txtLat2;

    private void getLastKnownLocation() {
        List<String> providers = mLocationManager.getAllProviders();
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
                prev=bestLocation;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLat = (TextView) findViewById(R.id.textview1);
//        txtLat1 = (TextView) findViewById(R.id.textview2);
//        txtLat2= (TextView) findViewById(R.id.textview3);
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                dist += prev.distanceTo(location);
                ans = (long) dist;
//                txtLat.setText(prev.getLongitude()+" "+prev.getLatitude());
//                txtLat1.setText(location.getLongitude()+" "+location.getLatitude());
                txtLat.setText("Distance Travelled : " + ans/1000 +"kms" + ans%1000 + " meter(s)");
                prev = location;
            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // TODO Auto-generated method stub
            }
        });
        getLastKnownLocation();
        txtLat.setText("Distance Travelled : " + ans/1000 +"kms" + ans%1000 + " meter(s)");
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
}
