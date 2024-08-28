package com.excellence.basetoolslibrary.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.excellence.basetoolslibrary.utils.FileUtils.isFileExists
import java.io.File
import java.util.Locale


/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/9/10
 *     desc   : 常见的Intent相关
 * </pre>
 */
object IntentUtils {

    /**
     * 获取App启动入口
     */
    @JvmStatic
    fun getLaunchAppIntent(context: Context, pkgName: String): Intent? {
        val launcherActivity: String = ActivityUtils.getAppLauncherActivity(context, pkgName)
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.setClassName(pkgName, launcherActivity)
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    /**
     * 判断Intent是否存在
     *
     * @param context
     * @param intent
     * @return
     */
    @JvmStatic
    fun isIntentAvailable(context: Context?, intent: Intent?): Boolean {
        return if (context == null || intent == null) {
            false
        } else context.packageManager.queryIntentActivities(intent, 0).size > 0
    }

    /**
     * Intent跳转
     *
     * @param context
     * @param intent
     * @return
     */
    @JvmStatic
    fun startIntent(context: Context, intent: Intent?): Boolean {
        try {
            if (isIntentAvailable(context, intent)) {
                context.startActivity(intent)
                return true
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 跳转Settings
     *
     * @return
     */
    @JvmStatic
    val settingIntent: Intent
        get() = Intent(Settings.ACTION_SETTINGS)

    /**
     * 隐式开启WiFi
     *
     * @return
     */
    @JvmStatic
    val wiFiIntent: Intent
        get() = Intent(Settings.ACTION_WIFI_SETTINGS)

    /**
     * 直接开启WiFi，防止被拦截
     *
     * @return
     */
    @JvmStatic
    val directWiFiIntent: Intent
        get() {
            val intent = Intent()
            val componentName =
                ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings")
            intent.component = componentName
            return intent
        }

    /**
     * 跳转到移动网络设置
     *
     * @return
     */
    @JvmStatic
    val roamingIntent: Intent
        get() = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)

    /**
     * 开启权限设置
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getPermissionIntent(context: Context): Intent {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        return intent
    }

    /**
     * 开启定位设置
     *
     * @return
     */
    @JvmStatic
    val locationIntent: Intent
        get() = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

    /**
     * 开启蓝牙设置
     *
     * @return
     */
    @JvmStatic
    val bluetoothIntent: Intent
        get() = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)

    /**
     * 开启语言设置
     *
     * @return
     */
    @JvmStatic
    val localeIntent: Intent
        get() = Intent(Settings.ACTION_LOCALE_SETTINGS)

    /**
     * 跳转应用程序列表界面
     *
     * @return
     */
    @JvmStatic
    val appIntent: Intent
        get() = Intent(Settings.ACTION_APPLICATION_SETTINGS)

    /**
     * 跳转到应用程序界面（所有的）
     *
     * @return
     */
    @JvmStatic
    val allAppIntent: Intent
        get() = Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS)

    /**
     * 跳转到应用程序界面（已安装的）
     *
     * @return
     */
    @JvmStatic
    val installedAppIntent: Intent
        get() = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)

    /**
     * 开启存储设置
     *
     * @return
     */
    @JvmStatic
    val storageIntent: Intent
        get() = Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS)

    /**
     * 开启辅助设置
     *
     * @return
     */
    @JvmStatic
    val accessibilityIntent: Intent
        get() = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)

    /**
     * 跳转到搜索设置
     *
     * @return
     */
    @JvmStatic
    val searchIntent: Intent
        get() = Intent(Settings.ACTION_SEARCH_SETTINGS)

    /**
     * 跳转输入法设置
     *
     * @return
     */
    @JvmStatic
    val inputMethodIntent: Intent
        get() = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)

    /**
     * 安装应用
     *
     * @param context
     * @param file
     * @return
     */
    @JvmStatic
    fun getInstallIntent(context: Context, file: File?): Intent? {
        if (!isFileExists(file)) {
            return null
        }
        val intent = Intent(Intent.ACTION_VIEW)
        val data: Uri
        val type = "application/vnd.android.package-archive"
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file)
        } else {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val authority = context.packageName + ".provider"
            data = FileProvider.getUriForFile(context, authority, file!!)
        }
        intent.setDataAndType(data, type)
        return intent
    }

    /**
     * 卸载应用
     *
     * @param packageName
     * @return
     */
    @JvmStatic
    fun getUninstallIntent(packageName: String): Intent {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$packageName")
        return intent
    }

    /**
     * 分享文本
     *
     * @param content
     * @return
     */
    @JvmStatic
    fun getShareTextIntent(content: String?): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, content)
        return intent
    }

    /**
     * 分享图片
     *
     * @param content
     * @return
     */
    @JvmStatic
    fun getShareImageIntent(content: String?, uri: Uri?): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, content)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.type = "image/*"
        return intent
    }

    /**
     * 分享图片
     *
     * @param content
     * @param uris
     * @return
     */
    @JvmStatic
    fun getShareImageIntent(content: String?, uris: ArrayList<Uri?>?): Intent {
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.putExtra(Intent.EXTRA_TEXT, content)
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
        intent.type = "image/*"
        return intent
    }

    /**
     * 跳转拨号界面
     *
     * @param phoneNumber
     * @return
     */
    @JvmStatic
    fun getDialIntent(phoneNumber: String): Intent {
        return Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber
     * @return
     */
    @JvmStatic
    fun getCallIntent(phoneNumber: String): Intent {
        return Intent("android.intent.action.CALL", Uri.parse("tel:$phoneNumber"))
    }

    /**
     * 跳转短信界面
     *
     * @param content
     * @return
     */
    @JvmStatic
    fun getSmsIntent(content: String?): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.putExtra("sms_body", content)
        intent.type = "vnd.android-dir/mms-sms"
        return intent
    }

    /**
     * 发送短信
     *
     * @param phoneNumber
     * @param content
     * @return
     */
    @JvmStatic
    fun getSendSmsIntent(phoneNumber: String, content: String?): Intent {
        val uri = Uri.parse("smsto:$phoneNumber")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", content)
        return intent
    }

    /**
     * 发送邮件
     *
     * @param email
     * @param content
     * @return
     */
    @JvmStatic
    fun getEmailIntent(email: String?, content: String?): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, email)
        intent.putExtra(Intent.EXTRA_TEXT, content)
        intent.type = "text/plain"
        return intent
    }

    /**
     * 打开相机
     *
     * @param uri
     * @return
     */
    @JvmStatic
    fun getCaptureIntent(uri: Uri?): Intent {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return intent
    }

    /**
     * 播放视频：本地 / 网络
     *
     * @param url
     * @return
     */
    @JvmStatic
    fun getVideoIntent(url: String?): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri: Uri = if (isFileExists(url)) {
            Uri.fromFile(File(url))
        } else {
            Uri.parse(url)
        }
        intent.setDataAndType(uri, "video/*")
        return intent
    }

    /**
     * 播放网络视频
     *
     * @param url
     * @return
     */
    @JvmStatic
    fun getNetVideoIntent(url: String?): Intent {
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), mimeType)
        return intent
    }

    /**
     * 播放本地音乐
     *
     * @param filePath
     * @return
     */
    @JvmStatic
    fun getAudioIntent(filePath: String?): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val file = File(filePath)
        val uri = Uri.fromFile(file)
        intent.setDataAndType(uri, "audio/*")
        return intent
    }

    /**
     * 文件选择器
     */
    @JvmStatic
    fun fileChooseIntent(type: String, title: CharSequence): Intent {
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        // */*
        intent.type = type
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        // 使用 startActivityForResult 接收
        return Intent.createChooser(intent, title)
    }

    /**
     * 打开文件
     */
    @JvmStatic
    fun fileOpenIntent(path: String): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse("file://$path")

        // 这里是所有能打开的应用，如果要指定文件管理器打开，需要指定包名或者设置type类型
        // {后缀名，MIME类型}
        val mimeMapTable = arrayOf(
            arrayOf(".3gp", "video/3gpp"),
            arrayOf(".apk", "application/vnd.android.package-archive"),
            arrayOf(".asf", "video/x-ms-asf"),
            arrayOf(".avi", "video/x-msvideo"),
            arrayOf(".bin", "application/octet-stream"),
            arrayOf(".bmp", "image/bmp"),
            arrayOf(".c", "text/plain"),
            arrayOf(".class", "application/octet-stream"),
            arrayOf(".conf", "text/plain"),
            arrayOf(".cpp", "text/plain"),
            arrayOf(".doc", "application/msword"),
            arrayOf(
                ".docx",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            ),
            arrayOf(".xls", "application/vnd.ms-excel"),
            arrayOf(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
            arrayOf(".exe", "application/octet-stream"),
            arrayOf(".gif", "image/gif"),
            arrayOf(".gtar", "application/x-gtar"),
            arrayOf(".gz", "application/x-gzip"),
            arrayOf(".h", "text/plain"),
            arrayOf(".htm", "text/html"),
            arrayOf(".html", "text/html"),
            arrayOf(".jar", "application/java-archive"),
            arrayOf(".java", "text/plain"),
            arrayOf(".jpeg", "image/jpeg"),
            arrayOf(".jpg", "image/jpeg"),
            arrayOf(".js", "application/x-javascript"),
            arrayOf(".log", "text/plain"),
            arrayOf(".m3u", "audio/x-mpegurl"),
            arrayOf(".m4a", "audio/mp4a-latm"),
            arrayOf(".m4b", "audio/mp4a-latm"),
            arrayOf(".m4p", "audio/mp4a-latm"),
            arrayOf(".m4u", "video/vnd.mpegurl"),
            arrayOf(".m4v", "video/x-m4v"),
            arrayOf(".mov", "video/quicktime"),
            arrayOf(".mp2", "audio/x-mpeg"),
            arrayOf(".mp3", "audio/x-mpeg"),
            arrayOf(".mp4", "video/mp4"),
            arrayOf(".mpc", "application/vnd.mpohun.certificate"),
            arrayOf(".mpe", "video/mpeg"),
            arrayOf(".mpeg", "video/mpeg"),
            arrayOf(".mpg", "video/mpeg"),
            arrayOf(".mpg4", "video/mp4"),
            arrayOf(".mpga", "audio/mpeg"),
            arrayOf(".msg", "application/vnd.ms-outlook"),
            arrayOf(".ogg", "audio/ogg"),
            arrayOf(".pdf", "application/pdf"),
            arrayOf(".png", "image/png"),
            arrayOf(".pps", "application/vnd.ms-powerpoint"),
            arrayOf(".ppt", "application/vnd.ms-powerpoint"),
            arrayOf(
                ".pptx",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            ),
            arrayOf(".prop", "text/plain"),
            arrayOf(".rc", "text/plain"),
            arrayOf(".rmvb", "audio/x-pn-realaudio"),
            arrayOf(".rtf", "application/rtf"),
            arrayOf(".sh", "text/plain"),
            arrayOf(".tar", "application/x-tar"),
            arrayOf(".tgz", "application/x-compressed"),
            arrayOf(".txt", "text/plain"),
            arrayOf(".wav", "audio/x-wav"),
            arrayOf(".wma", "audio/x-ms-wma"),
            arrayOf(".wmv", "audio/x-ms-wmv"),
            arrayOf(".wps", "application/vnd.ms-works"),
            arrayOf(".xml", "text/plain"),
            arrayOf(".z", "application/x-compress"),
            arrayOf(".zip", "application/x-zip-compressed"),
            arrayOf("", "*/*")
        )

        var type = "*/*"
        val dotIndex = path.lastIndexOf(".")
        if (dotIndex > 0) {
            val suffix = path.substring(dotIndex).toLowerCase(Locale.getDefault())
            for (i in mimeMapTable.indices) {
                if (suffix == mimeMapTable[i][0]) {
                    type = mimeMapTable[i][1]
                }
            }
        }

        intent.setDataAndType(uri, type)

        // 检查设备上是否有能够处理此Intent的应用程序
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // 启动Intent
//            startActivity(intent)
//        } else {
//            // 如果没有应用程序能够处理此Intent，显示提示消息
//            Toast.makeText(this, "No app to handle this type of file", Toast.LENGTH_SHORT).show()
//        }

        return intent
    }
}