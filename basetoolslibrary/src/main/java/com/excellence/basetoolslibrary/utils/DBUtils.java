package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/1/23
 *     desc   : 配置存储相关工具类
 * </pre>
 */

public class DBUtils {

    /**
     * 存储文件名
     */
    private static final String SHAREPREFERENCES_DEFAULT_FILE = "shared_prefs";

    private static String SHAREPREFERENCES_FILE_NAME = SHAREPREFERENCES_DEFAULT_FILE;

    /**
     * 初始化，设置存储文件名
     *
     * @param sharedFileName 存储文件名
     */
    public static void init(String sharedFileName) {
        SHAREPREFERENCES_FILE_NAME = sharedFileName;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHAREPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 判断键值是否存在
     *
     * @param context 上下文
     * @param key 键值
     * @return {@code true}:存在<br>{@code false}:不存在
     */
    public static boolean contains(Context context, String key) {
        return getSharedPreferences(context).contains(key);
    }

    /**
     * 存储字符串
     *
     * @param context 上下文
     * @param key 键值
     * @param value 字符串
     */
    public static void setSetting(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 读取字符串
     *
     * @param context 上下文
     * @param key 键值
     * @param defValue 默认返回字符串
     * @return 字符串
     */
    public static String getString(Context context, String key, String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    /**
     * 读取字符串
     *
     * @param context 上下文
     * @param key 键值
     * @return 字符串
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * 存储布尔类型
     *
     * @param context 上下文
     * @param key 键值
     * @param value boolean
     */
    public static void setSetting(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 读取布尔类型
     *
     * @param context 上下文
     * @param key 键值
     * @param defValue 默认返回值
     * @return boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    /**
     * 读取布尔类型
     *
     * @param context 上下文
     * @param key 键值
     * @return boolean
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * 存储int
     *
     * @param context 上下文
     * @param key 键值
     * @param value int
     */
    public static void setSetting(Context context, String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 读取int
     *
     * @param context 上下文
     * @param key 键值
     * @param defValue 默认返回值
     * @return int
     */
    public static int getInt(Context context, String key, int defValue) {
        return getSharedPreferences(context).getInt(key, defValue);
    }

    /**
     * 读取int
     *
     * @param context 上下文
     * @param key 键值
     * @return int
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    /**
     * 存储long
     *
     * @param context 上下文
     * @param key 键值
     * @param value long
     */
    public static void setSetting(Context context, String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 读取long
     *
     * @param context 上下文
     * @param key 键值
     * @param defValue 默认返回值
     * @return long
     */
    public static long getLong(Context context, String key, long defValue) {
        return getSharedPreferences(context).getLong(key, defValue);
    }

    /**
     * 读取long
     *
     * @param context 上下文
     * @param key 键值
     * @return long
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, 0);
    }

    /**
     * 存储float
     *
     * @param context 上下文
     * @param key 键值
     * @param value float
     */
    public static void setSetting(Context context, String key, float value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 读取float
     *
     * @param context 上下文
     * @param key 键值
     * @param defValue 默认返回值
     * @return float
     */
    public static float getFloat(Context context, String key, float defValue) {
        return getSharedPreferences(context).getFloat(key, defValue);
    }

    /**
     * 读取float
     *
     * @param context 上下文
     * @param key 键值
     * @return float
     */
    public static float getFloat(Context context, String key) {
        return getSharedPreferences(context).getFloat(key, 0);
    }

    /**
     * 存储Set<String>
     *
     * @param context 上下文
     * @param key 键值
     * @param value Set<String>
     */
    public static void setSetting(Context context, String key, Set<String> value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * 读取Set<String>
     *
     * @param context 上下文
     * @param key 键值
     * @param defValue 默认返回值
     * @return Set<String>
     */
    public static Set<String> getStringSet(Context context, String key, Set<String> defValue) {
        return getSharedPreferences(context).getStringSet(key, defValue);
    }

    /**
     * 读取Set<String>
     *
     * @param context 上下文
     * @param key 键值
     * @return Set<String>
     */
    public static Set<String> getStringSet(Context context, String key) {
        return getStringSet(context, key, null);
    }

    /**
     * 删除配置
     *
     * @param context 上下文
     * @param key 键值
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清空配置
     *
     * @param context 上下文
     */
    public static void clear(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }

}
