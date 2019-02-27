package org.tat.sdksample.inteface;

import org.tat.sdksample.model.Detail;

public interface DetailLoadingListener {
    void onSucceed(Detail detail);
    void onError(String message,int errorCode);
}
