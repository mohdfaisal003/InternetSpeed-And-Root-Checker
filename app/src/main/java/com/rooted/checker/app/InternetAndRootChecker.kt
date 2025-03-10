package com.rooted.checker.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.rooted.checker.app_utils.InternetConnectionChecker
import com.rooted.checker.app_utils.network.NetworkSpeedMonitor

class InternetAndRootChecker : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        InternetConnectionChecker.registerConnectivityReceiver(this)
        NetworkSpeedMonitor(this).registerNetwork()
    }
}