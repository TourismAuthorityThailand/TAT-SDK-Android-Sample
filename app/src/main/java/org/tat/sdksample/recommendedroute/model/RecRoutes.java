package org.tat.sdksample.recommendedroute.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.th.tatsdk.route.list.TATGetRoutesResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecRoutes implements Parcelable {

    List<RecRoute> recRoutes;

    public RecRoutes(TATGetRoutesResult[] result) {
            List<RecRoute> list = new ArrayList(result.length);
        for (TATGetRoutesResult item : result) {
            list.add(new RecRoute(
                    item.getId(),
                    item.getName(),
                    item.getInfo(),
                    item.getThumbnail(),
                    distanceToUnit(item.getDistance()),
                    item.getNumberOfDays()
            ));
        }
        this.recRoutes = list;
    }

    public List<RecRoute> getRecRoutes() {
        return recRoutes;
    }

    public static Creator<RecRoutes> getCREATOR() {
        return CREATOR;
    }

    protected RecRoutes(Parcel in) {
        recRoutes = in.createTypedArrayList(RecRoute.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(recRoutes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecRoutes> CREATOR = new Creator<RecRoutes>() {
        @Override
        public RecRoutes createFromParcel(Parcel in) {
            return new RecRoutes(in);
        }

        @Override
        public RecRoutes[] newArray(int size) {
            return new RecRoutes[size];
        }
    };

    public static String distanceToUnit(double distance) {
        if (distance != 0.0) {
            if (distance >= 1000) {
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                return String.valueOf(decimalFormat.format(distance / 1000)).replace(".0", "") + "km.";
            } else {
                return String.valueOf(Integer.valueOf((int) distance)) + "m.";
            }
        } else {
            return "";
        }
    }
}
