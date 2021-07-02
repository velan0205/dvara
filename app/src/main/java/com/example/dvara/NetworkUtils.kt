/*
package com.example.dvara

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.wifi.WifiManager

class NetworkUtil {


    val TYPE_WIFI = 1
    val TYPE_MOBILE = 2
    val TYPE_NOT_CONNECTED = 0

    val WIFI_ENABLED = "Wifi enabled"
    val MOBILE_ENABLE = "Mobile data enabled"
    val NOT_CONNECTED_TO_INTERNET = "Not connected to Internet"


    fun getConnectivityStatus(context: Context): Int {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE
        }
        return TYPE_NOT_CONNECTED
    }

    fun getConnectivityStatusString(context: Context?): String? {
        val conn: Int = NetworkUtil.getConnectivityStatus(context)
        var status: String? = null
        when {
            conn == NetworkUtil.TYPE_WIFI -> {
                status = WIFI_ENABLED
            }
            NetworkUtil.TYPE_MOBILE -> {
                status = MOBILE_ENABLE
            }
            NetworkUtil.TYPE_NOT_CONNECTED -> {
                status = NOT_CONNECTED_TO_INTERNET
            }
        }
        return status
    }

    fun getWifiOff(context: Context) {
        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifi.isWifiEnabled = false
    }

    fun getWifiOn(context: Context) {
        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifi.isWifiEnabled = true
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val netInfos = connectivityManager.activeNetworkInfo
            if (netInfos != null) return netInfos.isConnected
        }
        return false
    }
}
*/
