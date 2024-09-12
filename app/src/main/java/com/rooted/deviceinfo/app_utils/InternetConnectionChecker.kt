package com.rooted.deviceinfo.app_utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

object InternetConnectionChecker {

    interface ConnectivityChangeListener {
        fun onNetworkChanged(isConnected: Boolean)
    }

    private var connectivityChangeListener: ConnectivityChangeListener? = null

    fun setConnectivityChangeListener(listener: ConnectivityChangeListener) {
        connectivityChangeListener = listener
    }

    fun registerConnectivityReceiver(context: Context) {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(ConnectivityReceiver(),intentFilter)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCallBack = ConnectivityCallBack()
            connectivityManager.registerDefaultNetworkCallback(networkCallBack)
        }
    }

    private class ConnectivityReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            context?.let { checkConnectivity(it) }
        }
    }

    private class ConnectivityCallBack: NetworkCallback() {
        override fun onAvailable(network: Network) {
            notifyNetworkChange(true)
        }

        override fun onLost(network: Network) {
            notifyNetworkChange(false)
        }
    }

    private fun checkConnectivity(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        val isConnected = capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        notifyNetworkChange(isConnected)
    }

    private fun notifyNetworkChange(isConnected: Boolean) {
        connectivityChangeListener?.onNetworkChanged(isConnected)
    }
}