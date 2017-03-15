package hu.ait.locationdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyLocationManager.OnLocChanged,
        GoogleApiClient.OnConnectionFailedListener{

    private TextView tvStatus;
    private MyLocationManager myLocationManager = null;

    private Location prevLoc = null;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tvStatus);

        myLocationManager = new MyLocationManager(this, getApplicationContext());

        requestNeededPermission();

        tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //revGeoCode();
                showPlacePicker();
            }
        });

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
    }

    private void geoCode() {
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        String streetAddress = "Gázgyár utca 1, Budapest";
        List<Address> locations = null;
        try {
            locations = geocoder.getFromLocationName(streetAddress, 3);

            Toast.makeText(this, locations.get(0).getLongitude()+", "+locations.get(0).getLatitude(),
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void revGeoCode() {
        if (prevLoc != null) {
            double latitude = prevLoc.getLatitude();
            double longitude = prevLoc.getLongitude();
            Geocoder gc = new Geocoder(this, Locale.getDefault());
            List<Address> addrs = null;
            try {
                addrs = gc.getFromLocation(latitude, longitude, 10);
                Toast.makeText(this, addrs.get(0).getAddressLine(0)+"\n"+
                                addrs.get(0).getAddressLine(1)+"\n"+
                                addrs.get(0).getAddressLine(2),
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showPlacePicker() {
        PlacePicker.IntentBuilder builder =
                new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(MainActivity.this), 101);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());

                String distance = null;

                if (prevLoc != null) {
                    Location placeLocation = new Location("");
                    placeLocation.setLatitude(place.getLatLng().latitude);
                    placeLocation.setLongitude(place.getLatLng().longitude);
                    distance = String.valueOf(prevLoc.distanceTo(placeLocation));
                }

                if (distance != null) {
                    toastMsg+="\nDistance: "+distance;
                }

                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }



    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(MainActivity.this,
                        "I need it for gps", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    101);
        } else {
            myLocationManager.startLocatoinMonitoring();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "FINELOC perm granted", Toast.LENGTH_SHORT).show();

                    myLocationManager.startLocatoinMonitoring();

                } else {
                    Toast.makeText(MainActivity.this,
                            "FINE perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }








    @Override
    protected void onStop() {
        super.onStop();
        myLocationManager.stopLocationMonitoring();
    }

    @Override
    public void locationChanged(Location location) {


        prevLoc = location;


        tvStatus.setText("Lat: "+location.getLatitude()+"\n"+
            "Lng: "+location.getLongitude()+"\n"+
            "Accuracy: "+location.getAccuracy()+"\n"+
                "Provider: "+location.getProvider()
        );
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(MainActivity.this, "GEO API FAILED: " +
                connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }
}
