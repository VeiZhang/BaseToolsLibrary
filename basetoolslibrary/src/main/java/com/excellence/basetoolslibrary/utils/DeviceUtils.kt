package com.excellence.basetoolslibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/9/10
 *     desc   : 设备相关 权限：[android.Manifest.permission.READ_PHONE_STATE]
 *
 * </pre>
 */
object DeviceUtils {

    private fun getTelephonyManager(context: Context): TelephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    /**
     * 获取IMEI（设备识别码）
     *
     * @param context
     * @return
     */
    @JvmStatic
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getIMEI(context: Context): String =
            getTelephonyManager(context).deviceId

    /**
     * 获取IMSI（用户识别码）
     *
     * @param context
     * @return
     */
    @JvmStatic
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getIMSI(context: Context): String =
            getTelephonyManager(context).subscriberId

    /**
     * 获取手机号
     *
     * @param context
     * @return
     */
    @JvmStatic
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getPhone(context: Context): String =
            getTelephonyManager(context).line1Number

    /**
     * 获取SIM卡序列号
     *
     * @param context
     * @return
     */
    @JvmStatic
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getSIM(context: Context): String =
            getTelephonyManager(context).simSerialNumber

    /**
     * 获取SIM卡国家：cn
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getSimCountry(context: Context): String =
            getTelephonyManager(context).simCountryIso

    /**
     * 获取SIM卡运营商：46001
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getSimOperator(context: Context): String =
            getTelephonyManager(context).simOperator

    /**
     * 获取SIM卡运营商名字：中国移动
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getSimOperatorName(context: Context): String =
            getTelephonyManager(context).simOperatorName

    /**
     * 获取SIM卡状态
     *
     * @param context
     * @return
     */
    @JvmStatic
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getSimState(context: Context): Int =
            getTelephonyManager(context).simState

}