package org.tat.sdksample.placesearch.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.tat.sdksample.R;
import org.tat.sdksample.placesearch.inteface.DetailLoadingListener;
import org.tat.sdksample.placesearch.manager.PlaceDetailManager;
import org.tat.sdksample.placesearch.model.Detail;

public class DetailActivity extends AppCompatActivity
        implements DetailLoadingListener {

    private static String PLACE_ID = "PLACE_ID";
    private static String PLACE_CATEGORY = "PLACE_CATEGORY";
    private String placeName = "";
    private String placeId = "";
    private String placeCategory = "";
    private PlaceDetailManager placeDetailManager;
    private TextView tvNotFound;
    private ScrollView scrollViewContent;
    private View itemName;
    private View itemAddress;
    private View itemDetail;
    private View itemTel;
    private View itemUrl;
    private View itemFacilities;
    private View itemServices;
    private View itemPaymentOptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initInstance();
        setupToolbar();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.detail_title);
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

    private void initInstance() {
        Intent intent = getIntent();
        if (intent != null) {
            placeCategory = intent.getStringExtra(PLACE_CATEGORY);
            placeId = intent.getStringExtra(PLACE_ID);
        }
        itemName = findViewById(R.id.itemName);
        itemAddress = findViewById(R.id.itemAddress);
        itemDetail = findViewById(R.id.itemDetail);
        itemTel = findViewById(R.id.itemTel);
        itemUrl = findViewById(R.id.itemUrl);
        itemFacilities = findViewById(R.id.itemFacilities);
        itemServices = findViewById(R.id.itemService);
        itemPaymentOptions = findViewById(R.id.itemPayment);
        tvNotFound = findViewById(R.id.tv_detail_not_found);
        scrollViewContent = findViewById(R.id.scrollView_detail);
        placeDetailManager = new PlaceDetailManager(this);

        if (placeId == null || placeCategory == null) {
            showPlaceNotFound();
        } else {
            placeDetailManager.loadPlaceDetail(placeId, placeCategory);
        }

    }

    private void showPlaceNotFound() {
        tvNotFound.setVisibility(View.VISIBLE);
        scrollViewContent.setVisibility(View.GONE);
    }

    private void showDetail(Detail detail) {
        setItemText(itemName, "Name", detail.getPlaceName());
        setItemText(itemAddress, "Address", detail.getPlaceAddress());
        setItemText(itemDetail, "Detail", detail.getPlaceDetail());
        setItemText(itemTel, "Tel", detail.getPlaceTel());
        setItemText(itemUrl, "Website", detail.getPlaceWebsite());
        setItemText(itemFacilities, "Facilities", detail.getPlaceFacilities());
        setItemText(itemServices, "Services", detail.getPlaceServices());
        setItemText(itemPaymentOptions, "Payment options", detail.getPlacePaymentOptions());
    }

    private void setItemText(View v, String title, String content) {
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_detail_title);
        TextView tvContent = (TextView) v.findViewById(R.id.tv_detail_content);
        tvTitle.setText(title);
        if (!content.isEmpty()) {
            tvContent.setText(content);
        } else {
            tvContent.setText("-");
        }
    }


    @Override
    public void onSucceed(Detail detail) {
        showDetail(detail);
    }

    @Override
    public void onError(String message, int errorCode) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
        showPlaceNotFound();
    }
}
