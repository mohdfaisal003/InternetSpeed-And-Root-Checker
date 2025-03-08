package com.rooted.checker.app_utils.network

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.CellInfo
import android.telephony.CellInfoCdma
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.rooted.checker.mvvm.pojos.CellTowerInfo
import com.rooted.checker.mvvm.pojos.CellularData
import com.rooted.checker.mvvm.pojos.NetworkInfo
import com.rooted.checker.mvvm.pojos.WifiData
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Enumeration

object NetworkDetailUtil {

    @RequiresApi(Build.VERSION_CODES.P)
    fun getNetworkInfo(context: Context): NetworkInfo {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        val isWifi = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        val isCellular = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true

        return when {
            isWifi -> NetworkInfo(
                connectionType = "Wi-Fi",
                isConnected = true,
                wifiInfo = getWifiInfo(context),
                cellularInfo = null
            )
            isCellular -> NetworkInfo(
                connectionType = "Cellular",
                isConnected = true,
                wifiInfo = null,
                cellularInfo = getCellularInfo(context)
            )
            else -> NetworkInfo(
                connectionType = "None",
                isConnected = false,
                wifiInfo = null,
                cellularInfo = null
            )
        }
    }

    private fun getWifiInfo(context: Context): WifiData? {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo = wifiManager.connectionInfo

        return if (wifiInfo.ssid != null) {
            WifiData(
                ssid = wifiInfo.ssid,
                bssid = wifiInfo.bssid,
                rssi = wifiInfo.rssi,
                linkSpeed = wifiInfo.linkSpeed,
                frequency = wifiInfo.frequency,
                ipAddress = formatIpAddress(wifiInfo.ipAddress),
                macAddress = wifiInfo.macAddress,
                gateway = formatIpAddress(wifiManager.dhcpInfo.gateway),
                dnsServers = listOf(formatIpAddress(wifiManager.dhcpInfo.dns1), formatIpAddress(wifiManager.dhcpInfo.dns2)),
                subnetMask = formatIpAddress(wifiManager.dhcpInfo.netmask),
                isMetered = wifiManager.isDeviceToApRttSupported,
            )
        } else {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getCellularInfo(context: Context): CellularData? {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val signalStrength = telephonyManager.signalStrength?.gsmSignalStrength
        val networkType = getNetworkTypeString(telephonyManager.networkType)
        val cellInfo: CellInfo? = if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            telephonyManager.allCellInfo.firstOrNull()
        } else {
            null
        }

        return CellularData(
            networkType = networkType,
            carrierName  = telephonyManager.networkOperatorName,
            mcc = telephonyManager.networkOperator.take(3),
            mnc = telephonyManager.networkOperator.takeLast(2),
            signalStrength = signalStrength?.let { it * 2 - 113 },
            isRoaming = telephonyManager.isNetworkRoaming,
            ipAddress = getMobileIpAddress(),
            cellTowerInfo = getCellTowerInfo(cellInfo),
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getCellTowerInfo(cellInfo: CellInfo?): CellTowerInfo? {
        return cellInfo?.let {
            when (it) {
                is CellInfoGsm -> {
                    val cellIdentity = it.cellIdentity
                    CellTowerInfo(
                        cellId = cellIdentity.cid.toString(),
                        locationAreaCode = cellIdentity.lac.toString(),
                        mobileCountryCode = cellIdentity.mccString ?: "N/A",
                        mobileNetworkCode = cellIdentity.mncString ?: "N/A"
                    )
                }
                is CellInfoLte -> {
                    val cellIdentity = it.cellIdentity
                    CellTowerInfo(
                        cellId = cellIdentity.ci.toString(),
                        locationAreaCode = cellIdentity.tac.toString(),
                        mobileCountryCode = cellIdentity.mccString ?: "N/A",
                        mobileNetworkCode = cellIdentity.mncString ?: "N/A"
                    )
                }
                is CellInfoCdma -> {
                    val cellIdentity = it.cellIdentity
                    CellTowerInfo(
                        cellId = cellIdentity.basestationId.toString(),
                        locationAreaCode = cellIdentity.networkId.toString(),
                        mobileCountryCode = "N/A", // CDMA doesn't have MCC
                        mobileNetworkCode = "N/A"  // CDMA doesn't have MNC
                    )
                }
                else -> null
            }
        }
    }

    private fun getNetworkTypeString(type: Int): String {
        return when (type) {
            TelephonyManager.NETWORK_TYPE_LTE -> "4G"
            TelephonyManager.NETWORK_TYPE_NR -> "5G"
            TelephonyManager.NETWORK_TYPE_HSPA -> "3G"
            TelephonyManager.NETWORK_TYPE_EDGE -> "2G"
            else -> "Unknown"
        }
    }

    private fun formatIpAddress(ip: Int): String {
        return String.format(
            "%d.%d.%d.%d",
            (ip and 0xff),
            (ip shr 8 and 0xff),
            (ip shr 16 and 0xff),
            (ip shr 24 and 0xff)
        )
    }

    private fun getMobileIpAddress(): String? {
        try {
            val interfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val iface: NetworkInterface = interfaces.nextElement()
                val addresses: Enumeration<InetAddress> = iface.inetAddresses
                while (addresses.hasMoreElements()) {
                    val addr: InetAddress = addresses.nextElement()
                    if (!addr.isLoopbackAddress && addr is Inet4Address) {
                        return addr.hostAddress
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}