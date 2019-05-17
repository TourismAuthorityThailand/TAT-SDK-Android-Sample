package org.tat.sdksample.recommendedroute.detail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import org.tat.sdksample.GlideApp
import org.tat.sdksample.R
import org.tat.sdksample.recommendedroute.map.MapActivity
import org.th.tatsdk.common.TATRouteDay
import org.th.tatsdk.route.TATGetRouteDetailResult

class RouteDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listItem: TATGetRouteDetailResult? = null
    private val CAR = "C"
    private val WALK = "W"
    private val PUBLIC = "P"


    fun updateListItem(listItem: TATGetRouteDetailResult) {
        this.listItem = listItem
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {

        if (getItemViewType(i) == HEADER) {
            val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_route_header, viewGroup, false)
            return HeaderViewHolder(v)
        } else if (getItemViewType(i) == ITEM) {
            val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_route_stop, viewGroup, false)
            return ItemViewHolder(v)
        } else {
            //should not found this case
            return NoItemViewHolder(View(viewGroup.context))
        }
    }

    override fun getItemViewType(position: Int): Int {
        var count = 0
        val res = 9
        for (day in listItem!!.days) {
            if (position < day.stops.size + 1 + count) {
                return if (position == count) {
                    HEADER
                } else {
                    ITEM
                }
            } else {
                count += day.stops.size + 1
            }
        }
        return res
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var count = 0
        var item: TATRouteDay? = null
        for (day in listItem!!.days) {
            if (position < day.stops.size + 1 + count) {
                item = day
                break
            } else {
                count += day.stops.size + 1
            }
        }


        if (viewHolder is HeaderViewHolder) {
            if (item != null) {
                val mItem = item
                viewHolder.tvHeader.text = "Day " + mItem.day.toString()
                viewHolder.btnGotoMap.setOnClickListener {
                    val context = viewHolder.itemView.context
                    val intent = Intent(context, MapActivity::class.java)
                    intent.putExtra("STOP", mItem)
                    context.startActivity(intent)
                }
            }
        }

        if (viewHolder is ItemViewHolder) {
            val placeIndex = position - count
            val stop = item?.stops?.getOrNull(placeIndex - 1)
            stop?.let {
                val travelMode = getTransportType(stop.travelBy)
                val distance = stringDistance(stop.distance)
                viewHolder.tvName.text = stop.name
                viewHolder.tvDistance.text = distance
                viewHolder.tvTransportation.text = travelMode
                viewHolder.tvTransportation.setTextColor(getTravelModeColor(stop.travelBy))
                if (distance != "" || travelMode != "") {
                    viewHolder.constraintLayout.visibility = View.VISIBLE
                }
                GlideApp.with(viewHolder.itemView.context)
                        .load(stop.thumbnailUrl)
                        .error(R.drawable.no_image)
                        .into(viewHolder.imvRoute)
            } ?: let {
                viewHolder.tvName.text = ""
                viewHolder.tvDistance.text = ""
                viewHolder.tvTransportation.text = ""
                viewHolder.tvTransportation.setTextColor(getTravelModeColor(""))
                viewHolder.imvRoute.setImageResource(R.drawable.no_image)
            }
        }
    }

    private fun getTransportType(type: String): String {
        when (type) {
            CAR -> return "By Car"
            WALK -> return "By Walk"
            PUBLIC -> return "By Public transport"
            else -> return ""
        }
    }

    private fun getTravelModeColor(type: String): Int {
        when (type) {
            "C" -> return Color.RED
            "P" -> return Color.BLUE
            "W" -> return Color.GREEN
            else -> return Color.MAGENTA
        }
    }


    private fun stringDistance(distance: Double): String {
        return if (distance > 0.0) {
            if (distance >= 1000) String.format("%.1fkm.", distance / 1000) else Integer.valueOf(distance.toInt()).toString() + "m."
        } else {
            ""
        }
    }

    override fun getItemCount(): Int {
        if (listItem == null) {
            return 0
        } else {
            var stopCount = 0
            for (day in listItem!!.days) {
                stopCount += day.stops.size
            }
            listItem?.days?.size ?: 0 + stopCount
            return stopCount
        }
    }

    internal inner class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvName: TextView
        var imvRoute: ImageView
        var tvDistance: TextView
        var tvTransportation: TextView
        var constraintLayout: ConstraintLayout

        init {
            constraintLayout = v.findViewById(R.id.csl_day_transp)
            tvName = v.findViewById(R.id.tvRouteName)
            tvDistance = v.findViewById(R.id.tvRouteDistance)
            tvTransportation = v.findViewById(R.id.tvTransportBy)
            imvRoute = v.findViewById(R.id.imvRoute)
        }
    }

    internal inner class HeaderViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvHeader: TextView
        var btnGotoMap: TextView

        init {
            tvHeader = v.findViewById(R.id.tvDayHeader)
            btnGotoMap = v.findViewById(R.id.btnGoToMap)
        }
    }

    internal inner class NoItemViewHolder(v: View) : RecyclerView.ViewHolder(v)

    companion object {
        private val HEADER = 0
        private val ITEM = 1
    }
}
