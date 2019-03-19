package org.tat.sdksample.recommendedroute.list;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tat.sdksample.GlideApp;
import org.tat.sdksample.R;
import org.tat.sdksample.placesearch.search.SearchResultAdapter;
import org.tat.sdksample.recommendedroute.detail.RouteDetailActivity;
import org.tat.sdksample.recommendedroute.map.MapActivity;
import org.tat.sdksample.recommendedroute.model.RecRoute;
import org.tat.sdksample.recommendedroute.model.RecRoutes;

import java.util.List;

public class RecommendedRouteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RecRoute> listItem;

    public void updateListItem(RecRoutes list) {
        this.listItem = list.getRecRoutes();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_result, viewGroup, false);
        return new RecommendedRouteListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            final RecRoute item = listItem.get(position);
            ((ViewHolder) viewHolder).tvName.setText(item.getRouteName());
            ((ViewHolder) viewHolder).tvInfo.setText(item.getInfo());
            ((ViewHolder) viewHolder).tvDay.setText(String.valueOf(item.getNumberOfDay()) + " Day(s)");
            ((ViewHolder) viewHolder).tvDistance.setText(item.getDistance());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = viewHolder.itemView.getContext();
                    //Intent intent = new Intent(context, RouteDetailActivity.class);
                    Intent intent = new Intent(context, RouteDetailActivity.class);
                    intent.putExtra("rout_id",item.getRouteID());
                    context.startActivity(intent);
                }
            });
            GlideApp.with(viewHolder.itemView)
                    .load(item.getThumbnail())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.no_image)
                    .into(((ViewHolder) viewHolder).imvThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvName;
        AppCompatTextView tvInfo;
        AppCompatTextView tvDistance;
        AppCompatTextView tvDay;
        AppCompatImageView imvThumbnail;

        public ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_result_name);
            tvInfo = v.findViewById(R.id.tv_result_desc);
            tvDistance = v.findViewById(R.id.tv_result_distance);
            tvDay = v.findViewById(R.id.tv_result_special_detail);
            imvThumbnail = v.findViewById(R.id.imv_thumbnail);
        }
    }
}
