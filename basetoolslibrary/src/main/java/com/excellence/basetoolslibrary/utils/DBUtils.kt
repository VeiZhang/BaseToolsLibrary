package com.excellence.basetoolslibrary.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * <pre>
 * author : VeiZhang
 * blog   : https://veizhang.github.io/
 * time   : 2017/1/23
 * desc   : 配置存储相关工具类
</pre> *
 */
class DBUtils {

    companion object {

        /**
         * 存储文件名
         */
        private const val SHARE_PREFERENCES_DEFAULT_FILE = "shared_prefs"
        private var SHARE_PREFERENCES_FILE_NAME = SHARE_PREFERENCES_DEFAULT_FILE

        /**
         * 初始化，设置存储文件名
         *
         * @param sharedFileName 存储文件名
         */
        @JvmStatic
        fun init(sharedFileName: String) {
            SHARE_PREFERENCES_FILE_NAME = sharedFileName
        }

        private fun getSharedPreferences(context: Context): SharedPreferences =
                context.getSharedPreferences(SHARE_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

        /**
         * 判断键值是否存在
         *
         * @param context 上下文
         * @param key 键值
         * @return `true`:存在<br></br>`false`:不存在
         */
        @JvmStatic
        fun contains(context: Context, key: String?): Boolean =
                getSharedPreferences(context).contains(key)

        /**
         * 存储字符串
         *
         * @param context 上下文
         * @param key 键值
         * @param value 字符串
         */
        @JvmStatic
        fun setSetting(context: Context, key: String?, value: String?) {
            val editor = getSharedPreferences(context).edit()
            editor.putString(key, value)
            editor.apply()
        }

        /**
         * 读取字符串
         *
         * @param context 上下文
         * @param key 键值
         * @param defValue 默认返回字符串
         * @return 字符串
         */
        @JvmStatic
        fun getString(context: Context, key: String?, defValue: String?): String? =
                getSharedPreferences(context).getString(key, defValue)

        /**
         * 读取字符串
         *
         * @param context 上下文
         * @param key 键值
         * @return 字符串
         */
        @JvmStatic
        fun getString(context: Context, key: String?): String? =
                getString(context, key, null)

        /**
         * 存储布尔类型
         *
         * @param context 上下文
         * @param key 键值
         * @param value boolean
         */
        @JvmStatic
        fun setSetting(context: Context, key: String?, value: Boolean) {
            val editor = getSharedPreferences(context).edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        /**
         * 读取布尔类型
         *
         * @param context 上下文
         * @param key 键值
         * @param defValue 默认返回值
         * @return boolean
         */
        @JvmStatic
        fun getBoolean(context: Context, key: String?, defValue: Boolean): Boolean =
                getSharedPreferences(context).getBoolean(key, defValue)

        /**
         * 读取布尔类型
         *
         * @param context 上下文
         * @param key 键值
         * @return boolean
         */
        @JvmStatic
        fun getBoolean(context: Context, key: String?): Boolean =
                getBoolean(context, key, false)

        /**
         * 存储int
         *
         * @param context 上下文
         * @param key 键值
         * @param value int
         */
        @JvmStatic
        fun setSetting(context: Context, key: String?, value: Int) {
            val editor = getSharedPreferences(context).edit()
            editor.putInt(key, value)
            editor.apply()
        }

        /**
         * 读取int
         *
         * @param context 上下文
         * @param key 键值
         * @param defValue 默认返回值
         * @return int
         */
        @JvmStatic
        fun getInt(context: Context, key: String?, defValue: Int): Int =
                getSharedPreferences(context).getInt(key, defValue)

        /**
         * 读取int
         *
         * @param context 上下文
         * @param key 键值
         * @return int
         */
        @JvmStatic
        fun getInt(context: Context, key: String?): Int =
                getInt(context, key, 0)

        /**
         * 存储long
         *
         * @param context 上下文
         * @param key 键值
         * @param value long
         */
        @JvmStatic
        fun setSetting(context: Context, key: String?, value: Long) {
            val editor = getSharedPreferences(context).edit()
            editor.putLong(key, value)
            editor.apply()
        }

        /**
         * 读取long
         *
         * @param context 上下文
         * @param key 键值
         * @param defValue 默认返回值
         * @return long
         */
        @JvmStatic
        fun getLong(context: Context, key: String?, defValue: Long): Long =
                getSharedPreferences(context).getLong(key, defValue)

        /**
         * 读取long
         *
         * @param context 上下文
         * @param key 键值
         * @return long
         */
        @JvmStatic
        fun getLong(context: Context, key: String?): Long =
                getLong(context, key, 0)

        /**
         * 存储float
         *
         * @param context 上下文
         * @param key 键值
         * @param value float
         */
        @JvmStatic
        fun setSetting(context: Context, key: String?, value: Float) {
            val editor = getSharedPreferences(context).edit()
            editor.putFloat(key, value)
            editor.apply()
        }

        /**
         * 读取float
         *
         * @param context 上下文
         * @param key 键值
         * @param defValue 默认返回值
         * @return float
         */
        @JvmStatic
        fun getFloat(context: Context, key: String?, defValue: Float): Float =
                getSharedPreferences(context).getFloat(key, defValue)


        /**
         * 读取float
         *
         * @param context 上下文
         * @param key 键值
         * @return float
         */
        @JvmStatic
        fun getFloat(context: Context, key: String?): Float =
                getSharedPreferences(context).getFloat(key, 0f)

        /**
         * 存储Set<String>
         *
         * @param context 上下文
         * @param key 键值
         * @param value Set<String>
         */
        @JvmStatic
        fun setSetting(context: Context, key: String?, value: Set<String>?) {
            val editor = getSharedPreferences(context).edit()
            editor.putStringSet(key, value)
            editor.apply()
        }

        /**
         * 读取Set<String>
         *
         * @param context 上下文
         * @param key 键值
         * @param defValue 默认返回值
         * @return Set<String>
         */
        @JvmStatic
        fun getStringSet(context: Context, key: String?, defValue: Set<String>?): Set<String>? =
                getSharedPreferences(context).getStringSet(key, defValue)

        /**
         * 读取Set<String>
         *
         * @param context 上下文
         * @param key 键值
         * @return Set<String>
         */
        @JvmStatic
        fun getStringSet(context: Context, key: String?): Set<String>? =
                getStringSet(context, key, null)

        /**
         * 删除配置
         *
         * @param context 上下文
         * @param key 键值
         */
        @JvmStatic
        fun remove(context: Context, key: String?) {
            val editor = getSharedPreferences(context).edit()
            editor.remove(key)
            editor.apply()
        }

        /**
         * 清空配置
         *
         * @param context 上下文
         */
        @JvmStatic
        fun clear(context: Context) {
            val editor = getSharedPreferences(context).edit()
            editor.clear()
            editor.apply()
        }

    }

}