package org.tat.sdksample.news.newslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import org.tat.sdksample.R;
import org.th.tatsdk.ServiceRequestListener;
import org.th.tatsdk.common.TATLanguage;
import org.th.tatsdk.news.list.TATGetNews;
import org.th.tatsdk.news.list.TATGetNewsParameter;
import org.th.tatsdk.news.list.TATGetNewsResult;
import org.th.tatsdk.news.list.TATGetNewsResultSet;


public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvNoResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);
        setupToolbar();
        recyclerView = findViewById(R.id.rcv_list_result);
        tvNoResult = findViewById(R.id.tvNoresult);
        fetchNews();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.news);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchNews(){
        TATGetNewsParameter parameter = new TATGetNewsParameter(TATLanguage.ENGLISH);
        TATGetNews.executeAsync(parameter, new ServiceRequestListener<TATGetNewsResultSet>() {
            @Override
            public void onResponse(TATGetNewsResultSet result) {
                tvNoResult.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                TATGetNewsResult[] resultSet =   result.getResults();
                recyclerView.setAdapter(new NewsAdapter(resultSet));
            }

            @Override
            public void onError(String errorMessage, int statusCode) {
                tvNoResult.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(),errorMessage,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

