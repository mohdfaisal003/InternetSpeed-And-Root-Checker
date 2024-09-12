package com.rooted.deviceinfo.mvvm.pojos

//data class NetworkDetailPojo(networkDetailList: List<NetworkInfo>)

data class NetworkInfo(
    val connectionType: String,
    val isConnected: Boolean,
    val wifiInfo: WifiData?,
    val cellularInfo: CellularData?,
)

data class WifiData(
    val ssid: String?,
    val bssid: String?,
    val rssi: Int?,
    val linkSpeed: Int?,
    val frequency: Int?,
    val ipAddress: String?,
    val macAddress: String?,
    val gateway: String?,
    val dnsServers: List<String>?,
    val subnetMask: String?,
    val isMetered: Boolean,
)

data class CellularData(
    val networkType: String?,
    val carrierName: String?,
    val mcc: String?,
    val mnc: String?,
    val signalStrength: Int?,
    val isRoaming: Boolean?,
    val ipAddress: String?,
    val cellTowerInfo: CellTowerInfo?,
)

data class CellTowerInfo(
    val cellId: String?,
    val locationAreaCode: String?,
    val mobileCountryCode: String?,
    val mobileNetworkCode: String?,
)