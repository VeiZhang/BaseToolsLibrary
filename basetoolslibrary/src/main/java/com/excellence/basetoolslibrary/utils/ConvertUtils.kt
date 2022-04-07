package com.excellence.basetoolslibrary.utils

import androidx.annotation.Size
import com.excellence.basetoolslibrary.utils.CloseUtils.Companion.closeIO
import com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/6
 *     desc   : 转换相关工具类
 *              byte是基本数据类型
 *              Byte是byte的包装类
 *
 *              Kotlin的位运算只针对int类型和long类型
 * </pre>
 */
class ConvertUtils {

    companion object {

        private val HEX_CHAR = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

        /**
         * byte转short
         */
        @JvmStatic
        fun bytes2Short(@Size(min = 2) buffer: ByteArray, offset: Int): Short =
                (((buffer[offset].toInt() and 0xFF) shl 8)
                        or (buffer[offset + 1].toInt() and 0xFF)).toShort()

        /**
         * short转byte
         */
        @JvmStatic
        fun shortToByte(value: Short): ByteArray {
            val bytes = ByteArray(2)
            for (i in bytes.indices) {
                val offset = (bytes.size - 1 - i) * 8
                bytes[i] = (value.toInt() ushr offset and 0xFF).toByte()
            }
            return bytes
        }


        /**
         * byte转二进制
         */
        @JvmStatic
        fun byte2BinStr(b: Byte): String {
            var result = ""
            var a = b
            for (i in 0..7) {
                val c = a
                // 每移一位如同将10进制数除以2并去掉余数。
                a = (a.toInt() shr 1).toByte()
                a = (a.toInt() shl 1).toByte()

                result =
                        if (a == c) {
                            "0$result"
                        } else {
                            "1$result"
                        }

                a = (a.toInt() shr 1).toByte()
            }
            return result
        }

        /**
         * byte数组转二进制
         */
        @JvmStatic
        fun byte2BinStr(vararg bytes: Byte): String {
            if (isEmpty(bytes)) {
                return ""
            }
            val sb = StringBuilder()
            for (element in bytes) {
                sb.append(byte2BinStr(element))
                sb.append(" ")
            }
            return sb.toString()
        }

        /**
         * 字符串转二进制字符串
         *
         * @param src
         * @return
         */
        @JvmStatic
        fun str2BinStr(src: String): String {
            if (isEmpty(src)) {
                return ""
            }
            val strChar = src.toCharArray()
            var result = ""
            for (i in strChar.indices) {
                val binStr = Integer.toBinaryString(strChar[i].toInt())
                result += String.format("%08d ", Integer.valueOf(binStr))
            }
            return result
        }

        /**
         * byte数组转16进制字符串，[bytes2HexString(bytes: ByteArray, size: Int)]
         *
         * @param bytes bytes
         * @return 16进制字符串
         */
        @JvmStatic
        fun bytes2HexString(vararg bytes: Byte): String {
            if (isEmpty(bytes)) {
                return ""
            }
            val chars = CharArray(bytes.size * 2)
            for (i in bytes.indices) {
                val b = bytes[i]
                chars[i * 2] = HEX_CHAR[b.toInt() ushr 4 and 0x0F]
                chars[i * 2 + 1] = HEX_CHAR[b.toInt() and 0x0F]
            }
            return String(chars)
        }

        /**
         * byte数组转16进制字符串，[bytes2HexString(vararg bytes: Byte)]
         *
         * @param bytes
         * @param size
         * @return
         */
        @JvmStatic
        fun bytes2HexString(bytes: ByteArray, size: Int): String {
            if (isEmpty(bytes)) {
                return ""
            }
            val result = java.lang.StringBuilder()
            var hex: String
            for (i in 0 until size) {
                hex = Integer.toHexString(bytes[i].toInt() and 0xFF)
                if (hex.length == 1) {
                    hex = "0$hex"
                }
                result.append(hex.toUpperCase(Locale.getDefault()))
            }
            return result.toString()
        }

        /**
         * 16进制字符串转byte数组
         *
         * @param src
         * @return
         */
        @JvmStatic
        fun hexString2Bytes(src: String): ByteArray {
            if (isEmpty(src)) {
                return ByteArray(0)
            }
            val l = src.length / 2
            val ret = ByteArray(l)
            for (i in 0 until l) {
                ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).toByte()
            }
            return ret
        }

        /**
         * 字符串转16进制字符串
         *
         * @param src
         * @return
         */
        @JvmStatic
        fun string2HexString(src: String): String {
            if (isEmpty(src)) {
                return ""
            }
            val hexString = java.lang.StringBuilder()
            for (element in src) {
                val ch = element.toInt()
                val strHex = Integer.toHexString(ch)
                hexString.append(strHex)
            }
            return hexString.toString()
        }

        /**
         * 16进制字符串转字符串
         *
         * @param src
         * @return
         */
        @JvmStatic
        fun hexString2String(src: String): String {
            if (isEmpty(src)) {
                return ""
            }
            var temp = ""
            for (i in 0 until src.length / 2) {
                temp += Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).toByte().toChar()
            }
            return temp
        }

        /**
         * 字符串转byte数组
         *
         * @param src
         * @return
         */
        @JvmStatic
        fun string2Bytes(src: String): ByteArray {
            val hexStr = string2HexString(src)
            return hexString2Bytes(hexStr)
        }

        /**
         * byte数组转字符串
         *
         * @param bytes
         * @param length
         * @return
         */
        @JvmStatic
        fun bytes2String(bytes: ByteArray, length: Int): String {
            if (isEmpty(bytes)) {
                return ""
            }
            val hexStr = bytes2HexString(bytes, length)
            return hexString2String(hexStr)
        }

        /**
         * byte数组转有符号int
         *
         * @param bytes
         * @return
         */
        @JvmStatic
        fun byte2Int(bytes: ByteArray): Long {
            if (isEmpty(bytes)) {
                return 0
            }
            return (((bytes[0].toInt() and 0xff) shl 24) or
                    ((bytes[1].toInt() and 0xff) shl 16) or
                    ((bytes[2].toInt() and 0xff) shl 8) or
                    (bytes[3].toInt() and 0xff)).toLong()
        }

        /**
         * int转4位byte数组
         *
         * @param n
         * @return
         */
        @JvmStatic
        fun int2Byte(n: Int): ByteArray {
            val b = ByteArray(4)
            b[0] = (n and 0xff).toByte()
            b[1] = (n shr 8 and 0xff).toByte()
            b[2] = (n shr 16 and 0xff).toByte()
            b[3] = (n shr 24 and 0xff).toByte()
            return b
        }

        /**
         * 四字节byte数组转无符号long
         *
         * @param bytes
         * @return
         */
        @JvmStatic
        fun unintbyte2long(bytes: ByteArray): Long {
            if (isEmpty(bytes) || bytes.size < 4) {
                return 0
            }
            val index = 0
            val firstByte = 0x000000FF and bytes[index].toInt()
            val secondByte = 0x000000FF and bytes[index + 1].toInt()
            val thirdByte = 0x000000FF and bytes[index + 2].toInt()
            val fourthByte = 0x000000FF and bytes[index + 3].toInt()
            return (firstByte shl 24 or (secondByte shl 16) or (thirdByte shl 8) or fourthByte).toLong() and 0xFFFFFFFFL
        }

        /**
         * inputStream转outPutStream
         *
         * @param stream inputStream输入流
         * @return outputStream输出流
         */
        @JvmStatic
        fun inputStream2OutputStream(stream: InputStream): ByteArrayOutputStream? {
            try {
                if (isEmpty(stream)) {
                    return null
                }
                val os = ByteArrayOutputStream()
                val buffer = ByteArray(FileUtils.KB.toInt())
                var offset: Int
                while (stream.read(buffer).also { offset = it } != -1) {
                    os.write(buffer, 0, offset)
                }
                return os
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                closeIO(stream)
            }
            return null
        }

        /**
         * inputStream转bytes
         *
         * @param stream 输入流
         * @return 字节数组
         */
        @JvmStatic
        fun inputStream2Bytes(stream: InputStream): ByteArray? {
            if (isEmpty(stream)) {
                return null
            }
            val os = inputStream2OutputStream(stream)
            return os?.toByteArray()
        }

        /**
         * inputStream转字符串
         *
         * @param stream 输入流
         * @return 字符串
         */
        @JvmStatic
        fun inputStream2String(stream: InputStream): String {
            if (isEmpty(stream)) {
                return ""
            }
            val bytes = inputStream2Bytes(stream)
            return bytes?.let { String(it) } ?: ""
        }

        /**
         * 信息流
         *
         * @param stream 输入流
         * @return StringBuilder
         * @throws Exception
         */
        @JvmStatic
        @Throws(java.lang.Exception::class)
        fun inputStream2StringBuilder(stream: InputStream): java.lang.StringBuilder {
            val result = java.lang.StringBuilder()
            if (isEmpty(stream)) {
                return result
            }
            val reader = BufferedReader(InputStreamReader(stream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                result.append(line)
            }
            reader.close()
            return result
        }

    }

}