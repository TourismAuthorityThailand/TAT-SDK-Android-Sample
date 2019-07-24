package org.tat.sdksample.placesearch.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

import org.tat.sdksample.R
import org.tat.sdksample.placesearch.inteface.DetailLoadingListener
import org.tat.sdksample.placesearch.manager.PlaceDetailManager
import org.tat.sdksample.placesearch.model.Detail

class DetailActivity : AppCompatActivity(), DetailLoadingListener {
    private val placeName = ""
    private var placeId: String? = ""
    private var placeCategory: String? = ""
    private var placeDetailManager: PlaceDetailManager? = null
    private var tvNotFound: TextView? = null
    private var scrollViewContent: ScrollView? = null
    private var itemName: View? = null
    private var itemAddress: View? = null
    private var itemDetail: View? = null
    private var itemTel: View? = null
    private var itemUrl: View? = null
    private var itemFacilities: View? = null
    private var itemServices: View? = null
    private var itemPaymentOptions: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initInstance()
        setupToolbar()
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.detail_title)
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

    private fun initInstance() {
        val intent = intent
        if (intent != null) {
            placeCategory = intent.getStringExtra(PLACE_CATEGORY)
            placeId = intent.getStringExtra(PLACE_ID)
        }
        itemName = findViewById(R.id.itemName)
        itemAddress = findViewById(R.id.itemAddress)
        itemDetail = findViewById(R.id.itemDetail)
        itemTel = findViewById(R.id.itemTel)
        itemUrl = findViewById(R.id.itemUrl)
        itemFacilities = findViewById(R.id.itemFacilities)
        itemServices = findViewById(R.id.itemService)
        itemPaymentOptions = findViewById(R.id.itemPayment)
        tvNotFound = findViewById(R.id.tv_detail_not_found)
        scrollViewContent = findViewById(R.id.scrollView_detail)
        placeDetailManager = PlaceDetailManager(this)

        if (placeId == null || placeCategory == null) {
            showPlaceNotFound()
        } else {
            placeDetailManager!!.loadPlaceDetail(placeId?:"", placeCategory?:"")
        }

    }

    private fun showPlaceNotFound() {
        tvNotFound!!.visibility = View.VISIBLE
        scrollViewContent!!.visibility = View.GONE
    }

    private fun showDetail(detail: Detail) {
        setItemText(itemName!!, "Name", detail.placeName)
        setItemText(itemAddress!!, "Address", detail.placeAddress)
        setItemText(itemDetail!!, "Detail", detail.placeDetail)
        setItemText(itemTel!!, "Tel", detail.placeTel)
        setItemText(itemUrl!!, "Website", detail.placeWebsite)
        setItemText(itemFacilities!!, "Facilities", detail.placeFacilities)
        setItemText(itemServices!!, "Services", detail.placeServices)
        setItemText(itemPaymentOptions!!, "Payment options", detail.placePaymentOptions)
    }

    private fun setItemText(v: View, title: String, content: String) {
        val tvTitle = v.findViewById<View>(R.id.tv_detail_title) as TextView
        val tvContent = v.findViewById<View>(R.id.tv_detail_content) as TextView
        tvTitle.text = title
        if (!content.isEmpty()) {
            tvContent.text = content
        } else {
            tvContent.text = "-"
        }
    }


    override fun onSucceed(detail: Detail) {
        showDetail(detail)
    }

    override fun onError(message: String, errorCode: Int) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
        showPlaceNotFound()
    }

    companion object {

        private val PLACE_ID = "PLACE_ID"
        private val PLACE_CATEGORY = "PLACE_CATEGORY"
    }
}
