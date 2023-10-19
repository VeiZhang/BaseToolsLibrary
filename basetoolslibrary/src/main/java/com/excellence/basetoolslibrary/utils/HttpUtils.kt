package com.excellence.basetoolslibrary.utils

import android.text.TextUtils
import android.util.Log
import com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty
import com.excellence.basetoolslibrary.utils.PinyinUtils.hasDoubleCharacter
import com.excellence.basetoolslibrary.utils.RegexUtils.isURL
import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*
import java.util.regex.Pattern
import java.util.zip.GZIPInputStream
import java.util.zip.InflaterInputStream

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/5/18
 *     desc   :
 * </pre>
 */
object HttpUtils {

    private val TAG = HttpUtils::class.java.simpleName

    const val HTTP = "http://"
    const val HTTPS = "https://"
    const val SLASH = "/"

    /**
     * 检测有效的URL：
     * 不为空
     * 符合URL表达式
     * 不能是http://、https://
     */
    fun checkURL(url: String?): Boolean {
        var url = url
        if (isEmpty(url)) {
            return false
        }
        if (!url!!.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url
        }
        return !(!isURL(url)
                || url.equals(HTTP, ignoreCase = true)
                || url.equals(HTTPS, ignoreCase = true))
    }

    /**
     * 检测Http、Https，没有则增加前缀http://
     *
     * @param url
     * @return
     */
    fun checkHttpURL(url: String?): String? {
        var url = url
        if (isEmpty(url)) {
            return url
        }
        if (!url!!.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url
        }
        return url
    }

    /**
     * 拼接url
     *
     * @param url
     * @param path
     * @return
     */
    fun appendURLPath(url: String?, vararg path: String): String? {
        var url = url
        if (isEmpty(path)) {
            return url
        }
        if (isEmpty(url)) {
            url = ""
        }
        if (url!!.endsWith(SLASH)) {
            url = url.substring(0, url.length - 1)
        }
        for (item in path) {
            var param = item
            if (isEmpty(param)) {
                continue
            }
            if (param.startsWith(SLASH)) {
                param = param.substring(1)
            }
            if (param.endsWith(SLASH)) {
                param = param.substring(0, param.length - 1)
            }
            url = url + SLASH + param
        }
        return url
    }

    /**
     * Check if url exists.
     *
     * [NetworkUtils.isAvailableByPing]
     *
     * @param url URL to check
     * @return
     * @throws IOException
     */
    @JvmStatic
    @Throws(IOException::class)
    fun isUrlExists(url: String?): Boolean {
        val huc = URL(url).openConnection() as HttpURLConnection
        huc.requestMethod = "GET"
        huc.connect()
        val code = huc.responseCode
        huc.disconnect()
        return code != HttpURLConnection.HTTP_NOT_FOUND
    }

    /**
     * 转换链接中中文字符
     *
     * @param url
     * @return
     */
    @JvmStatic
    fun convertHttpUrl(url: String): String {
        var url = url
        if (TextUtils.isEmpty(url)) {
            return url
        }
        if (hasDoubleCharacter(url)) {
            val regex = "[^\\x00-\\xff]"
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(url)
            val strs: MutableSet<String> = HashSet()
            while (matcher.find()) {
                strs.add(matcher.group())
            }
            try {
                for (str in strs) {
                    url = url.replace(str.toRegex(), URLEncoder.encode(str, "UTF-8"))
                }
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }
        return url
    }

    /**
     * 通过类型转换流
     */
    @JvmStatic
    @Throws(IOException::class)
    fun convertInputStream(conn: HttpURLConnection): InputStream {
        val inputStream = conn.inputStream
        val encoding = conn.getHeaderField("Content-Encoding")
        Log.i(TAG, "convertInputStream: $encoding")
        return if (TextUtils.isEmpty(encoding)) {
            inputStream
        } else when (encoding) {
            "gzip" -> GZIPInputStream(inputStream)
            "deflate" -> InflaterInputStream(inputStream)
            else -> inputStream
        }
    }

    /**
     * 设置请求头信息
     */
    @JvmStatic
    @Throws(Exception::class)
    fun setConnectParam(conn: HttpURLConnection, url: String?) {
        // 设置 HttpURLConnection的请求方式
        // default request : GET
        conn.requestMethod = "GET"
        // 设置 HttpURLConnection的接收的文件类型
        val accept = StringBuilder()
        accept.append("image/gif, ")
                .append("image/jpeg, ")
                .append("image/pjpeg, ")
                .append("image/webp, ")
                .append("image/apng, ")
                .append("application/xml, ")
                .append("application/xaml+xml, ")
                .append("application/xhtml+xml, ")
                .append("application/x-shockwave-flash, ")
                .append("application/x-ms-xbap, ")
                .append("application/x-ms-application, ")
                .append("application/msword, ")
                .append("application/vnd.ms-excel, ")
                .append("application/vnd.ms-xpsdocument, ")
                .append("application/vnd.ms-powerpoint, ")
                .append("text/plain, ")
                .append("text/html, ")
                .append("*/*")
        conn.setRequestProperty("Accept", accept.toString())
        // 设置接收的压缩格式
        conn.setRequestProperty("Accept-Encoding", "identity")
        // 指定请求uri的源资源地址
        conn.setRequestProperty("Referer", url)
        // 设置 HttpURLConnection的字符编码
        conn.setRequestProperty("Charset", "UTF-8")
        // 检查浏览页面的访问者在用什么操作系统（包括版本号）浏览器（包括版本号）和用户个人偏好
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
        conn.setRequestProperty("Connection", "Keep-Alive")
    }

    /**
     * 打印全部请求头信息
     *
     * @param conn
     */
    @JvmStatic
    fun printHeader(conn: HttpURLConnection) {
        val headerFields = conn.headerFields
        for ((key, value) in headerFields) {
            Log.i(TAG, "[key : $key][value : $value]")
        }
    }

    /**
     * 获取具体的请求头信息
     *
     * @param conn
     * @param key
     * @return
     */
    @JvmStatic
    fun getHeader(conn: HttpURLConnection, key: String?): List<String?>? {
        val headerFields = conn.headerFields
        return headerFields[key]
    }

    /**
     * contentDisposition 头信息字符串的解析
     * attachment; filename="应用宝.apk" -> filename : 应用宝.apk
     */
    @JvmStatic
    fun getContentHeader(contentDisposition: String?, key: String): String? {
        if (contentDisposition == null) {
            return null
        }

        val content = contentDisposition.split(";")
        for (value in content) {
            if (value.trim().startsWith(key)) {
                return value.substring(value.indexOf('=') + 1).trim().replace("\"", "")
            }
        }
        return null
    }

}