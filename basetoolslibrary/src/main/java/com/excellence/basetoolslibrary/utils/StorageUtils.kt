package com.excellence.basetoolslibrary.utils

import android.content.Context
import android.os.storage.StorageManager
import java.util.*

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/8/30
 *     desc   : SD、TF等存储相关工具
 * </pre>
 */
object StorageUtils {

    /**
     * 获取所有存储路径，包括内置、外置存储设备：sdcard [android.os.Environment.getExternalStorageDirectory]、SD、TF
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getStorageList(context: Context): List<String?> {
        var storageList: List<String?> = ArrayList()
        try {
            val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            val getVolumePaths = storageManager.javaClass.getDeclaredMethod("getVolumePaths", *arrayOf())
            getVolumePaths.isAccessible = true
            val paths = getVolumePaths.invoke(storageManager) as Array<String>
            storageList = listOf(*paths)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return storageList
    }

    /**
     * 获取存储卷的相关信息
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getStorageVolumeList(context: Context): List<StorageVolume> {
        val storageVolumeList: MutableList<StorageVolume> = ArrayList()
        try {
            val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            val getVolumeList = storageManager.javaClass.getMethod("getVolumeList")
            val storageVolumes = getVolumeList.invoke(storageManager)
            if (storageVolumes != null) {
                val storageVolumeClazz = Class.forName("android.os.storage.StorageVolume")
                val getPath = storageVolumeClazz.getMethod("getPath")
                val getUserLabel = storageVolumeClazz.getMethod("getUserLabel")
                val isPrimaryMethod = storageVolumeClazz.getMethod("isPrimary")
                val isRemovableMethod = storageVolumeClazz.getMethod("isRemovable")
                val isEmulatedMethod = storageVolumeClazz.getMethod("isEmulated")
                val size = java.lang.reflect.Array.getLength(storageVolumes)
                for (i in 0 until size) {
                    val storageVolume = java.lang.reflect.Array.get(storageVolumes, i)
                    val path = getPath.invoke(storageVolume) as String
                    val userLabel = getUserLabel.invoke(storageVolume) as String
                    val isPrimary = isPrimaryMethod.invoke(storageVolume) as Boolean
                    val isRemovable = isRemovableMethod.invoke(storageVolume) as Boolean
                    val isEmulated = isEmulatedMethod.invoke(storageVolume) as Boolean
                    storageVolumeList.add(StorageVolume(path, userLabel, isPrimary, isRemovable, isEmulated))
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return storageVolumeList
    }

    class StorageVolume(
            var path: String,

            /**
             * Returns a user-visible description of the volume
             */
            var userLabel: String,

            /**
             * Returns true if the volume is the primary shared/external storage, which is the volume
             * backed by [android.os.Environment.getExternalStorageDirectory]
             */
            var isPrimary: Boolean,

            /**
             * Returns true if the volume is removable
             */
            var isRemovable: Boolean,

            /**
             * Returns true if the volume is emulated
             */
            var isEmulated: Boolean)
}