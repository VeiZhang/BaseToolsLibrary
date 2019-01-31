package com.excellence.basetoolslibrary.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/24
 *     desc   : 转换相关工具类
 *              byte是基本数据类型
 *              Byte是byte的包装类
 * </pre>
 */

public class ConvertUtils {

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * byte转short
     *
     * @param buffer
     * @param offset
     * @return
     */
    public static short bytes2Short(@Size(min = 2) byte[] buffer, int offset) {
        short value;
        value = (short) (((buffer[offset] & 0xFF) << 8) | (buffer[offset + 1] & 0xFF));
        return value;
    }

    /**
     * short转byte
     *
     * @param value
     * @return
     */
    public static byte[] shortToByte(short value) {
        byte[] bytes = new byte[2];
        for (int i = 0; i < bytes.length; i++) {
            int offset = (bytes.length - 1 - i) * 8;
            bytes[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return bytes;
    }

    /**
     * byte转二进制
     *
     * @param b
     * @return
     */
    public static String byte2BinStr(byte b) {
        String result = "";
        byte a = b;
        for (int i = 0; i < 8; i++) {
            byte c = a;
            // 每移一位如同将10进制数除以2并去掉余数。
            a = (byte) (a >> 1);
            a = (byte) (a << 1);
            if (a == c) {
                result = "0" + result;
            } else {
                result = "1" + result;
            }
            a = (byte) (a >> 1);
        }
        return result;
    }

    /**
     * byte数组转二进制
     *
     * @param bytes
     * @return
     */
    public static String byte2BinStr(@NonNull byte... bytes) {
        if (isEmpty(bytes)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(byte2BinStr(bytes[i]));
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 字符串转二进制字符串
     *
     * @param src
     * @return
     */
    public static String str2BinStr(@NonNull String src) {
        if (isEmpty(src)) {
            return null;
        }
        char[] strChar = src.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            String binStr = Integer.toBinaryString(strChar[i]);
            result += String.format("%08d", Integer.valueOf(binStr)) + " ";
        }
        return result;
    }

    /**
     * byte数组转16进制字符串，{@link #bytes2HexString(byte[], int)}
     *
     * @param bytes bytes
     * @return 16进制字符串
     */
    public static String bytes2HexString(@NonNull byte... bytes) {
        if (isEmpty(bytes)) {
            return null;
        }
        char[] chars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            chars[i * 2] = HEX_CHAR[(b >>> 4 & 0x0F)];
            chars[i * 2 + 1] = HEX_CHAR[(b & 0x0F)];
        }
        return new String(chars);
    }

    /**
     * byte数组转16进制字符串，{@link #bytes2HexString(byte...)}
     *
     * @param bytes
     * @param size
     * @return
     */
    public static String bytes2HexString(@NonNull byte[] bytes, int size) {
        if (isEmpty(bytes)) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        String hex;
        for (int i = 0; i < size; i++) {
            hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toUpperCase());
        }
        return result.toString();
    }

    /**
     * 16进制字符串转byte数组
     *
     * @param src
     * @return
     */
    public static byte[] hexString2Bytes(String src) {
        if (isEmpty(src)) {
            return null;
        }
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * 字符串转16进制字符串
     *
     * @param src
     * @return
     */
    public static String string2HexString(String src) {
        if (isEmpty(src)) {
            return null;
        }
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            int ch = (int) src.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }

    /**
     * 16进制字符串转字符串
     *
     * @param src
     * @return
     */
    public static String hexString2String(String src) {
        if (isEmpty(src)) {
            return null;
        }
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            temp = temp + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return temp;
    }

    /**
     * 字符串转byte数组
     *
     * @param src
     * @return
     */
    public static byte[] string2Bytes(String src) {
        String hexStr = string2HexString(src);
        return hexString2Bytes(hexStr);
    }

    /**
     * byte数组转字符串
     *
     * @param bytes
     * @param length
     * @return
     */
    public static String bytes2String(@NonNull byte[] bytes, int length) {
        if (isEmpty(bytes)) {
            return null;
        }
        String hexStr = bytes2HexString(bytes, length);
        return hexString2String(hexStr);
    }

    /**
     * byte数组转有符号int
     *
     * @param bytes
     * @return
     */
    public static long byte2Int(@NonNull byte[] bytes) {
        if (isEmpty(bytes)) {
            return 0;
        }
        return ((bytes[0] & 0xff) << 24) | ((bytes[1] & 0xff) << 16) | ((bytes[2] & 0xff) << 8) | (bytes[3] & 0xff);
    }

    /**
     * int转4位byte数组
     *
     * @param n
     * @return
     */
    public static byte[] int2Byte(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 四字节byte数组转无符号long
     *
     * @param bytes
     * @return
     */
    public static long unintbyte2long(@NonNull byte[] bytes) {
        if (isEmpty(bytes)) {
            return 0;
        }
        int firstByte = 0;
        int secondByte = 0;
        int thirdByte = 0;
        int fourthByte = 0;
        int index = 0;
        firstByte = (0x000000FF & ((int) bytes[index]));
        secondByte = (0x000000FF & ((int) bytes[index + 1]));
        thirdByte = (0x000000FF & ((int) bytes[index + 2]));
        fourthByte = (0x000000FF & ((int) bytes[index + 3]));
        return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
    }

    /**
     * inputStream转outPutStream
     *
     * @param is inputStream输入流
     * @return outputStream输出流
     */
    public static ByteArrayOutputStream inputStream2OutputStream(@NonNull InputStream is) {
        try {
            if (isEmpty(is)) {
                return null;
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[(int) FileUtils.KB];
            int offset;
            while ((offset = is.read(buffer)) != -1) {
                os.write(buffer, 0, offset);
            }
            return os;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(is);
        }
    }

    /**
     * inputStream转bytes
     *
     * @param is 输入流
     * @return 字节数组
     */
    public static byte[] inputStream2Bytes(@NonNull InputStream is) {
        if (isEmpty(is)) {
            return null;
        }
        ByteArrayOutputStream os = inputStream2OutputStream(is);
        return os == null ? null : os.toByteArray();
    }

    /**
     * inputStream转字符串
     *
     * @param is 输入流
     * @return 字符串
     */
    public static String inputStream2String(@NonNull InputStream is) {
        if (isEmpty(is)) {
            return null;
        }
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
    public static StringBuilder inputStream2StringBuilder(@NonNull InputStream is) throws IOException {
        if (isEmpty(is)) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result;
    }

}
