package com.excellence.basetoolslibrary.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
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
	 * 根据类获取类中所有成员，能访问类中所有的字段，与public、private、protect无关，不能访问从其它类继承来的方法
	 *
	 * @param cls 类
	 * @return
	 */
	public static Field[] getDeclaredFields(Class cls)
	{
		if (cls == null)
		{
			return null;
		}
		return cls.getDeclaredFields();
	}

	/**
	 * 根据对象获取类中所有成员，能访问类中所有的字段，与public、private、protect无关，不能访问从其它类继承来的方法
	 *
	 * @param owner 对象
	 * @return
	 */
	public static Field[] getDeclaredFields(Object owner)
	{
		if (owner == null)
		{
			return null;
		}
		return getDeclaredFields(owner.getClass());
	}

	/**
	 * 根据类名获取类中所有成员，能访问类中所有的字段，与public、private、protect无关，不能访问从其它类继承来的方法
	 *
	 * @param clsName 类名
	 * @return
	 */
	public static Field[] getDeclaredFields(String clsName)
	{
		try
		{
			if (EmptyUtils.isEmpty(clsName))
			{
				return null;
			}
			return getDeclaredFields(Class.forName(clsName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据类获取类中所有的公有成员，只能访问类中声明为公有的字段，私有的字段它无法访问，能访问从其它类继承来的公有方法
	 *
	 * @param cls 类
	 * @return
	 */
	public static Field[] getFields(Class cls)
	{
		if (cls == null)
		{
			return null;
		}
		return cls.getFields();
	}

	/**
	 * 根据对象获取类中所有的公有成员，只能访问类中声明为公有的字段，私有的字段它无法访问，能访问从其它类继承来的公有方法
	 *
	 * @param owner 对象
	 * @return
	 */
	public static Field[] getFields(Object owner)
	{
		if (owner == null)
		{
			return null;
		}
		return getFields(owner.getClass());
	}

	/**
	 * 根据类名获取类中所有的公有成员，只能访问类中声明为公有的字段，私有的字段它无法访问，能访问从其它类继承来的公有方法
	 *
	 * @param clsName 类名
	 * @return
	 */
	public static Field[] getFields(String clsName)
	{
		try
		{
			if (EmptyUtils.isEmpty(clsName))
			{
				return null;
			}
			return getFields(Class.forName(clsName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置类中指定成员变量的值，一般是设置私有成员变量值
	 *
	 * @param owner 类对象
	 * @param fieldName 成员变量名
	 * @param value 值
	 */
	public static void setFieldValue(Object owner, String fieldName, Object value)
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
	 * 获取类中指定成员变量的值，一般是获取私有成员变量值
	 *
	 * @param owner 类对象
	 * @param fieldName 成员变量名
	 * @return 值
	 */
	public static Object getFieldValue(Object owner, String fieldName)
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
	 * 根据类获取类中所有方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
	 *
	 * @param cls 类
	 * @return
	 */
	public static Method[] getDeclaredMethods(Class cls)
	{
		if (cls == null)
		{
			return null;
		}
		return cls.getDeclaredMethods();
	}

	/**
	 * 根据对象获取类中所有方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
	 *
	 * @param owner 对象
	 * @return
	 */
	public static Method[] getDeclaredMethods(Object owner)
	{
		if (owner == null)
		{
			return null;
		}
		return owner.getClass().getDeclaredMethods();
	}

	/**
	 * 根据对象获取类中指定的方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
	 *
	 * @param owner 对象
	 * @param methodName 方法名
	 * @param argsCls 参数类型
	 * @return
	 */
	public static Method getDeclaredMethods(Object owner, String methodName, Class[] argsCls)
	{
		try
		{
			if (owner == null)
			{
				return null;
			}
			return owner.getClass().getDeclaredMethod(methodName, argsCls);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据类获取类中所有的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
	 *
	 * @param cls 类
	 * @return
	 */
	public static Method[] getMetods(Class cls)
	{
		if (cls == null)
		{
			return null;
		}
		return cls.getMethods();
	}

	/**
	 * 根据对象获取类中所有的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
	 *
	 * @param owner 对象
	 * @return
	 */
	public static Method[] getMethods(Object owner)
	{
		if (owner == null)
		{
			return null;
		}
		return owner.getClass().getMethods();
	}

	/**
	 * 根据对象获取类中指定的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
	 *
	 * @param owner 对象
	 * @param methodName 方法名
	 * @param argsCls 参数类型
	 * @return
	 */
	public static Method getMethods(Object owner, String methodName, Class[] argsCls)
	{
		try
		{
			if (owner == null)
			{
				return null;
			}
			return owner.getClass().getMethod(methodName, argsCls);
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过反射调用类中指定的方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
	 *
	 * @param owner 对象
	 * @param methodName 方法名
	 * @param args 参数
	 * @return 方法返回值
	 */
	public static Object invokeDeclaredMethod(Object owner, String methodName, Object[] args)
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
				{
					argsCls[i] = args[i].getClass();
				}
			}
			ret = invokeDeclaredMethod(owner, methodName, args, argsCls);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 通过反射调用类中指定的方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
	 *
	 * @param owner 对象
	 * @param methodName 方法名
	 * @param args 参数
	 * @param argsClass 参数类型
	 * @return 方法返回值
	 */
	public static Object invokeDeclaredMethod(Object owner, String methodName, Object[] args, Class[] argsClass)
	{
		Object ret = null;
		try
		{
			Class<? extends Object> ownerCls = owner.getClass();
			Method method = ownerCls.getDeclaredMethod(methodName, argsClass);
			ret = method.invoke(owner, args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 通过反射调用类中指定的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
	 *
	 * @param owner 类对象
	 * @param methodName 方法名
	 * @param args 参数
	 * @return 方法返回值
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
				{
					argsCls[i] = args[i].getClass();
				}
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
	 * 通过反射调用类中指定的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
	 *
	 * @param owner 类对象
	 * @param methodName 方法名
	 * @param args 参数
	 * @param argsClass 参数类型
	 * @return 方法返回值
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

	/**
	 * 通过类创建带参数的构造函数，返回类对象
	 *
	 * @param cls 类
	 * @param args 参数
	 * @return 类对象
	 */
	public static Object newInstance(Class<? extends Object> cls, Object[] args)
	{
		try
		{
			Class[] argsClass = new Class[args.length];
			for (int i = 0; i < args.length; i++)
			{
				argsClass[i] = args[i].getClass();
			}
			Constructor constructor = cls.getConstructor(argsClass);
			return constructor.newInstance(args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断是否为某个类的实例
	 *
	 * @param cls 类
	 * @param owner 对象
	 * @return {@code true}:是<br>{@code false}:否
	 */
	public static boolean isInstance(Class cls, Object owner)
	{
		return cls.isInstance(owner);
	}

	/**
	 * 获取存在的、指定类型的注解，如果该类型注解不存在，返回null
	 *
	 * @param cls 类对象
	 * @param annotationCls 注解
	 * @return
	 */
	@Nullable
	public static Annotation getAnnotation(Class cls, Class annotationCls)
	{
		return cls.getAnnotation(annotationCls);
	}

	/**
	 * 获取类中存在的所有注解
	 *
	 * @param cls
	 * @return
	 */
	public static Annotation[] getAnnotations(Class cls)
	{
		return cls.getAnnotations();
	}

	/**
	 * 获取存在的、指定类型的注解，不包括继承的注解
	 *
	 * @param cls
	 * @param annotationCls
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.N)
	public static Annotation getDeclaredAnnotation(Class cls, Class annotationCls)
	{
		return cls.getDeclaredAnnotation(annotationCls);
	}

	/**
	 * 获取类中存在的所有注解，不包括继承的注解
	 *
	 * @return
	 */
	public static Annotation[] getDeclaredAnnotations(Class cls)
	{
		return cls.getDeclaredAnnotations();
	}
}
