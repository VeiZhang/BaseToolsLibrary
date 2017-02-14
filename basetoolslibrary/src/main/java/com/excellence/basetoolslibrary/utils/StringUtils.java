package com.excellence.basetoolslibrary.utils;

/**
 * Created by ZhangWei on 2017/2/13.
 */

import android.text.TextUtils;

/**
 * 字符串相关
 */
public class StringUtils
{
    /**
     * 判断字符串是否为空
     *
     * @param text 只读序列 {@link String }
     * @return
     */
	public static boolean isEmpty(String text)
    {
        return TextUtils.isEmpty(text);
    }

    /**
     * 判断字符串是否为空
     *
     * @param text 可读可写序列 {@link CharSequence}
     * @return
     */
    public static boolean isEmpty(CharSequence text)
    {
        return isEmpty(text.toString());
    }
}
