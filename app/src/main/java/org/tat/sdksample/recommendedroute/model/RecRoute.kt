package org.tat.sdksample.recommendedroute.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecRoute(var routeID: String= "",
                    var routeName: String = "",
                    var info: String = "",
                    var thumbnail: String = "",
                    var distance: String = "",
                    var numberOfDay: Int = 0) : Parcelable
