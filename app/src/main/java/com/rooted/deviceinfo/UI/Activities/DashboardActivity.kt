package com.rooted.deviceinfo.UI.Activities

import androidx.viewbinding.ViewBinding
import com.rooted.deviceinfo.AppBase.AppBaseActivity
import com.rooted.deviceinfo.databinding.ActivityDashboardBinding

class DashboardActivity : AppBaseActivity() {

    override fun layoutRes(): ViewBinding {
        return ActivityDashboardBinding.inflate(layoutInflater)
    }

    override fun initComponents() {
        /* Initialize */
    }
}