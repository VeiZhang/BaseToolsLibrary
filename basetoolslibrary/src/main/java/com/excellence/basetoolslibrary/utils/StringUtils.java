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

public class StringUtils
{
    /**
     * 判断字符串是否为空
     *
     * @param text 只读序列 {@link String }
     * @return {@code true}：空<br>{@code false}：不为空
     */
	public static boolean isEmpty(String text)
    {
        return TextUtils.isEmpty(text);
    }

    /**
     * 判断字符串是否为空
     *
     * @param text 可读可写序列 {@link CharSequence}
     * @return {@code true}：空<br>{@code false}：不为空
     */
    public static boolean isEmpty(CharSequence text)
    {
        return TextUtils.isEmpty(text);
    }
}
