package com.rooted.deviceinfo.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.rooted.deviceinfo.app_utils.InternetConnectionChecker
import com.rooted.deviceinfo.app_utils.network.NetworkSpeedMonitor

class RootedDeviceInfo : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        InternetConnectionChecker.registerConnectivityReceiver(this)
        NetworkSpeedMonitor(this).registerNetwork()
    }
}