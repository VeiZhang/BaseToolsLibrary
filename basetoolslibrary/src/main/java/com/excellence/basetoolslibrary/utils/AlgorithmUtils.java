package com.excellence.basetoolslibrary.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.jvm.JvmStatic;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/5/18
 *     desc   : 算法工具
 * </pre> 
 */
public class AlgorithmUtils {

    /**
     * 根据字符串生成MD5
     *
     * @param params 字符串
     * @return MD5
     */
    @JvmStatic
    public static String getMD5(String... params) {
        String md5 = "";
        if (params == null || params.length == 0) {
            return md5;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String param : params) {
            stringBuilder.append(param);
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(stringBuilder.toString().getBytes());
            md5 = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    /**
     * 根据流生成MD5
     *
     * @param is 流
     * @return MD5
     */
    @JvmStatic
    public static String getMD5(InputStream is) {
        String md5 = "";
        try {
            if (is == null) {
                return md5;
            }

            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            md5 = String.format("%32s", bigInt.toString(16).replace(' ', '0'));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    /**
     * 根据文件生成MD5
     *
     * @param file 文件
     * @return MD5
     */
    @JvmStatic
    public static String getMD5(File file) {
        String md5 = "";
        try {
            InputStream is = new FileInputStream(file);
            md5 = getMD5(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    /**
     * 检测文件的MD5
     *
     * @param md5 MD5
     * @param file 文件
     * @return {@code true}:是<br>{@code false}:否
     */
    @JvmStatic
    public static boolean checkMD5(String md5, File file) {
        if (TextUtils.isEmpty(md5) || file == null) {
            return false;
        }
        String fileMD5 = getMD5(file);
        return fileMD5 != null && fileMD5.equalsIgnoreCase(md5);
    }

    /**
     * 检测MD5
     *
     * @param md5
     * @param is
     * @return
     */
    @JvmStatic
    public static boolean checkMD5(String md5, InputStream is) {
        if (TextUtils.isEmpty(md5) || is == null) {
            return false;
        }
        String isMD5 = getMD5(is);
        return isMD5 != null && isMD5.equalsIgnoreCase(md5);
    }
}
