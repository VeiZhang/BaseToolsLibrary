package com.excellence.basetoolslibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import java.util.List;

/**
 * Created by ZhangWei on 2017/1/23.
 */

/**
 * Activity相关
 */
public class ActivityUtils
{
	/**
	 * Activity跳转
	 * 
	 * @param context
	 * @param activityCls
	 */
	public static void startAnotherActivity(Context context, Class<? extends Activity> activityCls)
	{
		Intent intent = new Intent(context, activityCls);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * Activity跳转
	 *
	 * @param context
	 * @param packageName
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
	 * @param activity
	 * @param alpha
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
	 * 需要权限 {@link android.Manifest.permission.GET_TASKS}
	 *
	 * @param context
	 * @param activityCls
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
}
