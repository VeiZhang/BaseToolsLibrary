package com.excellence.basetoolslibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/23
 *     desc   : Activity相关工具类
 * </pre>
 */

public class ActivityUtils
{
	/**
	 * Activity跳转
	 *
	 * @param context 上下文
	 * @param activityCls Activity.class
	 */
	public static void startAnotherActivity(Context context, @NonNull Class<? extends Activity> activityCls)
	{
		Intent intent = new Intent(context, activityCls);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * Activity跳转
	 *
	 * @param context 上下文
	 * @param packageName 包名
	 * @return 是否成功跳转
	 */
	public static boolean startAnotherActivity(Context context, String packageName)
	{
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
		if (intent != null)
		{
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			return true;
		}else
			return false;
	}

	/**
	 * 设置Activity窗口透明度
	 *
	 * @param activity Activity
	 * @param alpha 透明度
	 */
	public static void setActivityWindowAlpha(Activity activity, @FloatRange(from = 0.0, to = 1.0) float alpha)
	{
		// TODO Auto-generated method stub
		WindowManager.LayoutParams params = activity.getWindow().getAttributes();
		params.alpha = alpha;
		activity.getWindow().setAttributes(params);
	}

	/**
	 * 判断Activity是否在栈顶
	 * 需要权限 {@link android.Manifest.permission#GET_TASKS}
	 *
	 * @param context 上下文
	 * @param activityCls Activity.class
	 * @return
	 */
	public static boolean isActivityTopStack(Context context, @NonNull Class<? extends Activity> activityCls)
	{
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
		if (EmptyUtils.isNotEmpty(runningTaskInfos))
		{
			ComponentName componentName = runningTaskInfos.get(0).topActivity;
			if (activityCls.getName().equals(componentName.getClassName()))
				return true;
		}
		return false;
	}

	/**
	 * 获取某应用入口Activity
	 *
	 * @param context 上下文
	 * @param packageName 包名
	 * @return
	 */
	public static String getLauncherActivity(Context context, String packageName)
	{
		List<ResolveInfo> infos = AppUtils.getAllInstalledApps(context);
		for (ResolveInfo info : infos)
		{
			if (info.activityInfo.packageName.equals(packageName))
				return info.activityInfo.name;
		}
		return null;
	}
}
