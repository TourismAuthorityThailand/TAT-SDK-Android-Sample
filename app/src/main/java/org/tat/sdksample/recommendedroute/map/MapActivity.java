package org.tat.sdksample.recommendedroute.map;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.tat.sdksample.R;
import org.th.tatsdk.common.TATPoint;
import org.th.tatsdk.common.TATRouteDay;
import org.th.tatsdk.common.TATStop;
import org.th.tatsdk.util.CoordinatePoints;

import java.util.InputMismatchException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    TATRouteDay routeDay = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if (getIntent() != null) {
            routeDay = getIntent().getParcelableExtra("STOP");
        }
        setupToolbar();
        initInstances();
    }

    private void initInstances() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        if (mapFragment!=null){
            mapFragment.getMapAsync(this);
        }
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        final CameraUpdate c = CameraUpdateFactory.newLatLngZoom(new LatLng(13.09803,99.2034776),5.5f);
        map.moveCamera(c);
        if (routeDay != null) {
            //Create map marker
            MarkerOptions marker = new MarkerOptions();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            int index = 1;
            for (TATStop stop : routeDay.getPlaces()) {
                LatLng point = new LatLng(stop.getLatitude(), stop.getLongitude());

                marker.position(point).title(String.valueOf(index)+". "+stop.getName());
                builder.include(point);

                //draw marker
                map.addMarker(marker);

                try{
                    //Decode compress path to get list of latitude and longitude of route path
                    List<TATPoint> points = CoordinatePoints.Decode(stop.getCompressedPath());

                    //Create polyline
                    PolylineOptions line = new PolylineOptions();
                    switch (stop.getTravelMode()) {
                        case "C":
                            line.color(Color.RED);
                            break;
                        case "P":
                            line.color(Color.BLUE);
                            break;
                        case "W":
                            line.color(Color.GREEN);
                            break;
                        default:
                            line.color(Color.MAGENTA);
                            break;
                    }

                    line.width(4f);

                    for (TATPoint tPoint : points) {
                        LatLng p = new LatLng(tPoint.getLatitude(), tPoint.getLongitude());
                        line.add(p);
                        builder.include(p);
                    }
                    //draw line
                    map.addPolyline(line);
                }catch (InputMismatchException e){
                    //in the case of latitude and longitude is out of range
                    e.printStackTrace();
                }
                index++;
            }

            LatLngBounds bounds = builder.build();
            final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 36);
            map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    map.animateCamera(cu);
                }
            });
        }


    }
}
