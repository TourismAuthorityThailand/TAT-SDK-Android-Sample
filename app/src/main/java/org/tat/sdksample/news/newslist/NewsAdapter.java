package org.tat.sdksample.news.newslist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.tat.sdksample.GlideApp;
import org.tat.sdksample.R;
import org.tat.sdksample.event.eventdetail.EventDetailActivity;
import org.tat.sdksample.news.detail.NewsDetailActivity;
import org.th.tatsdk.news.list.TATGetNewsResult;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private TATGetNewsResult[] results;
    private Context context = null;

    public NewsAdapter(TATGetNewsResult[] results){
        this.results = results;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null){
            context = viewGroup.getContext();
        }

        return new NewsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int i) {
        final TATGetNewsResult news = results[i];

        if (news.getName() != null){
            holder.tvName.setText(news.getName());
        }
        else{
            holder.tvName.setText("");
        }
        if (news.getIntroduction() != null){
            holder.tvDesc.setText(news.getIntroduction());
        }
        else{
            holder.tvDesc.setText("");
        }
        if (news.getThumbnail() != null){
            GlideApp.with(context)
                    .load(news.getThumbnail())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.no_image)
                    .into(holder.image);
        }
        else{
            holder.image.setImageResource(R.drawable.no_image);
        }

        if (news.getPublishDate() != null){
            holder.tvDate.setText(news.getPublishDate());
        }else{
            holder.tvDate.setText("");
        }


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("newsId", news.getNewsId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.length;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public ImageView image;
        public TextView tvName;
        public TextView tvDesc;
        public TextView tvDate;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            image = view.findViewById(R.id.imageNews);
            tvName = view.findViewById(R.id.tvNewsName);
            tvDesc = view.findViewById(R.id.tvNewDesc);
            tvDate = view.findViewById(R.id.tvNewsDate);
        }
    }
}
