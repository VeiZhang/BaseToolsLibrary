package com.excellence.basetoolslibrary.utils;

/**
 * Created by ZhangWei on 2017/1/24.
 */

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间相关
 */
public class TimeUtils
{
    /**
     * <pre>
     *                     yyyy-MM-dd 1969-12-31
     *                     yyyy-MM-dd 1970-01-01
     *               yyyy-MM-dd HH:mm 1969-12-31 16:00
     *               yyyy-MM-dd HH:mm 1970-01-01 00:00
     *              yyyy-MM-dd HH:mmZ 1969-12-31 16:00-0800
     *              yyyy-MM-dd HH:mmZ 1970-01-01 00:00+0000
     *       yyyy-MM-dd HH:mm:ss.SSSZ 1969-12-31 16:00:00.000-0800
     *       yyyy-MM-dd HH:mm:ss.SSSZ 1970-01-01 00:00:00.000+0000
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ 1969-12-31T16:00:00.000-0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ 1970-01-01T00:00:00.000+0000
     *     yyyy-MM-dd HH:mm:ss E      2017-02-09 23:20:26 周四  (* E 根据系统语言改变，英文则是Thu)
     *     yyyy-MM-dd HH:mm:ss EEEE   2017-02-09 23:20:26 星期四 (* EEEE 根据系统语言改变，英文则是Thursday)
     * </pre>
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DAY_PATTERN = "yyyy-MM-dd";
    public static final String WEEK_PATTERN = "EEEE";

	private static final String[] CHINESE_ZODIAC = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };

	private final static String[] ZODIAC = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };
    private final static int[] ZODIAC_FLAGS = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };

	public static final int SEC = 1000;
	public static final int MIN = 60 * 1000;
	public static final int HOUR = 60 * 60 * 1000;
	public static final int DAY = 24 * 60 * 60 * 1000;

    public enum TimeUnit{
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }

    /**
     * 时间戳转时间字符串
     * <p>自定时间格式</p>
     *
     * @param millisec 毫秒时间戳
     * @param pattern 自定时间格式
     * @return 时间字符串
     */
    public static String millisec2String(long millisec, String pattern)
    {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(millisec));
    }

    /**
     * 时间戳转时间字符串
     * <p>默认时间格式</p>
     *
     * @param millisec 毫秒时间戳
     * @return 时间字符串
     */
    public static String millisec2String(long millisec)
    {
        return millisec2String(millisec, DEFAULT_PATTERN);
    }

    /**
     * 时间字符串转Date类型
     * <p>自定时间格式</p>
     *
     * @param time 时间字符串
     * @param pattern 自定时间格式
     * @return Date类型
     */
	public static Date string2Date(String time, String pattern)
	{
        return new Date(string2Millisec(time, pattern));
	}

    /**
     * 时间字符串转Date类型
     * <p>默认时间格式</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time)
    {
        return string2Date(time, DEFAULT_PATTERN);
    }

    /**
     * 时间字符串转毫秒时间戳
     * <p>自定时间格式</p>
     *
     * @param time 时间字符串
     * @param pattern 自定时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millisec(String time, String pattern)
    {
        try
        {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 时间字符串转毫秒时间戳
     * <p>默认时间格式</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millisec(String time)
    {
        return string2Millisec(time, DEFAULT_PATTERN);
    }

    /**
     * date转时间字符串
     * <p>自定时间格式</p>
     *
     * @param date date类型
     * @param pattern 自定时间格式
     * @return 时间字符串
     */
    public static String date2String(Date date, String pattern)
    {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    /**
     * date转时间字符串
     * <p>默认时间格式</p>
     *
     * @param date date类型
     * @return 时间字符串
     */
    public static String date2String(Date date)
    {
        return new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault()).format(date);
    }

    /**
     * 获取两个时间差
     *
     * @param millisec0 毫秒时间戳0
     * @param millisec1 毫秒时间戳1
     * @param unit 单位类型
     *        <ul>
     *          <li>{@link TimeUnit.MSEC}: 毫秒</li>
     *          <li>{@link TimeUnit.SEC}: 秒</li>
     *          <li>{@link TimeUnit.MIN}: 分</li>
     *          <li>{@link TimeUnit.HOUR}: 时</li>
     *          <li>{@link TimeUnit.DAY}: 天</li>
     *        </ul>
     * @return 时间差
     */
	public static long getTimeSpan(long millisec0, long millisec1, TimeUnit unit)
	{
		long timeSpan = Math.abs(millisec0 - millisec1);
		switch (unit)
		{
		default:
		case MSEC:
			return timeSpan;

		case SEC:
			return timeSpan / SEC;

		case MIN:
			return timeSpan / MIN;

		case HOUR:
			return timeSpan / HOUR;

		case DAY:
			return timeSpan / DAY;
		}
	}

    /**
     * 获取两个时间差
     * <p>自定时间格式</p>
     *
     * @param time0 字符串时间0
     * @param time1 字符串时间1
     * @param pattern 自定时间格式
     * @param unit 单位类型
     *        <ul>
     *          <li>{@link TimeUnit.MSEC}: 毫秒</li>
     *          <li>{@link TimeUnit.SEC}: 秒</li>
     *          <li>{@link TimeUnit.MIN}: 分</li>
     *          <li>{@link TimeUnit.HOUR}: 时</li>
     *          <li>{@link TimeUnit.DAY}: 天</li>
     *        </ul>
     * @return 时间差
     */
    public static long getTimeSpan(String time0, String time1, String pattern, TimeUnit unit)
    {
        return getTimeSpan(string2Millisec(time0, pattern), string2Millisec(time1, pattern), unit);
    }

    /**
     * 获取两个时间差
     * <p>默认时间格式</p>
     *
     * @param time0 字符串时间0
     * @param time1 字符串时间1
     * @param unit 单位类型
     *        <ul>
     *          <li>{@link TimeUnit.MSEC}: 毫秒</li>
     *          <li>{@link TimeUnit.SEC}: 秒</li>
     *          <li>{@link TimeUnit.MIN}: 分</li>
     *          <li>{@link TimeUnit.HOUR}: 时</li>
     *          <li>{@link TimeUnit.DAY}: 天</li>
     *        </ul>
     * @return 时间差
     */
    public static long getTimeSpan(String time0, String time1, TimeUnit unit)
    {
        return getTimeSpan(string2Millisec(time0, DEFAULT_PATTERN), string2Millisec(time1, DEFAULT_PATTERN), unit);
    }

    /**
     * 获取两个时间差
     *
     * @param date0 Date类型时间0
     * @param date1 Date类型时间1
     * @param unit 单位类型
     *        <ul>
     *          <li>{@link TimeUnit.MSEC}: 毫秒</li>
     *          <li>{@link TimeUnit.SEC}: 秒</li>
     *          <li>{@link TimeUnit.MIN}: 分</li>
     *          <li>{@link TimeUnit.HOUR}: 时</li>
     *          <li>{@link TimeUnit.DAY}: 天</li>
     *        </ul>
     * @return 时间差
     */
    public static long getTimeSpan(Date date0, Date date1, TimeUnit unit)
    {
        return getTimeSpan(date0.getTime(), date1.getTime(), unit);
    }

    /**
     * 获取当前毫秒时间戳
     *
     * @return 毫秒时间戳
     */
    public static long getNowTimeMillis()
    {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前Date时间
     *
     * @return Date类型时间
     */
    public static Date getNowTimeDate()
    {
        return new Date();
    }

    /**
     * 获取当前时间字符串
     * <p>自定时间格式</p>
     *
     * @param pattern 自定时间格式
     * @return 时间字符串
     */
    public static String getNowTimeString(String pattern)
    {
        return millisec2String(getNowTimeMillis(), pattern);
    }

    /**
     * 获取当前时间字符串
     * <p>默认时间格式</p>
     *
     * @return 时间字符串
     */
    public static String getNowTimeString()
    {
        return getNowTimeString(DEFAULT_PATTERN);
    }

    /**
     * 获取某毫秒时间戳与当前时间的差
     *
     * @param millisec 毫秒时间戳
     * @param unit 单位类型
     *        <ul>
     *          <li>{@link TimeUnit.MSEC}: 毫秒</li>
     *          <li>{@link TimeUnit.SEC}: 秒</li>
     *          <li>{@link TimeUnit.MIN}: 分</li>
     *          <li>{@link TimeUnit.HOUR}: 时</li>
     *          <li>{@link TimeUnit.DAY}: 天</li>
     *        </ul>
     * @return 时间差
     */
    public static long getTimeSpanByNow(long millisec, TimeUnit unit)
    {
        return getTimeSpan(getNowTimeMillis(), millisec, unit);
    }

    /**
     * 获取某时间字符串与当前时间的差
     * <p>自定时间格式</p>
     *
     * @param time 时间字符串
     * @param pattern 自定时间格式
     * @param unit 单位类型
     *        <ul>
     *          <li>{@link TimeUnit.MSEC}: 毫秒</li>
     *          <li>{@link TimeUnit.SEC}: 秒</li>
     *          <li>{@link TimeUnit.MIN}: 分</li>
     *          <li>{@link TimeUnit.HOUR}: 时</li>
     *          <li>{@link TimeUnit.DAY}: 天</li>
     *        </ul>
     * @return 时间差
     */
    public static long getTimeSpanByNow(String time, String pattern, TimeUnit unit)
    {
        return getTimeSpan(getNowTimeString(pattern), time, pattern, unit);
    }

    /**
     * 获取某时间字符串与当前时间的差
     * <p>默认时间格式</p>
     *
     * @param time 时间字符串
     * @param unit 单位类型
     *        <ul>
     *          <li>{@link TimeUnit.MSEC}: 毫秒</li>
     *          <li>{@link TimeUnit.SEC}: 秒</li>
     *          <li>{@link TimeUnit.MIN}: 分</li>
     *          <li>{@link TimeUnit.HOUR}: 时</li>
     *          <li>{@link TimeUnit.DAY}: 天</li>
     *        </ul>
     * @return 时间差
     */
    public static long getTimeSpanByNow(String time, TimeUnit unit)
    {
        return getTimeSpanByNow(time, DEFAULT_PATTERN, unit);
    }

    /**
     * 获取某Date时间与当前时间的差
     *
     * @param date Date类型
     * @param unit 单位类型
     *        <ul>
     *          <li>{@link TimeUnit.MSEC}: 毫秒</li>
     *          <li>{@link TimeUnit.SEC}: 秒</li>
     *          <li>{@link TimeUnit.MIN}: 分</li>
     *          <li>{@link TimeUnit.HOUR}: 时</li>
     *          <li>{@link TimeUnit.DAY}: 天</li>
     *        </ul>
     * @return 时间差
     */
    public static long getTimeSpanByNow(Date date, TimeUnit unit)
    {
        return getTimeSpanByNow(date.getTime(), unit);
    }

    /**
     * 判断毫秒时间戳是否是同一天
     *
     * @param millisec0 毫秒时间戳0
     * @param millisec1 毫秒时间戳1
     * @return
     */
    public static boolean isSameDay(long millisec0, long millisec1)
    {
        if (millisec2String(millisec0, DAY_PATTERN).equals(millisec2String(millisec1, DAY_PATTERN)))
            return true;
        return false;
    }

    /**
     * 判断毫秒时间戳是否是今天
     *
     * @param millisec 时间戳
     * @return
     */
    public static boolean isToday(long millisec)
    {
        return DateUtils.isToday(millisec);
    }

    /**
     * 判断是否是闰年
     *
     * @param year 年份
     * @return
     */
    public static boolean isLeapYear(int year)
    {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 判断Date是否是闰年
     *
     * @param date Date类型
     * @return
     */
    public static boolean isLeapYear(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return isLeapYear(calendar.get(Calendar.YEAR));
    }

    /**
     * 判断毫秒时间戳是否是闰年
     *
     * @param millisec 毫秒时间戳
     * @return
     */
    public static boolean isLeapYear(long millisec)
    {
        return isLeapYear(new Date(millisec));
    }

    /**
     * 判断字符串时间是否是闰年
     * <p>自定时间格式</p>
     *
     * @param time 字符串时间
     * @param pattern 自定时间格式
     * @return
     */
    public static boolean isLeapYear(String time, String pattern)
    {
        return isLeapYear(string2Date(time, pattern));
    }

    /**
     * 判断字符串时间是否是闰年
     * <p>默认时间格式</p>
     *
     * @param time 字符串时间
     * @return
     */
    public static boolean isLeapYear(String time)
    {
        return isLeapYear(time, DEFAULT_PATTERN);
    }

    /**
     * 根据Date获取星期
     *
     * @param date Date类型
     * @return 星期字符串
     */
    public static String getWeek(Date date)
    {
        return new SimpleDateFormat(WEEK_PATTERN, Locale.getDefault()).format(date);
    }

    /**
     * 根据毫秒时间戳获取星期
     *
     * @param millisec 毫秒时间戳
     * @return 星期字符串
     */
    public static String getWeek(long millisec)
    {
        return getWeek(new Date(millisec));
    }

    /**
     * 根据时间字符串获取星期
     * <p>自定时间格式</p>
     *
     * @param time 时间字符串
     * @param pattern 自定时间格式
     * @return 星期字符串
     */
    public static String getWeek(String time, String pattern)
    {
        return getWeek(string2Date(time, pattern));
    }

    /**
     * 根据时间字符串获取星期
     * <p>默认时间格式</p>
     *
     * @param time 时间字符串
     * @return 星期字符串
     */
    public static String getWeek(String time)
    {
        return getWeek(time, DEFAULT_PATTERN);
    }

    /**
     * 根据Date获取月份中第几周
     *
     * @param date Date类型
     * @return 第几周
     */
    public static int getWeekOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 根据毫秒时间戳获取月份中第几周
     *
     * @param millisec 毫秒时间戳
     * @return 第几周
     */
    public static int getWeekOfMonth(long millisec)
    {
        return getWeekOfMonth(new Date(millisec));
    }

    /**
     * 根据字符串时间获取月份中的第几周
     * <p>自定时间格式</p>
     *
     * @param time 时间字符串
     * @param pattern 自定时间格式
     * @return 第几周
     */
    public static int getWeekOfMonth(String time, String pattern)
    {
        return getWeekOfMonth(string2Date(time, pattern));
    }

    /**
     * 根据字符串时间获取月份中的第几周
     * <p>默认时间格式</p>
     *
     * @param time 时间字符串
     * @return 第几周
     */
    public static int getWeekOfMonth(String time)
    {
        return getWeekOfMonth(time, DEFAULT_PATTERN);
    }

    /**
     * 根据Date获取年份中的第几周
     *
     * @param date Date类型
     * @return 第几周
     */
    public static int getWeekOfYear(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取生肖
     *
     * @param date Date类型
     * @return 生肖
     */
    public static String getChineseZodiac(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return CHINESE_ZODIAC[calendar.get(Calendar.YEAR) % CHINESE_ZODIAC.length];
    }

    /**
     * 获取星座
     *
     * @param month 月
     * @param day 日
     * @return 星座
     */
	public static String getZodiac(int month, int day)
	{
		return ZODIAC[day < ZODIAC_FLAGS[month - 1] ? month - 1 : month];
	}

    /**
     * 获取星座
     * 注意:0对应一月 11对应十二月 {@link Calendar.MONTH}
     *
     * @param date Data类型
     * @return 星座
     */
    public static String getZodiac(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }


}
