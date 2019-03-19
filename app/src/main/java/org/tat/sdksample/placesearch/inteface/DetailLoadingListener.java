package org.tat.sdksample.placesearch.inteface;

import org.tat.sdksample.placesearch.model.Detail;

public interface DetailLoadingListener {
    void onSucceed(Detail detail);
    void onError(String message,int errorCode);
}
