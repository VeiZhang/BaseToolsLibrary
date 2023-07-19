package com.excellence.basetoolslibrary.utils

import android.content.Context
import android.os.storage.StorageManager

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
     * Android 10 监听USB拔插，/packages/SystemUI/src/com/android/systemui/usb/StorageNotification.java
     * 需要framework.jar隐藏的API
     * SD广播 + USB广播 + 监听的方式可以不需要后台服务
     */

//    <receiver
//        android:name=".UsbReceiver"
//        android:enabled="true"
//        android:exported="true">
//        <intent-filter>
//        <action android:name="android.hardware.usb.action.USB_DEVICE_ATTACHED" />
//        <action android:name="android.hardware.usb.action.USB_DEVICE_DETACHED" />
//
//        </intent-filter>
//        <intent-filter>
//        <action android:name="android.intent.action.MEDIA_MOUNTED" />
//        <action android:name="android.intent.action.MEDIA_CHECKING" />
//        <action android:name="android.intent.action.MEDIA_EJECT" />
//        <action android:name="android.intent.action.MEDIA_UNMOUNTED" />
//        <action android:name="android.intent.action.MEDIA_REMOVED" />
//
//        <!--必须添加，否则无法监听到SD卡插拔广播-->
//        <data android:scheme="file" />
//        </intent-filter>
//    </receiver>

//    private void registerStorageListener() {
//        mStorageManager.registerListener(new StorageEventListener() {
//            @Override
//            public void onUsbMassStorageConnectionChanged(boolean connected) {
//                Log.i(TAG, "onUsbMassStorageConnectionChanged: ");
//            }
//
//            @Override
//            public void onStorageStateChanged(String path, String oldState, String newState) {
//                Log.i(TAG, "onStorageStateChanged: ");
//            }
//
//            @Override
//            public void onVolumeStateChanged(VolumeInfo vol, int oldState, int newState) {
//                Log.i(TAG, "onVolumeStateChanged: ");
//                onVolumeStateChangedInternal(vol);
//            }
//
//            @Override
//            public void onVolumeRecordChanged(VolumeRecord rec) {
//                Log.i(TAG, "onVolumeRecordChanged: ");
//                // Avoid kicking notifications when getting early metadata before
//                // mounted. If already mounted, we're being kicked because of a
//                // nickname or init'ed change.
//                final VolumeInfo vol = mStorageManager.findVolumeByUuid(rec.getFsUuid());
//                if (vol != null && vol.isMountedReadable()) {
//                    onVolumeStateChangedInternal(vol);
//                }
//            }
//
//            @Override
//            public void onVolumeForgotten(String fsUuid) {
//                Log.i(TAG, "onVolumeForgotten: ");
//            }
//
//            @Override
//            public void onDiskScanned(DiskInfo disk, int volumeCount) {
//                Log.i(TAG, "onDiskScanned: ");
//            }
//
//            @Override
//            public void onDiskDestroyed(DiskInfo disk) {
//                Log.i(TAG, "onDiskDestroyed: ");
//            }
//        });
//    }
//
//    private void onVolumeStateChangedInternal(VolumeInfo vol) {
//        switch (vol.getType()) {
//            case VolumeInfo.TYPE_PRIVATE:
//            onPrivateVolumeStateChangedInternal(vol);
//            break;
//            case VolumeInfo.TYPE_PUBLIC:
//            onPublicVolumeStateChangedInternal(vol);
//            break;
//        }
//    }
//
//    private void onPrivateVolumeStateChangedInternal(VolumeInfo vol) {
//        Log.d(TAG, "Notifying about private volume: " + vol.toString());
//    }
//
//    private void onPublicVolumeStateChangedInternal(VolumeInfo vol) {
//        Log.d(TAG, "Notifying about public volume: " + vol.toString());
//        if (vol.getDisk() != null) {
//            Log.i(TAG, String.format("disk type check - USB : %s, SD : %s",
//                vol.getDisk().isUsb(), vol.getDisk().isSd()));
//            if (vol.getDisk().isSd()) {
//                // 可以过滤出SD，那与SD的广播就不重复了
//                return;
//            }
//        }
//
//        switch (vol.getState()) {
//            case VolumeInfo.STATE_UNMOUNTED:
//            Log.i(TAG, String.format("unmounted - path : %s, internalPath : %s",
//                vol.path, vol.internalPath));
//            if (!TextUtils.isEmpty(vol.path)) {
//
//            }
//            break;
//            case VolumeInfo.STATE_CHECKING:
//            break;
//            case VolumeInfo.STATE_MOUNTED:
//            case VolumeInfo.STATE_MOUNTED_READ_ONLY:
//            Log.i(TAG, String.format("mounted - path : %s, internalPath : %s",
//                vol.path, vol.internalPath));
//            if (!TextUtils.isEmpty(vol.path)) {
//
//            }
//            break;
//            case VolumeInfo.STATE_FORMATTING:
//            break;
//            case VolumeInfo.STATE_EJECTING:
//            break;
//            case VolumeInfo.STATE_UNMOUNTABLE:
//            break;
//            case VolumeInfo.STATE_REMOVED:
//            break;
//            case VolumeInfo.STATE_BAD_REMOVAL:
//            break;
//            default:
//            break;
//        }
//    }

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