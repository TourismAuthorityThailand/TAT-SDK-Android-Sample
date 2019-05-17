package org.tat.sdksample.placesearch.search

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.tat.sdksample.GlideApp
import org.tat.sdksample.R
import org.tat.sdksample.placesearch.inteface.SearchResultClickListener
import org.tat.sdksample.placesearch.model.SearchResult
import org.tat.sdksample.placesearch.model.SearchResults

class SearchResultAdapter(listener: SearchResultClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listItem: List<SearchResult>? = null
    private var listener: SearchResultClickListener? = null

    init {
        this.listener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_result, viewGroup, false)
        return ViewHolder(view)
    }

    fun updateListItem(list: SearchResults) {
        listItem = list.searchResults
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ViewHolder) {
            val item = listItem!![position]
            viewHolder.tvName.text = item.placeName
            viewHolder.tvAddress.text = item.placeAddress
            viewHolder.tvDistance.text = item.placeDistance
            viewHolder.tvCategory.text = item.placeCategory

            GlideApp.with(viewHolder.itemView)
                    .load(item.placeThumbnail)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.no_image)
                    .into(viewHolder.imvThumbnail)
            viewHolder.itemView.setOnClickListener {
                listener?.onItemClickListener(item.placeId?:"", item.placeCategory?:"")
            }
        }
    }

    override fun getItemCount(): Int {
        return if (listItem != null) {
            listItem!!.size
        } else {
            0
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var tvName: AppCompatTextView
        internal var tvAddress: AppCompatTextView
        internal var tvDistance: AppCompatTextView
        internal var tvCategory: AppCompatTextView
        internal var imvThumbnail: AppCompatImageView

        init {
            tvName = v.findViewById(R.id.tv_result_name)
            tvAddress = v.findViewById(R.id.tv_result_desc)
            tvDistance = v.findViewById(R.id.tv_result_distance)
            tvCategory = v.findViewById(R.id.tv_result_special_detail)
            imvThumbnail = v.findViewById(R.id.imv_thumbnail)
        }
    }
}
