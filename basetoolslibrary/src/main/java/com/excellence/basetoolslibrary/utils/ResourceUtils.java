package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/20
 *     desc   : 资源相关工具类
 * </pre>
 */

public class ResourceUtils
{
	/**
	 * 解析资源的全名
	 * 例如：R.string.app_name
	 * 结果：com.excellence.tooldemo:string/app_name
	 * 
	 * @see #getPackageName
	 * @see #getTypeName
	 * @see #getEntryName
	 *
	 * @param context 上下文
	 * @param resId 资源Id
	 * @return 结果格式：package:type/entry
	 */
	public static String getName(@NonNull Context context, @AnyRes int resId)
	{
		return context.getResources().getResourceName(resId);
	}

	/**
	 * 解析资源名
	 * 
	 * @see #getName
	 *
	 * @param context 上下文
	 * @param resId 资源Id
	 * @return 资源名
	 */
	public static String getEntryName(@NonNull Context context, @AnyRes int resId)
	{
		return context.getResources().getResourceEntryName(resId);
	}

	/**
	 * 解析资源类型名
	 * 
	 * @see #getName
	 *
	 * @param context 上下文
	 * @param resId 资源Id
	 * @return 资源类型名
	 */
	public static String getTypeName(@NonNull Context context, @AnyRes int resId)
	{
		return context.getResources().getResourceTypeName(resId);
	}

	/**
	 * 解析资源的包名
	 * @see #getName
	 *
	 * @param context 上下文
	 * @param resId 资源Id
	 * @return 资源所在的包名
	 */
	public static String getPackageName(@NonNull Context context, @AnyRes int resId)
	{
		return context.getResources().getResourcePackageName(resId);
	}

	/**
	 * 获取资源Id
	 * @see #getName
	 *
	 * @param context 上下文
	 * @param name 资源名
	 * @param type 资源类型名
	 * @param packageName 包名
	 * @return 0表示没有该资源
	 */
	public static int getIdentifier(Context context, String name, String type, String packageName)
	{
		return context.getResources().getIdentifier(name, type, packageName);
	}
}
