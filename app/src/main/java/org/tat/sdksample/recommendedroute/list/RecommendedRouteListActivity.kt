package org.tat.sdksample.recommendedroute.list

import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import org.tat.sdksample.R
import org.tat.sdksample.recommendedroute.model.RecRoutes

class RecommendedRouteListActivity : AppCompatActivity() {

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private var recRoutes: RecRoutes? = null
    private lateinit var adapter: RecommendedRouteListAdapter
    private lateinit var noresultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_result)
        if (intent != null) {
            recRoutes = intent.getParcelableExtra("route_list")
        }
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
        adapter = RecommendedRouteListAdapter()
        recyclerView = findViewById(R.id.rcv_list_result)
        noresultText = findViewById(R.id.tvNoresult)
        recyclerView.adapter = adapter
        if (recRoutes != null) {
            adapter.updateListItem(recRoutes!!)
        } else {
            noresultText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
        recyclerView.addItemDecoration(
                object : androidx.recyclerview.widget.DividerItemDecoration(baseContext, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL) {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
                        val position = parent.getChildAdapterPosition(view)
                        if (position == parent.adapter!!.itemCount - 1) {
                            outRect.setEmpty()
                        } else {
                            super.getItemOffsets(outRect, view, parent, state)
                        }
                    }
                }
        )
    }
}
