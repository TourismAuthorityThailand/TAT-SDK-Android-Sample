package org.tat.sdksample.news.newslist

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.tat.sdksample.GlideApp
import org.tat.sdksample.R
import org.tat.sdksample.news.detail.NewsDetailActivity
import org.th.tatsdk.news.TATFeedNewsResult

class NewsAdapter(private val results: Array<TATFeedNewsResult>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsViewHolder {
        if (context == null) {
            context = viewGroup.context
        }

        return NewsViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_news, viewGroup, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, i: Int) {
        val news = results[i]

        if (news.name != null) {
            holder.tvName.text = news.name
        } else {
            holder.tvName.text = ""
        }
        if (news.headline != null) {
            holder.tvDesc.text = news.headline
        } else {
            holder.tvDesc.text = ""
        }
        if (news.thumbnailUrl != null) {
            GlideApp.with(context!!)
                    .load(news.thumbnailUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.no_image)
                    .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.no_image)
        }

        if (news.displayPublishedDate != null) {
            holder.tvDate.text = news.displayPublishedDate
        } else {
            holder.tvDate.text = ""
        }


        holder.view.setOnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("newsId", news.id)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class NewsViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView
        var tvName: TextView
        var tvDesc: TextView
        var tvDate: TextView

        init {
            image = view.findViewById(R.id.imageNews)
            tvName = view.findViewById(R.id.tvNewsName)
            tvDesc = view.findViewById(R.id.tvNewDesc)
            tvDate = view.findViewById(R.id.tvNewsDate)
        }
    }
}
