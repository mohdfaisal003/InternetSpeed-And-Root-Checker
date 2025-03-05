package com.rooted.deviceinfo.app_utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NetworkSpeedMonitor(context: Context) {

    private val TAG = javaClass.name
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val wifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    private var isRegistered = false
    var handler = Handler(Looper.getMainLooper())


    interface NetworkSpeedListener {
        fun onChange(currentNetworkSpeed: String, speedType: String)
    }

    private var networkSpeedListener: NetworkSpeedListener? = null

    constructor(context: Context, networkListener: NetworkSpeedListener) : this(context) {
        this.networkSpeedListener = networkListener
        if (isRegistered) return
        registerNetwork()
    }

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    fun registerNetwork() {
        if (isRegistered) return
        connectivityManager.registerNetworkCallback(networkRequest, networkCallBack)
        isRegistered = true
    }

    fun unregisterNetworkCallBack() {
        if (isRegistered) {
            connectivityManager.unregisterNetworkCallback(networkCallBack)
            isRegistered = false
        }
    }

    private val networkCallBack = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            handler.post(object : Runnable {
                override fun run() {
                    connectivityManager.activeNetwork?.let { network ->
                        val capabilities = connectivityManager.getNetworkCapabilities(network)!!
                        trackSpeed(capabilities)
                    }
                    handler.postDelayed(this,3000)
                }
            })
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            stopSpeedMonitoring()
            unregisterNetworkCallBack()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
        }
    }

    private fun trackSpeed(capabilities: NetworkCapabilities) {
        val linkSpeedBps = getLinkSpeed(capabilities)
        val formattedSpeed = formatSpeed(linkSpeedBps)
        val formattedSpeedType = formatSpeedRange(linkSpeedBps)
        Log.d(TAG, "Formatted Network Speed: $formattedSpeed")
        if (networkSpeedListener == null) return
        networkSpeedListener?.onChange(formattedSpeed, formattedSpeedType)
    }

    private fun stopSpeedMonitoring() {
        handler.removeCallbacksAndMessages(null)
        //handler = null
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

//    private fun formatSpeed(linkSpeedBps: Long): String {
//        return when {
//            linkSpeedBps >= 1_000_000_000 -> {
//                String.format("%.2f", linkSpeedBps / 1_000_000_000.0)
//            }
//
//            linkSpeedBps >= 1_000_000 -> {
//                String.format("%.2f", linkSpeedBps / 1_000_000.0)
//            }
//
//            linkSpeedBps >= 1_000 -> {
//                String.format("%.2f", linkSpeedBps / 1_000.0)
//            }
//
//            else -> {
//                String.format("%d", linkSpeedBps)
//            }
//        }
//    }

    private fun formatSpeed(linkSpeedBps: Long): String {
        return when {
            linkSpeedBps >= 1_000_000_000 -> {
                (linkSpeedBps / 1_000_000_000.0).toBigDecimal().stripTrailingZeros().toPlainString()
            }

            linkSpeedBps >= 1_000_000 -> {
                (linkSpeedBps / 1_000_000.0).toBigDecimal().stripTrailingZeros().toPlainString()
            }

            linkSpeedBps >= 1_000 -> {
                (linkSpeedBps / 1_000.0).toBigDecimal().stripTrailingZeros().toPlainString()
            }

            else -> {
                linkSpeedBps.toString()
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
}