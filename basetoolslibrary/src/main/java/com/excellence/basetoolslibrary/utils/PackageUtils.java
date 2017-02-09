package com.excellence.basetoolslibrary.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * Created by ZhangWei on 2016/12/19.
 */

/**
 * 包相关
 */
public class PackageUtils
{
	/**
	 * 获取安装的所有应用
	 *
	 * @param context
	 * @return
	 */
	public static List<ResolveInfo> getAllInstalledApps(Context context)
	{
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
		// 进行排序
		Collections.sort(apps, new ResolveInfo.DisplayNameComparator(packageManager));
		return apps;
	}

	/**
	 * 获取安装的系统应用
	 *
	 * @param context
	 * @return
     */
	public static List<ResolveInfo> getSystemInstalledApps(Context context)
	{
		List<ResolveInfo> allApps = getAllInstalledApps(context);
		List<ResolveInfo> systemInstalledApps = new ArrayList<>();
		for (ResolveInfo resolveInfo : allApps)
		{
			if ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
			{
				systemInstalledApps.add(resolveInfo);
			}
		}
		return systemInstalledApps;
	}

	/**
	 * 获取安装的第三方应用
	 *
	 * @param context
	 * @return
	 */
	public static List<ResolveInfo> getUserInstalledApps(Context context)
	{
		List<ResolveInfo> allApps = getAllInstalledApps(context);
		List<ResolveInfo> userInstalledApps = new ArrayList<>();
		for (ResolveInfo resolveInfo : allApps)
		{
			if ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0 || (resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0)
			{
				userInstalledApps.add(resolveInfo);
			}
		}
		return userInstalledApps;
	}

	/**
	 * 判断应用是否安装
	 *
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static PackageInfo isAppInstalled(Context context, String packageName)
	{
		try
		{
			return context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
		}
		catch (PackageManager.NameNotFoundException e)
		{
			return null;
		}
	}

	/**
	 * 获取某应用的所有权限
	 *
	 * @param context
	 * @param packageName 某应用包名
     * @return
     */
	public static List<String> getPermissionList(Context context, String packageName)
	{
		List<String> permissionList = new ArrayList<>();
		try
		{
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
			if (packageInfo != null && packageInfo.requestedPermissions != null)
				permissionList.addAll(Arrays.asList(packageInfo.requestedPermissions));
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return permissionList;
	}

	/**
	 * 获取当前应用的所有权限
	 *
	 * @param context
	 * @return
	 */
	public static List<String> getPermissionList(Context context)
	{
		return getPermissionList(context, context.getPackageName());
	}

	/**
	 * 检测某应用是否有某权限
	 *
	 * @param context
	 * @param unCheckedPermission 待检测的权限
	 * @param packageName 某应用包名
     * @return
     */
	public static boolean checkPermission(Context context, String unCheckedPermission, String packageName)
	{
		return PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(unCheckedPermission, packageName);
	}

	/**
	 * 检测当前应用是否有某权限
	 *
	 * @param context
	 * @param unCheckedPermission 待检测的权限
	 * @return
	 */
	public static boolean checkPermission(Context context, String unCheckedPermission)
	{
		return checkPermission(context, unCheckedPermission, context.getPackageName());
	}

	/**
	 * 获取当前应用版本名
	 *
	 * @param context
	 * @return
	 */
	public static String getAppVersionName(Context context)
	{
		PackageManager manager = context.getPackageManager();
		String versionName = "";
		try
		{
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			versionName = packageInfo.versionName;
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 获取当前应用版本号
	 *
	 * @param context
	 * @return
	 */
	public static int getAppVersionCode(Context context)
	{
		PackageManager manager = context.getPackageManager();
		int versionCode = 0;
		try
		{
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			versionCode = packageInfo.versionCode;
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 获取当前应用大小
	 *
	 * @param context
	 * @return
	 */
	public static String getAppSize(Context context)
	{
		PackageManager manager = context.getPackageManager();
		String size = "";
		try
		{
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			size = FileUtils.formatFileSize((new File(packageInfo.applicationInfo.publicSourceDir).length()));
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 获取当前应用安装时间
	 *
	 * @param context
	 * @return
	 */
	public static String getAppTime(Context context)
	{
		PackageManager manager = context.getPackageManager();
		String time = "";
		try
		{
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			time = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(new File(packageInfo.applicationInfo.sourceDir).lastModified()));
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return time;
	}
}
