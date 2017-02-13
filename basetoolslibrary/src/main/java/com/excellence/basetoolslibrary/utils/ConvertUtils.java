package com.excellence.basetoolslibrary.utils;

/**
 * Created by ZhangWei on 2017/1/24.
 */

/**
 * 进制转换
 */
public class ConvertUtils
{

	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * bytes转换16进制
	 *
	 * @param rawByteArray
	 * @return
	 */
	public static String bytesToHexString(byte[] rawByteArray)
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
}
