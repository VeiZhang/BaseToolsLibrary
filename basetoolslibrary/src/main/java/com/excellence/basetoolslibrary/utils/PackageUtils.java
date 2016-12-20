package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.Collections;
import java.util.List;

/**
 * Created by ZhangWei on 2016/12/19.
 */

public class PackageUtils
{

	public static List<ResolveInfo> getResolveInfoApps(Context context)
	{
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
		// 进行排序
		Collections.sort(apps, new ResolveInfo.DisplayNameComparator(packageManager));
		return apps;
	}
}
