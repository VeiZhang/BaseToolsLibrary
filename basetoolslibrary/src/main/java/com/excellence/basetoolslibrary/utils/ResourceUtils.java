package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty;
import static com.excellence.basetoolslibrary.utils.FileIOUtils.copyFile;
import static com.excellence.basetoolslibrary.utils.FileIOUtils.readStream2Bytes;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/20
 *     desc   : 资源相关工具类
 * </pre>
 */

public class ResourceUtils
{
	/**
	 * 解析资源的全名
	 * 例如：R.string.app_name
	 * 结果：com.excellence.tooldemo:string/app_name
	 * 
	 * @see #getPackageName
	 * @see #getTypeName
	 * @see #getEntryName
	 *
	 * @param context 上下文
	 * @param resId 资源Id
	 * @return 结果格式：package:type/entry
	 */
	public static String getName(@NonNull Context context, @AnyRes int resId)
	{
		return context.getResources().getResourceName(resId);
	}

	/**
	 * 解析资源名
	 * 
	 * @see #getName
	 *
	 * @param context 上下文
	 * @param resId 资源Id
	 * @return 资源名
	 */
	public static String getEntryName(@NonNull Context context, @AnyRes int resId)
	{
		return context.getResources().getResourceEntryName(resId);
	}

	/**
	 * 解析资源类型名
	 * 
	 * @see #getName
	 *
	 * @param context 上下文
	 * @param resId 资源Id
	 * @return 资源类型名
	 */
	public static String getTypeName(@NonNull Context context, @AnyRes int resId)
	{
		return context.getResources().getResourceTypeName(resId);
	}

	/**
	 * 解析资源的包名
	 * @see #getName
	 *
	 * @param context 上下文
	 * @param resId 资源Id
	 * @return 资源所在的包名
	 */
	public static String getPackageName(@NonNull Context context, @AnyRes int resId)
	{
		return context.getResources().getResourcePackageName(resId);
	}

	/**
	 * 获取资源Id
	 * @see #getName
	 *
	 * @param context 上下文
	 * @param name 资源名
	 * @param type 资源类型名
	 * @param packageName 包名
	 * @return 0表示没有该资源
	 */
	public static int getIdentifier(Context context, String name, String type, String packageName)
	{
		return context.getResources().getIdentifier(name, type, packageName);
	}

	/**
	 * 拷贝assets文件到指定目录
	 *
	 * @param context
	 * @param srcFilePath 相对路径，相对assets目录，如，"docs/home.html"
	 * @param destFilePath 目标目录路径
	 * @return
	 */
	public static boolean copyFileFromAssets(Context context, String srcFilePath, File destFilePath)
	{
		try
		{
			if (isEmpty(context) || isEmpty(srcFilePath))
			{
				return false;
			}
			String[] assets = context.getAssets().list(srcFilePath);
			boolean ret = true;
			if (assets.length > 0)
			{
				for (String asset : assets)
				{
					InputStream is = context.getAssets().open(asset);
					OutputStream os = new FileOutputStream(new File(destFilePath, asset));
					ret &= copyFile(is, os);
				}
			}
			return ret;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 拷贝raw资源到指定目录文件
	 *
	 * @param context
	 * @param resId 资源id
	 * @param destFilePath 目标文件路径
	 * @return
	 */
	public static boolean copyFileFromRaw(Context context, int resId, File destFilePath)
	{
		try
		{
			if (isEmpty(context))
			{
				return false;
			}
			InputStream is = context.getResources().openRawResource(resId);
			OutputStream os = new FileOutputStream(destFilePath);
			return copyFile(is, os);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 读取asset文件转字符串
	 *
	 * @param context
	 * @param fileName
	 * @param charset
	 * @return
	 */
	public static String readAsset(Context context, String fileName, String charset)
	{
		try
		{
			InputStream is = context.getAssets().open(fileName);
			byte[] bytes = readStream2Bytes(is);
			if (bytes != null)
			{
				if (isEmpty(charset))
				{
					return new String(bytes);
				}
				else
				{
					return new String(bytes, charset);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
