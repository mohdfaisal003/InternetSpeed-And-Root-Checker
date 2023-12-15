package com.rooted.deviceinfo.Base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.rooted.deviceinfo.AppUtils.AppUtil
import com.rooted.deviceinfo.R

abstract class BaseFragment : Fragment(), OnClickListener {

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

    override fun onClick(view: View?) {
        /* Initialize */
    }
}