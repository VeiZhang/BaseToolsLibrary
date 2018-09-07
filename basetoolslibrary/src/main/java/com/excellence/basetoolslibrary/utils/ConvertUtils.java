package com.excellence.basetoolslibrary.utils;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/24
 *     desc   : 转换相关工具类
 * </pre>
 */

public class ConvertUtils
{

	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * bytes转换16进制
	 * byte是基本数据类型
	 * Byte是byte的包装类
	 *
	 * @param rawByteArray bytes
	 * @return 16进制字符串
	 */
	public static String bytes2HexString(@NonNull byte... rawByteArray)
	{
		char[] chars = new char[rawByteArray.length * 2];
		for (int i = 0; i < rawByteArray.length; i++)
		{
			byte b = rawByteArray[i];
			chars[i * 2] = HEX_CHAR[(b >>> 4 & 0x0F)];
			chars[i * 2 + 1] = HEX_CHAR[(b & 0x0F)];
		}
		return new String(chars);
	}

	/**
	 * inputStream转outPutStream
	 *
	 * @param is inputStream输入流
	 * @return outputStream输出流
	 */
	public static ByteArrayOutputStream inputStream2OutputStream(@NonNull InputStream is)
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buffer = new byte[(int) FileUtils.KB];
			int offset;
			while ((offset = is.read(buffer)) != -1)
			{
				os.write(buffer, 0, offset);
			}
			return os;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			CloseUtils.closeIO(is);
		}
	}

	/**
	 * inputStream转bytes
	 *
	 * @param is 输入流
	 * @return 字节数组
	 */
	public static byte[] inputStream2Bytes(@NonNull InputStream is)
	{
		ByteArrayOutputStream os = inputStream2OutputStream(is);
		return os == null ? null : os.toByteArray();
	}

	/**
	 * inputStream转字符串
	 *
	 * @param is 输入流
	 * @return 字符串
	 */
	public static String inputStream2String(@NonNull InputStream is)
	{
		byte[] bytes = inputStream2Bytes(is);
		return bytes == null ? null : new String(bytes);
	}

	/**
	 * 信息流
	 *
	 * @param is 输入流
	 * @return StringBuilder
	 * @throws IOException
	 */
	public static StringBuilder inputStream2StringBuilder(@NonNull InputStream is) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder result = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			result.append(line);
		}
		reader.close();
		return result;
	}

}
