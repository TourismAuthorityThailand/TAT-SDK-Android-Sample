package org.tat.sdksample;

import org.th.tatsdk.TATSDKEnvironment;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String apiKey = "[API_KEY]";
        TATSDKEnvironment.setEnvironment(apiKey, this);
    }
}