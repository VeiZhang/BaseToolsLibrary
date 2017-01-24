package com.excellence.basetoolslibrary.utils;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by ZhangWei on 2017/1/23.
 */

/**
 * 文件相关
 */
public class FileUtils
{
	/**
	 * 格式化文件大小
	 * 
	 * @param filesize
	 * @return
	 */
	public static String formatFileSize(long filesize)
	{
		String strUnit = "Bytes";
		String strAfterComma = "";
		int intDivisor = 1;
		if (filesize >= 1024 * 1024)
		{
			strUnit = "MB";
			intDivisor = 1024 * 1024;
		}
		else if (filesize >= 1024)
		{
			strUnit = "KB";
			intDivisor = 1024;
		}

		if (intDivisor == 1)
			return filesize + " " + strUnit;

		strAfterComma = "" + 100 * (filesize % intDivisor) / intDivisor;

		if (strAfterComma == "")
			strAfterComma = ".0";

		return filesize / intDivisor + "." + strAfterComma + " " + strUnit;
	}

	/**
	 * 删除目录
	 * 
	 * @param dir
	 * @return
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
	 * 删除目录下的某后缀文件
	 * 
	 * @param dirPath
	 */
	public static void deletePostfixFiles(String dirPath, String postfix)
	{
		File dir = new File(dirPath);
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
	 * 删除文件
	 * 
	 * @param filePath
	 */
	public static void deleteFile(String filePath)
	{
		File file = new File(filePath);
		if (file.exists())
			file.delete();
	}
}
