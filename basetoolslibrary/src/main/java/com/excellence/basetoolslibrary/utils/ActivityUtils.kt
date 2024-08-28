package com.excellence.basetoolslibrary.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.annotation.FloatRange
import androidx.annotation.NonNull

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/2
 *     desc   : Activity相关工具类
 * </pre>
 */
object ActivityUtils {

    /**
     * Activity跳转
     */
    @JvmStatic
    fun startAnotherActivity(context: Context, @NonNull activityCls: Class<out Activity>) {
        val intent = Intent(context, activityCls)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * Activity跳转
     */
    @JvmStatic
    fun startAnotherActivity(context: Context, packageName: String): Boolean {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            return true
        }
        return false
    }

    /**
     * 设置Activity窗口透明度
     */
    @JvmStatic
    fun setActivityWindowAlpha(activity: Activity, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
        AlphaUtils.setAlpha(activity, alpha)
    }

    /**
     * 判断Activity是否在栈顶
     * 需要权限 [android.Manifest.permission.GET_TASKS]
     */
    @JvmStatic
    fun isActivityTopStack(context: Context, @NonNull activityCls: Class<out Activity>): Boolean {
        val manager: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTaskInfo: List<ActivityManager.RunningTaskInfo> = manager.getRunningTasks(1)
        if (EmptyUtils.isNotEmpty(runningTaskInfo)) {
            val componentName = runningTaskInfo[0].topActivity
            return activityCls.name == componentName?.className
        }
        return false
    }

    /**
     * 获取某应用入口Activity
     */
    @JvmStatic
    fun getLauncherActivity(context: Context, packageName: String): String {
        val infos: List<ResolveInfo> = AppUtils.getAllInstalledApps(context)
        for (info: ResolveInfo in infos) {
            if (info.activityInfo.packageName == packageName) {
                return info.activityInfo.name
            }
        }
        return ""
    }

    /**
     * 获取某应用入口Activity
     */
    @JvmStatic
    fun getAppLauncherActivity(context: Context, packageName: String): String {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.setPackage(packageName)
        val pm: PackageManager = context.packageManager
        val info = pm.queryIntentActivities(intent, 0)
        return if (info == null || info.size == 0) {
            ""
        } else info[0].activityInfo.name
    }
}