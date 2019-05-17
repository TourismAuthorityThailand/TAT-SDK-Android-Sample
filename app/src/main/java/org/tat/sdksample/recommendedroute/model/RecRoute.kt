package org.tat.sdksample.recommendedroute.model

import android.os.Parcel
import android.os.Parcelable

data class RecRoute(var routeID: String= "",
                    var routeName: String = "",
                    var info: String = "",
                    var thumbnail: String = "",
                    var distance: String = "",
                    var numberOfDay: Int = 0) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(routeID)
        parcel.writeString(routeName)
        parcel.writeString(info)
        parcel.writeString(thumbnail)
        parcel.writeString(distance)
        parcel.writeInt(numberOfDay)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecRoute> {
        override fun createFromParcel(parcel: Parcel): RecRoute {
            return RecRoute(parcel)
        }

        override fun newArray(size: Int): Array<RecRoute?> {
            return arrayOfNulls(size)
        }
    }


}
