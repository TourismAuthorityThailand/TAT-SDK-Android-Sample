package org.tat.sdksample.placesearch.model

import android.os.Parcel
import android.os.Parcelable

import java.text.DecimalFormat


data class SearchResult(var placeId: String? = null,
                        var placeName: String? = null,
                        var placeAddress: String? = null,
                        var placeDistance: String? = null,
                        var placeCategory: String? = null,
                        var placeThumbnail: String? = null) : Parcelable {


    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(placeId)
        parcel.writeString(placeName)
        parcel.writeString(placeAddress)
        parcel.writeString(placeDistance)
        parcel.writeString(placeCategory)
        parcel.writeString(placeThumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchResult> {
        override fun createFromParcel(parcel: Parcel): SearchResult {
            return SearchResult(parcel)
        }

        override fun newArray(size: Int): Array<SearchResult?> {
            return arrayOfNulls(size)
        }



        fun sortAddress(address: String?, subDistrict: String?, district: String?, province: String?): String {
            var sortedAddress = ""

            if (address != null && !address.isEmpty()) {
                sortedAddress = address
            }
            if (subDistrict != null && !subDistrict.isEmpty()) {
                sortedAddress = "$sortedAddress $subDistrict"
            }
            if (district != null && !district.isEmpty()) {
                sortedAddress = "$sortedAddress $district"
            }
            if (province != null && !province.isEmpty()) {
                sortedAddress = "$sortedAddress $province"
            }
            return sortedAddress.trim { it <= ' ' }
        }

        fun distanceToUnit(distance: Double): String {
            if (distance != 0.0) {
                if (distance >= 1000) {
                    val decimalFormat = DecimalFormat("#.#")
                    return decimalFormat.format(distance / 1000).toString().replace(".0", "") + "km."
                } else {
                    return Integer.valueOf(distance.toInt()).toString() + "m."
                }
            } else {
                return ""
            }
        }
    }


}
