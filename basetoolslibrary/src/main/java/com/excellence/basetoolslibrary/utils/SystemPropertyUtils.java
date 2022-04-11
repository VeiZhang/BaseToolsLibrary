package com.excellence.basetoolslibrary.utils;

import java.lang.reflect.Method;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/9/10
 *     desc   : 系统属性相关
 * </pre>
 */
public class SystemPropertyUtils {

    /**
     * 获取系统属性值
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        String ret = null;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method mthd = clazz.getMethod("get", new Class[]{String.class});
            mthd.setAccessible(true);
            Object obj = mthd.invoke(clazz, new Object[]{key});
            if (obj instanceof String) {
                ret = (String) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取系统属性值
     *
     * @param key
     * @param def
     * @return
     */
    public static String get(String key, String def) {
        String ret = def;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method mthd = clazz.getMethod("get", new Class[]{String.class, String.class});
            mthd.setAccessible(true);
            Object obj = mthd.invoke(clazz, new Object[]{key, def});
            if (obj instanceof String) {
                ret = (String) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取系统属性值
     *
     * @param key
     * @param def
     * @return
     */
    public static boolean getBoolean(String key, boolean def) {
        boolean ret = def;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method mthd = clazz.getMethod("getBoolean", new Class[]{String.class, boolean.class});
            mthd.setAccessible(true);
            Object obj = mthd.invoke(clazz, new Object[]{key, def});
            if (obj instanceof Boolean) {
                ret = (Boolean) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取系统属性值
     *
     * @param key
     * @param def
     * @return
     */
    public static int getInt(String key, int def) {
        int ret = def;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method mthd = clazz.getMethod("getInt", new Class[]{String.class, int.class});
            mthd.setAccessible(true);
            Object obj = mthd.invoke(clazz, new Object[]{key, def});
            if (obj instanceof Integer) {
                ret = (Integer) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取系统属性值
     *
     * @param key
     * @param def
     * @return
     */
    public static long getLong(String key, long def) {
        long ret = def;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method mthd = clazz.getMethod("getLong", new Class[]{String.class, long.class});
            mthd.setAccessible(true);
            Object obj = mthd.invoke(clazz, new Object[]{key, def});
            if (obj instanceof Long) {
                ret = (Long) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 设置系统属性值
     *
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method mthd = clazz.getMethod("set", new Class[]{String.class, String.class});
            mthd.setAccessible(true);
            mthd.invoke(clazz, new Object[]{key, value});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /************ 系统内核kernel信息读取 ************/

    /**
     * Linux version 3.14.29 (eric@bogon) (gcc version 4.9.2 20140904 (prerelease) (cro
     * sstool-NG linaro-1.13.1-4.9-2014.09 - Linaro GCC 4.9-2014.09) ) #11 SMP PREEMPT
     * Tue Jul 16 12:15:26 CST 2019
     *
     * 解析为 3.14.29
     *
     * @return
     */
    public static String getLinuxKernelVersion() {
        try {
            ShellUtils.CommandResult result = ShellUtils.execProcessBuilderCommand("cat", "/proc/version");
            String kernelInfo = result.getResultString();
            if (EmptyUtils.isNotEmpty(kernelInfo)) {
                return RegexUtils.getMatch("version\\s(.*?(?=\\s))", kernelInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
