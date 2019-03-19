package org.tat.sdksample.placesearch.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.tat.sdksample.GlideApp;
import org.tat.sdksample.R;
import org.tat.sdksample.placesearch.inteface.SearchResultClickListener;
import org.tat.sdksample.placesearch.model.SearchResult;
import org.tat.sdksample.placesearch.model.SearchResults;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchResult> listItem;
    private SearchResultClickListener listener = null;

    public  SearchResultAdapter(SearchResultClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_result, viewGroup, false);
        return new ViewHolder(view);
    }

    public void updateListItem(SearchResults list) {
        listItem = list.searchResults;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            final SearchResult item = listItem.get(position);
            ((ViewHolder) viewHolder).tvName.setText(item.getPlaceName());
            ((ViewHolder) viewHolder).tvAddress.setText(item.getPlaceAddress());
            ((ViewHolder) viewHolder).tvDistance.setText(item.getPlaceDistance());
            ((ViewHolder) viewHolder).tvCategory.setText(item.getPlaceCategory());

            GlideApp.with(viewHolder.itemView)
                    .load(item.getPlaceThumbnail())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.no_image)
                    .into(((ViewHolder) viewHolder).imvThumbnail);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (listener != null ){
                        listener.onItemClickListener(item.getPlaceId(),item.getPlaceCategory());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listItem!=null){
            return listItem.size();
        }else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvName;
        AppCompatTextView tvAddress;
        AppCompatTextView tvDistance;
        AppCompatTextView tvCategory;
        AppCompatImageView imvThumbnail;

        public ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_result_name);
            tvAddress = v.findViewById(R.id.tv_result_desc);
            tvDistance = v.findViewById(R.id.tv_result_distance);
            tvCategory = v.findViewById(R.id.tv_result_special_detail);
            imvThumbnail = v.findViewById(R.id.imv_thumbnail);
        }
    }
}
