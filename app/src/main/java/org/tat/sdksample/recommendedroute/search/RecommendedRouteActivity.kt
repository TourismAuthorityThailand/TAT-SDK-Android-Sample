package org.tat.sdksample.recommendedroute.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import org.tat.sdksample.R
import org.tat.sdksample.recommendedroute.list.RecommendedRouteListActivity
import org.tat.sdksample.recommendedroute.model.RecRoutes
import org.th.tatsdk.ServiceRequestListener
import org.th.tatsdk.common.TATGeolocation
import org.th.tatsdk.common.TATLanguage
import org.th.tatsdk.common.TATRegion
import org.th.tatsdk.route.TATFindRoutesParameter
import org.th.tatsdk.route.TATFindRoutesResultSet
import org.th.tatsdk.route.TATRecommendedRoutes

class RecommendedRouteActivity : AppCompatActivity() {

    private lateinit var spinnerDay: Spinner
    private lateinit var spinnerRegion: Spinner
    private lateinit var swNearby: Switch
    private lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended_route)
        initInstances()
        setupToolbar()
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.recommended_routes)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> {
            }
        }
        return true
    }

    private fun initInstances() {
        spinnerDay = findViewById(R.id.spinnerDay)
        spinnerRegion = findViewById(R.id.spinnerRegion)
        swNearby = findViewById(R.id.swNearbyRecRoute)
        btnSearch = findViewById(R.id.btn_search_rectoute)
        btnSearch.setOnClickListener { loadRecRoute() }
        val days = resources.getStringArray(R.array.day)
        spinnerDay.adapter = ArrayAdapter(baseContext, android.R.layout.simple_list_item_1, days)
        val region = resources.getStringArray(R.array.region)
        spinnerRegion.adapter = ArrayAdapter(baseContext, android.R.layout.simple_list_item_1, region)

    }

    private fun loadRecRoute() {
        val param = TATFindRoutesParameter(TATLanguage.ENGLISH)
        val region = spinnerRegion.selectedItem.toString()
        val day = spinnerDay.selectedItemPosition
        if (region != "All") {
            val selectedRegion = stringToRegion(region)
            if (selectedRegion != null) {
                param.region = selectedRegion
            }
        }

        //set number of trip day
        if (day != 0) {
            param.numberOfDays = day
        }

        //set search center location
        if (swNearby.isChecked) {
            param.geolocation = TATGeolocation(13.74918,100.55785)
        }

        TATRecommendedRoutes.findAsync(param, object : ServiceRequestListener<TATFindRoutesResultSet> {

            override fun onResponse(result: TATFindRoutesResultSet?) {
                result?.results?.let {
                    val res = RecRoutes().transform(it)
                    val intent = Intent(baseContext, RecommendedRouteListActivity::class.java)
                    intent.putExtra("route_list", res)
                    startActivity(intent)
                }?:let {
                    val intent = Intent(baseContext, RecommendedRouteListActivity::class.java)
                    startActivity(intent)
                }

            }

            override fun onError(errorMessage: String?, statusCode: Int) {
                val intent = Intent(baseContext, RecommendedRouteListActivity::class.java)
                startActivity(intent)
            }
        })

    }

    private fun stringToRegion(selectedRegion: String): TATRegion? {
        var region: TATRegion? = null
        when (selectedRegion) {
            "Northern" -> {
                region = TATRegion.NORTHERN
                return region
            }
            "Northeastern" -> {
                region = TATRegion.NORTHEASTERN
                return region
            }
            "Western" -> {
                region = TATRegion.WESTERN
                return region
            }
            "Central" -> {
                region = TATRegion.CENTRAL
                return region
            }
            "Eastern" -> {
                region = TATRegion.EASTERN
                return region
            }
            "Southern" -> {
                region = TATRegion.SOUTHERN
                return region
            }
            else -> return null
        }
    }

}
