package com.rooted.checker.ui.fragments

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.rooted.checker.app_base.AppBaseFragment
import com.rooted.checker.app_utils.InternetConnectionChecker
import com.rooted.checker.app_utils.network.NetworkSpeedMonitor
import com.rooted.checker.databinding.FragmentNetworkDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetworkDetailFragment : AppBaseFragment() {

    private lateinit var binding: FragmentNetworkDetailBinding

    override fun layoutRes(): View {
        binding = FragmentNetworkDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initComponents() {
        super.initComponents()
        InternetConnectionChecker.setConnectivityChangeListener(this)
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        if (!isConnected) {
            CoroutineScope(Dispatchers.Main).launch {
                binding.speedTv.text = "0.0"
                binding.speedTypeTv.text = "bps"
            }
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                NetworkSpeedMonitor(context?.applicationContext!!,
                    object : NetworkSpeedMonitor.NetworkSpeedListener {
                        @RequiresApi(Build.VERSION_CODES.P)
                        override fun onChange(currentNetworkSpeed: String, speedType: String) {
                            if (currentNetworkSpeed.isEmpty()) return
                            Log.d("NetworkDetailFragment", currentNetworkSpeed)
                            binding.speedTv.text = currentNetworkSpeed
                            binding.speedTypeTv.text = speedType
                        }
                    })
            }
        }
    }
}