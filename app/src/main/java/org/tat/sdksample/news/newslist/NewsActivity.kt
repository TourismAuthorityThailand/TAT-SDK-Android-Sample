package org.tat.sdksample.news.newslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.tat.sdksample.R
import org.th.tatsdk.ServiceRequestListener
import org.th.tatsdk.common.TATLanguage
import org.th.tatsdk.news.TATFeedNewsParameter
import org.th.tatsdk.news.TATFeedNewsResultSet
import org.th.tatsdk.news.TATNews


class NewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var tvNoResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_result)
        setupToolbar()
        recyclerView = findViewById(R.id.rcv_list_result)
        tvNoResult = findViewById(R.id.tvNoresult)
        fetchNews()
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.news)
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

    private fun fetchNews() {
        val parameter = TATFeedNewsParameter(TATLanguage.ENGLISH)
        TATNews.feedAsync(parameter, object : ServiceRequestListener<TATFeedNewsResultSet> {
            override fun onResponse(result: TATFeedNewsResultSet?) {
                result?.let {
                    tvNoResult.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    val resultSet = result.results
                    recyclerView.adapter = NewsAdapter(resultSet)
                }
            }

            override fun onError(errorMessage: String?, statusCode: Int) {
                tvNoResult.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                //Toast.makeText(this@NewsActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

