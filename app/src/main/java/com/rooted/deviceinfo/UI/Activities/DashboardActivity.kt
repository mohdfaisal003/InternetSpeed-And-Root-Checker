package com.rooted.deviceinfo.UI.Activities

import android.view.View
import com.rooted.deviceinfo.AppBase.AppBaseActivity
import com.rooted.deviceinfo.AppUtils.AppUtil
import com.rooted.deviceinfo.R
import com.rooted.deviceinfo.UI.Fragments.QuoteFragment
import com.rooted.deviceinfo.UI.Fragments.RootCheckerFragment
import com.rooted.deviceinfo.databinding.ActivityDashboardBinding

class DashboardActivity : AppBaseActivity() {

    private lateinit var binding: ActivityDashboardBinding
    override fun layoutRes(): View {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initComponents() {
        /* Initialize */
        binding.toolbarTitleTv.text = getString(R.string.app_name)

        /* RootChecker Fragment */
        AppUtil.replaceOrAddFragment(
            supportFragmentManager,
            R.id.root_checker_fragment_container,
            RootCheckerFragment(),
            false,
            null
        )
        /* Quote Fragment */
        AppUtil.replaceOrAddFragment(
            supportFragmentManager,
            R.id.quote_fragment_container,
            QuoteFragment(),
            false,
            null
        )
    }
}