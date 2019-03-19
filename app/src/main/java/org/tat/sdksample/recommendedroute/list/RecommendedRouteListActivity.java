package org.tat.sdksample.recommendedroute.list;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import org.tat.sdksample.R;
import org.tat.sdksample.recommendedroute.model.RecRoutes;

public class RecommendedRouteListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecRoutes recRoutes = null ;
    RecommendedRouteListAdapter adapter;
    TextView noresultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        if (getIntent()!=null){
            recRoutes =getIntent().getParcelableExtra("route_list");
        }
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

    private void initInstances(){
        adapter = new RecommendedRouteListAdapter();
        recyclerView = findViewById(R.id.rcv_list_result);
        noresultText = findViewById(R.id.tvNoresult);
        recyclerView.setAdapter(adapter);
        if (recRoutes!=null){
            adapter.updateListItem(recRoutes);
        }else {
            noresultText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        recyclerView.addItemDecoration(
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
    }
}
