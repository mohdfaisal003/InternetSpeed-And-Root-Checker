package com.rooted.deviceinfo.Base

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), OnClickListener {

    abstract fun layoutRes(): View
    abstract fun initComponents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        initComponents()
    }

    override fun onClick(view: View?) {
        /* Initialize */
    }

    open fun setFragmentContainerId(): Int {
        return setFragmentContainerId()
    }
}