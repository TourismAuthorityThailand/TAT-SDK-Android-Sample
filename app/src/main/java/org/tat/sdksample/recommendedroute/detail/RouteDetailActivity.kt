package org.tat.sdksample.recommendedroute.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import org.tat.sdksample.R
import org.th.tatsdk.ServiceRequestListener
import org.th.tatsdk.common.TATLanguage
import org.th.tatsdk.route.TATGetRouteDetailParameter
import org.th.tatsdk.route.TATGetRouteDetailResult
import org.th.tatsdk.route.TATRecommendedRoutes

class RouteDetailActivity : AppCompatActivity() {

    private lateinit var rcv: androidx.recyclerview.widget.RecyclerView
    private var routeId = ""
    private lateinit var adapter: RouteDetailAdapter
    private lateinit var noResultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_result)
        if (intent != null) {
            routeId = intent.getStringExtra("rout_id")
        }
        setupToolbar()
        initInstances()
        loadDetail()
    }


    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.route_detail_title)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun initInstances() {
        adapter = RouteDetailAdapter()
        rcv = findViewById(R.id.rcv_list_result)
        noResultText = findViewById(R.id.tvNoresult)
        rcv.adapter = adapter
    }

    private fun loadDetail() {
        val param = TATGetRouteDetailParameter(routeId, TATLanguage.ENGLISH)
        TATRecommendedRoutes.getDetailAsync(param, object : ServiceRequestListener<TATGetRouteDetailResult> {
            override fun onResponse(result: TATGetRouteDetailResult?) {
                result?.let {
                    adapter.updateListItem(it)
                }
            }

            override fun onError(errorMessage: String?, statusCode: Int) {
                noResultText.visibility = View.VISIBLE
                rcv.visibility = View.GONE
            }
        })
    }
}
