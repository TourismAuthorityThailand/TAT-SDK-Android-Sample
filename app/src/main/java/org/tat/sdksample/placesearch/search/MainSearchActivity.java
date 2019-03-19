package org.tat.sdksample.placesearch.search;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.tat.sdksample.R;
import org.tat.sdksample.placesearch.model.SearchResult;
import org.tat.sdksample.placesearch.model.SearchResults;
import org.th.tatsdk.ServiceRequestListener;
import org.th.tatsdk.common.TATCategory;
import org.th.tatsdk.common.TATLanguage;
import org.th.tatsdk.common.TATLocation;
import org.th.tatsdk.search.place.TATPlacesSearch;
import org.th.tatsdk.search.place.TATPlacesSearchParameter;
import org.th.tatsdk.search.place.TATPlacesSearchResult;
import org.th.tatsdk.search.place.TATPlacesSearchResultSet;

import java.util.ArrayList;

public class MainSearchActivity extends AppCompatActivity {

    public static String SEARCH_RESULT = "SEARCH_RESULT";
    private Button searchButton;
    private AppCompatCheckBox ckbAttraction;
    private AppCompatCheckBox ckbAccommodation;
    private AppCompatCheckBox ckbRestaurant;
    private AppCompatCheckBox ckbShop;
    private AppCompatCheckBox ckbOther;
    private SearchView searchView;
    private SwitchCompat switchLocaiton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        initInstances();
        setupToolbar();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.search_main_title);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void initInstances() {
        searchButton = findViewById(R.id.btn_search);
        searchButton.setOnClickListener(onClickListener);
        ckbAttraction = findViewById(R.id.ckbAttraction);
        ckbAccommodation = findViewById(R.id.ckbAccommodation);
        ckbRestaurant = findViewById(R.id.ckbRestaurant);
        ckbShop = findViewById(R.id.ckbShop);
        ckbOther = findViewById(R.id.ckbOther);
        searchView = findViewById(R.id.searchView);
        switchLocaiton = findViewById(R.id.switch_location);
    }

    private TATCategory[] getSelectedCategory() {

         // count the number of selected check box

        int count = 0;
        if (ckbAttraction.isChecked()) {
            count++;
        }
        if (ckbAccommodation.isChecked()) {
            count++;
        }
        if (ckbRestaurant.isChecked()) {
            count++;
        }
        if (ckbShop.isChecked()) {
            count++;
        }
        if (ckbOther.isChecked()) {
            count++;
        }


         // create array of TAT CATEGORY from count amount

        TATCategory[] list = new TATCategory[count];
        int index = 0;

        if (ckbAttraction.isChecked()) {
            list[index] = TATCategory.ATTRACTION;
            index++;
        }
        if (ckbAccommodation.isChecked()) {
            list[index] = TATCategory.ACCOMMODATION;
            index++;
        }
        if (ckbRestaurant.isChecked()) {
            list[index] = TATCategory.RESTAURANT;
            index++;
        }
        if (ckbShop.isChecked()) {
            list[index] = TATCategory.SHOP;
            index++;
        }
        if (ckbOther.isChecked()) {
            list[index] = TATCategory.OTHER;
        }
        return list;
    }

    private void search(String keyword, TATCategory[] selectedCategory) {
        TATPlacesSearchParameter param = new TATPlacesSearchParameter(keyword, TATLanguage.ENGLISH);

        // category of interest place
        param.setCategories(selectedCategory);

        //take first 10 result
        param.setNumberOfResult(10);


         // set radius 10 kilometers

        param.setRadius(10);


         //set search center location
        if (switchLocaiton.isChecked()) {
            param.setLatitude(13.74918);
            param.setLongitude(100.55785);
        }

        TATPlacesSearch.executeAsync(param, new ServiceRequestListener<TATPlacesSearchResultSet>() {
            @Override
            public void onResponse(TATPlacesSearchResultSet result) {

                ArrayList<SearchResult> resultList = new ArrayList<>();

                for (TATPlacesSearchResult item : result.getResults()) {
                    TATLocation location = item.getLocation();

                    resultList.add(new SearchResult(
                            item.getId(),
                            item.getName(),
                            SearchResult.sortAddress(
                                    location.getAddress(),
                                    location.getSubDistrict(),
                                    location.getDistrict(),
                                    location.getProvince()),
                            SearchResult.distanceToUnit(item.getDistance()),
                            item.getCategoryName(),
                            item.getThumbnail()));
                }
                SearchResults searchResults = new SearchResults(resultList);

                /**sent result to result page*/
                Intent intent = new Intent(getBaseContext(), SearchResultActivity.class);
                intent.putExtra(SEARCH_RESULT, searchResults);
                startActivity(intent);
            }

            @Override
            public void onError(String errorMessage, int statusCode) {
                Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), SearchResultActivity.class);
                startActivity(intent);
            }
        });
    }


    /////////////////////////////View Listener///////////////////////////
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            search(searchView.getQuery().toString(), getSelectedCategory());
        }
    };


}
