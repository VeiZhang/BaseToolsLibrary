package com.excellence.basetoolslibrary.utils

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.util.Log
import androidx.annotation.AnyRes
import androidx.annotation.RawRes
import androidx.annotation.StringDef
import com.excellence.basetoolslibrary.R.*
import com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty
import com.excellence.basetoolslibrary.utils.EmptyUtils.isNotEmpty
import com.excellence.basetoolslibrary.utils.FileIOUtils.copyFile
import com.excellence.basetoolslibrary.utils.FileIOUtils.readFile2Bytes
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.*


/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/20
 *     desc   : 资源相关工具类
 * </pre>
 */
object ResourceUtils {

    private val TAG = ResourceUtils::class.java.simpleName

    /**属性值对应的类型是color */
    const val TYPE_NAME_COLOR = "color"

    /**属性值对应的类型是drawable */
    const val TYPE_NAME_DRAWABLE = "drawable"

    /**属性值对应的类型是mipmap */
    const val TYPE_NAME_MIPMAP = "mipmap"

    /**属性值对应的类型是string */
    const val TYPE_NAME_TEXT = "string"

    /**属性值对应的类型是dimen */
    const val TYPE_NAME_DIMEN = "dimen"

    /**
     * 解析资源的全名
     * 例如：R.string.app_name
     * 结果：com.excellence.tooldemo:string/app_name
     *
     * @see getPackageName
     * @see getTypeName
     * @see getEntryName
     *
     * @param context 上下文
     * @param resId 资源Id
     * @return 结果格式：package:type/entry
     */
    @JvmStatic
    fun getName(context: Context, @AnyRes resId: Int): String {
        return context.resources.getResourceName(resId)
    }

    /**
     * 解析资源名
     * 例如：R.string.app_name
     * 结果：app_name
     *
     * @see getName
     *
     * @param context 上下文
     * @param resId 资源Id
     * @return 资源名
     */
    @JvmStatic
    fun getEntryName(context: Context, @AnyRes resId: Int): String {
        return context.resources.getResourceEntryName(resId)
    }

    /**
     * 解析资源类型名
     * 例如：R.drawable.app_name
     * 结果：drawable
     *
     * @see getName
     *
     * @param context 上下文
     * @param resId 资源Id
     * @return 资源类型名
     */
    @JvmStatic
    fun getTypeName(context: Context, @AnyRes resId: Int): String {
        return context.resources.getResourceTypeName(resId)
    }

    /**
     * 解析资源的包名
     * 例如：R.string.app_name
     * 结果：com.excellence.tooldemo
     *
     * @see getName
     *
     * @param context 上下文
     * @param resId 资源Id
     * @return 资源所在的包名
     */
    @JvmStatic
    fun getPackageName(context: Context, @AnyRes resId: Int): String {
        return context.resources.getResourcePackageName(resId)
    }

    /**
     * 获取资源Id
     * @see getName
     *
     * @param context 上下文
     * @param entryName 资源名
     * @param type 资源类型名 [Type]
     * @param packageName 包名
     * @param def 默认资源
     * @return 0表示没有该资源
     */
    @JvmStatic
    fun getIdentifier(context: Context, entryName: String?, type: String?, packageName: String?, def: Int): Int {
        var res = def
        try {
            res = context.resources.getIdentifier(entryName, type, packageName)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "getIdentifier: ", e)
        }
        return if (res == 0) def else res
    }

    /**
     * 获取资源Id
     * @see .getName
     *
     *
     * @param context
     * @param entryName
     * @param type
     * @param def
     * @return
     */
    @JvmStatic
    fun getIdentifier(context: Context, entryName: String?, type: String?, def: Int): Int {
        return getIdentifier(context, entryName, type, context.packageName, def)
    }

    /**
     * 传入R类名com.excellence.iptv.R，遍历读取 R 资源列表，找到指定的资源类型，如drawable
     *
     * @param context
     * @param type
     * @param rPackageName 因为Lib#R 与 App#R 区别，Lib#R 拿不到App里面的资源，需要App#R
     * @param prefix 资源文件过滤条件，前缀
     * @return
     */
    @JvmStatic
    fun getIdentifiers(context: Context, type: String,
                       rPackageName: String?, prefix: String?): List<Int> {
        var desireClass: Class<*>? = null
        try {
            desireClass = Class.forName(rPackageName)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "getIdentifier: ", e)
        }
        return getIdentifiers(context, type, desireClass, prefix)
    }

    /**
     * 传入R类名com.excellence.iptv.R，遍历读取 R 资源列表，找到指定的资源类型，如drawable
     *
     * @param context
     * @param type [Type]
     * @param rPackageClass 因为Lib#R 与 App#R 区别，Lib#R 拿不到App里面的资源，需要App#R
     * @param prefix 资源文件过滤条件，前缀
     * @return
     */
    @JvmStatic
    fun getIdentifiers(context: Context, type: String, rPackageClass: Class<*>?,
                       prefix: String?): List<Int> {
        var rPackageClass = rPackageClass
        var desireClass: Class<*>
        desireClass = when (type) {
            TYPE_NAME_COLOR -> color::class.java

            TYPE_NAME_DIMEN -> dimen::class.java

            TYPE_NAME_TEXT -> string::class.java

            TYPE_NAME_DRAWABLE,
            TYPE_NAME_MIPMAP -> drawable::class.java
            else -> drawable::class.java
        }
        if (rPackageClass == null) {
            rPackageClass = desireClass
        }

        val resList: MutableList<Int> = ArrayList()
        try {
            val r: Class<*> = rPackageClass
            val classes = r.classes
            for (i in classes.indices) {
                /**
                 * com.excellence.R$drawable -> drawable
                 * 过滤出需要的资源类型
                 */
                if (classes[i].name.split("\\$").toTypedArray()[1] == type) {
                    desireClass = classes[i]
                    Log.i(TAG, "getIdentifiers: $desireClass")
                    break
                }
            }

            if (classes.isEmpty()) {
                desireClass = r
                Log.i(TAG, "getIdentifiers itself: $desireClass")
            }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "getIdentifier: ", e)
        }

        val fields = desireClass.declaredFields
        if (isNotEmpty(prefix)) {
            for (field in fields) {
                if (field.name.startsWith(prefix!!)) {
                    val resId = getIdentifier(context, field.name, type, 0)
                    if (resId != 0) {
                        resList.add(resId)
                    }
                }
            }
        } else {
            for (field in fields) {
                /**
                 * field.getName() -> resource entryName
                 */
                val resId = getIdentifier(context, field.name, type, 0)
                if (resId != 0) {
                    resList.add(resId)
                }
            }
        }
        return resList
    }

    /**
     * 指定类中带了资源类型，如com.excellence.R$drawable，直接找到类，然后遍历读取 R 资源列表
     *
     * @param context
     * @param rClassName 因为Lib#R$drawable 与 App#R$drawable 区别，Lib#R$drawable 拿不到App里面的资源，需要App#R$drawable
     * @param prefix 资源文件过滤条件，前缀
     * @return
     */
    @JvmStatic
    fun getIdentifiers(context: Context, rClassName: String?,
                       prefix: String?): List<Int> {
        var desireClass: Class<*>? = null
        try {
            desireClass = Class.forName(rClassName)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "getIdentifier: ", e)
        }
        return getIdentifiers(context, desireClass, prefix)
    }

    /**
     * 指定类中带了资源类型，如com.excellence.R$drawable，直接找到类，然后遍历读取 R 资源列表
     *
     * @param context
     * @param rClass 因为Lib#R$drawable 与 App#R$drawable 区别，Lib#R$drawable 拿不到App里面的资源，需要App#R$drawable
     * @param prefix 资源文件过滤条件，前缀
     * @return
     */
    @JvmStatic
    fun getIdentifiers(context: Context, rClass: Class<*>?,
                       prefix: String?): List<Int> {
        var desireClass = rClass
        if (desireClass == null) {
            desireClass = drawable::class.java
        }
        var type = TYPE_NAME_DRAWABLE
        val resList: MutableList<Int> = ArrayList()
        try {
            type = desireClass.name.split("\\$").toTypedArray()[1]
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "getIdentifier: ", e)
        }

        val fields = desireClass.declaredFields
        if (isNotEmpty(prefix)) {
            for (field in fields) {
                if (field.name.startsWith(prefix!!)) {
                    val resId = getIdentifier(context, field.name, type, 0)
                    if (resId != 0) {
                        resList.add(resId)
                    }
                }
            }
        } else {
            for (field in fields) {
                /**
                 * field.getName() -> resource entryName
                 */
                val resId = getIdentifier(context, field.name, type, 0)
                if (resId != 0) {
                    resList.add(resId)
                }
            }
        }
        return resList
    }

    /**
     * 遍历Context对应的 R 资源列表
     *
     * @param context
     * @param type
     * @return
     */
    @JvmStatic
    fun getIdentifiers(context: Context, type: String): List<Int> {
        val rPackageName = context.packageName + ".R"
        return getIdentifiers(context, type, rPackageName, null)
    }

    /**
     * 拷贝assets文件到指定目录
     *
     * @param context
     * @param srcFilePath 相对路径，相对assets目录，如，"docs/home.html"
     * @param destFilePath 目标目录路径
     * @return
     */
    @JvmStatic
    fun copyFileFromAssets(context: Context, srcFilePath: String?, destFilePath: File?): Boolean {
        try {
            if (isEmpty(context) || isEmpty(srcFilePath)) {
                return false
            }
            val assets = context.assets.list(srcFilePath!!)
            var ret = true
            if (assets!!.isNotEmpty()) {
                for (asset in assets) {
                    val `is` = context.assets.open(asset!!)
                    val os: OutputStream = FileOutputStream(File(destFilePath, asset))
                    ret = ret and copyFile(`is`, os)
                }
            }
            return ret
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 拷贝raw资源到指定目录文件
     *
     * @param context
     * @param resId 资源id
     * @param destFilePath 目标文件路径
     * @return
     */
    @JvmStatic
    fun copyFileFromRaw(context: Context, resId: Int, destFilePath: File?): Boolean {
        try {
            if (isEmpty(context)) {
                return false
            }
            val `is` = context.resources.openRawResource(resId)
            val os: OutputStream = FileOutputStream(destFilePath)
            return copyFile(`is`, os)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 读取asset文件转字符串
     *
     * @param context
     * @param fileName
     * @param charset
     * @return
     */
    @JvmStatic
    fun readAsset(context: Context, fileName: String?, charset: String?): String? {
        try {
            val `is` = context.assets.open(fileName!!)
            val bytes = readFile2Bytes(`is`)
            if (bytes != null) {
                return if (isEmpty(charset)) {
                    String(bytes)
                } else {
                    String(bytes, Charset.forName(charset))
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 转换raw为Uri
     *
     * @param context
     * @param rawId
     * @return
     */
    @JvmStatic
    fun readRaw(context: Context, @RawRes rawId: Int): Uri {
        return Uri.parse(String.format(Locale.getDefault(), "%s://%s/%d",
                ContentResolver.SCHEME_ANDROID_RESOURCE, context.packageName, rawId))
    }

    /**
     * 获取当前系统语言：zh_CN
     *
     * @return
     */
    @JvmStatic
    val local: String
        get() = Locale.getDefault().toString()

    /**
     * 获取当前系统语言：zh
     *
     * @return
     */
    @JvmStatic
    val language: String
        get() = Locale.getDefault().language

    /**
     * 获取当前系统语言国家：cn
     *
     * @return
     */
    @JvmStatic
    val country: String
        get() = Locale.getDefault().country

    /**
     * 跨APP进程，读取其他应用里面的资源：读取资源id，匹配一个type
     *
     * R.drawable.icon
     *
     * @param context
     * @param entryName target resource entry name -> icon
     * @param packageName package name
     * @param def default resource when target resource is empty
     * @param type resource type name -> drawable [Type]
     * @return resource id
     */
    @JvmStatic
    fun getIdentifier(context: Context, entryName: String?, packageName: String?, def: Int, @Type type: String?): Loader {
        var skinResources = context.resources
        var resId = 0
        try {
            val skinContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY)
            skinResources = skinContext.resources
            resId = skinResources.getIdentifier(entryName, type, packageName)
            Log.i(TAG, "getIdentifier: $resId")
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "getIdentifier: " + e.message)
        }
        if (resId == 0) {
            skinResources = context.resources
            resId = def
        }
        return Loader(skinResources, resId)
    }

    /**
     * 跨APP进程，读取其他应用里面的资源：读取资源id，匹配多个type，直到找到第一个资源文件
     *
     * @param context
     * @param entryName target resource entry name
     * @param packageName package name
     * @param def default resource when target resource is empty
     * @param types resource type names [Type]
     * @return
     */
    @JvmStatic
    fun getIdentifier(context: Context, entryName: String?, packageName: String?, def: Int, vararg types: String?): Loader {
        var skinResources = context.resources
        var resId = 0
        try {
            val skinContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY)
            skinResources = skinContext.resources
            for (type in types) {
                resId = skinResources.getIdentifier(entryName, type, packageName)
                if (resId != 0) {
                    break
                }
            }
            Log.i(TAG, "getIdentifier: $resId")
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "getIdentifier: " + e.message)
        }
        if (resId == 0) {
            skinResources = context.resources
            resId = def
        }
        return Loader(skinResources, resId)
    }

    /**
     * 跨进度App的资源对象，需要使用它的Resources去加载资源，不能用本应用的Resource，否则无法成功显示
     */
    class Loader(@field:JvmField var resources: Resources, @field:JvmField var resId: Int)

    @StringDef(TYPE_NAME_COLOR,
            TYPE_NAME_DRAWABLE,
            TYPE_NAME_MIPMAP,
            TYPE_NAME_TEXT,
            TYPE_NAME_DIMEN)
    annotation class Type
}