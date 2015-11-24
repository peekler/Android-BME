package hu.bme.aut.android.mapdemo;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setTrafficEnabled(true);

        LatLng hungary = new LatLng(47, 19);
        addMarker(hungary);
        drawPolygon();
        moveCamera(hungary);
    }

    private void moveCamera(LatLng hungary) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(hungary)
                .zoom(17)
                .bearing(90)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void drawPolygon() {
        PolygonOptions polyRect = new PolygonOptions().add(new LatLng(44, 19),
                new LatLng(44, 26),
                new LatLng(48, 26),
                new LatLng(48, 19));
        Polygon polygon = mMap.addPolygon(polyRect);
        polygon.setFillColor(Color.GREEN);
    }

    private void addMarker(LatLng hungary) {
        Marker markerHU = mMap.addMarker(
                new MarkerOptions()
                        .position(hungary)
                        .title(getString(R.string.title_hungary))
                        .snippet(getString(R.string.desc_hungary))
                        .icon(BitmapDescriptorFactory.fromResource(
                                R.mipmap.ic_launcher)));
        markerHU.setDraggable(true);
    }
}
