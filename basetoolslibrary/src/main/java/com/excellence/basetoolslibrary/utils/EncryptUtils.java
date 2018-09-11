package com.excellence.basetoolslibrary.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.excellence.basetoolslibrary.utils.ConvertUtils.bytes2HexString;
import static com.excellence.basetoolslibrary.utils.ConvertUtils.hexString2Bytes;
import static com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/9/11
 *     desc   : 加密解密算法相关：常见的MD5、DES、3DES、AES、RSA
 *              参考：https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/EncryptUtils.java
 *              散列算法（单向散列，不可逆）
 *                  MD5：https://baike.baidu.com/item/MD5
 *              对称加密（加密解密使用同一密钥，速度快）
 *                  DES：https://baike.baidu.com/item/DES
 *                  3DES：https://baike.baidu.com/item/3DES
 *                  AES：https://baike.baidu.com/item/aes
 *              非对称加密（公钥加密，私钥解密，可以签名，更安全）
 *                  RSA：https://baike.baidu.com/item/RSA
 * </pre> 
 */
public class EncryptUtils
{
	/*************************************************************************/
	/**
	 * 散列加密
	 */
	/*************************************************************************/

	/**
	 * Return the bytes of hash encryption.
	 *
	 * @param data      The data.
	 * @param algorithm The name of hash encryption: MD2, MD5, SHA1, SHA224, SHA256, SHA384, SHA512
	 * @return the bytes of hash encryption
	 */
	public static byte[] hashTemplate(byte[] data, String algorithm)
	{
		try
		{
			if (isEmpty(data))
			{
				return null;
			}
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(data);
			return md.digest();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////
	// MD5 encryption
	///////////////////////////////////////////////////////////////////////////

	/**
	 * MD5加密
	 *
	 * @param data
	 * @return
	 */
	public static byte[] encryptMD5(byte[] data)
	{
		return hashTemplate(data, "MD5");
	}

	/**
	 * MD5加密转16进制
	 *
	 * @param data
	 * @return 16进制
	 */
	public static String encryptMD5HexString(String data)
	{
		if (isEmpty(data))
		{
			return null;
		}
		return bytes2HexString(encryptMD5(data.getBytes()));
	}

	/*************************************************************************/
	/**
	 * 对称加密、解密
	 *
	 * transformation方式如下
	 * AES/CBC/NoPadding (128)
	 * AES/CBC/PKCS5Padding (128)
	 * AES/ECB/NoPadding (128)
	 * AES/ECB/PKCS5Padding (128)
	 * DES/CBC/NoPadding (56)
	 * DES/CBC/PKCS5Padding (56)
	 * DES/ECB/NoPadding (56)
	 * DES/ECB/PKCS5Padding (56)
	
	 (DESede实际上是3-DES）
	 * DESede/CBC/NoPadding (168)
	 * DESede/CBC/PKCS5Padding (168)
	 * DESede/ECB/NoPadding (168)
	 * DESede/ECB/PKCS5Padding (168)
	 */
	/*************************************************************************/

	/**
	 * Return the bytes of symmetric encryption or decryption.
	 *
	 * @param data           The data.
	 * @param key            The key.
	 * @param algorithm      The name of algorithm: DES, 3DES, AES
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
	 * @param isEncrypt      True to encrypt, false otherwise.
	 * @return the bytes of symmetric encryption or decryption
	 */
	public static byte[] symmetricTemplate(byte[] data, byte[] key, String algorithm, String transformation, byte[] iv, boolean isEncrypt)
	{
		try
		{
			if (isEmpty(data) || isEmpty(key))
			{
				return null;
			}
			SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
			Cipher cipher = Cipher.getInstance(transformation);
			if (iv == null || iv.length == 0)
			{
				cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec);
			}
			else
			{
				AlgorithmParameterSpec params = new IvParameterSpec(iv);
				cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, params);
			}
			return cipher.doFinal(data);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////
	// DES encryption
	///////////////////////////////////////////////////////////////////////////

	/**
	 * DES加密
	 *
	 * @param data
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return
	 */
	public static byte[] encryptDES(byte[] data, byte[] key, String transformation, byte[] iv)
	{
		return symmetricTemplate(data, key, "DES", transformation, iv, true);
	}

	/**
	 * DES加密，转16进制
	 *
	 * @param data
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return 16进制字符串
	 */
	public static String encryptDES2HexString(String data, String key, String transformation, String iv)
	{
		if (isEmpty(data) || isEmpty(key))
		{
			return null;
		}
		return bytes2HexString(encryptDES(data.getBytes(), key.getBytes(), transformation, iv == null ? null : iv.getBytes()));
	}

	/**
	 * DES解密
	 *
	 * @param data
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return
	 */
	public static byte[] decryptDES(byte[] data, byte[] key, String transformation, byte[] iv)
	{
		return symmetricTemplate(data, key, "DES", transformation, iv, false);
	}

	/**
	 * DES解密
	 *
	 * @param data 16进制字符串
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return
	 */
	public static String decryptHexStringDES(String data, String key, String transformation, String iv)
	{
		if (isEmpty(data) || isEmpty(key))
		{
			return null;
		}
		return new String(decryptDES(hexString2Bytes(data), key.getBytes(), transformation, iv == null ? null : iv.getBytes()));
	}

	///////////////////////////////////////////////////////////////////////////
	// 3DES encryption
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 3DES加密
	 *
	 * @param data
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return
	 */
	public static byte[] encrypt3DES(byte[] data, byte[] key, String transformation, byte[] iv)
	{
		return symmetricTemplate(data, key, "DESede", transformation, iv, true);
	}

	/**
	 * 3DES加密，转16进制
	 *
	 * @param data
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return 16进制字符串
	 */
	public static String encrypt3DES2HexString(String data, String key, String transformation, String iv)
	{
		if (isEmpty(data) || isEmpty(key))
		{
			return null;
		}
		return bytes2HexString(encrypt3DES(data.getBytes(), key.getBytes(), transformation, iv == null ? null : iv.getBytes()));
	}

	/**
	 * 3DES解密
	 *
	 * @param data
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return
	 */
	public static byte[] decrypt3DES(byte[] data, byte[] key, String transformation, byte[] iv)
	{
		return symmetricTemplate(data, key, "DESede", transformation, iv, false);
	}

	/**
	 * 3DES解密
	 *
	 * @param data 16进制字符串
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return
	 */
	public static String decryptHexString3DES(String data, String key, String transformation, String iv)
	{
		if (isEmpty(data) || isEmpty(key))
		{
			return null;
		}
		return new String(decrypt3DES(hexString2Bytes(data), key.getBytes(), transformation, iv == null ? null : iv.getBytes()));
	}

	///////////////////////////////////////////////////////////////////////////
	// AES
	///////////////////////////////////////////////////////////////////////////

	/**
	 * AES加密
	 *
	 * @param data
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return
	 */
	public static byte[] encryptAES(byte[] data, byte[] key, String transformation, byte[] iv)
	{
		return symmetricTemplate(data, key, "AES", transformation, iv, true);
	}

	/**
	 * AES加密，转16进制
	 *
	 * @param data
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return 16进制字符串
	 */
	public static String encryptAES2HexString(String data, String key, String transformation, String iv)
	{
		if (isEmpty(data) || isEmpty(key))
		{
			return null;
		}
		return bytes2HexString(encryptAES(data.getBytes(), key.getBytes(), transformation, iv == null ? null : iv.getBytes()));
	}

	/**
	 * AES解密
	 *
	 * @param data
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return
	 */
	public static byte[] decryptAES(byte[] data, byte[] key, String transformation, byte[] iv)
	{
		return symmetricTemplate(data, key, "AES", transformation, iv, false);
	}

	/**
	 * AES解密
	 *
	 * @param data 16进制字符串
	 * @param key
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>
	 * @param iv
	 * @return
	 */
	public static String decryptHexStringAES(String data, String key, String transformation, String iv)
	{
		if (isEmpty(data) || isEmpty(key))
		{
			return null;
		}
		return new String(decryptAES(hexString2Bytes(data), key.getBytes(), transformation, iv == null ? null : iv.getBytes()));
	}

	/*************************************************************************/
	/**
	 * 非对称加密、解密
	 */
	/*************************************************************************/

	/**
	 * Return the bytes of RSA encryption or decryption.
	 *
	 * @param data           The data.
	 * @param key            The key.
	 * @param isPublicKey    True to use public key, false to use private key.
	 * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS1Padding</i>.
	 * @param isEncrypt      True to encrypt, false otherwise.
	 * @return the bytes of RSA encryption or decryption
	 */
	public static byte[] rsaTemplate(byte[] data, byte[] key, boolean isPublicKey, String transformation, boolean isEncrypt)
	{
		try
		{
			if (isEmpty(data) || isEmpty(key))
			{
				return null;
			}
			Key rsaKey;
			if (isPublicKey)
			{
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
				rsaKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
			}
			else
			{
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
				rsaKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
			}
			if (rsaKey == null)
			{
				return null;
			}
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, rsaKey);
			int len = data.length;
			int maxLen = isEncrypt ? 117 : 128;
			int count = len / maxLen;
			if (count > 0)
			{
				byte[] ret = new byte[0];
				byte[] buff = new byte[maxLen];
				int index = 0;
				for (int i = 0; i < count; i++)
				{
					System.arraycopy(data, index, buff, 0, maxLen);
					ret = joins(ret, cipher.doFinal(buff));
					index += maxLen;
				}
				if (index != len)
				{
					int restLen = len - index;
					buff = new byte[restLen];
					System.arraycopy(data, index, buff, 0, restLen);
					ret = joins(ret, cipher.doFinal(buff));
				}
				return ret;
			}
			else
			{
				return cipher.doFinal(data);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] joins(final byte[] prefix, final byte[] suffix)
	{
		byte[] ret = new byte[prefix.length + suffix.length];
		System.arraycopy(prefix, 0, ret, 0, prefix.length);
		System.arraycopy(suffix, 0, ret, prefix.length, suffix.length);
		return ret;
	}

	///////////////////////////////////////////////////////////////////////////
	// RSA encryption:公钥加密的用私钥解密，私钥加密的用公钥解密
	///////////////////////////////////////////////////////////////////////////

	/**
	 * RSA加密
	 *
	 * @param data
	 * @param key
	 * @param isPublicKey
	 * @param transformation
	 * @return
	 */
	public static byte[] encryptRSA(byte[] data, byte[] key, boolean isPublicKey, String transformation)
	{
		return rsaTemplate(data, key, isPublicKey, transformation, true);
	}

	/**
	 * RSA加密转16进制
	 *
	 * @param data
	 * @param key
	 * @param isPublicKey
	 * @param transformation
	 * @return 16进制字符串
	 */
	public static String encryptRSA2HexString(String data, String key, boolean isPublicKey, String transformation)
	{
		if (isEmpty(data) || isEmpty(key))
		{
			return null;
		}
		return bytes2HexString(rsaTemplate(data.getBytes(), key.getBytes(), isPublicKey, transformation, true));
	}

	/**
	 * RSA解密
	 *
	 * @param data 16进制字符串
	 * @param key
	 * @param isPublicKey
	 * @param transformation
	 * @return
	 */
	public static byte[] decryptRSA(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation)
	{
		return rsaTemplate(data, key, isPublicKey, transformation, false);
	}

	/**
	 * RSA解密
	 *
	 * @param data 16进制字符串
	 * @param key
	 * @param isPublicKey
	 * @param transformation
	 * @return
	 */
	public static String decryptHexStringRSA(String data, String key, boolean isPublicKey, String transformation)
	{
		if (isEmpty(data) || isEmpty(key))
		{
			return null;
		}
		return new String(decryptRSA(hexString2Bytes(data), key.getBytes(), isPublicKey, transformation));
	}
}
