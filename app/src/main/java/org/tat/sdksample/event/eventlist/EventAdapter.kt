package org.tat.sdksample.event.eventlist

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.tat.sdksample.GlideApp
import org.tat.sdksample.R
import org.tat.sdksample.event.eventdetail.EventDetailActivity
import org.th.tatsdk.event.TATEventInfo

class EventAdapter(private val results: Array<TATEventInfo>) : androidx.recyclerview.widget.RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): EventViewHolder {
        if (context == null) {
            context = viewGroup.context
        }

        return EventViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_event, viewGroup, false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, i: Int) {
        val event = results[i]

        if (event.name != null) {
            holder.name.text = event.name
        } else {
            holder.name.text = ""
        }
        if (event.displayPeriodDate != null) {
            holder.time.text = event.displayPeriodDate
        } else {
            holder.time.text = ""
        }
        if (event.thumbnailUrl != null) {
            GlideApp.with(context!!)
                    .load(event.thumbnailUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.no_image)
                    .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.no_image)
        }

        holder.distance.text = stringDistance(event.distance)

        holder.view.setOnClickListener {
            val intent = Intent(context, EventDetailActivity::class.java)
            intent.putExtra("eventId", event.id)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    private fun stringDistance(distance: Double): String {
        return if (distance > 0.0) {
            if (distance >= 1000) String.format("%.1fkm.", distance / 1000) else Integer.valueOf(distance.toInt()).toString() + "m."
        } else {
            ""
        }
    }

    inner class EventViewHolder(var view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var image: ImageView
        var name: TextView
        var time: TextView
        var distance: TextView

        init {
            image = view.findViewById(R.id.image)
            name = view.findViewById(R.id.textName)
            time = view.findViewById(R.id.textTime)
            distance = view.findViewById(R.id.textDistance)
        }
    }
}
