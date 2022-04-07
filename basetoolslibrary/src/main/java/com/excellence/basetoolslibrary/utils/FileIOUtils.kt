package com.excellence.basetoolslibrary.utils

import com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty
import java.io.*
import java.nio.charset.Charset

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/7
 *     desc   : 文件流相关
 * </pre>
 */
object FileIOUtils {

    private const val BUF_SIZE = 8 * 1024

    /**
     * 将字符串写入文件
     *
     * @param file
     * @param content
     * @param append
     * @return
     */
    @JvmStatic
    fun writeFile(file: File?, content: String?, append: Boolean): Boolean {
        try {
            if (file == null || isEmpty(content)) {
                return false
            }
            FileUtils.createNewFile(file)
            val writer = BufferedWriter(FileWriter(file, append))
            writer.write(content)
            writer.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 将字符串写入文件
     *
     * @param path
     * @param content
     * @param append
     * @return
     */
    @JvmStatic
    fun writeFile(path: String?, content: String?, append: Boolean): Boolean {
        if (isEmpty(path)) {
            return false
        }
        return writeFile(File(path), content, append)
    }

    /**
     * 将字节数组写入文件
     *
     * @param file
     * @param bytes
     * @param append
     * @return
     */
    @JvmStatic
    fun writeFile(file: File?, bytes: ByteArray?, append: Boolean): Boolean {
        try {
            if (file == null || isEmpty(bytes)) {
                return false
            }
            FileUtils.createNewFile(file)
            val os: OutputStream = BufferedOutputStream(FileOutputStream(file, append))
            os.write(bytes)
            os.close()
            return true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 将字节数组写入文件
     *
     * @param path
     * @param bytes
     * @param append
     * @return
     */
    @JvmStatic
    fun writeFile(path: String?, bytes: ByteArray?, append: Boolean): Boolean {
        if (isEmpty(path)) {
            return false
        }
        return writeFile(File(path), bytes, append)
    }

    /**
     * 将输入流写入文件里
     *
     * @param file
     * @param is
     * @param append
     * @param isCloseable 是否关闭输入流
     * @return
     */
    @JvmStatic
    fun writeFile(file: File?, stream: InputStream?, append: Boolean, isCloseable: Boolean): Boolean {
        try {
            if (file == null || stream == null) {
                return false
            }

            FileUtils.createNewFile(file)
            val os: OutputStream = BufferedOutputStream(FileOutputStream(file, append))
            val buf = ByteArray(BUF_SIZE)
            var len: Int
            while (stream.read(buf, 0, buf.size).also { len = it } != -1) {
                os.write(buf, 0, len)
            }
            os.close()
            if (isCloseable) {
                stream.close()
            }
            return true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 将输入流写入文件里
     *
     * @param path
     * @param stream
     * @param append
     * @param isCloseable 是否关闭输入流
     * @return
     */
    @JvmStatic
    fun writeFile(path: String?, stream: InputStream?, append: Boolean, isCloseable: Boolean): Boolean {
        if (isEmpty(path)) {
            return false
        }
        return writeFile(File(path), stream, append, isCloseable)
    }

    /**
     * 读取输入流为字节数组
     *
     * @param stream
     * @return
     */
    @JvmStatic
    fun readFile2Bytes(stream: InputStream?): ByteArray {
        try {
            val os = ByteArrayOutputStream()
            val buf = ByteArray(BUF_SIZE)
            var len: Int
            while (stream!!.read(buf, 0, buf.size).also { len = it } != -1) {
                os.write(buf, 0, len)
            }
            stream.close()
            os.close()
            return os.toByteArray()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ByteArray(0)
    }

    /**
     * 读取文件为字节数组
     *
     * @param file
     * @return
     */
    @JvmStatic
    fun readFile2Bytes(file: File?): ByteArray {
        try {
            if (!FileUtils.isFileExists(file)) {
                return ByteArray(0)
            }
            val stream: InputStream = FileInputStream(file)
            return readFile2Bytes(stream)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ByteArray(0)
    }

    /**
     * 读取文件为字节数组
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun readFile2Bytes(path: String?): ByteArray? {
        if (isEmpty(path)) {
            return null
        }
        return readFile2Bytes(File(path))
    }

    /**
     * 读取输入流为字符串
     *
     * @param stream
     * @param charset
     * @return
     */
    @JvmStatic
    fun readFile2String(stream: InputStream?, charset: String?): String {
        try {
            if (isEmpty(stream)) {
                return ""
            }
            val bytes = readFile2Bytes(stream)
            if (isEmpty(charset)) {
                return String(bytes)
            } else {
                return String(bytes, Charset.forName(charset))
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 读取文件为字符串
     *
     * @param file
     * @param charset UTF-8、GBK...
     * @return
     */
    @JvmStatic
    fun readFile2String(file: File?, charset: String?): String {
        try {
            if (!FileUtils.isFileExists(file)) {
                return ""
            }
            val stream: InputStream = FileInputStream(file)
            return readFile2String(stream, charset)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 读取文件为字符串
     *
     * @param path
     * @param charset UTF-8、GBK...
     * @return
     */
    @JvmStatic
    fun readFile2String(path: String?, charset: String?): String {
        if (isEmpty(path)) {
            return ""
        }
        return readFile2String(File(path), charset)
    }

    /**
     * 拷贝文件
     *
     * @param stream
     * @param os
     * @return
     */
    @JvmStatic
    fun copyFile(stream: InputStream?, os: OutputStream?): Boolean {
        try {
            if (stream == null || os == null) {
                return false
            }
            val buf = ByteArray(BUF_SIZE)
            var len: Int
            while (stream.read(buf, 0, buf.size).also { len = it } != -1) {
                os.write(buf, 0, len)
            }
            stream.close()
            os.close()
            return true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 拷贝文件
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    @JvmStatic
    fun copyFile(sourceFile: File?, targetFile: File?): Boolean {
        if (!FileUtils.isFileExists(sourceFile)) {
            return false
        }
        try {
            return copyFile(FileInputStream(sourceFile), FileOutputStream(targetFile))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 拷贝文件
     * 1.起始位置
     * 2.截止长度，最大为原始文件长度；<=0，表示不截取
     *
     * @param sourceFile
     * @param targetFile
     * @param skip
     * @param targetSize
     * @return
     */
    @JvmStatic
    fun copyFile(sourceFile: File, targetFile: File?, skip: Long, targetSize: Long): Boolean {
        if (!FileUtils.isFileExists(sourceFile)) {
            return false
        }
        try {
            val stream: InputStream = FileInputStream(sourceFile)
            val os: OutputStream = FileOutputStream(targetFile)
            stream.skip(skip)

            if (targetSize > 0 && targetSize < sourceFile.length()) {
                if (targetSize <= BUF_SIZE) {
                    val buf = ByteArray(targetSize.toInt())
                    var len: Int
                    if (stream.read(buf, 0, buf.size).also { len = it } != -1) {
                        os.write(buf, 0, len)
                    }
                } else {
                    val buf = ByteArray(BUF_SIZE)
                    var len: Int
                    var count: Long = 0
                    while (stream.read(buf, 0, buf.size).also { len = it } != -1) {
                        os.write(buf, 0, len)
                        count += len.toLong()

                        if (targetSize + BUF_SIZE > count) {
                            len = stream.read(buf, 0, buf.size)
                            os.write(buf, 0, len)
                            count += len.toLong()
                        }

                        if (targetSize <= count) {
                            break
                        }
                    }
                }
                stream.close()
                os.close()
                return true
            } else {
                return copyFile(stream, os)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 拷贝文件，截止长度，最大为原始文件长度；<=0，表示不截取
     *
     * @param sourceFile
     * @param targetFile
     * @param targetSize
     * @return
     */
    @JvmStatic
    fun copyFile(sourceFile: File, targetFile: File?, targetSize: Long): Boolean {
        return copyFile(sourceFile, targetFile, 0, targetSize)
    }
}