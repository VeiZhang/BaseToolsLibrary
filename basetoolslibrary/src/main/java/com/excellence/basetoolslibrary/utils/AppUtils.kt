package com.excellence.basetoolslibrary.utils

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.pm.Signature
import android.os.Build
import android.os.Process
import android.util.DisplayMetrics
import android.util.Log
import java.io.File
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale
import kotlin.system.exitProcess

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/2
 *     desc   : 应用相关工具类
 * </pre>
 */
object AppUtils {

    /**
     * 退出应用
     */
    @JvmStatic
    fun exitApp(context: Context) {
        try {
            // 清除Activity栈
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                am.appTasks?.forEach {
                    Log.d("exitApp", "activity task : ${it.taskInfo}")
                    it.finishAndRemoveTask()
                }
            }

            // 进入Launcher主界面
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(homeIntent)

            // 杀死该应用进程
            Process.killProcess(Process.myPid())
            System.exit(0)
            Runtime.getRuntime().exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
            Runtime.getRuntime().exit(-1)
        }
    }

    /**
     * 重启应用
     */
    @JvmStatic
    fun relaunchApp(context: Context) {
        try {
            val intent: Intent? = IntentUtils.getLaunchAppIntent(context, context.packageName)
            if (intent == null) {
                Log.e("AppUtils", "Didn't exist launcher activity.")
                return
            }
            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            context.startActivity(intent)
            Process.killProcess(Process.myPid())
            exitProcess(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取所有应用包
     * targetSDK高版本需要权限
     * <uses-permission
     *      android:name="android.permission.QUERY_ALL_PACKAGES"
     *      tools:ignore="QueryAllPackagesPermission" />
     */
    @JvmStatic
    fun getAllApps(context: Context): List<PackageInfo> {
        val packageManager = context.packageManager
        return packageManager.getInstalledPackages(0)
    }

    /**
     * 获取安装的所有应用
     */
    @JvmStatic
    fun getAllInstalledApps(context: Context): List<ResolveInfo> {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val packageManager = context.packageManager
        val apps = packageManager.queryIntentActivities(mainIntent, 0)
        // 进行排序
        Collections.sort(apps, ResolveInfo.DisplayNameComparator(packageManager))
        return apps
    }

    /**
     * 获取安装的系统应用
     */
    @JvmStatic
    fun getSystemInstalledApps(context: Context): List<ResolveInfo> {
        val allApps = getAllInstalledApps(context)
        val systemApps = ArrayList<ResolveInfo>()
        for (item: ResolveInfo in allApps) {
            if (item.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                systemApps.add(item)
            }
        }
        return systemApps
    }

    /**
     * 获取安装的第三方应用
     */
    @JvmStatic
    fun getUserInstalledApps(context: Context): List<ResolveInfo> {
        val allApps = getAllInstalledApps(context)
        val userApps = ArrayList<ResolveInfo>()
        for (item: ResolveInfo in allApps) {
            if (item.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM <= 0
                    || item.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0) {
                userApps.add(item)
            }
        }
        return userApps
    }

    /**
     * 获取正在运行的应用：系统权限获取更多进程
     */
    @JvmStatic
    fun getRunningApps(context: Context): List<ResolveInfo> {
        val manager = (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
        val appProcessList = manager.runningAppProcesses

        val pgkProcessAppMap: MutableMap<String, RunningAppProcessInfo> = HashMap()
        for (appProcess in appProcessList) {
            val pkgNameList = appProcess.pkgList
            for (i in pkgNameList.indices) {
                val pkgName = pkgNameList[i]
                pgkProcessAppMap[pkgName] = appProcess
            }
        }

        val resolveInfoList = ArrayList<ResolveInfo>()
        val appList = getAllInstalledApps(context)
        for (info in appList) {
            val packageName = info.activityInfo.packageName
            if (pgkProcessAppMap.containsKey(packageName)) {
                resolveInfoList.add(info)
            }
        }
        return resolveInfoList
    }

    /**
     * 获取某应用的所有权限
     */
    @JvmStatic
    fun getPermissionList(context: Context, packageName: String): List<String> {
        val permissionList = ArrayList<String>()
        try {
            val packageInfo = context.packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            permissionList.addAll(packageInfo.requestedPermissions.asList())
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return permissionList
    }

    /**
     * 获取当前应用的所有权限
     */
    @JvmStatic
    fun getPermissionList(context: Context) = getPermissionList(context, context.packageName)

    /**
     * 检测某应用是否有某权限
     */
    @JvmStatic
    fun checkPermission(context: Context, unCheckedPermission: String, packageName: String) = PackageManager.PERMISSION_GRANTED == context.packageManager.checkPermission(unCheckedPermission, packageName)

    /**
     * 检测当前应用是否有某权限
     */
    @JvmStatic
    fun checkPermission(context: Context, unCheckedPermission: String) = checkPermission(context, unCheckedPermission, context.packageName)

    /**
     * 获取当前应用的PackageInfo
     */
    @JvmStatic
    fun getPackageInfo(context: Context) = getPackageInfo(context, context.packageName)

    /**
     * 获取PackageInfo
     */
    @JvmStatic
    fun getPackageInfo(context: Context, packageName: String): PackageInfo? {
        try {
            return context.packageManager.getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 判断应用是否已安装
     * 系统应用是否已更新
     */
    @JvmStatic
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return isUserApp(context, packageName) || isUpdatedSystemApp(context, packageName)
    }

    /**
     * 判断是否系统应用
     */
    @JvmStatic
    fun isSystemApp(context: Context, packageName: String): Boolean {
        val packageInfo = getPackageInfo(context, packageName)
        if (packageInfo != null) {
            return packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
        }
        return false
    }

    /**
     * 判断系统应用是否已更新安装
     */
    @JvmStatic
    fun isUpdatedSystemApp(context: Context, packageName: String): Boolean {
        val packageInfo = getPackageInfo(context, packageName)
        if (packageInfo != null) {
            return packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0
        }
        return false
    }

    /**
     * 判断是否第三方应用
     */
    @JvmStatic
    fun isUserApp(context: Context, packageName: String): Boolean {
        val packageInfo = getPackageInfo(context, packageName)
        if (packageInfo != null) {
            return packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM <= 0
        }
        return false
    }

    /**
     * 判断应用是否存在
     */
    @JvmStatic
    fun isAppExist(context: Context, packageName: String) = getPackageInfo(context, packageName) != null

    /**
     * 获取当前应用版本名
     */
    @JvmStatic
    fun getAppVersionName(context: Context) = getPackageInfo(context)?.versionName ?: ""

    /**
     * 获取当前应用版本号
     */
    @JvmStatic
    fun getAppVersionCode(context: Context): Long {
        val pi = getPackageInfo(context)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            pi?.longVersionCode ?: 0
        } else {
            return (pi?.versionCode ?: 0).toLong()
        }
    }

    /**
     * 获取当前应用大小
     */
    @JvmStatic
    fun getAppSize(context: Context): String {
        var size = ""
        try {
            val pi = getPackageInfo(context)!!
            size = FileUtils.formatFileSize(File(pi.applicationInfo.publicSourceDir).length())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 获取当前应用安装时间
     */
    @JvmStatic
    fun getAppTime(context: Context): String {
        var time = ""
        try {
            val pi = getPackageInfo(context)!!
            time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(File(pi.applicationInfo.sourceDir).lastModified()))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return time
    }

    /**
     * 获取App路径
     */
    @JvmStatic
    fun getAppPath(context: Context): String {
        var path = ""
        try {
            val pi = getPackageInfo(context)!!
            path = pi.applicationInfo.sourceDir
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return path
    }

    /**
     * 获取apk文件的签名
     * 需要权限 {@link android.Manifest.permission#READ_EXTERNAL_STORAGE}
     *
     * @param apkPath apk文件路径
     * @return 证书MD5值:32位16进制 如:D17A70403EB7CD52181004C847180287
     */
    @JvmStatic
    fun getAPKFileSignature(apkPath: String): String {
        var signatureMD5 = ""
        try {
            val packageParser = "android.content.pm.PackageParser"
            val pkgParserCls = Class.forName(packageParser)
            var typeArgs = arrayOfNulls<Class<*>>(1)
            typeArgs[0] = String::class.java
            val pkgParserCt = pkgParserCls.getConstructor(*typeArgs)
            var valueArgs = arrayOfNulls<Any>(1)
            valueArgs[0] = apkPath
            val pkgParser = pkgParserCt.newInstance(*valueArgs)

            val metrics = DisplayMetrics()
            metrics.setToDefaults()

            typeArgs = arrayOfNulls(4)
            typeArgs[0] = File::class.java
            typeArgs[1] = String::class.java
            typeArgs[2] = DisplayMetrics::class.java
            typeArgs[3] = Integer.TYPE
            val parsePackageMtd = pkgParserCls.getDeclaredMethod("parsePackage", *typeArgs)
            valueArgs = arrayOfNulls(4)
            valueArgs[0] = File(apkPath)
            valueArgs[1] = apkPath
            valueArgs[2] = metrics
            valueArgs[3] = PackageManager.GET_SIGNATURES
            val pkgParserPkg = parsePackageMtd.invoke(pkgParser, *valueArgs)

            typeArgs = arrayOfNulls(2)
            typeArgs[0] = pkgParserPkg.javaClass
            typeArgs[1] = Integer.TYPE
            val collectCertificatesMtd = pkgParserCls.getDeclaredMethod("collectCertificates", *typeArgs)
            valueArgs = arrayOfNulls(2)
            valueArgs[0] = pkgParserPkg
            valueArgs[1] = PackageManager.GET_SIGNATURES
            collectCertificatesMtd.invoke(pkgParser, *valueArgs)

            val packageInfoFld = pkgParserPkg.javaClass.getDeclaredField("mSignatures")
            val signatures = packageInfoFld[pkgParserPkg] as Array<Signature>
            if (signatures.isNotEmpty()) {
                signatureMD5 = getSignatureMD5(signatures[0])
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return signatureMD5
    }

    /**
     * 获取系统的签名
     */
    @JvmStatic
    fun getFirmwareSignature(context: Context): String {
        var signatureMD5 = ""
        try {
            val pi = context.packageManager.getPackageInfo("android", PackageManager.GET_SIGNATURES)
            // signatures[0].toCharsString()
            signatureMD5 = getSignatureMD5(pi.signatures[0])
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return signatureMD5
    }

    /**
     * 获取某安装应用的签名
     */
    @JvmStatic
    fun getPackageSignature(context: Context, packageName: String): String {
        var signatureMD5 = ""
        try {
            val pi = getPackageInfo(context, packageName)!!
            signatureMD5 = getSignatureMD5(pi.signatures[0])
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return signatureMD5
    }

    /**
     * 获取当前应用的签名
     *
     * @param context
     * @return 证书MD5值:32位16进制 如:D17A70403EB7CD52181004C847180287
     */
    @JvmStatic
    fun getPackageSignature(context: Context) = getPackageSignature(context, context.packageName)

    /**
     * MD5值
     */
    @JvmStatic
    fun getSignatureMD5(signature: Signature): String {
        val md5 = MessageDigest.getInstance("MD5")
        val digest = md5.digest(signature.toByteArray())
        return ConvertUtils.bytes2HexString(*digest)
    }

    @JvmStatic
    fun isAppDebug(context: Context): Boolean {
        try {
            val pm = context.packageManager
            val info = pm.getApplicationInfo(context.packageName, 0)
            return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 当前应用分配的最大内存
     *
     * @return
     */
    @JvmStatic
    fun getMaxMemory() = Runtime.getRuntime().maxMemory()

    /**
     * 当前应用分配的总内存
     *
     * @return
     */
    @JvmStatic
    fun getTotalMemory() = Runtime.getRuntime().totalMemory()

    /**
     * 当前应用分配的剩余内存
     *
     * @return
     */
    @JvmStatic
    fun getFreeMemory() = Runtime.getRuntime().freeMemory()

}