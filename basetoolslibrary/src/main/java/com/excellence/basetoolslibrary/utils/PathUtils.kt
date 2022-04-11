package com.excellence.basetoolslibrary.utils

import android.content.Context
import android.os.Build
import android.os.Environment

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/11
 *     desc   : 路径相关
 *              转载自：https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/PathUtils.java
 * </pre>
 */
object PathUtils {

    @JvmStatic
    val getRootPath: String
        get() = Environment.getRootDirectory().absolutePath

    /**
     * Return the path of /data.
     *
     * @return the path of /data
     */
    @JvmStatic
    val getDataPath: String
        get() = Environment.getDataDirectory().absolutePath

    /**
     * Return the path of /cache.
     *
     * @return the path of /cache
     */
    @JvmStatic
    val getDownloadCachePath: String
        get() = Environment.getDownloadCacheDirectory().absolutePath

    /**
     * Return the path of /data/data/package.
     *
     * @return the path of /data/data/package
     */
    @JvmStatic
    fun getInternalAppDataPath(context: Context): String {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            context.applicationInfo.dataDir
        } else context.dataDir.absolutePath
    }

    /**
     * Return the path of /data/data/package/code_cache.
     *
     * @return the path of /data/data/package/code_cache
     */
    @JvmStatic
    fun getInternalAppCodeCacheDir(context: Context): String {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context.applicationInfo.dataDir + "/code_cache"
        } else context.codeCacheDir.absolutePath
    }

    /**
     * Return the path of /data/data/package/cache.
     *
     * @return the path of /data/data/package/cache
     */
    @JvmStatic
    fun getInternalAppCachePath(context: Context): String {
        return context.cacheDir.absolutePath
    }

    /**
     * Return the path of /data/data/package/databases.
     *
     * @return the path of /data/data/package/databases
     */
    @JvmStatic
    fun getInternalAppDbsPath(context: Context): String {
        return context.applicationInfo.dataDir + "/databases"
    }

    /**
     * Return the path of /data/data/package/databases/name.
     *
     * @param name The name of database.
     * @return the path of /data/data/package/databases/name
     */
    @JvmStatic
    fun getInternalAppDbPath(context: Context, name: String): String {
        return context.getDatabasePath(name).absolutePath
    }

    /**
     * Return the path of /data/data/package/files.
     *
     * @return the path of /data/data/package/files
     */
    @JvmStatic
    fun getInternalAppFilesPath(context: Context): String {
        return context.filesDir.absolutePath
    }

    /**
     * Return the path of /data/data/package/shared_prefs.
     *
     * @return the path of /data/data/package/shared_prefs
     */
    @JvmStatic
    fun getInternalAppSpPath(context: Context): String {
        return context.applicationInfo.dataDir + "shared_prefs"
    }

    /**
     * Return the path of /data/data/package/no_backup.
     *
     * @return the path of /data/data/package/no_backup
     */
    @JvmStatic
    fun getInternalAppNoBackupFilesPath(context: Context): String {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context.applicationInfo.dataDir + "no_backup"
        } else context.noBackupFilesDir.absolutePath
    }

    /**
     * Return the path of /storage/emulated/0.
     *
     * @return the path of /storage/emulated/0
     */
    @JvmStatic
    fun getExternalStoragePath(): String {
        return if (isExternalStorageDisable()) {
            ""
        } else Environment.getExternalStorageDirectory().absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/Music.
     *
     * @return the path of /storage/emulated/0/Music
     */
    @JvmStatic
    fun getExternalMusicPath(): String {
        return if (isExternalStorageDisable()) {
            ""
        } else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/Podcasts.
     *
     * @return the path of /storage/emulated/0/Podcasts
     */
    @JvmStatic
    fun getExternalPodcastsPath(): String {
        return if (isExternalStorageDisable()) {
            ""
        } else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/Ringtones.
     *
     * @return the path of /storage/emulated/0/Ringtones
     */
    @JvmStatic
    fun getExternalRingtonesPath(): String {
        return if (isExternalStorageDisable()) {
            ""
        } else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/Alarms.
     *
     * @return the path of /storage/emulated/0/Alarms
     */
    @JvmStatic
    fun getExternalAlarmsPath(): String {
        return if (isExternalStorageDisable()) {
            ""
        } else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/Notifications.
     *
     * @return the path of /storage/emulated/0/Notifications
     */
    @JvmStatic
    fun getExternalNotificationsPath(): String {
        return if (isExternalStorageDisable()) {
            ""
        } else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/Pictures.
     *
     * @return the path of /storage/emulated/0/Pictures
     */
    @JvmStatic
    fun getExternalPicturesPath(): String {
        return if (isExternalStorageDisable()) {
            ""
        } else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/Movies.
     *
     * @return the path of /storage/emulated/0/Movies
     */
    @JvmStatic
    fun getExternalMoviesPath(): String {
        return if (isExternalStorageDisable()) {
            ""
        } else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/Download.
     *
     * @return the path of /storage/emulated/0/Download
     */
    @JvmStatic
    fun getExternalDownloadsPath(): String {
        return if (isExternalStorageDisable()) {
            ""
        } else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/DCIM.
     *
     * @return the path of /storage/emulated/0/DCIM
     */
    @JvmStatic
    fun getExternalDcimPath(): String {
        return if (isExternalStorageDisable()) {
            ""
        } else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/Documents.
     *
     * @return the path of /storage/emulated/0/Documents
     */
    @JvmStatic
    fun getExternalDocumentsPath(): String {
        if (isExternalStorageDisable()) {
            return ""
        }
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Environment.getExternalStorageDirectory().absolutePath + "/Documents"
        } else Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package.
     *
     * @return the path of /storage/emulated/0/Android/data/package
     */
    @JvmStatic
    fun getExternalAppDataPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.externalCacheDir!!.parentFile!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/cache.
     *
     * @return the path of /storage/emulated/0/Android/data/package/cache
     */
    @JvmStatic
    fun getExternalAppCachePath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.externalCacheDir!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files
     */
    @JvmStatic
    fun getExternalAppFilesPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.getExternalFilesDir(null)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Music.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Music
     */
    @JvmStatic
    fun getExternalAppMusicPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Podcasts.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Podcasts
     */
    @JvmStatic
    fun getExternalAppPodcastsPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Ringtones.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Ringtones
     */
    @JvmStatic
    fun getExternalAppRingtonesPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Alarms.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Alarms
     */
    @JvmStatic
    fun getExternalAppAlarmsPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.getExternalFilesDir(Environment.DIRECTORY_ALARMS)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Notifications.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Notifications
     */
    @JvmStatic
    fun getExternalAppNotificationsPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Pictures.
     *
     * @return path of /storage/emulated/0/Android/data/package/files/Pictures
     */
    @JvmStatic
    fun getExternalAppPicturesPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Movies.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Movies
     */
    @JvmStatic
    fun getExternalAppMoviesPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Download.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Download
     */
    @JvmStatic
    fun getExternalAppDownloadPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/DCIM.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/DCIM
     */
    @JvmStatic
    fun getExternalAppDcimPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.getExternalFilesDir(Environment.DIRECTORY_DCIM)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Documents.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Documents
     */
    @JvmStatic
    fun getExternalAppDocumentsPath(context: Context): String {
        if (isExternalStorageDisable()) {
            return ""
        }
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            // noinspection ConstantConditions
            context.getExternalFilesDir(null)!!.absolutePath + "/Documents"
        } else context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.absolutePath
        // noinspection ConstantConditions
    }

    /**
     * Return the path of /storage/emulated/0/Android/obb/package.
     *
     * @return the path of /storage/emulated/0/Android/obb/package
     */
    @JvmStatic
    fun getExternalAppObbPath(context: Context): String {
        return if (isExternalStorageDisable()) {
            ""
        } else context.obbDir.absolutePath
    }

    @JvmStatic
    fun isExternalStorageDisable(): Boolean {
        return Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()
    }
}