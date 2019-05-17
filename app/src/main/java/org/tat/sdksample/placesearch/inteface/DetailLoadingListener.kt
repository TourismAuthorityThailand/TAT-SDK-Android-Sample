package org.tat.sdksample.placesearch.inteface

import org.tat.sdksample.placesearch.model.Detail

interface DetailLoadingListener {
    fun onSucceed(detail: Detail)
    fun onError(message: String, errorCode: Int)
}
