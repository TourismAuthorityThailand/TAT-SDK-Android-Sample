package org.tat.sdksample.placesearch.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import org.tat.sdksample.R
import org.tat.sdksample.placesearch.model.SearchResult
import org.tat.sdksample.placesearch.model.SearchResults
import org.th.tatsdk.ServiceRequestListener
import org.th.tatsdk.common.TATCategoryCode
import org.th.tatsdk.common.TATGeolocation
import org.th.tatsdk.common.TATLanguage
import org.th.tatsdk.search.TATPlaces
import org.th.tatsdk.search.TATPlacesSearchParameter
import org.th.tatsdk.search.TATPlacesSearchResultSet
import java.util.*

class MainSearchActivity : AppCompatActivity() {
    private var searchButton: Button? = null
    private var ckbAttraction: AppCompatCheckBox? = null
    private var ckbAccommodation: AppCompatCheckBox? = null
    private var ckbRestaurant: AppCompatCheckBox? = null
    private var ckbShop: AppCompatCheckBox? = null
    private var ckbOther: AppCompatCheckBox? = null
    private var searchView: SearchView? = null
    private var switchLocation: SwitchCompat? = null

    // create array of TAT CATEGORY from checked checkBox
    private val selectedCategory: Array<TATCategoryCode>
        get() {
            val list = mutableListOf<TATCategoryCode>()

            if (ckbAttraction!!.isChecked) {
                list.add(TATCategoryCode.ATTRACTION)
            }
            if (ckbAccommodation!!.isChecked) {
                list.add(TATCategoryCode.ACCOMMODATION)
            }
            if (ckbRestaurant!!.isChecked) {
                list.add(TATCategoryCode.RESTAURANT)

            }
            if (ckbShop!!.isChecked) {
                list.add(TATCategoryCode.SHOP)
            }
            if (ckbOther!!.isChecked) {
                list.add(TATCategoryCode.OTHER)
            }
            return list.toTypedArray()
        }


    /////////////////////////////View Listener///////////////////////////
    private val onClickListener = View.OnClickListener { search(searchView!!.query.toString(), selectedCategory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_search)
        initInstances()
        setupToolbar()
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.search_main_title)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    private fun initInstances() {
        searchButton = findViewById(R.id.btn_search)
        searchButton!!.setOnClickListener(onClickListener)
        ckbAttraction = findViewById(R.id.ckbAttraction)
        ckbAccommodation = findViewById(R.id.ckbAccommodation)
        ckbRestaurant = findViewById(R.id.ckbRestaurant)
        ckbShop = findViewById(R.id.ckbShop)
        ckbOther = findViewById(R.id.ckbOther)
        searchView = findViewById(R.id.searchView)
        switchLocation = findViewById(R.id.switch_location)
    }

    private fun search(keyword: String, selectedCategory: Array<TATCategoryCode>) {
        val param = TATPlacesSearchParameter(keyword, TATLanguage.ENGLISH)

        // category of interest place
        param.categoryCodes = selectedCategory

        //take first 10 result
        param.numberOfResult = 10


        // set radius 10 kilometers
        param.searchRadius = 10

        //set search center location
        if (switchLocation!!.isChecked) {
            param.geolocation = TATGeolocation(13.74918, 100.55785)
        }

        TATPlaces.searchAsync(param, object : ServiceRequestListener<TATPlacesSearchResultSet> {
            override fun onResponse(result: TATPlacesSearchResultSet?) {

                val resultList = ArrayList<SearchResult>()
                result?.results?.let {
                    for (item in result.results) {
                        val location = item.location

                        resultList.add(SearchResult(
                                item.id,
                                item.name,
                                SearchResult.sortAddress(
                                        location?.address,
                                        location?.subDistrict,
                                        location?.district,
                                        location?.province),
                                SearchResult.distanceToUnit(item.distance),
                                item.categoryName,
                                item.thumbnailUrl))
                    }
                    val searchResults = SearchResults(resultList)

                    /**sent result to result page */
                    val intent = Intent(baseContext, SearchResultActivity::class.java)
                    intent.putExtra(SEARCH_RESULT, searchResults)
                    startActivity(intent)
                } ?: let {
                    val intent = Intent(baseContext, SearchResultActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onError(errorMessage: String?, statusCode: Int) {
                Toast.makeText(baseContext, errorMessage, Toast.LENGTH_SHORT).show()
                val intent = Intent(baseContext, SearchResultActivity::class.java)
                startActivity(intent)
            }
        })
    }

    companion object {

        var SEARCH_RESULT = "SEARCH_RESULT"
    }


}
