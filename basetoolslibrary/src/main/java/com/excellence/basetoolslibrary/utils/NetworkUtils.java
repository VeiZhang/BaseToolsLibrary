package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/24
 *     desc   : 网络相关工具类
 * </pre>
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
	 * @param context 上下文
	 * @return 网络类型
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
