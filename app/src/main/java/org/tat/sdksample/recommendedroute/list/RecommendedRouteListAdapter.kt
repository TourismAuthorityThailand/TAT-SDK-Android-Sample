package org.tat.sdksample.recommendedroute.list

import android.content.Intent
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.tat.sdksample.GlideApp
import org.tat.sdksample.R
import org.tat.sdksample.recommendedroute.detail.RouteDetailActivity
import org.tat.sdksample.recommendedroute.model.RecRoute
import org.tat.sdksample.recommendedroute.model.RecRoutes

class RecommendedRouteListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listItem: List<RecRoute>? = null

    fun updateListItem(list: RecRoutes) {
        this.listItem = list.recRoutes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_result, viewGroup, false)
        return RecommendedRouteListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ViewHolder) {
            val item = listItem!![position]
            viewHolder.tvName.text = item.routeName
            viewHolder.tvInfo.text = item.info
            viewHolder.tvDay.text = item.numberOfDay.toString() + " Day(s)"
            viewHolder.tvDistance.text = item.distance
            viewHolder.itemView.setOnClickListener {
                val context = viewHolder.itemView.context
                //Intent intent = new Intent(context, RouteDetailActivity.class);
                val intent = Intent(context, RouteDetailActivity::class.java)
                intent.putExtra("rout_id", item.routeID)
                context.startActivity(intent)
            }

            GlideApp.with(viewHolder.itemView)
                    .load(item.thumbnail)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.no_image)
                    .into(viewHolder.imvThumbnail)
        }
    }

    override fun getItemCount(): Int {
        return listItem!!.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var tvName: AppCompatTextView
        internal var tvInfo: AppCompatTextView
        internal var tvDistance: AppCompatTextView
        internal var tvDay: AppCompatTextView
        internal var imvThumbnail: AppCompatImageView

        init {
            tvName = v.findViewById(R.id.tv_result_name)
            tvInfo = v.findViewById(R.id.tv_result_desc)
            tvDistance = v.findViewById(R.id.tv_result_distance)
            tvDay = v.findViewById(R.id.tv_result_special_detail)
            imvThumbnail = v.findViewById(R.id.imv_thumbnail)
        }
    }
}
