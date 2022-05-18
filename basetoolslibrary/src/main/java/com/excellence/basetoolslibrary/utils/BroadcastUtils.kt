package com.excellence.basetoolslibrary.utils

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/5/13
 *     desc   :
 * </pre>
 */
object BroadcastUtils {

    /**
     * receiver USB broadcast
     */
    @JvmStatic
    fun registerMountAction(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED)
        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED)
        intentFilter.addDataScheme("file")
        return intentFilter
    }

    /**
     * receiver screen on/off broadcast
     */
    @JvmStatic
    fun registerScreenAction(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        return intentFilter
    }

    /**
     * receiver package install/uninstall broadcast
     */
    @JvmStatic
    fun registerPackageAction(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED)
        intentFilter.addDataScheme("package")
        return intentFilter
    }

    /**
     * receiver network broadcast
     */
    @JvmStatic
    fun registerNetworkStateAction(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION)
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        return intentFilter
    }

    /**
     * receiver boot broadcast
     */
    @JvmStatic
    fun registerBootAction(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED)
        return intentFilter
    }
}