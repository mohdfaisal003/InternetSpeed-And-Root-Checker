package com.rooted.deviceinfo.app_utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NetworkSpeedMonitor(
    context: Context,
    networkListener: NetworkSpeedListener
) {
    private val TAG = javaClass.name
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val wifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    interface NetworkSpeedListener {
        fun onChange(currentNetworkSpeed: String,speedType: String)
    }

    private var networkSpeedListener: NetworkSpeedListener? = null

    init {
        this.networkSpeedListener = networkListener
        startMonitoring()
    }

    private fun startMonitoring() {
        GlobalScope.launch(Dispatchers.IO) {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    checkNetworkSpeed()
                }
                delay(5000)
            }
        }
    }

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    private fun checkNetworkSpeed() {
        connectivityManager.registerNetworkCallback(networkRequest,networkCallBack)
    }

    private val networkCallBack = object: NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val linkSpeedBps = getLinkSpeed(networkCapabilities)
            val formattedSpeed = formatSpeed(linkSpeedBps)
            val formattedSpeedType = formatSpeedRange(linkSpeedBps)
            Log.d(TAG, "Formatted Network Speed: $formattedSpeed")
            if (networkSpeedListener == null) return
            CoroutineScope(Dispatchers.Main).launch {
                networkSpeedListener?.onChange(formattedSpeed,formattedSpeedType)
            }
        }
    }

    private fun getLinkSpeed(capabilities: NetworkCapabilities): Long {
        return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            getWifiLinkSpeed().toLong() * 1_000_000
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            capabilities.linkDownstreamBandwidthKbps.toLong() * 1_000
        } else {
            0
        }
    }

    private fun getWifiLinkSpeed(): Int {
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.linkSpeed
    }

    private fun formatSpeed(linkSpeedBps: Long): String {
        return when {
            linkSpeedBps >= 1_000_000_000 -> {
                String.format("%.2f", linkSpeedBps / 1_000_000_000.0)
            }

            linkSpeedBps >= 1_000_000 -> {
                String.format("%.2f", linkSpeedBps / 1_000_000.0)
            }

            linkSpeedBps >= 1_000 -> {
                String.format("%.2f", linkSpeedBps / 1_000.0)
            }

            else -> {
                String.format("%d", linkSpeedBps)
            }
        }
    }

    private fun formatSpeedRange(linkSpeedBps: Long): String {
        return when {
            linkSpeedBps >= 1_000_000_000 -> {
                String.format("Gbps", linkSpeedBps / 1_000_000_000.0)
            }

            linkSpeedBps >= 1_000_000 -> {
                String.format("Mbps", linkSpeedBps / 1_000_000.0)
            }

            linkSpeedBps >= 1_000 -> {
                String.format("Kbps", linkSpeedBps / 1_000.0)
            }

            else -> {
                String.format("bps", linkSpeedBps)
            }
        }
    }

    fun unregisterNetworkSpeedMonitor() {
        connectivityManager.unregisterNetworkCallback(networkCallBack)
    }
}