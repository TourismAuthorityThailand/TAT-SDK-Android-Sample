package org.tat.sdksample.recommendedroute.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import org.tat.sdksample.R;
import org.th.tatsdk.ServiceRequestListener;
import org.th.tatsdk.common.TATLanguage;
import org.th.tatsdk.route.detail.TATGetRouteDetail;
import org.th.tatsdk.route.detail.TATGetRouteDetailParameter;
import org.th.tatsdk.route.detail.TATGetRouteDetailResult;

public class RouteDetailActivity extends AppCompatActivity {

    RecyclerView rcv;
    String routeId = "";
    RouteDetailAdapter adapter;
    TextView noResultText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        if (getIntent() != null) {
            routeId = getIntent().getStringExtra("rout_id");
        }
        setupToolbar();
        initInstances();
        loadDetail();
    }


    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.route_detail_title);
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

    private void initInstances() {
        adapter = new RouteDetailAdapter();
        rcv = findViewById(R.id.rcv_list_result);
        noResultText = findViewById(R.id.tvNoresult);
        rcv.setAdapter(adapter);
    }

    private void loadDetail() {
        TATGetRouteDetailParameter param = new TATGetRouteDetailParameter(routeId, TATLanguage.ENGLISH);
        TATGetRouteDetail.executeAsync(param, new ServiceRequestListener<TATGetRouteDetailResult>() {
            @Override
            public void onResponse(TATGetRouteDetailResult result) {
                adapter.updateListItem(result);
            }

            @Override
            public void onError(String errorMessage, int statusCode) {
                noResultText.setVisibility(View.VISIBLE);
                rcv.setVisibility(View.GONE);
            }
        });
    }
}
