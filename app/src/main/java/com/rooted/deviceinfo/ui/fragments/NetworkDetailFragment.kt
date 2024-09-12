package com.rooted.deviceinfo.ui.fragments

import android.util.Log
import android.view.View
import com.rooted.deviceinfo.app_base.AppBaseFragment
import com.rooted.deviceinfo.app_utils.network.NetworkSpeedMonitor
import com.rooted.deviceinfo.app_utils.InternetConnectionChecker
import com.rooted.deviceinfo.databinding.FragmentNetworkDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetworkDetailFragment : AppBaseFragment() {

    private lateinit var binding: FragmentNetworkDetailBinding

    override fun layoutRes(): View {
        binding = FragmentNetworkDetailBinding.inflate(layoutInflater)
        return binding.root
    }

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