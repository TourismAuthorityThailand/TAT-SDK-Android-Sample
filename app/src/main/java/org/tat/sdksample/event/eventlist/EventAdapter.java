package org.tat.sdksample.event.eventlist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.tat.sdksample.GlideApp;
import org.tat.sdksample.R;
import org.tat.sdksample.event.eventdetail.EventDetailActivity;
import org.th.tatsdk.event.list.TATGetEventsResult;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private TATGetEventsResult[] results;
    private Context context = null;

    public EventAdapter(TATGetEventsResult[] results) {
        this.results = results;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }

        return new EventViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int i) {
        final TATGetEventsResult event = results[i];

        if (event.getName() != null) {
            holder.name.setText(event.getName());
        } else {
            holder.name.setText("");
        }
        if (event.getEventTime() != null) {
            holder.time.setText(event.getEventTime());
        } else {
            holder.time.setText("");
        }
        if (event.getThumbnail() != null) {
            GlideApp.with(context)
                    .load(event.getThumbnail())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.no_image)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.no_image);
        }

        holder.distance.setText(stringDistance(event.getDistance()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetailActivity.class);
                intent.putExtra("eventId", event.getEventId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.length;
    }

    private String stringDistance(double distance) {
        if (distance > 0.0) {
            return (distance >= 1000) ? String.format("%.1fkm.", distance / 1000) : String.valueOf(String.valueOf(Integer.valueOf((int) distance))) + "m.";
        } else {
            return "";
        }
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public ImageView image;
        public TextView name;
        public TextView time;
        public TextView distance;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.textName);
            time = view.findViewById(R.id.textTime);
            distance = view.findViewById(R.id.textDistance);
        }
    }
}
