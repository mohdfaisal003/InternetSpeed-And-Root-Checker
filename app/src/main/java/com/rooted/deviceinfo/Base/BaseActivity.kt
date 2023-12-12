package com.rooted.deviceinfo.Base

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity: AppCompatActivity(), OnClickListener {

    abstract fun layoutRes(): ViewBinding
    abstract fun initComponents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes().root)
        initComponents()
    }

    override fun onClick(view: View?) {
        /* Initialize */
    }
}