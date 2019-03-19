package org.tat.sdksample.placesearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;


public class SearchResult implements Parcelable {
    String placeId;
    String placeName;
    String placeAddress;
    String placeDistance;
    String placeCategory;
    String placeThumbnail;

    public SearchResult(String id,String placeName, String placeAddress, String placeDistance, String placeCategory,String thumbnail) {
        this.placeId = id;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeDistance = placeDistance;
        this.placeCategory = placeCategory;
        this.placeThumbnail = thumbnail;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceDistance() {
        return placeDistance;
    }

    public void setPlaceDistance(String placeDistance) {
        this.placeDistance = placeDistance;
    }

    public String getPlaceCategory() {
        return placeCategory;
    }

    public void setPlaceCategory(String placeCategory) {
        this.placeCategory = placeCategory;
    }

    public String getPlaceThumbnail() {
        return placeThumbnail;
    }

    public void setPlaceThumbnail(String placeThumbnail) {
        this.placeThumbnail = placeThumbnail;
    }

    public static Creator<SearchResult> getCREATOR() {
        return CREATOR;
    }

    protected SearchResult(Parcel in) {
        placeId = in.readString();
        placeName = in.readString();
        placeAddress = in.readString();
        placeDistance = in.readString();
        placeCategory = in.readString();
        placeThumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeId);
        dest.writeString(placeName);
        dest.writeString(placeAddress);
        dest.writeString(placeDistance);
        dest.writeString(placeCategory);
        dest.writeString(placeThumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public static String sortAddress(String address, String subDistrict, String district, String province) {
        String sortedAddress = "";

        if (address != null && !address.isEmpty()) {
            sortedAddress = address;
        }
        if (subDistrict != null && !subDistrict.isEmpty()) {
            sortedAddress = sortedAddress + " " + subDistrict;
        }
        if (district != null && !district.isEmpty()) {
            sortedAddress = sortedAddress + " " + district;
        }
        if (province != null && !province.isEmpty()) {
            sortedAddress = sortedAddress + " " + province;
        }
        return sortedAddress.trim();
    }

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
