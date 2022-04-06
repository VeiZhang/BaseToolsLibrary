package com.excellence.basetoolslibrary.utils

import java.io.Closeable

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/6
 *     desc   : 关闭相关工具类
 * </pre>
 */
class CloseUtils {

    companion object {

        /**
         * 关闭IO
         */
        @JvmStatic
        fun closeIO(vararg closeables: Closeable) {
            for (closeable in closeables) {
                try {
                    closeable.close()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * 安静关闭IO
         */
        @JvmStatic
        fun closeIOQuietly(vararg closeables: Closeable) {
            for (closeable in closeables) {
                try {
                    closeable.close()
                } catch (ignored: java.lang.Exception) {

                }
            }
        }
    }
}