package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/8/30
 *     desc   : SD、TF等存储相关工具
 * </pre> 
 */
public class StorageUtils
{
	/**
	 * 获取所有存储路径，包括内置、外置存储设备：sdcard {@link Environment#getExternalStorageDirectory()}、SD、TF
	 *
	 * @param context
	 * @return
	 */
	public static List<String> getStorageList(Context context)
	{
		List<String> storageList = new ArrayList<>();
		try
		{
			StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
			Method getVolumePaths = storageManager.getClass().getDeclaredMethod("getVolumePaths", new Class[] {});
			getVolumePaths.setAccessible(true);
			String[] paths = (String[]) getVolumePaths.invoke(storageManager);
			if (paths != null)
			{
				storageList = Arrays.asList(paths);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return storageList;
	}

	/**
	 * 获取存储卷的相关信息
	 *
	 * @param context
	 * @return
	 */
	public static List<StorageVolume> getStorageVolumeList(Context context)
	{
		List<StorageVolume> storageVolumeList = new ArrayList<>();
		try
		{
			StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
			Method getVolumeList = storageManager.getClass().getMethod("getVolumeList");
			Object storageVolumes = getVolumeList.invoke(storageManager);
			if (storageVolumes != null)
			{
				Class storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
				Method getPath = storageVolumeClazz.getMethod("getPath");
				Method getUserLabel = storageVolumeClazz.getMethod("getUserLabel");
				Method isPrimaryMethod = storageVolumeClazz.getMethod("isPrimary");
				Method isRemovableMethod = storageVolumeClazz.getMethod("isRemovable");
				Method isEmulatedMethod = storageVolumeClazz.getMethod("isEmulated");
				int size = Array.getLength(storageVolumes);
				for (int i = 0; i < size; i++)
				{
					Object storageVolume = Array.get(storageVolumes, i);
					String path = (String) getPath.invoke(storageVolume);
					String userLabel = (String) getUserLabel.invoke(storageVolume);
					boolean isPrimary = (boolean) isPrimaryMethod.invoke(storageVolume);
					boolean isRemovable = (boolean) isRemovableMethod.invoke(storageVolume);
					boolean isEmulated = (boolean) isEmulatedMethod.invoke(storageVolume);
					storageVolumeList.add(new StorageVolume(path, userLabel, isPrimary, isRemovable, isEmulated));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return storageVolumeList;
	}

	public static class StorageVolume
	{
		private String mPath;
		private String mUserLabel;
		private boolean isPrimary;
		private boolean isRemovable;
		private boolean isEmulated;

		public StorageVolume(String path, String userLabel, boolean isPrimary, boolean isRemovable, boolean isEmulated)
		{
			mPath = path;
			mUserLabel = userLabel;
			this.isPrimary = isPrimary;
			this.isRemovable = isRemovable;
			this.isEmulated = isEmulated;
		}

		public String getPath()
		{
			return mPath;
		}

		/**
		 * Returns a user-visible description of the volume
		 * 
		 * @return
		 */
		public String getUserLabel()
		{
			return mUserLabel;
		}

		/**
		 * Returns true if the volume is the primary shared/external storage, which is the volume
		 * backed by {@link Environment#getExternalStorageDirectory()}
		 */
		public boolean isPrimary()
		{
			return isPrimary;
		}

		/**
		 * Returns true if the volume is removable
		 * 
		 * @return
		 */
		public boolean isRemovable()
		{
			return isRemovable;
		}

		/**
		 * Returns true if the volume is emulated
		 * 
		 * @return
		 */
		public boolean isEmulated()
		{
			return isEmulated;
		}
	}
}
