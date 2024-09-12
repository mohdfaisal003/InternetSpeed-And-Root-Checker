package com.rooted.deviceinfo.app_base

import com.rooted.deviceinfo.app_utils.network.NetworkSpeedMonitor
import com.rooted.deviceinfo.base.BaseFragment

abstract class AppBaseFragment: BaseFragment(), NetworkSpeedMonitor.NetworkSpeedListener {

    override fun initComponents() {
        /* Initialize */
    }

    override fun onChange(currentNetworkSpeed: String, speedType: String) {
        /* Do your work */
    }
}