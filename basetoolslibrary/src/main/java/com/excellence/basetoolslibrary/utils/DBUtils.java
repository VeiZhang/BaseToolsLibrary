package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/23
 *     desc   : 存储相关工具类
 * </pre>
 */

public class DBUtils
{
	/**
	 * 存储文件名
	 */
	private static String SHAREPREFERENCES_FILE_NAME = DBUtils.class.getSimpleName();

	/**
	 * 存储字符串
	 *
	 * @param context 上下文
	 * @param key 键值
	 * @param value 字符串
	 */
	public static void setStringSharedPreferences(Context context, String key, String value)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.apply();
	}

	/**
	 * 读取字符串
	 *
	 * @param context 上下文
	 * @param key 键值
	 * @return 字符串
	 */
	public static String getStringSharedPreferences(Context context, String key)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, null);
	}

	/**
	 * 存储布尔类型
	 *
	 * @param context 上下文
	 * @param key 键值
	 * @param value boolean
	 */
	public static void setBooleanSharedPreferences(Context context, String key, boolean value)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	/**
	 * 读取布尔类型
	 *
	 * @param context 上下文
	 * @param key 键值
	 * @return boolean
	 */
	public static boolean getBooleanSharedPreferences(Context context, String key)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, false);
	}
}
