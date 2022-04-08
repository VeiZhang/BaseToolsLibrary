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
import java.util.*

/**
 * <pre>
 * author : VeiZhang
 * blog   : http://tiimor.cn
 * time   : 2018/9/10
 * desc   : 常见的Intent相关
 * </pre>
 */
object IntentUtils {

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
            val componentName = ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings")
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
}