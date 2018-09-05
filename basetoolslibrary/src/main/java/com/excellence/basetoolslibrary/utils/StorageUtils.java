package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

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
}
