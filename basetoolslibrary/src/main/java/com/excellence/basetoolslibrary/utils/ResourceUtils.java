package com.excellence.basetoolslibrary.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.excellence.basetoolslibrary.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.annotation.StringDef;

import static com.excellence.basetoolslibrary.utils.EmptyUtils.isEmpty;
import static com.excellence.basetoolslibrary.utils.EmptyUtils.isNotEmpty;
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
     * 例如：R.drawable.app_name
     * 结果：drawable
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
     * @param entryName 资源名
     * @param type 资源类型名
     * @param packageName 包名
     * @param def 默认资源
     * @return 0表示没有该资源
     */
    public static int getIdentifier(Context context, String entryName, String type, String packageName, int def) {
        int res = def;
        try {
            res = context.getResources().getIdentifier(entryName, type, packageName);
        } catch (Exception e) {
            Log.e(TAG, "getIdentifier: ", e);
        }
        return res == 0 ? def : res;
    }

    /**
     * 获取资源Id
     * @see #getName
     *
     * @param context
     * @param entryName
     * @param type
     * @param def
     * @return
     */
    public static int getIdentifier(Context context, String entryName, String type, int def) {
        return getIdentifier(context, entryName, type, context.getPackageName(), def);
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
    public static List<Integer> getIdentifiers(Context context, String type, String rPackageName,
                                               String prefix) {
        Class desireClass = null;
        try {
            desireClass = Class.forName(rPackageName);
        } catch (Exception e) {
            Log.e(TAG, "getIdentifier: ", e);
        }
        return getIdentifiers(context, type, desireClass, prefix);
    }

    /**
     * 传入R类名com.excellence.iptv.R，遍历读取 R 资源列表，找到指定的资源类型，如drawable
     *
     * @param context
     * @param type
     * @param rPackageClass 因为Lib#R 与 App#R 区别，Lib#R 拿不到App里面的资源，需要App#R
     * @param prefix 资源文件过滤条件，前缀
     * @return
     */
    public static List<Integer> getIdentifiers(Context context, String type, Class rPackageClass,
                                               String prefix) {
        Class desireClass;
        switch (type) {
            case TYPE_NAME_COLOR:
                desireClass = R.color.class;
                break;
            case TYPE_NAME_DIMEN:
                desireClass = R.dimen.class;
                break;
            case TYPE_NAME_TEXT:
                desireClass = R.string.class;
                break;
            case TYPE_NAME_DRAWABLE:
            case TYPE_NAME_MIPMAP:
            default:
                desireClass = R.drawable.class;
                break;
        }
        if (rPackageClass == null) {
            rPackageClass = desireClass;
        }

        List<Integer> resList = new ArrayList<>();

        try {
            Class r = rPackageClass;
            Class[] classes = r.getClasses();
            for (int i = 0; i < classes.length; ++i) {
                /**
                 * com.excellence.R$drawable -> drawable
                 * 过滤出需要的资源类型
                 */
                if (classes[i].getName().split("\\$")[1].equals(type)) {
                    desireClass = classes[i];
                    Log.i(TAG, "getIdentifiers: " + desireClass);
                    break;
                }
            }
            if (classes.length == 0) {
                desireClass = r;
                Log.i(TAG, "getIdentifiers itself: " + desireClass);
            }
        } catch (Exception e) {
            Log.e(TAG, "getIdentifier: ", e);
        }

        Field[] fields = desireClass.getDeclaredFields();

        if (isNotEmpty(prefix)) {
            for (Field field : fields) {
                if (field.getName().startsWith(prefix)) {
                    int resId = getIdentifier(context, field.getName(), type, 0);
                    if (resId != 0) {
                        resList.add(resId);
                    }
                }
            }
        } else {
            for (Field field : fields) {
                /**
                 * field.getName() -> resource entryName
                 */
                int resId = getIdentifier(context, field.getName(), type, 0);
                if (resId != 0) {
                    resList.add(resId);
                }
            }
        }
        return resList;
    }

    /**
     * 指定类中带了资源类型，如com.excellence.R$drawable，直接找到类，然后遍历读取 R 资源列表
     *
     * @param context
     * @param rClassName 因为Lib#R$drawable 与 App#R$drawable 区别，Lib#R$drawable 拿不到App里面的资源，需要App#R$drawable
     * @param prefix 资源文件过滤条件，前缀
     * @return
     */
    public static List<Integer> getIdentifiers(Context context, String rClassName,
                                               String prefix) {
        Class desireClass = null;
        try {
            desireClass = Class.forName(rClassName);
        } catch (Exception e) {
            Log.e(TAG, "getIdentifier: ", e);
        }
        return getIdentifiers(context, desireClass, prefix);
    }

    /**
     * 指定类中带了资源类型，如com.excellence.R$drawable，直接找到类，然后遍历读取 R 资源列表
     *
     * @param context
     * @param rClass 因为Lib#R$drawable 与 App#R$drawable 区别，Lib#R$drawable 拿不到App里面的资源，需要App#R$drawable
     * @param prefix 资源文件过滤条件，前缀
     * @return
     */
    public static List<Integer> getIdentifiers(Context context, Class rClass,
                                               String prefix) {
        Class desireClass = rClass;
        if (desireClass == null) {
            desireClass = R.drawable.class;
        }
        String type = TYPE_NAME_DRAWABLE;

        List<Integer> resList = new ArrayList<>();

        try {
            type = desireClass.getName().split("\\$")[1];
        } catch (Exception e) {
            Log.e(TAG, "getIdentifier: ", e);
        }

        Field[] fields = desireClass.getDeclaredFields();

        if (isNotEmpty(prefix)) {
            for (Field field : fields) {
                if (field.getName().startsWith(prefix)) {
                    int resId = getIdentifier(context, field.getName(), type, 0);
                    if (resId != 0) {
                        resList.add(resId);
                    }
                }
            }
        } else {
            for (Field field : fields) {
                /**
                 * field.getName() -> resource entryName
                 */
                int resId = getIdentifier(context, field.getName(), type, 0);
                if (resId != 0) {
                    resList.add(resId);
                }
            }
        }
        return resList;
    }

    /**
     * 遍历Context对应的 R 资源列表
     *
     * @param context
     * @param type
     * @return
     */
    public static List<Integer> getIdentifiers(Context context, String type) {
        String rPackageName = context.getPackageName() + ".R";
        return getIdentifiers(context, type, rPackageName, null);
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
     * 转换raw为Uri
     *
     * @param context
     * @param rawId
     * @return
     */
    public static Uri readRaw(Context context, @RawRes int rawId) {
        return Uri.parse(String.format(Locale.getDefault(), "%s://%s/%d",
                ContentResolver.SCHEME_ANDROID_RESOURCE, context.getPackageName(), rawId));
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
     * @param entryName target resource entry name -> icon
     * @param packageName package name
     * @param def default resource when target resource is empty
     * @param type resource type name -> drawable
     * @return resource id
     */
    public static Loader getIdentifier(Context context, String entryName, String packageName, int def, @Type String type) {
        Resources skinResources = null;
        int resId = 0;
        try {
            Context skinContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
            skinResources = skinContext.getResources();
            resId = skinResources.getIdentifier(entryName, type, packageName);
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
     * @param entryName target resource entry name
     * @param packageName package name
     * @param def default resource when target resource is empty
     * @param types resource type names
     * @return
     */
    public static Loader getIdentifier(Context context, String entryName, String packageName, int def, @Type String... types) {
        Resources skinResources = null;
        int resId = 0;

        try {
            Context skinContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
            skinResources = skinContext.getResources();
            for (String type : types) {
                resId = skinResources.getIdentifier(entryName, type, packageName);
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
