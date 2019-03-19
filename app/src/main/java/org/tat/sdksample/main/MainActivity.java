package org.tat.sdksample.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.tat.sdksample.R;
import org.tat.sdksample.event.eventlist.MainEventActivity;
import org.tat.sdksample.news.newslist.NewsActivity;
import org.tat.sdksample.placesearch.search.MainSearchActivity;
import org.tat.sdksample.recommendedroute.search.RecommendedRouteActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstances();
        setupToolBar();
    }

    private void setupToolBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.main_title);
        }
    }

    private void initInstances(){
        Button buttonSearch = (Button)findViewById(R.id.buttonSearchPlace);
        Button buttonEvent = (Button)findViewById(R.id.buttonEventAndFestival);
        Button buttonNews = (Button)findViewById(R.id.buttonNews);
        Button buttonRecRoute = (Button)findViewById(R.id.buttonRecRoute);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getBaseContext(),MainSearchActivity.class);
               startActivity(intent);
            }
        });

        buttonEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainEventActivity.class);
                startActivity(intent);
            }
        });

        buttonNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NewsActivity.class);
                startActivity(intent);
            }
        });

        buttonRecRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RecommendedRouteActivity.class);
                startActivity(intent);
            }
        });

    }

}
