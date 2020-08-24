package com.excellence.basetoolslibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import static com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty;
import static com.excellence.basetoolslibrary.utils.FileIOUtils.copyFile;
import static com.excellence.basetoolslibrary.utils.FileIOUtils.readFile2Bytes;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/7/20
 *     desc   : 资源相关工具类
 * </pre>
 */

public class ResourceUtils {

    /**
     * 解析资源的全名
     * 例如：R.string.app_name
     * 结果：com.excellence.tooldemo:string/app_name
     *
     * @see #getPackageName
     * @see #getTypeName
     * @see #getEntryName
     *
     * @param context 上下文
     * @param resId 资源Id
     * @return 结果格式：package:type/entry
     */
    public static String getName(@NonNull Context context, @AnyRes int resId) {
        return context.getResources().getResourceName(resId);
    }

    /**
     * 解析资源名
     * 例如：R.string.app_name
     * 结果：app_name
     *
     * @see #getName
     *
     * @param context 上下文
     * @param resId 资源Id
     * @return 资源名
     */
    public static String getEntryName(@NonNull Context context, @AnyRes int resId) {
        return context.getResources().getResourceEntryName(resId);
    }

    /**
     * 解析资源类型名
     * 例如：R.string.app_name
     * 结果：string
     *
     * @see #getName
     *
     * @param context 上下文
     * @param resId 资源Id
     * @return 资源类型名
     */
    public static String getTypeName(@NonNull Context context, @AnyRes int resId) {
        return context.getResources().getResourceTypeName(resId);
    }

    /**
     * 解析资源的包名
     * 例如：R.string.app_name
     * 结果：com.excellence.tooldemo
     *
     * @see #getName
     *
     * @param context 上下文
     * @param resId 资源Id
     * @return 资源所在的包名
     */
    public static String getPackageName(@NonNull Context context, @AnyRes int resId) {
        return context.getResources().getResourcePackageName(resId);
    }

    /**
     * 获取资源Id
     * @see #getName
     *
     * @param context 上下文
     * @param name 资源名
     * @param type 资源类型名
     * @param packageName 包名
     * @param def 默认资源
     * @return 0表示没有该资源
     */
    public static int getIdentifier(Context context, String name, String type, String packageName, int def) {
        int res = context.getResources().getIdentifier(name, type, packageName);
        return res == 0 ? def : res;
    }

    /**
     * 获取资源Id
     * @see #getName
     *
     * @param context
     * @param name
     * @param type
     * @param def
     * @return
     */
    public static int getIdentifier(Context context, String name, String type, int def) {
        return getIdentifier(context, name, type, context.getPackageName(), def);
    }

    /**
     * 拷贝assets文件到指定目录
     *
     * @param context
     * @param srcFilePath 相对路径，相对assets目录，如，"docs/home.html"
     * @param destFilePath 目标目录路径
     * @return
     */
    public static boolean copyFileFromAssets(Context context, String srcFilePath, File destFilePath) {
        try {
            if (isEmpty(context) || isEmpty(srcFilePath)) {
                return false;
            }
            String[] assets = context.getAssets().list(srcFilePath);
            boolean ret = true;
            if (assets.length > 0) {
                for (String asset : assets) {
                    InputStream is = context.getAssets().open(asset);
                    OutputStream os = new FileOutputStream(new File(destFilePath, asset));
                    ret &= copyFile(is, os);
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 拷贝raw资源到指定目录文件
     *
     * @param context
     * @param resId 资源id
     * @param destFilePath 目标文件路径
     * @return
     */
    public static boolean copyFileFromRaw(Context context, int resId, File destFilePath) {
        try {
            if (isEmpty(context)) {
                return false;
            }
            InputStream is = context.getResources().openRawResource(resId);
            OutputStream os = new FileOutputStream(destFilePath);
            return copyFile(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取asset文件转字符串
     *
     * @param context
     * @param fileName
     * @param charset
     * @return
     */
    public static String readAsset(Context context, String fileName, String charset) {
        try {
            InputStream is = context.getAssets().open(fileName);
            byte[] bytes = readFile2Bytes(is);
            if (bytes != null) {
                if (isEmpty(charset)) {
                    return new String(bytes);
                } else {
                    return new String(bytes, charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前系统语言：zh_CN
     *
     * @return
     */
    public static String getLocal() {
        return Locale.getDefault().toString();
    }

    /**
     * 获取当前系统语言：zh
     *
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统语言国家：cn
     *
     * @return
     */
    public static String getCountry() {
        return Locale.getDefault().getCountry();
    }

    /*************** 跨APP进程，读取其他应用里面的资源 ***************/
    private static final String TAG = ResourceUtils.class.getSimpleName();

    /**属性值对应的类型是color*/
    public static final String TYPE_NAME_COLOR = "color";

    /**属性值对应的类型是drawable*/
    public static final String TYPE_NAME_DRAWABLE = "drawable";

    /**属性值对应的类型是mipmap*/
    public static final String TYPE_NAME_MIPMAP = "mipmap";

    /**属性值对应的类型是string**/
    public static final String TYPE_NAME_TEXT = "string";

    /**属性值对应的类型是dimen**/
    public static final String TYPE_NAME_DIMEN = "dimen";

    /**
     * 跨APP进程，读取其他应用里面的资源：读取资源id，匹配一个type
     *
     * R.drawable.icon
     *
     * @param context
     * @param targetName target resource entry name -> icon
     * @param packageName package name
     * @param def default resource when target resource is empty
     * @param type resource type name -> drawable
     * @return resource id
     */
    public static Loader getIdentifier(Context context, String targetName, String packageName, int def, @Type String type) {
        Resources skinResources = null;
        int resId = 0;
        try {
            Context skinContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
            skinResources = skinContext.getResources();
            resId = skinResources.getIdentifier(targetName, type, packageName);
            Log.i(TAG, "getIdentifier: " + resId);
        } catch (Exception e) {
            Log.e(TAG, "getIdentifier: " + e.getMessage());
        }
        if (resId == 0) {
            skinResources = context.getResources();
            resId = def;
        }
        return new Loader(skinResources, resId);
    }

    /**
     * 跨APP进程，读取其他应用里面的资源：读取资源id，匹配多个type，直到找到第一个资源文件
     *
     * @param context
     * @param targetName target resource entry name
     * @param packageName package name
     * @param def default resource when target resource is empty
     * @param types resource type names
     * @return
     */
    public static Loader getIdentifier(Context context, String targetName, String packageName, int def, @Type String... types) {
        Resources skinResources = null;
        int resId = 0;

        try {
            Context skinContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
            skinResources = skinContext.getResources();
            for (String type : types) {
                resId = skinResources.getIdentifier(targetName, type, packageName);
                if (resId != 0) {
                    break;
                }
            }
            Log.i(TAG, "getIdentifier: " + resId);
        } catch (Exception e) {
            Log.e(TAG, "getIdentifier: " + e.getMessage());
        }
        if (resId == 0) {
            skinResources = context.getResources();
            resId = def;
        }
        return new Loader(skinResources, resId);
    }

    public static class Loader {

        /**
         * 跨进度App的资源对象，需要使用它的Resources去加载资源，不能用本应用的Resource，否则无法成功显示
         */
        public Resources resources;
        public int resId;

        public Loader(Resources skinResources, int resId) {
            this.resources = skinResources;
            this.resId = resId;
        }
    }

    @StringDef({TYPE_NAME_COLOR, TYPE_NAME_DRAWABLE, TYPE_NAME_MIPMAP, TYPE_NAME_TEXT, TYPE_NAME_DIMEN})
    public @interface Type {

    }

}
