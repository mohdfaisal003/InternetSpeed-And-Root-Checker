package com.rooted.deviceinfo.App

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class RootedDeviceInfo: Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}