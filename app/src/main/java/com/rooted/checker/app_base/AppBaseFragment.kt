package com.rooted.checker.app_base

import com.rooted.checker.app_utils.network.NetworkSpeedMonitor
import com.rooted.checker.base.BaseFragment

abstract class AppBaseFragment: BaseFragment(), NetworkSpeedMonitor.NetworkSpeedListener {

    override fun initComponents() {
        /* Initialize */
    }

    override fun onChange(currentNetworkSpeed: String, speedType: String) {
        /* Do your work */
    }
}