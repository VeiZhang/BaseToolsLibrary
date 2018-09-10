package com.excellence.basetoolslibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/9/10
 *     desc   : 设备相关
 *              权限：{@link android.Manifest.permission#READ_PHONE_STATE}
 * </pre> 
 */
public class DeviceUtils
{
	private static TelephonyManager getTelephonyManager(Context context)
	{
		return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}

	/**
	 * 获取IMEI（设备识别码）
	 *
	 * @param context
	 * @return
	 */
	@SuppressLint({ "MissingPermission", "HardwareIds" })
	public static String getIMEI(Context context)
	{
		return getTelephonyManager(context).getDeviceId();
	}

	/**
	 * 获取IMSI（用户识别码）
	 *
	 * @param context
	 * @return
	 */
	@SuppressLint({ "MissingPermission", "HardwareIds" })
	public static String getIMSI(Context context)
	{
		return getTelephonyManager(context).getSubscriberId();
	}

	/**
	 * 获取手机号
	 *
	 * @param context
	 * @return
	 */
	@SuppressLint({ "MissingPermission", "HardwareIds" })
	public static String getPhone(Context context)
	{
		return getTelephonyManager(context).getLine1Number();
	}

	/**
	 * 获取SIM卡序列号
	 *
	 * @param context
	 * @return
	 */
	@SuppressLint({ "MissingPermission", "HardwareIds" })
	public static String getSIM(Context context)
	{
		return getTelephonyManager(context).getSimSerialNumber();
	}

	/**
	 * 获取SIM卡国家：cn
	 *
	 * @param context
	 * @return
	 */
	public static String getSimCountry(Context context)
	{
		return getTelephonyManager(context).getSimCountryIso();
	}

	/**
	 * 获取SIM卡运营商：46001
	 *
	 * @param context
	 * @return
	 */
	public static String getSimOperator(Context context)
	{
		return getTelephonyManager(context).getSimOperator();
	}

	/**
	 * 获取SIM卡运营商名字：中国移动
	 *
	 * @param context
	 * @return
	 */
	public static String getSimOperatorName(Context context)
	{
		return getTelephonyManager(context).getSimOperatorName();
	}

	/**
	 * 获取SIM卡状态
	 *
	 * @param context
	 * @return
	 */
	@SuppressLint({ "MissingPermission", "HardwareIds" })
	public static int getSimState(Context context)
	{
		return getTelephonyManager(context).getSimState();
	}

}
