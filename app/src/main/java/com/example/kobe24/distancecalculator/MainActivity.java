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
    double dist=0.0; // total distance travelled in double
    long ans=0;      // total distance travelled in long , used to display answer
    Location prev;   // last location recorded
    Location bestLocation=null;
    TextView txtLat;
//    TextView txtLat1,txtLat2;

    private void getLastKnownLocation() {
        List<String> providers = mLocationManager.getAllProviders(); // all types from which data can be obtained like GSM,CDMA
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l; // best location
                prev=bestLocation;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLat = (TextView) findViewById(R.id.textview1); // textview where the answer is displayed
//        txtLat1 = (TextView) findViewById(R.id.textview2);
//        txtLat2= (TextView) findViewById(R.id.textview3);
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, new LocationListener() {
            // request listener with following parameters
            // GPS , 2sec interval , minimum distance for recording change
            @Override
            public void onLocationChanged(Location location) {
                // function is called when Location is changed
                dist += prev.distanceTo(location); // Distance between two locations in meters,float
                ans = (long) dist;
//                txtLat.setText(prev.getLongitude()+" "+prev.getLatitude());
//                txtLat1.setText(location.getLongitude()+" "+location.getLatitude());
                txtLat.setText("Distance Travelled : " + ans/1000 +" kms " + ans%1000 + " meter(s)"); // Displaying
                prev = location; // changing current location as previous location for next iteration
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
        getLastKnownLocation(); // get the best distance and initialise previous distance
        txtLat.setText("Distance Travelled : " + ans/1000 +" kms " + ans%1000 + " meter(s)");
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
