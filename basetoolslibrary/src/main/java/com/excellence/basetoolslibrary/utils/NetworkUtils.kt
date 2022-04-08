package com.excellence.basetoolslibrary.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import com.excellence.basetoolslibrary.utils.ConvertUtils.bytes2HexString
import com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*
import java.util.concurrent.Executors

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2022/4/8
 *     desc   : 网络相关工具类
 *     			权限：[android.Manifest.permission.INTERNET]
 *                   [android.Manifest.permission.ACCESS_NETWORK_STATE]
 *                   [android.Manifest.permission.ACCESS_WIFI_STATE]
 *                   [android.Manifest.permission.CHANGE_WIFI_STATE]
 *
 *     			isAvailable、isConnected：1，显示连接已保存，但标题栏没有，即没有实质连接上   not connect， available
 *										 2，显示连接已保存，标题栏也有已连接上的图标，           connect， available
 * 										 3，选择不保存后 								 not connect， available
 *										 4，选择连接，在正在获取IP地址时					 not connect， not available
 * </pre>
 */
object NetworkUtils {

    const val DEFAULT_WIRELESS_MAC = "02:00:00:00:00:00"

    enum class NetworkType {
        NETWORK_ETH,
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    /**
     * 获取活动的网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    @JvmStatic
    fun getActiveNetworkInfo(context: Context): NetworkInfo? {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
    }

    /**
     * 检查网络是否连接
     *
     * @param context 上下文
     * @return `true`：是<br>`false`：否
     */
    @JvmStatic
    fun isConnected(context: Context): Boolean {
        return getActiveNetworkInfo(context)?.isConnected ?: false
    }

    /**
     * 判断网络是否可用
     * `ping -c 1 -w 1`：`-c 1`：执行次数 1次；`-w 1`：等待每个响应的最长时间 1s
     * 223.5.5.5：阿里云
     * 206.190.36.45：yahoo.com
     *
     * @return `true`：可用<br>`false`：不可用
     */
    @JvmStatic
    fun isAvailableByPing(): Boolean {
        return isAvailableByPing("223.5.5.5")
    }

    /**
     * 使用ping命令，判断网络是否可用
     * `ping -c 1 -w 1`：`-c 1`：执行次数 1次；`-w 1`：等待每个响应的最长时间 1s
     *
     * @param address
     * @return
     */
    @JvmStatic
    fun isAvailableByPing(address: String?): Boolean {
        val cmd = String.format("ping -c 1 -w 1 %s", address)
        val result = ShellUtils.execRuntimeCommand(cmd)
        return result.resultCode == 0
    }

    /**
     * 判断移动数据是否打开
     *
     * @param context 上下文
     * @return `true`：打开<br>`false`：关闭
     */
    @JvmStatic
    fun isMobileDataEnabled(context: Context): Boolean {
        try {
            val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val getMobileDataEnabledMethod = manager.javaClass.getDeclaredMethod("getDataEnabled")
            return getMobileDataEnabledMethod.invoke(manager) as Boolean
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 打开或关闭移动数据
     *
     * @param context 上下文
     * @param enabled `true`：打开<br>`false`：关闭
     */
    @Deprecated("反射不能打开或关闭移动数据，故舍弃")
    @JvmStatic
    fun setMobileDataEnabled(context: Context, enabled: Boolean) {
        try {
            val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val setMobileDataEnabledMethod = manager.javaClass.getDeclaredMethod("setDataEnabled", Boolean::class.javaPrimitiveType)
            setMobileDataEnabledMethod.invoke(manager, enabled)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 判断是否是4G网络
     *
     * @param context 上下文
     * @return `true`：是<br>`false`：否
     */
    @JvmStatic
    fun is4G(context: Context): Boolean {
        return getActiveNetworkInfo(context)?.let {
            it.isAvailable && it.subtype == TelephonyManager.NETWORK_TYPE_LTE
        } ?: false
    }

    /**
     * 判断是否打开WiFi
     *
     * @param context 上下文
     * @return `true`：是<br>`false`：否
     */
    @JvmStatic
    fun isWiFiEnabled(context: Context): Boolean {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }

    /**
     * 打开或关闭WiFi
     *
     * @param context 上下文
     * @param enabled `true`：打开<br>`false`：关闭
     */
    @JvmStatic
    fun setWiFiEnabled(context: Context, enabled: Boolean) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (enabled) {
            if (!wifiManager.isWifiEnabled) {
                wifiManager.isWifiEnabled = true
            }
        } else {
            if (wifiManager.isWifiEnabled) {
                wifiManager.isWifiEnabled = false
            }
        }
    }

    /**
     * 判断以太网是否连接
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isEthConnected(context: Context): Boolean {
        return getActiveNetworkInfo(context)?.let {
            it.isConnected && it.type == ConnectivityManager.TYPE_ETHERNET
        } ?: false
    }

    /**
     * 判断以太网是否可用
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isEthAvailable(context: Context): Boolean {
        return getActiveNetworkInfo(context)?.let {
            it.isAvailable && it.type == ConnectivityManager.TYPE_ETHERNET
        } ?: false
    }

    /**
     * 判断WiFi是否连接
     *
     * @param context 上下文
     * @return `true`：是<br>`false`：否
     */
    @JvmStatic
    fun isWiFiConnected(context: Context): Boolean {
        return getActiveNetworkInfo(context)?.let {
            it.isConnected && it.type == ConnectivityManager.TYPE_WIFI
        } ?: false
    }

    /**
     * 判断WiFi是否可用
     *
     * @param context 上下文
     * @return `true`：是<br>`false`：否
     */
    @JvmStatic
    fun isWiFiAvailable(context: Context): Boolean {
        return getActiveNetworkInfo(context)?.let {
            it.isAvailable && it.type == ConnectivityManager.TYPE_WIFI
        } ?: false
    }

    /**
     * 通过ping的方式判断WiFi是否可用
     *
     * @param context 上下文
     * @return `true`：是<br>`false`：否
     */
    @JvmStatic
    fun isWiFiAvailableByPing(context: Context): Boolean {
        return isWiFiEnabled(context) && isAvailableByPing()
    }

    /**
     * 获取网络运营商名称
     *
     * 中国移动、中国联通、中国电信
     *
     * @param context 上下文
     * @return 网络运营商名称
     */
    @JvmStatic
    fun getNetworkOperatorName(context: Context): String? {
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return manager.networkOperatorName
    }

    private const val NETWORK_TYPE_GSM = 16
    private const val NETWORK_TYPE_TD_SCDMA = 17
    private const val NETWORK_TYPE_IWLAN = 18

    /**
     * 获取当前网络类型
     *
     * @param context 上下文
     * @return NetworkType网络类型
     * <ul>
     *   <li>[NetworkType.NETWORK_ETH]</li>
     *   <li>[NetworkType.NETWORK_WIFI]</li>
     *   <li>[NetworkType.NETWORK_4G]</li>
     *   <li>[NetworkType.NETWORK_3G]</li>
     *   <li>[NetworkType.NETWORK_2G]</li>
     *   <li>[NetworkType.NETWORK_UNKNOWN]</li>
     *   <li>[NetworkType.NETWORK_NO]</li>
     * </ul>
     */
    @JvmStatic
    fun getNetworkType(context: Context): NetworkType {
        var networkType = NetworkType.NETWORK_NO
        val info = getActiveNetworkInfo(context)
        if (info != null && info.isAvailable) {
            networkType = when (info.type) {
                ConnectivityManager.TYPE_ETHERNET -> NetworkType.NETWORK_ETH
                ConnectivityManager.TYPE_WIFI -> NetworkType.NETWORK_WIFI
                ConnectivityManager.TYPE_MOBILE -> {
                    when (info.subtype) {
                        NETWORK_TYPE_GSM,
                        TelephonyManager.NETWORK_TYPE_GPRS,
                        TelephonyManager.NETWORK_TYPE_CDMA,
                        TelephonyManager.NETWORK_TYPE_EDGE,
                        TelephonyManager.NETWORK_TYPE_1xRTT,
                        TelephonyManager.NETWORK_TYPE_IDEN -> NetworkType.NETWORK_2G

                        NETWORK_TYPE_TD_SCDMA,
                        TelephonyManager.NETWORK_TYPE_EVDO_A,
                        TelephonyManager.NETWORK_TYPE_UMTS,
                        TelephonyManager.NETWORK_TYPE_EVDO_0,
                        TelephonyManager.NETWORK_TYPE_HSDPA,
                        TelephonyManager.NETWORK_TYPE_HSUPA,
                        TelephonyManager.NETWORK_TYPE_HSPA,
                        TelephonyManager.NETWORK_TYPE_EVDO_B,
                        TelephonyManager.NETWORK_TYPE_EHRPD,
                        TelephonyManager.NETWORK_TYPE_HSPAP -> NetworkType.NETWORK_3G

                        NETWORK_TYPE_IWLAN,
                        TelephonyManager.NETWORK_TYPE_LTE -> NetworkType.NETWORK_4G

                        else -> {
                            val subTypeName = info.subtypeName
                            if (subTypeName.equals("TD-SCDMA", ignoreCase = true)
                                    || subTypeName.equals("WCDMA", ignoreCase = true)
                                    || subTypeName.equals("CDMA2000", ignoreCase = true)) {
                                NetworkType.NETWORK_3G
                            } else {
                                NetworkType.NETWORK_UNKNOWN
                            }
                        }
                    }
                }
                else -> NetworkType.NETWORK_UNKNOWN
            }
        }
        return networkType
    }

    /**
     * 获取内网网络IP地址
     *
     * @return IP地址
     */
    @JvmStatic
    fun getIPAddress(useIPv4: Boolean): String? {
        try {
            val enumeration = NetworkInterface.getNetworkInterfaces()
            while (enumeration.hasMoreElements()) {
                val networkInterface = enumeration.nextElement()
                // 防止小米手机返回10.0.2.15
                if (!networkInterface.isUp) {
                    continue
                }
                val addresses = networkInterface.inetAddresses
                while (addresses.hasMoreElements()) {
                    val inetAddress = addresses.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        val hostAddress = inetAddress.hostAddress ?: continue
                        val isIPv4 = (hostAddress.indexOf(':') < 0)

                        if (useIPv4) {
                            if (isIPv4) {
                                return hostAddress
                            }
                        } else {
                            if (!isIPv4) {
                                val index = hostAddress.indexOf('%')
                                return if (index < 0)
                                    hostAddress.toUpperCase(Locale.getDefault())
                                else
                                    hostAddress.substring(0, index).toUpperCase(Locale.getDefault())
                            }
                        }
                    }

                }

            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 根据域名获取ip
     *
     * @param domain 域名
     * @return IP地址
     */
    @JvmStatic
    fun getDomainAddress(domain: String?): String? {
        try {
            val executorService = Executors.newCachedThreadPool()
            val future = executorService.submit<String> {
                val inetAddress = InetAddress.getByName(domain)
                inetAddress.hostAddress
            }
            return future.get()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 读取Mac地址：优先获取Eth的MAC，当Eth为空，接着获取WiFi的MAC
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun readMac(context: Context): String? {
        var mac = getWiredMac(context)
        if (isEmpty(mac)) {
            mac = getWiredMac("eth0")
        }
        if (isEmpty(mac)) {
            mac = getWirelessMac(context)
        }
        return mac
    }

    /**
     * 获取Mac地址：使用Eth时读取Eth的MAC，否则读取WiFi的MAC
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getMac(context: Context): String? {
        return if (isEthConnected(context)) {
            getWiredMac(context)
        } else {
            getWirelessMac(context)
        }
    }

    /**
     * 获取有线Mac地址
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getWiredMac(context: Context): String? {
        var macAddress = ""
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null) {
                if (activeNetwork.type == ConnectivityManager.TYPE_ETHERNET) {
                    macAddress = activeNetwork.extraInfo
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return macAddress
    }

    /**
     * 读取eth里面的Mac地址：如eth0、eth1
     *
     * @param ethName eth0
     * @return
     */
    @JvmStatic
    fun getWiredMac(ethName: String?): String? {
        var ethName = ethName
        var macAddress = ""
        if (isEmpty(ethName)) {
            ethName = "eth0"
        }
        val nicInterface: NetworkInterface?
        try {
            nicInterface = NetworkInterface.getByName(ethName)
            if (nicInterface != null) {
                val buf = nicInterface.hardwareAddress
                val sbBuffer = StringBuilder()
                if (buf != null && buf.size > 1) {
                    sbBuffer.append(bytes2HexString(buf[0])).append(":")
                            .append(bytes2HexString(buf[1])).append(":")
                            .append(bytes2HexString(buf[2])).append(":")
                            .append(bytes2HexString(buf[3])).append(":")
                            .append(bytes2HexString(buf[4])).append(":")
                            .append(bytes2HexString(buf[5]))
                    macAddress = sbBuffer.toString()
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return macAddress
    }

    /**
     * 获取无线Mac地址，有可能获取[DEFAULT_WIRELESS_MAC]，再进一步读取WiFi接口wlan0的MAC
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getWirelessMac(context: Context): String? {
        var macAddress: String? = ""
        try {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo != null) {
                macAddress = wifiInfo.macAddress
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        if (DEFAULT_WIRELESS_MAC.equals(macAddress, ignoreCase = true)) {
            macAddress = getWiredMac("wlan0")
        }
        return macAddress
    }
}