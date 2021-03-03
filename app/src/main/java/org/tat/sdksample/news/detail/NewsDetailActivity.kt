package org.tat.sdksample.news.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import org.tat.sdksample.R
import org.th.tatsdk.ServiceRequestListener
import org.th.tatsdk.common.TATLanguage
import org.th.tatsdk.news.TATGetNewsDetailParameter
import org.th.tatsdk.news.TATGetNewsDetailResult
import org.th.tatsdk.news.TATNews

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var name: View
    private lateinit var date : View
    private lateinit var address: View
    private lateinit var url: View
    private lateinit var detail: View
    internal var newsId = ""
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        if (intent != null) {
            newsId = intent.getStringExtra("newsId")?:""
        }
        setupToolbar()
        initInstances()
        loadDetail()
    }

    private fun initInstances() {
        name = findViewById(R.id.name)
        date = findViewById(R.id.date)
        address = findViewById(R.id.address)
        url = findViewById(R.id.url)
        detail = findViewById(R.id.detail)
        progress = findViewById(R.id.progress_news_detail)
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.new_detail)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun loadDetail() {
        progress.visibility = View.VISIBLE
        val parameter = TATGetNewsDetailParameter(newsId, TATLanguage.ENGLISH)
        TATNews.getDetailAsync(parameter, object : ServiceRequestListener<TATGetNewsDetailResult> {
            override fun onResponse(result: TATGetNewsDetailResult?) {
                result?.let {
                progress.visibility = View.GONE
                val tvNameTitle = name.findViewById<TextView>(R.id.tv_detail_title)
                tvNameTitle.text = "Name"
                val tvNameContent = name.findViewById<TextView>(R.id.tv_detail_content)
                setTextIfNotNullOrEmpty(result.name, tvNameContent)

                val tvDateTitle = date.findViewById<TextView>(R.id.tv_detail_title)
                tvDateTitle.text = "Publish date"
                val tvDateContent = date.findViewById<TextView>(R.id.tv_detail_content)
                setTextIfNotNullOrEmpty(result.publishedDate?.time.toString(), tvDateContent)

                val tvLocationTitle = address.findViewById<TextView>(R.id.tv_detail_title)
                tvLocationTitle.text = "Location"
                val tvLocationContent = address.findViewById<TextView>(R.id.tv_detail_content)
                setTextIfNotNullOrEmpty(result.location, tvLocationContent)

                val tvUrlTitle = url.findViewById<TextView>(R.id.tv_detail_title)
                tvUrlTitle.text = "Website"
                val tvUrlContent = url.findViewById<TextView>(R.id.tv_detail_content)
                if (result.urls != null && result.urls?.size?:0 > 0) {
                    setTextIfNotNullOrEmpty(result.urls?.get(0), tvUrlContent)
                } else {
                    tvUrlContent.text = "-"
                }

                val tvDetailTitle = detail.findViewById<TextView>(R.id.tv_detail_title)
                tvDetailTitle.text = "Detail"
                val tvDetailContent = detail.findViewById<TextView>(R.id.tv_detail_content)
                val spanned = htmlToString(result.htmlDetail)
                setSpannedIfNotNullOrEmpty(spanned, tvDetailContent)
                }
            }

            override fun onError(errorMessage: String?, statusCode: Int) {
                progress.visibility = View.GONE
                val tvNotFound = findViewById<TextView>(R.id.tv_news_detail_not_found)
                tvNotFound.visibility = View.VISIBLE
                val scrollViewDetail = findViewById<ScrollView>(R.id.scrollView_detail)
                scrollViewDetail.visibility = View.GONE
                Toast.makeText(baseContext, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setTextIfNotNullOrEmpty(text: String?, view: TextView) {
        if (text != null && text != "") {
            view.text = text
        } else {
            view.text = "-"
        }
    }

    private fun setSpannedIfNotNullOrEmpty(text: Spanned?, view: TextView) {
        if (text != null && text.toString() != "") {
            view.text = text
        } else {
            view.text = "-"
        }
    }

    private fun htmlToString(html: String): Spanned {
        return Html.fromHtml(html)
    }

}
