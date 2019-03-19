package org.tat.sdksample.recommendedroute.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RecRoute implements Parcelable {
    String routeID = "";
    String routeName = "";
    String info = "";
    String thumbnail = "";
    String distance = "";
    int numberOfDay =  0;

    public RecRoute(String routeID,
                    String routeName,
                    String info,
                    String thumbnail,
                    String distance,
                    int numberOfDay) {
        this.routeID = routeID;
        this.routeName = routeName;
        this.info = info;
        this.thumbnail = thumbnail;
        this.distance = distance;
        this.numberOfDay = numberOfDay;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getNumberOfDay() {
        return numberOfDay;
    }

    public void setNumberOfDay(int numberOfDay) {
        this.numberOfDay = numberOfDay;
    }

    public static Creator<RecRoute> getCREATOR() {
        return CREATOR;
    }

    protected RecRoute(Parcel in) {
        routeID = in.readString();
        routeName = in.readString();
        info = in.readString();
        thumbnail = in.readString();
        distance = in.readString();
        numberOfDay = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(routeID);
        dest.writeString(routeName);
        dest.writeString(info);
        dest.writeString(thumbnail);
        dest.writeString(distance);
        dest.writeInt(numberOfDay);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecRoute> CREATOR = new Creator<RecRoute>() {
        @Override
        public RecRoute createFromParcel(Parcel in) {
            return new RecRoute(in);
        }

        @Override
        public RecRoute[] newArray(int size) {
            return new RecRoute[size];
        }
    };
}
