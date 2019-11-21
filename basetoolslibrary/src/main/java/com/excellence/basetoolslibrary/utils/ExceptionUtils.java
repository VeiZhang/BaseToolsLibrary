package com.excellence.basetoolslibrary.utils;

import android.text.TextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/7
 *     desc   : 异常相关工具
 * </pre>
 */

public class ExceptionUtils {

    /**
     * 打印异常信息字符串
     *
     * @param t 异常信息
     * @return 字符串
     */
    public static String printException(@NonNull Throwable t) {
        StringBuilder exceptionStr = new StringBuilder();
        try {
            String msg = t.getMessage();
            if (!TextUtils.isEmpty(msg)) {
                exceptionStr.append(msg).append("\n");
            }
            exceptionStr.append("Trace: ").append("\n");
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            t.printStackTrace(printWriter);
            Throwable cause = t.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            StringBuilder crash = new StringBuilder(writer.toString().replace("\t", ""));
            crash.insert(0, "\n");
            crash.delete(crash.lastIndexOf("\n"), crash.lastIndexOf("\n") + "\n".length());
            exceptionStr.append(crash);
            printWriter.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exceptionStr.toString();
    }
}
