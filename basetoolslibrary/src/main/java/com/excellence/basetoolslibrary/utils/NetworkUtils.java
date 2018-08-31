package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/24
 *     desc   : 网络相关工具类
 *     			权限：{@link android.Manifest.permission#INTERNET				}
 *     			 	 {@link android.Manifest.permission#ACCESS_NETWORK_STATE}
 *     			 	 {@link android.Manifest.permission#ACCESS_WIFI_STATE	}
 *     				 {@link android.Manifest.permission#CHANGE_WIFI_STATE	}
 *
 *     			isAvailable、isConnected：1，显示连接已保存，但标题栏没有，即没有实质连接上   not connect， available
 *										 2，显示连接已保存，标题栏也有已连接上的图标，          connect， available
 * 										 3，选择不保存后 								not connect， available
 *										 4，选择连接，在正在获取IP地址时					not connect， not available
 * </pre>
 */

public class NetworkUtils
{

	public enum NetworkType
	{
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
	private static NetworkInfo getActiveNetworkInfo(Context context)
	{
		return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
	}

	/**
	 * 检查网络是否连接
	 *
	 * @param context 上下文
	 * @return {@code true}：是<br>{@code false}：否
	 */
	public static boolean isConnected(Context context)
	{
		NetworkInfo info = getActiveNetworkInfo(context);
		return info != null && info.isConnected();
	}

	/**
	 * 判断网络是否可用
	 * {@code ping -c 1 -w 1}：{@code -c 1}：执行次数 1次；{@code -w 1}：等待每个响应的最长时间 1s
	 * 223.5.5.5：阿里云
	 * 206.190.36.45：yahoo.com
	 *
	 * @return {@code true}：可用<br>{@code false}：不可用
	 */
	public static boolean isAvailableByPing()
	{
		ShellUtils.CommandResult result = ShellUtils.execRuntimeCommand("ping -c 1 -w 1 223.5.5.5");
		return result.resultCode == 0;
	}

	/**
	 * 判断移动数据是否打开
	 *
	 * @param context 上下文
	 * @return {@code true}：打开<br>{@code false}：关闭
	 */
	public static boolean isMobileDataEnabled(Context context)
	{
		try
		{
			TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			Method getMobileDataEnabledMethod = manager.getClass().getDeclaredMethod("getDataEnabled");
			if (getMobileDataEnabledMethod != null)
			{
				return (boolean) getMobileDataEnabledMethod.invoke(manager);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @deprecated 反射不能打开或关闭移动数据，故舍弃
	 * 打开或关闭移动数据
	 *
	 * @param context 上下文
	 * @param enabled {@code true}：打开<br>{@code false}：关闭
	 */
	public static void setMobileDataEnabled(Context context, boolean enabled)
	{
		try
		{
			TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			Method setMobileDataEnabledMethod = manager.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
			if (setMobileDataEnabledMethod != null)
			{
				setMobileDataEnabledMethod.invoke(manager, enabled);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是4G网络
	 *
	 * @param context 上下文
	 * @return {@code true}：是<br>{@code false}：否
	 */
	public static boolean is4G(Context context)
	{
		NetworkInfo info = getActiveNetworkInfo(context);
		return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
	}

	/**
	 * 判断是否打开WiFi
	 *
	 * @param context 上下文
	 * @return {@code true}：是<br>{@code false}：否
	 */
	public static boolean isWiFiEnabled(Context context)
	{
		WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}

	/**
	 * 打开或关闭WiFi
	 *
	 * @param context 上下文
	 * @param enabled {@code true}：打开<br>{@code false}：关闭
	 */
	public static void setWiFiEnabled(Context context, boolean enabled)
	{
		WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		if (enabled)
		{
			if (!wifiManager.isWifiEnabled())
			{
				wifiManager.setWifiEnabled(true);
			}
		}
		else
		{
			if (wifiManager.isWifiEnabled())
			{
				wifiManager.setWifiEnabled(false);
			}
		}
	}

	/**
	 * 判断WiFi是否连接
	 *
	 * @param context 上下文
	 * @return {@code true}：是<br>{@code false}：否
	 */
	public static boolean isWiFiConnected(Context context)
	{
		NetworkInfo info = getActiveNetworkInfo(context);
		return info != null && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_WIFI;
	}

	/**
	 * 判断WiFi是否可用
	 *
	 * @param context 上下文
	 * @return {@code true}：是<br>{@code false}：否
	 */
	public static boolean isWiFiAvailable(Context context)
	{
		return isWiFiEnabled(context) && isAvailableByPing();
	}

	private static final int NETWORK_TYPE_GSM = 16;
	private static final int NETWORK_TYPE_TD_SCDMA = 17;
	private static final int NETWORK_TYPE_IWLAN = 18;

	/**
	 * 获取网络运营商名称
	 * <p>中国移动、中国联通、中国电信</p>
	 *
	 * @param context 上下文
	 * @return 网络运营商名称
	 */
	public static String getNetworkOperatorName(Context context)
	{
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return manager == null ? null : manager.getNetworkOperatorName();
	}

	/**
	 * 获取当前网络类型
	 *
	 * @param context 上下文
	 * @return NetworkType网络类型
	 *         <ul>
	 *           <li>{@link NetworkType#NETWORK_WIFI   } </li>
	 *           <li>{@link NetworkType#NETWORK_4G     } </li>
	 *           <li>{@link NetworkType#NETWORK_3G     } </li>
	 *           <li>{@link NetworkType#NETWORK_2G     } </li>
	 *           <li>{@link NetworkType#NETWORK_UNKNOWN} </li>
	 *           <li>{@link NetworkType#NETWORK_NO     } </li>
	 *         </ul>
	 */
	public static NetworkType getNetworkType(Context context)
	{
		NetworkType networkType = NetworkType.NETWORK_NO;
		NetworkInfo info = getActiveNetworkInfo(context);
		if (info != null && info.isAvailable())
		{
			switch (info.getType())
			{
			case ConnectivityManager.TYPE_WIFI:
				networkType = NetworkType.NETWORK_WIFI;
				break;
			case ConnectivityManager.TYPE_MOBILE:
				switch (info.getSubtype())
				{
				case NETWORK_TYPE_GSM:
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN:
					networkType = NetworkType.NETWORK_2G;
					break;

				case NETWORK_TYPE_TD_SCDMA:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B:
				case TelephonyManager.NETWORK_TYPE_EHRPD:
				case TelephonyManager.NETWORK_TYPE_HSPAP:
					networkType = NetworkType.NETWORK_3G;
					break;

				case NETWORK_TYPE_IWLAN:
				case TelephonyManager.NETWORK_TYPE_LTE:
					networkType = NetworkType.NETWORK_4G;
					break;

				default:
					String subTypeName = info.getSubtypeName();
					if (subTypeName.equalsIgnoreCase("TD-SCDMA") || subTypeName.equalsIgnoreCase("WCDMA") || subTypeName.equalsIgnoreCase("CDMA2000"))
					{
						networkType = NetworkType.NETWORK_3G;
					}
					else
					{
						networkType = NetworkType.NETWORK_UNKNOWN;
					}
					break;
				}
				break;

			default:
				networkType = NetworkType.NETWORK_UNKNOWN;
				break;
			}
		}
		return networkType;
	}

	/**
	 * 获取网络IP地址
	 *
	 * @return IP地址
	 */
	public static String getIPAddress(boolean useIPv4)
	{
		try
		{
			for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements();)
			{
				NetworkInterface networkInterface = enumeration.nextElement();
				// 防止小米手机返回10.0.2.15
				if (!networkInterface.isUp())
				{
					continue;
				}
				for (Enumeration<InetAddress> addresses = networkInterface.getInetAddresses(); addresses.hasMoreElements();)
				{
					InetAddress inetAddress = addresses.nextElement();
					if (!inetAddress.isLoopbackAddress())
					{
						String hostAddress = inetAddress.getHostAddress();
						boolean isIPv4 = hostAddress.indexOf(':') < 0;
						if (useIPv4)
						{
							if (isIPv4)
							{
								return hostAddress;
							}
						}
						else
						{
							if (!isIPv4)
							{
								int index = hostAddress.indexOf('%');
								return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据域名获取ip
	 *
	 * @param domain 域名
	 * @return IP地址
	 */
	public static String getDomainAddress(final String domain)
	{
		try
		{
			ExecutorService executorService = Executors.newCachedThreadPool();
			Future<String> future = executorService.submit(new Callable<String>()
			{
				@Override
				public String call() throws Exception
				{
					InetAddress inetAddress;
					inetAddress = InetAddress.getByName(domain);
					return inetAddress.getHostAddress();
				}
			});
			return future.get();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
