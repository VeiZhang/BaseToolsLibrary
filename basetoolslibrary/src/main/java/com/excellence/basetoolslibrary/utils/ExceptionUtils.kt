package com.excellence.basetoolslibrary.utils

import android.text.TextUtils
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/7
 *     desc   : 异常相关工具
 * </pre>
 */
object ExceptionUtils {

    /**
     * 打印异常信息字符串
     *
     * @param t 异常信息
     * @return 字符串
     */
    @JvmStatic
    fun printException(t: Throwable): String {
        val exceptionStr = StringBuilder()
        try {
            val msg = t.message
            if (!TextUtils.isEmpty(msg)) {
                exceptionStr.append(msg).append("\n")
            }
            exceptionStr.append("Trace: ").append("\n")
            val writer: Writer = StringWriter()
            val printWriter = PrintWriter(writer)
            t.printStackTrace(printWriter)
            var cause = t.cause
            while (cause != null) {
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            val crash = StringBuilder(writer.toString().replace("\t", ""))
            crash.insert(0, "\n")
            crash.delete(crash.lastIndexOf("\n"), crash.lastIndexOf("\n") + "\n".length)
            exceptionStr.append(crash)
            printWriter.close()
            writer.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return exceptionStr.toString()
    }
}