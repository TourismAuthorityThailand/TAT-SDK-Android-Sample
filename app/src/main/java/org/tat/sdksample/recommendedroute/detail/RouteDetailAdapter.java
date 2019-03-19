package org.tat.sdksample.recommendedroute.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.tat.sdksample.GlideApp;
import org.tat.sdksample.R;
import org.tat.sdksample.recommendedroute.map.MapActivity;
import org.th.tatsdk.common.TATRouteDay;
import org.th.tatsdk.common.TATStop;
import org.th.tatsdk.route.detail.TATGetRouteDetailResult;

public class RouteDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private TATGetRouteDetailResult listItem = null;
    private static int HEADER = 0;
    private static int ITEM = 1;
    private final String CAR = "C";
    private final String WALK = "W";
    private final String PUBLIC = "P";


    public void updateListItem(TATGetRouteDetailResult listItem) {
        this.listItem = listItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (getItemViewType(i) == HEADER) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_route_header, viewGroup, false);
            return new HeaderViewHolder(v);
        } else if (getItemViewType(i) == ITEM) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_route_stop, viewGroup, false);
            return new ItemViewHolder(v);
        } else {
            //should not found this case
            return new NoItemViewHolder(new View(viewGroup.getContext()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        int count = 0;
        int res = 9;
        for (TATRouteDay day : listItem.getDays()) {
            if (position < day.getPlaces().length + 1 + count) {
                if (position == count) {
                    return HEADER;
                } else {
                    return ITEM;
                }
            } else {
                count += day.getPlaces().length + 1;
            }
        }
        return res;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {
        int count = 0;
        TATRouteDay item = null;
        for (TATRouteDay day : listItem.getDays()) {
            if (position < day.getPlaces().length + 1 + count) {
                item = day;
                break;
            } else {
                count += day.getPlaces().length + 1;
            }
        }


        if (viewHolder instanceof HeaderViewHolder) {
            if (item != null) {
                final TATRouteDay mItem = item;
                ((HeaderViewHolder) viewHolder).tvHeader.setText("Day " + String.valueOf(mItem.getDay()));
                ((HeaderViewHolder) viewHolder).btnGotoMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = viewHolder.itemView.getContext();
                        Intent intent = new Intent(context, MapActivity.class);
                        intent.putExtra("STOP", mItem);
                        context.startActivity(intent);
                    }
                });
            }
        }

        if (viewHolder instanceof ItemViewHolder) {
            int placeIndex = (position - count);
            TATStop stop = item.getPlaces()[placeIndex - 1];

            String travelMode = getTransportType(stop.getTravelMode());
            String distance = stringDistance(stop.getDistance());
            ((ItemViewHolder) viewHolder).tvName.setText(stop.getName());
            ((ItemViewHolder) viewHolder).tvDistance.setText(distance);
            ((ItemViewHolder) viewHolder).tvTransportation.setText(travelMode);
            ((ItemViewHolder) viewHolder).tvTransportation.setTextColor(getTravelModeColor(stop.getTravelMode()));
            if (!distance.equals("") || !travelMode.equals("")) {
                ((ItemViewHolder) viewHolder).constraintLayout.setVisibility(View.VISIBLE);
            }
            GlideApp.with(viewHolder.itemView.getContext())
                    .load(stop.getThumbnail())
                    .error(R.drawable.no_image)
                    .into(((ItemViewHolder) viewHolder).imvRoute);
        }
    }

    private String getTransportType(String type) {
        switch (type) {
            case CAR:
                return "By Car";
            case WALK:
                return "By Walk";
            case PUBLIC:
                return "By Public transport";
            default:
                return "";
        }
    }

    private int getTravelModeColor(String type) {
        switch (type) {
            case "C":
                return Color.RED;
            case "P":
                return Color.BLUE;
            case "W":
                return Color.GREEN;
            default:
                return Color.MAGENTA;
        }
    }


    private String stringDistance(double distance) {
        if (distance > 0.0) {
            return (distance >= 1000) ? String.format("%.1fkm.", distance / 1000) : String.valueOf(String.valueOf(Integer.valueOf((int) distance))) + "m.";
        } else {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        if (listItem == null) {
            return 0;
        } else {
            int stopCount = 0;
            for (TATRouteDay day : listItem.getDays()) {
                stopCount += day.getPlaces().length;
            }
            return listItem.getNumberOfDays() + stopCount;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imvRoute;
        TextView tvDistance;
        TextView tvTransportation;
        ConstraintLayout constraintLayout;

        ItemViewHolder(View v) {
            super(v);
            constraintLayout = v.findViewById(R.id.csl_day_transp);
            tvName = v.findViewById(R.id.tvRouteName);
            tvDistance = v.findViewById(R.id.tvRouteDistance);
            tvTransportation = v.findViewById(R.id.tvTransportBy);
            imvRoute = v.findViewById(R.id.imvRoute);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeader;
        TextView btnGotoMap;

        HeaderViewHolder(View v) {
            super(v);
            tvHeader = v.findViewById(R.id.tvDayHeader);
            btnGotoMap = v.findViewById(R.id.btnGoToMap);
        }
    }

    class NoItemViewHolder extends RecyclerView.ViewHolder {
        NoItemViewHolder(View v) {
            super(v);
        }
    }
}
