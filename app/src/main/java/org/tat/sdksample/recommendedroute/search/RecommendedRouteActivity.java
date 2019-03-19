package org.tat.sdksample.recommendedroute.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import org.tat.sdksample.R;
import org.tat.sdksample.recommendedroute.list.RecommendedRouteListActivity;
import org.tat.sdksample.recommendedroute.model.RecRoutes;
import org.th.tatsdk.ServiceRequestListener;
import org.th.tatsdk.common.TATLanguage;
import org.th.tatsdk.common.TATPoint;
import org.th.tatsdk.common.TATRegion;
import org.th.tatsdk.route.list.TATGetRoutes;
import org.th.tatsdk.route.list.TATGetRoutesParameter;
import org.th.tatsdk.route.list.TATGetRoutesResultSet;

public class RecommendedRouteActivity extends AppCompatActivity {

    Spinner spinnerDay;
    Spinner spinnerRegion;
    Switch swNearby;
    Button btnSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_route);
        initInstances();
        setupToolbar();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.recommended_routes);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void initInstances() {
        spinnerDay = findViewById(R.id.spinnerDay);
        spinnerRegion = findViewById(R.id.spinnerRegion);
        swNearby = findViewById(R.id.swNearbyRecRoute);
        btnSearch = findViewById(R.id.btn_search_rectoute);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRecRoute();
            }
        });
        String[] days = getResources().getStringArray(R.array.day);
        spinnerDay.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, days));
        String[] region = getResources().getStringArray(R.array.region);
        spinnerRegion.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, region));

    }

    private void loadRecRoute() {
        TATGetRoutesParameter param = new TATGetRoutesParameter(TATLanguage.ENGLISH);
        String region = spinnerRegion.getSelectedItem().toString();
        int day = spinnerDay.getSelectedItemPosition();
        if (!region.equals("All")) {
            TATRegion selectedRegion = stringToRegion(region);
            if (selectedRegion != null) {
                param.setRegion(selectedRegion);
            }
        }

        //set number of trip day
        if (day != 0) {
            param.setNumberOfDays(day);
        }

        //set search center location
        if (swNearby.isChecked()) {
            param.setLatitude(13.74918);
            param.setLongitude(100.55785);
        }

        TATGetRoutes.executeAsync(param, new ServiceRequestListener<TATGetRoutesResultSet>() {

            @Override
            public void onResponse(TATGetRoutesResultSet result) {
                RecRoutes res = new RecRoutes(result.getResults());
                Intent intent = new Intent(getBaseContext(), RecommendedRouteListActivity.class);
                intent.putExtra("route_list", res);
                startActivity(intent);
            }

            @Override
            public void onError(String errorMessage, int statusCode) {
                Intent intent = new Intent(getBaseContext(), RecommendedRouteListActivity.class);
                startActivity(intent);
            }
        });

    }

    private TATRegion stringToRegion(String selectedRegion) {
        TATRegion region = null;
        switch (selectedRegion) {
            case "Northern":
                region = TATRegion.NORTHERN;
                return region;
            case "Northeastern":
                region = TATRegion.NORTHEASTERN;
                return region;
            case "Western":
                region = TATRegion.WESTERN;
                return region;
            case "Central":
                region = TATRegion.CENTRAL;
                return region;
            case "Eastern":
                region = TATRegion.EASTERN;
                return region;
            case "Southern":
                region = TATRegion.SOUTHERN;
                return region;
            default:
                return null;
        }
    }

}
