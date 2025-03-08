package com.rooted.checker.ui.activities

import android.view.View
import androidx.activity.viewModels
import com.rooted.checker.R
import com.rooted.checker.app_base.AppBaseActivity
import com.rooted.checker.app_utils.AppUtil
import com.rooted.checker.databinding.ActivityDashboardBinding
import com.rooted.checker.mvvm.view_models.DetailDataViewModel
import com.rooted.checker.ui.fragments.NetworkDetailFragment
import com.rooted.checker.ui.fragments.RootCheckerFragment

class DashboardActivity : AppBaseActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val detailsViewModel by viewModels<DetailDataViewModel>()

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
        /*AppUtil.replaceOrAddFragment(
            supportFragmentManager,
            R.id.quote_fragment_container,
            QuoteFragment(),
            false,
            null
        )*/
        /* Network Detail Fragment */
        AppUtil.replaceOrAddFragment(
            supportFragmentManager,
            R.id.network_detail_fragment_container,
            NetworkDetailFragment(),
            false,
            null
        )
        /* Device Details */
        /*detailsViewModel.getDetailsList(this)
        detailsViewModel.detailData.observe(this, Observer {
            DetailRecyclerView(this, binding.deviceDetailsRV, it.data)
        })*/
    }
}