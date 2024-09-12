package com.rooted.deviceinfo.base

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.rooted.deviceinfo.app_utils.AppUtil
import com.rooted.deviceinfo.app_utils.InternetConnectionChecker
import com.rooted.deviceinfo.R

abstract class BaseActivity : AppCompatActivity(), OnClickListener, InternetConnectionChecker.ConnectivityChangeListener {

    abstract fun layoutRes(): View
    abstract fun initComponents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        initComponents()
        InternetConnectionChecker.setConnectivityChangeListener(this)
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        if (!isConnected) {
            AppUtil.showMessageInSnackBar(this,getString(R.string.no_internet))
        }
    }

    override fun onClick(view: View?) {
        /* Initialize */
    }

    open fun setFragmentContainerId(): Int {
        return setFragmentContainerId()
    }
}