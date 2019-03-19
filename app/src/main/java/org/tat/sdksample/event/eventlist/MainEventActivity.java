package org.tat.sdksample.event.eventlist;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.tat.sdksample.R;
import org.th.tatsdk.ServiceRequestListener;
import org.th.tatsdk.common.TATLanguage;
import org.th.tatsdk.common.TATSort;
import org.th.tatsdk.event.list.TATGetEvents;
import org.th.tatsdk.event.list.TATGetEventsParameter;
import org.th.tatsdk.event.list.TATGetEventsResultSet;

public class MainEventActivity extends AppCompatActivity {

    private final double LAT = 13.74918;
    private final double LNG = 100.55785;

    private SwipeRefreshLayout swipe;
    private RecyclerView recycler;
    private TATGetEventsParameter parameter;
    private TextView tvSort;
    private TextView tvNoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);
        setupToolbar();
        swipe = findViewById(R.id.swipe);
        tvSort = findViewById(R.id.tv_sort_by);
        tvNoResult = findViewById(R.id.tvEventNoresult);
        recycler = findViewById(R.id.recyclerList);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(
                new DividerItemDecoration(getBaseContext(), LinearLayoutManager.VERTICAL) {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        int position = parent.getChildAdapterPosition(view);
                        if (position == parent.getAdapter().getItemCount() - 1) {
                            outRect.setEmpty();
                        } else {
                            super.getItemOffsets(outRect, view, parent, state);
                        }
                    }
                }
        );
        parameter = new TATGetEventsParameter(TATLanguage.ENGLISH);
        parameter.setLatitude(LAT);     //Optional
        parameter.setLongitude(LNG);    //Optional

        loadEvent();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadEvent();
            }
        });
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.event_and_festival);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item_time:
                parameter.setSort(TATSort.DATE);
                tvSort.setText("Date");
                loadEvent();
                return true;
            case R.id.item_distance:
                parameter.setSort(TATSort.DISTANCE);
                tvSort.setText("Distance");
                loadEvent();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadEvent() {
        swipe.setRefreshing(true);
        TATGetEvents.executeAsync(parameter, new ServiceRequestListener<TATGetEventsResultSet>() {
            @Override
            public void onResponse(TATGetEventsResultSet result) {
                swipe.setRefreshing(false);
                swipe.setVisibility(View.VISIBLE);
                tvNoResult.setVisibility(View.GONE);
                EventAdapter adapter = new EventAdapter(result.getResults());
                recycler.setAdapter(adapter);
            }

            @Override
            public void onError(String errorMessage, int statusCode) {
                swipe.setRefreshing(false);
                tvNoResult.setVisibility(View.VISIBLE);
                swipe.setVisibility(View.GONE);
                Toast.makeText(MainEventActivity.this, errorMessage + statusCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
}