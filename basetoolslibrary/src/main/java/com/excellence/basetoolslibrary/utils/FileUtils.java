package com.excellence.basetoolslibrary.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by ZhangWei on 2017/1/23.
 */

/**
 * 文件相关
 */
public class FileUtils
{

	public static final long KB = 1024;
	public static final long MB = 1024 * 1024;
	public static final long GB = 1024 * 1024 * 1024;
	public static final long TB = 1024 * 1024 * 1024 * 1024L;
	public static final String DEFAULT_FORMAT_PATTERN = "#.##";

	/**
	 * 创建文件
	 *
	 * @param file File类型
	 * @return 文件是否创建成功
     */
	public static boolean createNewFile(File file)
	{
		try
		{
			if (file == null)
				return false;

			if (!file.exists())
				return file.createNewFile();
			else
				return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建文件
	 *
	 * @param filePath 文件路径字符串
	 * @return 文件是否创建成功
	 */
	public static boolean createNewFile(String filePath)
	{
		if (StringUtils.isEmpty(filePath))
			return false;
		return createNewFile(new File(filePath));
	}

	/**
	 * 删除文件
	 *
	 * @param file File类型
	 * @return 是否成功删除文件
	 */
	public static boolean deleteFile(File file)
	{
		if (file == null)
			return false;

		if (file.exists())
			return file.delete();

		return true;
	}

	/**
	 * 删除文件
	 *
	 * @param filePath 文件路径字符串
	 * @return 是否成功删除文件
	 */
	public static boolean deleteFile(String filePath)
	{
		if (StringUtils.isEmpty(filePath))
			return false;
		return deleteFile(new File(filePath));
	}

	/**
	 * 创建目录
	 *
	 * @param dir File类型
	 * @return 目录是否创建成功
     */
	public static boolean mkDir(File dir)
	{
		if (dir == null)
			return false;

		if (!dir.exists())
			return dir.mkdirs();

		return true;
	}

	/**
	 * 创建目录
	 *
	 * @param dirPath 目录路径字符串
	 * @return 目录是否创建成功
	 */
	public static boolean mkDir(String dirPath)
	{
		if (StringUtils.isEmpty(dirPath))
			return false;
		return mkDir(new File(dirPath));
	}

	/**
	 * 删除目录
	 *
	 * @param dir File类型
	 * @return 目录是否成功删除
	 */
	public static boolean deleteDir(File dir)
	{
		if (dir.isDirectory())
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++)
			{
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success)
				{
					return false;
				}
			}
		}
		return dir.delete();
	}

	/**
	 * 删除目录
	 *
	 * @param dirPath 目录路径字符串
	 * @return 目录是否成功删除
	 */
	public static boolean deleteDir(String dirPath)
	{
		if (StringUtils.isEmpty(dirPath))
			return false;
		return deleteDir(new File(dirPath));
	}

	/**
	 * 删除目录下的后缀文件
	 *
	 * @param dir File类型
	 * @param postfix 后缀
	 */
	public static void deletePostfixFiles(File dir, String postfix)
	{
		if (dir == null)
			return;

		if (!dir.exists() || TextUtils.isEmpty(postfix))
			return;
		if (dir.isFile() && dir.getName().endsWith(postfix))
			dir.delete();
		else if (dir.isDirectory())
		{
			File[] files = dir.listFiles();
			for (File file : files)
			{
				if (file.isFile() && file.getName().endsWith(postfix))
					file.delete();
			}
		}
	}

	/**
	 * 删除目录下的后缀文件
	 *
	 * @param dirPath 目录路径字符串
	 * @param postfix 后缀
	 */
	public static void deletePostfixFiles(String dirPath, String postfix)
	{
		if (StringUtils.isEmpty(dirPath))
			return;
		deletePostfixFiles(new File(dirPath), postfix);
	}

	/**
	 * 格式化文件大小
	 * <p>自定格式，保留位数</p>
	 *
	 * @param fileSize 文件大小
	 * @param pattern 保留格式
	 * @return 转换后文件大小
	 */
	public static String formatFileSize(long fileSize, String pattern)
	{
		DecimalFormat sizeFormat = new DecimalFormat(pattern);
		String unitStr = "Bytes";
		long unit = 1;
		if (fileSize >= TB)
		{
			unitStr = "TB";
			unit = TB;
		}
		else if (fileSize >= GB)
		{
			unitStr = "GB";
			unit = GB;
		}
		else if (fileSize >= MB)
		{
			unitStr = "MB";
			unit = MB;
		}
		else if (fileSize >= KB)
		{
			unitStr = "KB";
			unit = KB;
		}
		return sizeFormat.format((double) fileSize / unit) + unitStr;
	}

	/**
	 * 格式化文件大小
	 * <p>默认格式，保留两位小数</p>
	 *
	 * @param fileSize 文件大小
	 * @return 转换后文件大小
	 */
	public static String formatFileSize(long fileSize)
	{
		return formatFileSize(fileSize, DEFAULT_FORMAT_PATTERN);
	}

	/**
	 * 修改目录、文件权限
	 *
	 * @param path 目录、文件路径字符串
	 * @param permission 权限字符串，如：777
     */
	public static void chmod(String path, String permission)
	{
		ShellUtils.execProceeBuilderCommand("chmod", permission, path);
	}

	/**
	 * 修改目录、文件权限
	 *
	 * @param file File类型
	 * @param permission 权限字符串，如：777
     */
	public static void chmod(File file, String permission)
	{
		chmod(file.getPath(), permission);
	}

	/**
	 * 修改目录、文件777权限
	 *
	 * @param path 目录、文件路径字符串
     */
	public static void chmod777(String path)
	{
		chmod(path, "777");
	}

	/**
	 * 修改目录、文件777权限
	 *
	 * @param file File类型
     */
	public static void chmod777(File file)
	{
		chmod(file.getPath(), "777");
	}

	/**
	 * 判断文件或目录是否存在
	 *
	 * @param file File类型
	 * @return
     */
	public static boolean isFileExists(File file)
	{
		return file != null && file.exists();
	}

	/**
	 * 判断文件或目录是否存在
	 *
	 * @param path 路径字符串
	 * @return
     */
	public static boolean isFileExists(String path)
	{
		return !StringUtils.isEmpty(path) && isFileExists(new File(path));
	}
}
