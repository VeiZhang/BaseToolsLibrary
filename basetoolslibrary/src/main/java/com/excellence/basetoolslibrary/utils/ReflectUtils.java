package com.excellence.basetoolslibrary.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/20
 *     desc   : 反射相关工具类
 * </pre>
 */

public class ReflectUtils
{

	/**
	 * 根据类获取类中所有成员，包括private成员
	 *
	 * @param cls 类
	 * @return
	 */
	public static Field[] getDeclaredFields(Class cls)
	{
		if (cls == null)
			return null;
		return cls.getDeclaredFields();
	}

	/**
	 * 根据对象获取类中所有成员，包括private成员
	 *
	 * @param owner 对象
	 * @return
	 */
	public static Field[] getDeclaredFields(Object owner)
	{
		if (owner == null)
			return null;
		return getDeclaredFields(owner.getClass());
	}

	/**
	 * 根据类名获取类中所有成员，包括private成员
	 *
	 * @param clsName 类名
	 * @return
	 */
	public static Field[] getDeclaredFields(String clsName)
	{
		try
		{
			if (EmptyUtils.isEmpty(clsName))
				return null;
			return getDeclaredFields(Class.forName(clsName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据类获取类中所有的公有成员，即public成员
	 *
	 * @param cls 类
	 * @return
	 */
	public static Field[] getFields(Class cls)
	{
		if (cls == null)
			return null;
		return cls.getFields();
	}

	/**
	 * 根据对象获取类中所有的公有成员，即public成员
	 *
	 * @param owner 对象
	 * @return
	 */
	public static Field[] getFields(Object owner)
	{
		if (owner == null)
			return null;
		return getFields(owner.getClass());
	}

	/**
	 * 根据类名获取类中所有的公有成员，即public成员
	 *
	 * @param clsName 类名
	 * @return
	 */
	public static Field[] getFields(String clsName)
	{
		try
		{
			if (EmptyUtils.isEmpty(clsName))
				return null;
			return getFields(Class.forName(clsName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置私有成员的值
	 *
	 * @param owner 类对象
	 * @param fieldName 私有成员名
	 * @param value 值
	 */
	public static void setField(Object owner, String fieldName, Object value)
	{
		try
		{
			Class cls = owner.getClass();
			Field field = cls.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(owner, value);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取私有成员的值
	 *
	 * @param owner 类对象
	 * @param fieldName 私有成员名
	 * @return 值
	 */
	public static Object getField(Object owner, String fieldName)
	{
		try
		{
			Class cls = owner.getClass();
			Field field = cls.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(owner);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过反射调用私有方法
	 *
	 * @param owner 类对象
	 * @param methodName 方法名
	 * @param args 参数
	 * @return
	 */
	public static Object invokeMethod(Object owner, String methodName, Object[] args)
	{
		Object ret = null;
		try
		{
			Class[] argsCls = new Class[args.length];
			for (int i = 0; i < args.length; i++)
			{
				String clsName = args[i].getClass().getName();
				if (clsName.equals(Integer.class.getName()))
				{
					argsCls[i] = int.class;
				}
				else if (clsName.equals(Float.class.getName()))
				{
					argsCls[i] = float.class;
				}
				else if (clsName.startsWith("android.view.SurfaceView"))
				{
					argsCls[i] = android.view.SurfaceHolder.class;
				}
				else
					argsCls[i] = args[i].getClass();
			}

			ret = invokeMethod(owner, methodName, args, argsCls);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 通过反射调用私有方法
	 *
	 * @param owner 类对象
	 * @param methodName 方法名
	 * @param args 参数
	 * @param argsClass 参数类型
	 * @return
	 */
	public static Object invokeMethod(Object owner, String methodName, Object[] args, Class[] argsClass)
	{
		Object ret = null;
		try
		{
			Class<? extends Object> ownerClass = owner.getClass();
			Method method = ownerClass.getMethod(methodName, argsClass);
			ret = method.invoke(owner, args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}
}
