package org.tat.sdksample;

import android.app.Application;

import org.th.tatsdk.TATSDKEnvironment;

public class PlaceSearchApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // See how to get your API Key on github's readme
        TATSDKEnvironment.setEnvironment([API_KEY], this);
    }
}
