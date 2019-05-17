package org.tat.sdksample.placesearch.manager

import org.tat.sdksample.placesearch.inteface.DetailLoadingListener
import org.tat.sdksample.placesearch.model.Detail
import org.th.tatsdk.ServiceRequestListener
import org.th.tatsdk.common.TATLanguage
import org.th.tatsdk.common.TATPlaceContact
import org.th.tatsdk.search.*

class PlaceDetailManager(listener: DetailLoadingListener) {

    internal var listener: DetailLoadingListener? = null

    init {
        this.listener = listener
    }

    ///request for detail of place using place id for each category.
    fun loadPlaceDetail(placeId: String, placeCategory: String) {

        val param = TATGetPlaceDetailParameter(placeId, TATLanguage.ENGLISH)

        when (placeCategory) {
            "Attraction" -> TATPlaces.getAttractionAsync(param, object : ServiceRequestListener<TATGetAttractionDetailResult> {
                override fun onResponse(result: TATGetAttractionDetailResult?) {
                    result?.let {
                    val location = result.location
                    val detail = Detail(result.name,
                            Detail.sortAddress(location.address, location.subDistrict, location.district, location.province),
                            result.information.detail,
                            checkTelephone(result.contact),
                            checkUrl(result.contact),
                            sortList(result.facilities),
                            sortList(result.services),
                            sortList(result.paymentMethods)
                    )
                    if (listener != null) {
                        listener!!.onSucceed(detail)
                    }
                    }
                }

                override fun onError(message: String?, errorCode: Int) {
                    if (listener != null) {
                        listener!!.onError(message?:"", errorCode)
                    }
                }
            })
            "Accommodation" -> TATPlaces.getAccommodationAsync(param, object : ServiceRequestListener<TATGetAccommodationDetailResult> {
                override fun onResponse(result: TATGetAccommodationDetailResult?) {
                    result?.let {
                        val location = result.location
                        val detail = Detail(result.name,
                                Detail.sortAddress(location.address, location.subDistrict, location.district, location.province),
                                result.information.detail,
                                checkTelephone(result.contact),
                                checkUrl(result.contact),
                                sortList(result.facilities),
                                sortList(result.services),
                                sortList(result.paymentMethods)
                        )
                        if (listener != null) {
                            listener!!.onSucceed(detail)
                        }
                    }
                }

                override fun onError(message: String?, errorCode: Int) {
                    if (listener != null) {
                        listener!!.onError(message ?: "", errorCode)
                    }
                }
            })
            "Restaurant" -> TATPlaces.getRestaurantAsync(param, object : ServiceRequestListener<TATGetRestaurantDetailResult> {
                override fun onResponse(result: TATGetRestaurantDetailResult?) {
                    result?.let {
                        val location = result.location
                        val detail = Detail(result.name,
                                Detail.sortAddress(location.address, location.subDistrict, location.district, location.province),
                                result.information.detail,
                                checkTelephone(result.contact),
                                checkUrl(result.contact),
                                sortList(result.facilities),
                                sortList(result.services),
                                sortList(result.paymentMethods)
                        )
                        if (listener != null) {
                            listener!!.onSucceed(detail)
                        }
                    }

                }

                override fun onError(message: String?, errorCode: Int) {
                    if (listener != null) {
                        listener!!.onError(message ?: "", errorCode)
                    }
                }
            })
            "Shop" -> TATPlaces.getShopAsync(param, object : ServiceRequestListener<TATGetShopDetailResult> {
                override fun onResponse(result: TATGetShopDetailResult?) {
                    result?.let {
                        val location = result.location
                        val detail = Detail(result.name,
                                Detail.sortAddress(location.address, location.subDistrict, location.district, location.province),
                                result.information.detail,
                                checkTelephone(result.contact),
                                checkUrl(result.contact),
                                sortList(result.facilities),
                                sortList(result.services),
                                sortList(result.paymentMethods)
                        )
                        if (listener != null) {
                            listener!!.onSucceed(detail)
                        }
                    }
                }

                override fun onError(message: String?, errorCode: Int) {
                    if (listener != null) {
                        listener!!.onError(message ?: "", errorCode)
                    }
                }
            })
            "Other" -> TATPlaces.getOtherPlaceAsync(param, object : ServiceRequestListener<TATGetOtherPlaceDetailResult> {
                override fun onResponse(result: TATGetOtherPlaceDetailResult?) {
                    result?.let {
                        val location = result.location
                        val detail = Detail(result.name,
                                Detail.sortAddress(location.address, location.subDistrict, location.district, location.province),
                                result.information.detail,
                                checkTelephone(result.contact),
                                checkUrl(result.contact),
                                "",
                                "",
                                ""
                        )
                        if (listener != null) {
                            listener!!.onSucceed(detail)
                        }
                    }
                }

                override fun onError(message: String?, errorCode: Int) {
                    if (listener != null) {
                        listener!!.onError(message ?: "", errorCode)
                    }
                }
            })
            else -> {
            }
        }
    }

    private fun checkTelephone(contact: TATPlaceContact?): String {
        return if (contact != null) {
            if (contact.phones != null) {
                if (contact.phones.size > 0) {
                    contact.phones[0]
                } else
                    ""
            } else
                ""
        } else
            ""
    }

    private fun checkUrl(contact: TATPlaceContact?): String {
        return if (contact != null) {
            if (contact.urls != null) {
                if (contact.urls.size > 0) {
                    contact.urls[0]
                } else
                    ""
            } else
                ""
        } else
            ""
    }

    private fun sortList(list: Array<TATItem>?): String {
        if (list != null) {
            if (list.size > 0) {
                var index = 0
                var nameList = ""
                for (item in list) {
                    if (index != 0) {
                        nameList = nameList + "," + item.description
                        index++
                    } else {
                        nameList = item.description
                        index++
                    }

                }
                return nameList
            } else {
                return ""
            }
        } else {
            return ""
        }
    }
}
