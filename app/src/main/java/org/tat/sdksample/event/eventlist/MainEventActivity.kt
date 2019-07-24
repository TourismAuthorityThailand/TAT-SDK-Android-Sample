package org.tat.sdksample.event.eventlist

import android.graphics.Rect
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.tat.sdksample.R
import org.th.tatsdk.ServiceRequestListener
import org.th.tatsdk.common.TATEventSortBy
import org.th.tatsdk.common.TATGeolocation
import org.th.tatsdk.common.TATLanguage
import org.th.tatsdk.event.TATEvents
import org.th.tatsdk.event.TATFindEventsParameter
import org.th.tatsdk.event.TATFindEventsResultSet

class MainEventActivity : AppCompatActivity() {

    private val LAT = 13.74918
    private val LNG = 100.55785

    private var swipe: SwipeRefreshLayout? = null
    private var recycler: androidx.recyclerview.widget.RecyclerView? = null
    private var parameter: TATFindEventsParameter? = null
    private var tvSort: TextView? = null
    private var tvNoResult: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_event)
        setupToolbar()
        swipe = findViewById(R.id.swipe)
        tvSort = findViewById(R.id.tv_sort_by)
        tvNoResult = findViewById(R.id.tvEventNoresult)
        recycler = findViewById(R.id.recyclerList)
        recycler!!.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler!!.addItemDecoration(
                object : androidx.recyclerview.widget.DividerItemDecoration(baseContext, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL) {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
                        val position = parent!!.getChildAdapterPosition(view!!)
                        if (position == parent.adapter!!.itemCount - 1) {
                            outRect.setEmpty()
                        } else {
                            super.getItemOffsets(outRect, view, parent, state)
                        }
                    }
                }
        )
        parameter = TATFindEventsParameter(TATLanguage.ENGLISH)

        loadEvent()

        swipe!!.setOnRefreshListener { loadEvent() }
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.event_and_festival)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.item_time -> {
                //Create parameter and sorting by DATE.
                parameter = TATFindEventsParameter(TATLanguage.ENGLISH)
                tvSort!!.text = "Date"
                loadEvent()
                return true
            }
            R.id.item_distance -> {
                //Create parameter and sorting by DISTANCE have to provide TATGeolocation also.
                parameter = TATFindEventsParameter(TATLanguage.ENGLISH)
                parameter?.sortByDistance(TATGeolocation(LAT, LNG))
                tvSort!!.text = "Distance"
                loadEvent()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadEvent() {
        swipe!!.isRefreshing = true

        TATEvents.findAsync(parameter!!, object : ServiceRequestListener<TATFindEventsResultSet> {
            override fun onResponse(result: TATFindEventsResultSet?) {
                result?.let {
                    swipe!!.isRefreshing = false
                    swipe!!.visibility = View.VISIBLE
                    tvNoResult!!.visibility = View.GONE
                    val adapter = EventAdapter(result.results)
                    val a = result.results
                    recycler!!.adapter = adapter
                }
            }

            override fun onError(errorMessage: String?, statusCode: Int) {
                swipe!!.isRefreshing = false
                tvNoResult!!.visibility = View.VISIBLE
                swipe!!.visibility = View.GONE
                Toast.makeText(this@MainEventActivity, errorMessage + statusCode, Toast.LENGTH_SHORT).show()
            }
        })
    }
}