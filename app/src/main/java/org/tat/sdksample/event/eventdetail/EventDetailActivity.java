package org.tat.sdksample.event.eventdetail;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import org.th.tatsdk.event.detail.TATGetEventDetail;
import org.th.tatsdk.event.detail.TATGetEventDetailParameter;
import org.th.tatsdk.event.detail.TATGetEventDetailResult;

public class EventDetailActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private View name;
    private View province;
    private View eventType;
    private View tel;
    private View web;
    private View detail;
    private TextView tvNoResult;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        setupToolbar();

        progressBar = findViewById(R.id.progress);
        name = findViewById(R.id.name);
        province = findViewById(R.id.address);
        eventType = findViewById(R.id.type);
        tel = findViewById(R.id.tel);
        web = findViewById(R.id.url);
        detail = findViewById(R.id.detail);
        tvNoResult = findViewById(R.id.tv_event_detail_not_found);
        scrollView = findViewById(R.id.scrollView2);

        ((TextView) name.findViewById(R.id.tv_detail_title)).setText("Name");
        ((TextView) province.findViewById(R.id.tv_detail_title)).setText("Province");
        ((TextView) eventType.findViewById(R.id.tv_detail_title)).setText("Event Type");
        ((TextView) tel.findViewById(R.id.tv_detail_title)).setText("Tel.");
        ((TextView) web.findViewById(R.id.tv_detail_title)).setText("Website");
        ((TextView) detail.findViewById(R.id.tv_detail_title)).setText("Detail");

        String eventId = getIntent().getStringExtra("eventId");

        progressBar.setVisibility(View.VISIBLE);
        TATGetEventDetailParameter parameter = new TATGetEventDetailParameter(eventId, TATLanguage.ENGLISH);
        TATGetEventDetail.executeAsync(parameter, new ServiceRequestListener<TATGetEventDetailResult>() {
            @Override
            public void onResponse(TATGetEventDetailResult result) {
                scrollView.setVisibility(View.VISIBLE);
                tvNoResult.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (result.getName() != null && !result.getName().isEmpty()) {
                    ((TextView) name.findViewById(R.id.tv_detail_content)).setText(result.getName());
                } else {
                    ((TextView) name.findViewById(R.id.tv_detail_content)).setText("-");
                }

                if (result.getProvince() != null && !result.getProvince().isEmpty()) {
                    ((TextView) province.findViewById(R.id.tv_detail_content)).setText(result.getProvince());
                } else {
                    ((TextView) province.findViewById(R.id.tv_detail_content)).setText("-");
                }

                if (result.getInfo() != null && result.getInfo().getEventType().length > 0) {
                    ((TextView) eventType.findViewById(R.id.tv_detail_content)).setText(result.getInfo().getEventType()[0]);
                } else {
                    ((TextView) eventType.findViewById(R.id.tv_detail_content)).setText("-");
                }
                if (result.getInfo() != null && !result.getInfo().getHtmlDetail().isEmpty()) {
                    ((TextView) detail.findViewById(R.id.tv_detail_content)).setText(htmlToString(result.getInfo().getHtmlDetail()));
                } else {
                    ((TextView) detail.findViewById(R.id.tv_detail_content)).setText("-");
                }

                if (result.getTATContact() != null && result.getTATContact().getPhones().length > 0) {
                    ((TextView) tel.findViewById(R.id.tv_detail_content)).setText(result.getTATContact().getPhones()[0]);
                } else {
                    ((TextView) tel.findViewById(R.id.tv_detail_content)).setText("-");
                }

                if (result.getTATContact() != null && result.getTATContact().getUrls().length > 0) {
                    ((TextView) web.findViewById(R.id.tv_detail_content)).setText(result.getTATContact().getUrls()[0]);
                } else {
                    ((TextView) web.findViewById(R.id.tv_detail_content)).setText("-");
                }
            }

            @Override
            public void onError(String errorMessage, int statusCode) {
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                tvNoResult.setVisibility(View.VISIBLE);
                Toast.makeText(EventDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.event_detail);
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

    private Spanned htmlToString(String html) {
        return Html.fromHtml(html);
    }
}
