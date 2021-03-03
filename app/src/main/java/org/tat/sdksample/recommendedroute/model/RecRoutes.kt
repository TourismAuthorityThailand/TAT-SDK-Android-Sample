package org.tat.sdksample.recommendedroute.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.th.tatsdk.route.TATFindRoutesResult
import java.text.DecimalFormat

@Parcelize
data class RecRoutes(var recRoutes: List<RecRoute>? = null) : Parcelable {

    fun transform (result: Array<TATFindRoutesResult>) : RecRoutes {
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
        return RecRoutes(list)
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



