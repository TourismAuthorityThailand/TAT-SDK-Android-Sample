package org.tat.sdksample.recommendedroute.model

import android.os.Parcel
import android.os.Parcelable
import org.th.tatsdk.route.TATFindRoutesResult
import java.text.DecimalFormat


data class RecRoutes(var recRoutes: List<RecRoute>? = null) : Parcelable {


    constructor(result: Array<TATFindRoutesResult>) : this() {
        val list = ArrayList<RecRoute>(result.size)
        for (item in result) {
            list.add(RecRoute(
                    item.id,
                    item.name,
                    item.introduction,
                    item.thumbnailUrl,
                    distanceToUnit(item.distance),
                    item.numberOfDays?:0
            ))
        }
        this.recRoutes = list
    }

    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(RecRoute.CREATOR)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(recRoutes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecRoutes> {
        override fun createFromParcel(parcel: Parcel): RecRoutes {
            return RecRoutes(parcel)
        }

        override fun newArray(size: Int): Array<RecRoutes?> {
            return arrayOfNulls(size)
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
