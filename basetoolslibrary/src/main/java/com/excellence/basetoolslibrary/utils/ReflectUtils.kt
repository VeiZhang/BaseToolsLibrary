package com.excellence.basetoolslibrary.utils

import android.os.Build
import android.view.SurfaceHolder
import com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * <pre>
 * author : VeiZhang
 * blog   : http://tiimor.cn
 * time   : 2017/7/20
 * desc   : 反射相关工具类
 * </pre>
 */
object ReflectUtils {

    /**
     * 根据类获取类中所有成员，能访问类中所有的字段，与public、private、protect无关，不能访问从其它类继承来的方法
     *
     * @param cls 类
     * @return
     */
    @JvmStatic
    fun getDeclaredFields(cls: Class<*>): Array<Field>? {
        return cls.declaredFields
    }

    /**
     * 根据对象获取类中所有成员，能访问类中所有的字段，与public、private、protect无关，不能访问从其它类继承来的方法
     *
     * @param owner 对象
     * @return
     */
    @JvmStatic
    fun getDeclaredFields(owner: Any?): Array<Field>? {
        return if (owner == null) {
            null
        } else getDeclaredFields(owner.javaClass)
    }

    /**
     * 根据类名获取类中所有成员，能访问类中所有的字段，与public、private、protect无关，不能访问从其它类继承来的方法
     *
     * @param clsName 类名
     * @return
     */
    @JvmStatic
    fun getDeclaredFields(clsName: String?): Array<Field>? {
        try {
            return if (isEmpty(clsName)) {
                null
            } else getDeclaredFields(Class.forName(clsName))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 根据类获取类中所有的公有成员，只能访问类中声明为公有的字段，私有的字段它无法访问，能访问从其它类继承来的公有方法
     *
     * @param cls 类
     * @return
     */
    @JvmStatic
    fun getFields(cls: Class<*>?): Array<Field>? {
        return cls?.fields
    }

    /**
     * 根据对象获取类中所有的公有成员，只能访问类中声明为公有的字段，私有的字段它无法访问，能访问从其它类继承来的公有方法
     *
     * @param owner 对象
     * @return
     */
    @JvmStatic
    fun getFields(owner: Any?): Array<Field>? {
        return if (owner == null) {
            null
        } else getFields(owner.javaClass)
    }

    /**
     * 根据类名获取类中所有的公有成员，只能访问类中声明为公有的字段，私有的字段它无法访问，能访问从其它类继承来的公有方法
     *
     * @param clsName 类名
     * @return
     */
    @JvmStatic
    fun getFields(clsName: String?): Array<Field>? {
        try {
            return if (isEmpty(clsName)) {
                null
            } else getFields(Class.forName(clsName))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 设置类中指定成员变量的值，一般是设置私有成员变量值
     *
     * @param owner 类对象
     * @param fieldName 成员变量名
     * @param value 值
     */
    @JvmStatic
    fun setFieldValue(owner: Any, fieldName: String?, value: Any?) {
        try {
            val cls: Class<*> = owner.javaClass
            val field = cls.getDeclaredField(fieldName)
            field.isAccessible = true
            field[owner] = value
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取类中指定成员变量的值，一般是获取私有成员变量值
     *
     * @param owner 类对象
     * @param fieldName 成员变量名
     * @return 值
     */
    @JvmStatic
    fun getFieldValue(owner: Any, fieldName: String?): Any? {
        try {
            val cls: Class<*> = owner.javaClass
            val field = cls.getDeclaredField(fieldName)
            field.isAccessible = true
            return field[owner]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 根据类获取类中所有方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
     *
     * @param cls 类
     * @return
     */
    @JvmStatic
    fun getDeclaredMethods(cls: Class<*>?): Array<Method>? {
        return cls?.declaredMethods
    }

    /**
     * 根据对象获取类中所有方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
     *
     * @param owner 对象
     * @return
     */
    @JvmStatic
    fun getDeclaredMethods(owner: Any?): Array<Method>? {
        return owner?.javaClass?.declaredMethods
    }

    /**
     * 根据对象获取类中指定的方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
     *
     * @param owner 对象
     * @param methodName 方法名
     * @param argsCls 参数类型
     * @return
     */
    @JvmStatic
    fun getDeclaredMethods(owner: Any?, methodName: String?, argsCls: Array<Class<*>?>): Method? {
        try {
            return owner?.javaClass?.getDeclaredMethod(methodName, *argsCls)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 根据类获取类中所有的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
     *
     * @param cls 类
     * @return
     */
    @JvmStatic
    fun getMetods(cls: Class<*>?): Array<Method>? {
        return cls?.methods
    }

    /**
     * 根据对象获取类中所有的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
     *
     * @param owner 对象
     * @return
     */
    @JvmStatic
    fun getMethods(owner: Any?): Array<Method>? {
        return owner?.javaClass?.methods
    }

    /**
     * 根据对象获取类中指定的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
     *
     * @param owner 对象
     * @param methodName 方法名
     * @param argsCls 参数类型
     * @return
     */
    @JvmStatic
    fun getMethods(owner: Any?, methodName: String?, argsCls: Array<Class<*>?>): Method? {
        try {
            return owner?.javaClass?.getMethod(methodName, *argsCls)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 通过反射调用类中指定的方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
     *
     * @param owner 对象
     * @param methodName 方法名
     * @param args 参数
     * @return 方法返回值
     */
    @JvmStatic
    fun invokeDeclaredMethod(owner: Any, methodName: String?, args: Array<Any>): Any? {
        var ret: Any? = null
        try {
            val argsCls = arrayOfNulls<Class<*>?>(args.size)
            for (i in args.indices) {
                val clsName = args[i].javaClass.name
                when {
                    clsName == Int::class.java.name -> {
                        argsCls[i] = Int::class.javaPrimitiveType
                    }
                    clsName == Float::class.java.name -> {
                        argsCls[i] = Float::class.javaPrimitiveType
                    }
                    clsName.startsWith("android.view.SurfaceView") -> {
                        argsCls[i] = SurfaceHolder::class.java
                    }
                    else -> {
                        argsCls[i] = args[i].javaClass
                    }
                }
            }
            ret = invokeDeclaredMethod(owner, methodName, args, argsCls)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
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
    @JvmStatic
    fun invokeDeclaredMethod(owner: Any, methodName: String?, args: Array<Any>, argsClass: Array<Class<*>?>): Any? {
        var ret: Any? = null
        try {
            val ownerCls: Class<*> = owner.javaClass
            val method = ownerCls.getDeclaredMethod(methodName, *argsClass)
            ret = method.invoke(owner, *args)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
    }

    /**
     * 通过反射调用类中指定的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
     *
     * @param owner 类对象
     * @param methodName 方法名
     * @param args 参数
     * @return 方法返回值
     */
    @JvmStatic
    fun invokeMethod(owner: Any, methodName: String?, args: Array<Any>): Any? {
        var ret: Any? = null
        try {
            val argsCls = arrayOfNulls<Class<*>?>(args.size)
            for (i in args.indices) {
                val clsName = args[i].javaClass.name
                when {
                    clsName == Int::class.java.name -> {
                        argsCls[i] = Int::class.javaPrimitiveType
                    }
                    clsName == Float::class.java.name -> {
                        argsCls[i] = Float::class.javaPrimitiveType
                    }
                    clsName.startsWith("android.view.SurfaceView") -> {
                        argsCls[i] = SurfaceHolder::class.java
                    }
                    else -> {
                        argsCls[i] = args[i].javaClass
                    }
                }
            }
            ret = invokeMethod(owner, methodName, args, argsCls)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
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
    @JvmStatic
    fun invokeMethod(owner: Any, methodName: String?, args: Array<Any>, argsClass: Array<Class<*>?>): Any? {
        var ret: Any? = null
        try {
            val ownerClass: Class<*> = owner.javaClass
            val method = ownerClass.getMethod(methodName, *argsClass)
            ret = method.invoke(owner, *args)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
    }

    /**
     * 通过类创建带参数的构造函数，返回类对象
     *
     * @param cls 类
     * @param args 参数
     * @return 类对象
     */
    @JvmStatic
    fun newInstance(cls: Class<*>, args: Array<Any>): Any? {
        try {
            val argsClass: Array<Class<*>?> = arrayOfNulls(args.size)
            for (i in args.indices) {
                argsClass[i] = args[i].javaClass
            }
            val constructor = cls.getConstructor(*argsClass)
            return constructor.newInstance(*args)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 判断是否为某个类的实例
     *
     * @param cls 类
     * @param owner 对象
     * @return `true`:是<br>`false`:否
     */
    @JvmStatic
    fun isInstance(cls: Class<*>, owner: Any?): Boolean {
        return cls.isInstance(owner)
    }

    /**
     * 获取存在的、指定类型的注解，如果该类型注解不存在，返回null
     *
     * @param cls 类对象
     * @param annotationCls 注解
     * @return
     */
    @JvmStatic
    fun getAnnotation(cls: Class<*>, annotationCls: Class<Annotation>): Annotation? {
        return cls.getAnnotation(annotationCls)
    }

    /**
     * 获取类中存在的所有注解
     *
     * @param cls
     * @return
     */
    @JvmStatic
    fun getAnnotations(cls: Class<*>): Array<Annotation> {
        return cls.annotations
    }

    /**
     * 获取存在的、指定类型的注解，不包括继承的注解
     *
     * @param cls
     * @param annotationCls
     * @return
     */
    @JvmStatic
    fun getDeclaredAnnotation(cls: Class<*>, annotationCls: Class<Annotation>): Annotation? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cls.getDeclaredAnnotation(annotationCls)
        } else {
            null
        }
    }

    /**
     * 获取类中存在的所有注解，不包括继承的注解
     *
     * @return
     */
    @JvmStatic
    fun getDeclaredAnnotations(cls: Class<*>): Array<Annotation> {
        return cls.declaredAnnotations
    }
}