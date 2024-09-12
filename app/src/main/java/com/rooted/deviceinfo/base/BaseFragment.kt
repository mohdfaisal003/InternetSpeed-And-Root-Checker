package com.rooted.deviceinfo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rooted.deviceinfo.R
import com.rooted.deviceinfo.app_utils.AppUtil
import com.rooted.deviceinfo.app_utils.InternetConnectionChecker

abstract class BaseFragment : Fragment(), OnClickListener, InternetConnectionChecker.ConnectivityChangeListener {

    abstract fun layoutRes(): View
    abstract fun initComponents()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutRes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        if (!isConnected) {
            AppUtil.showMessageInSnackBar(requireActivity(),getString(R.string.no_internet))
        }
    }

    override fun onClick(view: View?) {
        /* Initialize */
    }
}