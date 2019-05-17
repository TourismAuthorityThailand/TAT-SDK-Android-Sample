package org.tat.sdksample

import org.th.tatsdk.TATSDKEnvironment

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        val apiKey = "[API_KEY]"
        TATSDKEnvironment.setEnvironment(apiKey, this)
    }
}