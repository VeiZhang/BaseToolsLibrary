package com.excellence.basetoolslibrary.utils;

import android.text.TextUtils;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/2/13
 *     desc   : 字符串相关工具类
 * </pre>
 */

public class StringUtils {

    private static final String NULL = "NULL";

    /**
     * 判断字符串是否为空
     *
     * @param text 只读序列 {@link String }
     * @return {@code true}：空<br>{@code false}：不为空
     */
    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    /**
     * 判断字符串是否为空
     *
     * @param text 可读可写序列 {@link CharSequence}
     * @return {@code true}：空<br>{@code false}：不为空
     */
    public static boolean isEmpty(CharSequence text) {
        return TextUtils.isEmpty(text);
    }

    /**
     * 判断字符串是否为空，是否是"NULL"字符串
     *
     * @param text 只读序列 {@link String }
     * @return {@code true}：空<br>{@code false}：不为空
     */
    public static boolean checkNULL(String text) {
        return isEmpty(text) || text.equalsIgnoreCase(NULL);
    }

    /**
     * 判断字符串是否为空，是否是"NULL"字符串
     *
     * @param text 可读可写序列 {@link CharSequence}
     * @return {@code true}：空<br>{@code false}：不为空
     */
    public static boolean checkNULL(CharSequence text) {
        return isEmpty(text) || text.toString().equalsIgnoreCase(NULL);
    }

    /**
     * 比较字符串是否相等
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return {@code true}：相等<br>{@code false}：不相等
     */
    public static boolean equals(String str1, String str2) {
        return isEmpty(str1) && isEmpty(str2) || str1 != null && str1.equals(str2);
    }

    /**
     * 比较字符串是否相等
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return {@code true}：相等<br>{@code false}：不相等
     */
    public static boolean equals(CharSequence str1, CharSequence str2) {
        return isEmpty(str1) && isEmpty(str2) || str1 != null && str1.toString().equals(str2);
    }

    /**
     * 比较字符串是否相等，忽略大小写
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return {@code true}：相等<br>{@code false}：不相等
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return isEmpty(str1) && isEmpty(str2) || str1 != null && str1.equalsIgnoreCase(str2);
    }

    /**
     * 比较字符串是否相等，忽略大小写
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return {@code true}：相等<br>{@code false}：不相等
     */
    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return isEmpty(str1) && isEmpty(str2) || str1 != null && str2 != null && str1.toString().equalsIgnoreCase(str2.toString());
    }

    /**
     * 判断字符一是否包含字符串二
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return {@code true}：包含<br>{@code false}：不包含
     */
    public static boolean contains(CharSequence str1, CharSequence str2) {
        return isEmpty(str1) && isEmpty(str2) || str1 != null && str2 != null && str1.toString().contains(str2.toString());
    }

    /**
     * 判断字符一是否包含字符串二，忽略大小写
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return {@code true}：包含<br>{@code false}：不包含
     */
    public static boolean containsIgnoreCase(CharSequence str1, CharSequence str2) {
        return isEmpty(str1) && isEmpty(str2) || str1 != null && str2 != null && str1.toString().toLowerCase().contains(str2.toString().toLowerCase());
    }
}
