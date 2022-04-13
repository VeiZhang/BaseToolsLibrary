package com.excellence.basetoolslibrary.utils

import android.text.TextUtils
import java.util.*

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/2/13
 *     desc   : 字符串相关工具类
 * </pre>
 */
object StringUtils {

    private const val NULL = "NULL"

    /**
     * 判断字符串是否为空
     *
     * @param text 只读序列 [String]
     * @return `true`：空<br>`false`：不为空
     */
    @JvmStatic
    fun isEmpty(text: String?): Boolean {
        return TextUtils.isEmpty(text)
    }

    /**
     * 判断字符串是否为空
     *
     * @param text 可读可写序列 [CharSequence]
     * @return `true`：空<br>`false`：不为空
     */
    @JvmStatic
    fun isEmpty(text: CharSequence?): Boolean {
        return TextUtils.isEmpty(text)
    }

    /**
     * 判断字符串是否为空，是否是"NULL"字符串
     *
     * @param text 只读序列 [String]
     * @return `true`：空<br>`false`：不为空
     */
    @JvmStatic
    fun checkNULL(text: String?): Boolean {
        return isEmpty(text) || text.equals(NULL, ignoreCase = true)
    }

    /**
     * 判断字符串是否为空，是否是"NULL"字符串
     *
     * @param text 可读可写序列 [CharSequence]
     * @return `true`：空<br>`false`：不为空
     */
    @JvmStatic
    fun checkNULL(text: CharSequence?): Boolean {
        return isEmpty(text) || text.toString().equals(NULL, ignoreCase = true)
    }

    /**
     * 比较字符串是否相等
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return `true`：相等<br>`false`：不相等
     */
    @JvmStatic
    fun equals(str1: String?, str2: String?): Boolean {
        return isEmpty(str1) && isEmpty(str2) || str1 != null && str1 == str2
    }

    /**
     * 比较字符串是否相等
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return `true`：相等<br>`false`：不相等
     */
    @JvmStatic
    fun equals(str1: CharSequence?, str2: CharSequence?): Boolean {
        return isEmpty(str1) && isEmpty(str2)
                || str1 != null && str1.toString() == str2
    }

    /**
     * 比较字符串是否相等，忽略大小写
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return `true`：相等<br>`false`：不相等
     */
    @JvmStatic
    fun equalsIgnoreCase(str1: String?, str2: String?): Boolean {
        return isEmpty(str1) && isEmpty(str2)
                || str1 != null && str1.equals(str2, ignoreCase = true)
    }

    /**
     * 比较字符串是否相等，忽略大小写
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return `true`：相等<br>`false`：不相等
     */
    @JvmStatic
    fun equalsIgnoreCase(str1: CharSequence?, str2: CharSequence?): Boolean {
        return isEmpty(str1) && isEmpty(str2)
                || str1 != null && str2 != null && str1.toString().equals(str2.toString(), ignoreCase = true)
    }

    /**
     * 判断字符一是否包含字符串二
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return `true`：包含<br>`false`：不包含
     */
    @JvmStatic
    fun contains(str1: CharSequence?, str2: CharSequence?): Boolean {
        return isEmpty(str1) && isEmpty(str2)
                || str1 != null && str2 != null && str1.toString().contains(str2.toString())
    }

    /**
     * 判断字符一是否包含字符串二，忽略大小写
     *
     * @param str1 字符串一
     * @param str2 字符串二
     * @return `true`：包含<br>`false`：不包含
     */
    @JvmStatic
    fun containsIgnoreCase(str1: CharSequence?, str2: CharSequence?): Boolean {
        return isEmpty(str1) && isEmpty(str2)
                || str1 != null && str2 != null && str1.toString().toLowerCase(Locale.getDefault()).contains(str2.toString().toLowerCase(Locale.getDefault()))
    }
}