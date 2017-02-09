package com.excellence.basetoolslibrary.utils;

/**
 * Created by ZhangWei on 2017/1/24.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间相关
 */
public class TimeUtils
{
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳转时间字符串
     * <p>格式自定</p>
     *
     * @param millisec 毫秒时间戳
     * @param pattern 自定格式
     * @return
     */
    public static String millisec2String(long millisec, String pattern)
    {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(millisec));
    }

    /**
     * 时间戳转时间字符串
     * <p>格式为默认</p>
     *
     * @param millisec 毫秒时间戳
     * @return
     */
    public static String millisec2String(long millisec)
    {
        return millisec2String(millisec, DEFAULT_PATTERN);
    }
}
