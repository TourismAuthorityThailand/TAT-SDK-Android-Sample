package org.tat.sdksample.placesearch.search

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.TextView

import org.tat.sdksample.R
import org.tat.sdksample.placesearch.detail.DetailActivity
import org.tat.sdksample.placesearch.inteface.SearchResultClickListener
import org.tat.sdksample.placesearch.model.SearchResults
import org.tat.sdksample.recommendedroute.model.RecRoutes

class SearchResultActivity : AppCompatActivity(), SearchResultClickListener {
    private var resultList: SearchResults? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: SearchResultAdapter? = null
    private var tvNoresult: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_result)
        val intent = intent
        if (intent != null) {
            resultList = getIntent().getParcelableExtra(MainSearchActivity.SEARCH_RESULT)
        }
        initInstances()
        setupToolbar()
    }

    private fun initInstances() {
        recyclerView = findViewById(R.id.rcv_list_result)
        tvNoresult = findViewById(R.id.tvNoresult)
        adapter = SearchResultAdapter(this)
        recyclerView!!.adapter = adapter
        if (resultList != null) {
            adapter!!.updateListItem(resultList!!)
            recyclerView!!.addItemDecoration(
                    object : DividerItemDecoration(baseContext, LinearLayoutManager.VERTICAL) {
                        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                            val position = parent.getChildAdapterPosition(view)
                            if (position == parent.adapter!!.itemCount - 1) {
                                outRect.setEmpty()
                            } else {
                                super.getItemOffsets(outRect, view, parent, state)
                            }
                        }
                    }
            )
        } else {
            recyclerView!!.visibility = View.GONE
            tvNoresult!!.visibility = View.VISIBLE
        }
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.result_title)
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


    private fun navigateToDetail(id: String, category: String) {
        val i = Intent(baseContext, DetailActivity::class.java)
        i.putExtra(PLACE_ID, id)
        i.putExtra(PLACE_CATEGORY, category)
        startActivity(i)
    }

    ////////////////////////Listener/////////////////////////////////

    override fun onItemClickListener(id: String, category: String) {
        navigateToDetail(id, category)
    }

    companion object {


        private val PLACE_ID = "PLACE_ID"
        private val PLACE_NAME = "PLACE_NAME"
        private val PLACE_CATEGORY = "PLACE_CATEGORY"
    }

}
