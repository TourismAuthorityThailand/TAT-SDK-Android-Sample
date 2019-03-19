package org.tat.sdksample.placesearch.search;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.tat.sdksample.R;
import org.tat.sdksample.placesearch.detail.DetailActivity;
import org.tat.sdksample.placesearch.inteface.SearchResultClickListener;
import org.tat.sdksample.placesearch.model.SearchResults;
import org.tat.sdksample.recommendedroute.model.RecRoutes;

public class SearchResultActivity extends AppCompatActivity implements SearchResultClickListener {


    private static String PLACE_ID = "PLACE_ID";
    private static String PLACE_NAME = "PLACE_NAME";
    private static String PLACE_CATEGORY = "PLACE_CATEGORY";
    private SearchResults resultList = null;
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private TextView tvNoresult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        Intent intent = getIntent();
        if (intent != null) {
            resultList = getIntent().getParcelableExtra(MainSearchActivity.SEARCH_RESULT);
        }
        initInstances();
        setupToolbar();
    }

    private void initInstances() {
        recyclerView = findViewById(R.id.rcv_list_result);
        tvNoresult = findViewById(R.id.tvNoresult);
        adapter = new SearchResultAdapter(this);
        recyclerView.setAdapter(adapter);
        if (resultList!=null){
        adapter.updateListItem(resultList);
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
        }else {
            recyclerView.setVisibility(View.GONE);
            tvNoresult.setVisibility(View.VISIBLE);
        }
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.result_title);
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


    private void navigateToDetail(String id, String category) {
        Intent i = new Intent(getBaseContext(), DetailActivity.class);
        i.putExtra(PLACE_ID, id);
        i.putExtra(PLACE_CATEGORY, category);
        startActivity(i);
    }

    ////////////////////////Listener/////////////////////////////////

    @Override
    public void onItemClickListener(String id, String category) {
        navigateToDetail(id, category);
    }

}
