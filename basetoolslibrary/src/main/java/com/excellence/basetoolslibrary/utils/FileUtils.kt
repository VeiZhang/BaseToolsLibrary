package com.excellence.basetoolslibrary.utils

import com.excellence.basetoolslibrary.utils.ConvertUtils.bytes2HexString
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.DigestInputStream
import java.security.MessageDigest
import java.text.DecimalFormat

/**
 * <pre>
 * author : VeiZhang
 * blog   : https://veizhang.github.io/
 * time   : 2017/1/23
 * desc   : 文件相关工具类 权限 [android.Manifest.permission.WRITE_EXTERNAL_STORAGE]
 *
 * </pre>
 */
object FileUtils {

    private const val KB: Long = 1024
    private const val MB = 1024 * 1024.toLong()
    private const val GB = 1024 * 1024 * 1024.toLong()
    private const val TB = 1024 * 1024 * 1024 * 1024L
    private const val DEFAULT_FORMAT_PATTERN = "#.##"

    /**
     * 创建文件
     *
     * @param file File类型
     * @return 文件是否创建成功
     */
    @JvmStatic
    fun createNewFile(file: File): Boolean {
        try {
            return !isFileExists(file) && file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 创建文件
     *
     * @param filePath 文件路径字符串
     * @return 文件是否创建成功
     */
    @JvmStatic
    fun createNewFile(filePath: String?): Boolean {
        return !StringUtils.isEmpty(filePath) && createNewFile(File(filePath))
    }

    /**
     * 删除文件
     *
     * @param file File类型
     * @return 是否成功删除文件
     */
    @JvmStatic
    fun deleteFile(file: File): Boolean {
        return isFileExists(file) && file.delete()
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径字符串
     * @return 是否成功删除文件
     */
    @JvmStatic
    fun deleteFile(filePath: String?): Boolean {
        return !StringUtils.isEmpty(filePath) && deleteFile(File(filePath))
    }

    /**
     * 创建目录
     *
     * @param dir File类型
     * @return 目录是否创建成功
     */
    @JvmStatic
    fun mkDir(dir: File): Boolean {
        return !isFileExists(dir) && dir.mkdirs()
    }

    /**
     * 创建目录
     *
     * @param dirPath 目录路径字符串
     * @return 目录是否创建成功
     */
    @JvmStatic
    fun mkDir(dirPath: String?): Boolean {
        return !StringUtils.isEmpty(dirPath) && mkDir(File(dirPath))
    }

    /**
     * 删除目录
     *
     * @param dir File类型
     * @return 目录是否成功删除
     */
    @JvmStatic
    fun deleteDir(dir: File): Boolean {
        if (!isFileExists(dir)) {
            return false
        }
        if (dir.isDirectory) {
            val fileList = dir.listFiles() ?: return false
            for (file in fileList) {
                val success = deleteDir(file)
                if (!success) {
                    return false
                }
            }
        }
        return dir.delete()
    }

    /**
     * 删除目录
     *
     * @param dirPath 目录路径字符串
     * @return 目录是否成功删除
     */
    @JvmStatic
    fun deleteDir(dirPath: String?): Boolean {
        return !StringUtils.isEmpty(dirPath) && deleteDir(File(dirPath))
    }

    /**
     * 删除目录下的后缀文件
     *
     * @param dir File类型
     * @param postfix 后缀
     */
    @JvmStatic
    fun deletePostfixFiles(dir: File, postfix: String?): Boolean {
        if (!isFileExists(dir) || StringUtils.isEmpty(postfix)) {
            return false
        }
        if (dir.isFile && dir.name.endsWith(postfix!!)) {
            return dir.delete()
        } else if (dir.isDirectory) {
            val fileList = dir.listFiles() ?: return false
            for (file in fileList) {
                val success = deletePostfixFiles(file, postfix)
                if (!success) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * 删除目录下的后缀文件
     *
     * @param dirPath 目录路径字符串
     * @param postfix 后缀
     */
    @JvmStatic
    fun deletePostfixFiles(dirPath: String?, postfix: String?): Boolean {
        return !StringUtils.isEmpty(dirPath) && deletePostfixFiles(File(dirPath), postfix)
    }
    /**
     * 格式化文件大小
     *
     * 自定格式，保留位数
     *
     * @param fileSize 文件大小
     * @param pattern 保留格式
     * @return 转换后文件大小
     */
    /**
     * 格式化文件大小
     *
     * 默认格式，保留两位小数
     *
     * @param fileSize 文件大小
     * @return 转换后文件大小
     */
    @JvmStatic
    @JvmOverloads
    fun formatFileSize(fileSize: Long, pattern: String? = DEFAULT_FORMAT_PATTERN): String {
        val sizeFormat = DecimalFormat(pattern)
        var unitStr = "Bytes"
        var unit: Long = 1
        when {
            fileSize >= TB -> {
                unitStr = "TB"
                unit = TB
            }
            fileSize >= GB -> {
                unitStr = "GB"
                unit = GB
            }
            fileSize >= MB -> {
                unitStr = "MB"
                unit = MB
            }
            fileSize >= KB -> {
                unitStr = "KB"
                unit = KB
            }
        }
        return sizeFormat.format(fileSize.toDouble() / unit) + unitStr
    }

    /**
     * 获取文件或者遍历目录大小
     *
     * @param file File类型
     * @return
     */
    @JvmStatic
    fun getFilesSize(file: File): Long {
        var fileSize: Long = 0
        if (isFileExists(file)) {
            fileSize =
                    if (file.isDirectory) {
                        getDirSize(file)
                    } else {
                        getFileSize(file)
                    }
        }
        return fileSize
    }

    /**
     * 获取文件或者遍历目录大小
     *
     * @param filePath 文件路径字符串
     * @return
     */
    @JvmStatic
    fun getFilesSize(filePath: String?): Long {
        return if (isFileExists(filePath)) {
            getFilesSize(File(filePath))
        } else 0
    }

    /**
     * 获取文件大小
     *
     * @param file File类型
     * @return
     */
    @JvmStatic
    fun getFileSize(file: File): Long {
        try {
            if (isFileExists(file)) {
                if (file.isFile) {
                    val inStream = FileInputStream(file)
                    return inStream.available().toLong()
                } else if (file.isDirectory) {
                    return getDirSize(file)
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 获取文件大小
     *
     * @param filePath 文件路径字符串
     * @return
     */
    @JvmStatic
    fun getFileSize(filePath: String?): Long {
        return if (isFileExists(filePath)) {
            getFileSize(File(filePath))
        } else 0
    }

    /**
     * 遍历目录大小
     *
     * @param dir File类型
     * @return
     */
    @JvmStatic
    fun getDirSize(dir: File): Long {
        var size: Long = 0
        if (isFileExists(dir)) {
            val fileList = dir.listFiles() ?: return size
            for (file in fileList) {
                size += if (file.isDirectory) {
                    getDirSize(file)
                } else {
                    getFileSize(file)
                }
            }
        }
        return size
    }

    /**
     * 遍历目录大小
     *
     * @param filePath 文件路径字符串
     * @return
     */
    @JvmStatic
    fun getDirSize(filePath: String?): Long {
        return if (isFileExists(filePath)) {
            getDirSize(File(filePath))
        } else 0
    }

    /**
     * @see File.getFreeSpace() 获取系统root用户可用空间
     * @see File.getUsableSpace() 取非root用户可用空间
     *
     * 获取目录剩余空间，同 [StatFs.getFreeBytes]
     * 剩余空间 = 总空间 - 已使用空间
     * 剩余空间 ！= 可用空间
     *
     * @param dir File类型
     * @return
     */
    @JvmStatic
    fun getDirFreeSpace(dir: File): Long {
        var freeSpace: Long = 0
        if (isFileExists(dir)) {
            if (dir.isDirectory) {
                freeSpace = dir.freeSpace
            } else if (dir.isFile) {
                freeSpace = dir.parentFile.freeSpace
            }
        }
        return freeSpace
    }

    /**
     * @see File.getFreeSpace() 获取系统root用户可用空间
     * @see File.getUsableSpace() 取非root用户可用空间
     *
     * 获取目录剩余空间，同 [StatFs.getFreeBytes]
     * 剩余空间 = 总空间 - 已使用空间
     * 剩余空间 ！= 可用空间
     *
     * @param filePath 文件路径字符串
     * @return
     */
    @JvmStatic
    fun getDirFreeSpace(filePath: String?): Long {
        return if (isFileExists(filePath)) {
            getDirFreeSpace(File(filePath))
        } else 0
    }

    /**
     * 获取目录总空间，同[StatFs.getTotalBytes]
     *
     * @param dir File类型
     * @return
     */
    @JvmStatic
    fun getDirTotalSpace(dir: File): Long {
        var totalSpace: Long = 0
        if (isFileExists(dir)) {
            if (dir.isDirectory) {
                totalSpace = dir.totalSpace
            } else if (dir.isFile) {
                totalSpace = dir.parentFile.totalSpace
            }
        }
        return totalSpace
    }

    /**
     * 获取目录总空间，同[StatFs.getTotalBytes]
     *
     * @param filePath 文件路径字符串
     * @return
     */
    @JvmStatic
    fun getDirTotalSpace(filePath: String?): Long {
        return if (isFileExists(filePath)) {
            getDirTotalSpace(File(filePath))
        } else 0
    }

    /**
     * @see File.getFreeSpace 获取系统root用户可用空间
     * @see File.getUsableSpace 取非root用户可用空间
     *
     * 获取目录可用空间，同[StatFs.getAvailableBytes]
     *
     * @param dir File类型
     * @return
     */
    @JvmStatic
    fun getDirUsableSpace(dir: File): Long {
        var usableSpace: Long = 0
        if (isFileExists(dir)) {
            if (dir.isDirectory) {
                usableSpace = dir.usableSpace
            } else if (dir.isFile) {
                usableSpace = dir.parentFile.usableSpace
            }
        }
        return usableSpace
    }

    /**
     * @see File.getFreeSpace 获取系统root用户可用空间
     * @see File.getUsableSpace 取非root用户可用空间
     *
     * 获取目录可用空间，同[StatFs.getAvailableBytes]
     *
     * @param filePath 文件路径字符串
     * @return
     */
    @JvmStatic
    fun getDirUsableSpace(filePath: String?): Long {
        return if (isFileExists(filePath)) {
            getDirUsableSpace(File(filePath))
        } else 0
    }

    /**
     * 修改目录、文件权限
     *
     * @param path 目录、文件路径字符串
     * @param permission 权限字符串，如：777
     */
    @JvmStatic
    fun chmod(path: String, permission: String) {
        ShellUtils.execProcessBuilderCommand("chmod", permission, path)
    }

    /**
     * 修改目录、文件权限
     *
     * @param file File类型
     * @param permission 权限字符串，如：777
     */
    @JvmStatic
    fun chmod(file: File, permission: String) {
        chmod(file.path, permission)
    }

    /**
     * 修改目录、文件777权限
     *
     * @param path 目录、文件路径字符串
     */
    @JvmStatic
    fun chmod777(path: String) {
        chmod(path, "777")
    }

    /**
     * 修改目录、文件777权限
     *
     * @param file File类型
     */
    @JvmStatic
    fun chmod777(file: File) {
        chmod(file.path, "777")
    }

    /**
     * 判断文件或目录是否存在
     *
     * @param file File类型
     * @return
     */
    @JvmStatic
    fun isFileExists(file: File?): Boolean {
        return file != null && file.exists()
    }

    /**
     * 判断文件或目录是否存在
     *
     * @param filePath 路径字符串
     * @return
     */
    @JvmStatic
    fun isFileExists(filePath: String?): Boolean {
        return !StringUtils.isEmpty(filePath) && isFileExists(File(filePath))
    }

    /**
     * 读取文件最后的修改时间：milliseconds
     *
     * @param file
     * @return
     */
    @JvmStatic
    fun getFileLastModified(file: File): Long {
        return if (isFileExists(file)) {
            file.lastModified()
        } else -1
    }

    /**
     * 读取文件最后的修改时间：milliseconds
     *
     * @param filePath
     * @return
     */
    @JvmStatic
    fun getFileLastModified(filePath: String?): Long {
        return if (isFileExists(filePath)) {
            getFileLastModified(File(filePath))
        } else -1
    }

    /**
     * 读取文件MD5值
     *
     * @param file
     * @return
     */
    @JvmStatic
    fun getFileMd5(file: File?): String {
        try {
            if (!isFileExists(file)) {
                return ""
            }

            val fis = FileInputStream(file)
            var md = MessageDigest.getInstance("MD5")
            val dis = DigestInputStream(fis, md)
            val buffer = ByteArray(1024 * 256)
            while (true) {
                if (dis.read(buffer) <= 0) {
                    break
                }
            }
            md = dis.messageDigest
            fis.close()
            dis.close()
            return bytes2HexString(*md.digest())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 读取文件MD5值
     *
     * @param filePath
     * @return
     */
    @JvmStatic
    fun getFileMd5(filePath: String?): String {
        return if (isFileExists(filePath)) {
            getFileMd5(File(filePath))
        } else ""
    }
}