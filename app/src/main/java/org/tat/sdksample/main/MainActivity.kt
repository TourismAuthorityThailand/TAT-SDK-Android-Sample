package org.tat.sdksample.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import org.tat.sdksample.R
import org.tat.sdksample.event.eventlist.MainEventActivity
import org.tat.sdksample.news.newslist.NewsActivity
import org.tat.sdksample.placesearch.search.MainSearchActivity
import org.tat.sdksample.recommendedroute.search.RecommendedRouteActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstances()
        setupToolBar()
    }

    private fun setupToolBar() {
        val actionBar = supportActionBar
        actionBar?.setTitle(R.string.main_title)
    }

    private fun initInstances() {
        val buttonSearch = findViewById<View>(R.id.buttonSearchPlace) as Button
        val buttonEvent = findViewById<View>(R.id.buttonEventAndFestival) as Button
        val buttonNews = findViewById<View>(R.id.buttonNews) as Button
        val buttonRecRoute = findViewById<View>(R.id.buttonRecRoute) as Button

        buttonSearch.setOnClickListener {
            val intent = Intent(baseContext, MainSearchActivity::class.java)
            startActivity(intent)
        }

        buttonEvent.setOnClickListener {
            val intent = Intent(baseContext, MainEventActivity::class.java)
            startActivity(intent)
        }

        buttonNews.setOnClickListener {
            val intent = Intent(baseContext, NewsActivity::class.java)
            startActivity(intent)
        }

        buttonRecRoute.setOnClickListener {
            val intent = Intent(baseContext, RecommendedRouteActivity::class.java)
            startActivity(intent)
        }

    }

}
