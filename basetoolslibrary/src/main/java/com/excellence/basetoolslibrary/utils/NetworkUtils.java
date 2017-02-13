package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ZhangWei on 2017/1/24.
 */

/**
 * 网络相关
 */
public class NetworkUtils
{
	public static final int NO_NETWORK = -1;
	public static final int TYPE_ETHERNET = 1;
	public static final int TYPE_WIFI = 2;
	public static final int TYPE_3G = 3;

	/**
	 * 检测网络连接
	 *
	 * @param context
	 * @return
	 */
	public static int checkNetState(Context context)
	{
		NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
		{
			int iType = networkInfo.getType();
			switch (iType)
			{
				case TYPE_ETHERNET:
					return TYPE_ETHERNET;

				case TYPE_WIFI:
					return TYPE_WIFI;

				default:
					// 3G
					return TYPE_3G;
			}
		}
		return NO_NETWORK;
	}
}
