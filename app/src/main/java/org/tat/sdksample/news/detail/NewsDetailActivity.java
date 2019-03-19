package org.tat.sdksample.news.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.tat.sdksample.R;
import org.th.tatsdk.ServiceRequestListener;
import org.th.tatsdk.common.TATLanguage;
import org.th.tatsdk.news.detail.TATGetNewsDetail;
import org.th.tatsdk.news.detail.TATGetNewsDetailParameter;
import org.th.tatsdk.news.detail.TATGetNewsDetailResult;

public class NewsDetailActivity extends AppCompatActivity {

    View name;
    View date;
    View address;
    View url;
    View detail;
    String newsId = "";
    ProgressBar progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        if (getIntent() != null) {
            newsId = getIntent().getStringExtra("newsId");
        }
        setupToolbar();
        initInstances();
        loadDetail();
    }

    private void initInstances() {
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        address = findViewById(R.id.address);
        url = findViewById(R.id.url);
        detail = findViewById(R.id.detail);
        progress = findViewById(R.id.progress_news_detail);
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.new_detail);
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

    private void loadDetail() {
        progress.setVisibility(View.VISIBLE);
        TATGetNewsDetailParameter parameter = new TATGetNewsDetailParameter(newsId, TATLanguage.ENGLISH);
        TATGetNewsDetail.executeAsync(parameter, new ServiceRequestListener<TATGetNewsDetailResult>() {
            @Override
            public void onResponse(TATGetNewsDetailResult result) {
                progress.setVisibility(View.GONE);
                TextView tvNameTitle = name.findViewById(R.id.tv_detail_title);
                tvNameTitle.setText("Name");
                TextView tvNameContent = name.findViewById(R.id.tv_detail_content);
                setTextIfNotNullOrEmpty(result.getName(), tvNameContent);

                TextView tvDateTitle = date.findViewById(R.id.tv_detail_title);
                tvDateTitle.setText("Publish date");
                TextView tvDateContent = date.findViewById(R.id.tv_detail_content);
                setTextIfNotNullOrEmpty(result.getPublishDate(), tvDateContent);

                TextView tvLocationTitle = address.findViewById(R.id.tv_detail_title);
                tvLocationTitle.setText("Location");
                TextView tvLocationContent = address.findViewById(R.id.tv_detail_content);
                setTextIfNotNullOrEmpty(result.getLocation(), tvLocationContent);

                TextView tvUrlTitle = url.findViewById(R.id.tv_detail_title);
                tvUrlTitle.setText("Website");
                TextView tvUrlContent = url.findViewById(R.id.tv_detail_content);
                if (result.getUrls() != null && result.getUrls().length > 0) {
                    setTextIfNotNullOrEmpty(result.getUrls()[0], tvUrlContent);
                } else {
                    tvUrlContent.setText("-");
                }

                TextView tvDetailTitle = detail.findViewById(R.id.tv_detail_title);
                tvDetailTitle.setText("Detail");
                TextView tvDetailContent = detail.findViewById(R.id.tv_detail_content);
                Spanned spanned = htmlToString(result.getHtmlDetail());
                setSpannedIfNotNullOrEmpty(spanned, tvDetailContent);

            }

            @Override
            public void onError(String errorMessage, int statusCode) {
                progress.setVisibility(View.GONE);
                TextView tvNotFound = findViewById(R.id.tv_news_detail_not_found);
                tvNotFound.setVisibility(View.VISIBLE);
                ScrollView scrollViewDetail = findViewById(R.id.scrollView_detail);
                scrollViewDetail.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTextIfNotNullOrEmpty(String text, TextView view) {
        if (text != null && !text.equals("")) {
            view.setText(text);
        } else {
            view.setText("-");
        }
    }

    private void setSpannedIfNotNullOrEmpty(Spanned text, TextView view) {
        if (text != null && !text.equals("")) {
            view.setText(text);
        } else {
            view.setText("-");
        }
    }

    private Spanned htmlToString(String html) {
        return Html.fromHtml(html);
    }

}
