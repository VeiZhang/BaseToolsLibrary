# Android通用适配器和常用的工具类

[![Bintray][icon_Bintray]][Bintray]
[![GitHub issues][icon_issues]][issues]
[![GitHub forks][icon_forks]][forks]
[![GitHub stars][icon_stars]][stars]

<!-- you should configure jcenter repository-->
## 导入Android Studio
添加jCenter远程依赖到module里的build.gradle：
```
  dependencies {
    compile 'com.excellence:basetools:1.1.0'
    // 或者直接使用最新版本
    // compile 'com.excellence:basetools:+'
  }
```
或者直接添加本地Library依赖
```
    compile project(':basetoolslibrary')
```

## 1.CommonAdapter

#### gridview，listview的通用适配器

示例：[GridAdapterActivity][GridAdapterActivity]


```使用CommonAdapter
    创建adapter类继承CommonAdapter

    private class AppGridAdapter extends CommonAdapter<ResolveInfo>
    {
        public AppGridAdapter(Context context, List<ResolveInfo> datas, int layoutId)
        {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder viewHolder, ResolveInfo item, int position)
        {
            ImageView iconView = viewHolder.getView(android.R.id.icon);
            iconView.setImageDrawable(item.loadIcon(mPackageManager));
            viewHolder.setText(android.R.id.text1, item.loadLabel(mPackageManager).toString());
        }
    }
```


```使用ViewHolder
    ViewHolder辅助方法

    public <T extends View> T getView(int viewId); 用于获取Item内的子控件，参数为控件的id
    public ViewHolder setText(int viewId, int strId); 用于设置文本，参数控件id、字符串id
    public ViewHolder setBackgroundResource(int viewId, int resId); 用于设置背景图片，参数控件id、图片id
    public ViewHolder setImageResource(int viewId, int resId); 用于设置ImageView图片资源，参数控件id、图片id
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener); 用于点击事件监听
    *
    *
    *
    可拓展其他方法
```


```刷新
    刷新适配器
    adapter.notifyNewData(data);
```


## 2.BaseRecyclerAdapter

#### RecyclerView的通用适配器

示例：[RecyclerAdapterActivity][RecyclerAdapterActivity]

```使用CommonAdapter
    创建adapter类继承BaseRecyclerAdapter

    private class AppRecyclerAdapter extends BaseRecyclerAdapter<ResolveInfo>
    {
        private PackageManager mPackageManager = null;

        public AppRecyclerAdapter(Context context, List<ResolveInfo> datas, int layoutId)
        {
            super(context, datas, layoutId);
            mPackageManager = context.getPackageManager();
        }

        @Override
        public void convert(RecyclerViewHolder viewHolder, ResolveInfo item, int position)
        {
            viewHolder.setText(android.R.id.text1, item.loadLabel(mPackageManager));
            viewHolder.setImageDrawable(android.R.id.icon, item.loadIcon(mPackageManager));
        }

    }
```


```使用RecyclerViewHolder
    RecyclerViewHolder辅助方法类同ViewHolder辅助方法
    *
    *
    *
```


## 3.Utils

##### 权限
```
<uses-permission android:name="android.permission.GET_TASKS"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

#### 常用的方法

> - **Activity相关→[ActivityUtils.java][ActivityUtils]**
```
startAnotherActivity   : Activity跳转
setActivityWindowAlpha : 设置Activity窗口透明值
isActivityTopStack     : 判断Activity是否在栈顶
getLauncherActivity    : 获取某应用入口Activity
```

> - **应用相关→[AppUtils.java][AppUtils]**
```
getInstalledApps       : 获取安装的全部应用
getSystemInstalledApps : 获取安装的系统应用
getUserInstalledApps   : 获取安装的第三方应用
getPermissionList      : 获取某应用的所有权限
checkPermission        : 检测某应用是否有某权限
getAppVersionName      : 获取当前应用版本名
getAppVersionCode      : 获取当前应用版本号
getAppSize             : 获取当前应用大小
getAppTime             : 获取当前应用安装时间
getAppPath             : 获取当前应用路径
getAPKFileSignature    : 获取apk文件的签名
getPackageSignature    : 获取某安装应用的签名
isAppInstalled         : 判断应用是否安装
isAppDebug             : 判断当前应用是否是Debug版本
```

> - **进制相关→[ConvertUtils.java][ConvertUtils]**
```
bytesToHexString : bytes转16进制
```

> - **存储相关→[DBUtils.java][DBUtils]**
```
setStringSharedPreferences  : 存储String
getStringSharedPreferences  : 读取String
setBooleanSharedPreferences : 存储Boolean
getBooleanSharedPreferences : 读取Boolean
```

> - **分辨率相关→[DensityUtils.java][DensityUtils]**
```
getDensity      : 获取当前屏幕分辨率
getScaleDensity : 获取当前文字分辨率
dp2px           : dp转px
px2dp           : px转dp
sp2px           : sp转px
px2sp           : px转sp
```

> - **判断空相关→[EmptyUtils.java][EmptyUtils]**
```
isEmpty    : 判断对象是否为空
isNotEmpty : 判断对象是否非空
```

> - **文件相关→[FileUtils.java][FileUtils]**
```
createNewFile      : 创建文件
deleteFile         : 删除文件
mkDir              : 创建目录
deleteDir          : 删除目录
deletePostfixFiles : 删除目录下的某后缀文件
formatFileSize     : 格式化文件大小
getFileOrDirSize   : 获取目录、文件大小
getFileSize        : 获取文件大小
getDirSize         : 获取目录大小
chmod              : 修改目录、文件权限
isFileExists       : 判断文件或目录是否存在
```

> - **Handler相关→[HandlerUtils.java][HandlerUtils]**
```
HandlerHolder: 使用必读
```

> - **Image相关→[ImageUtils.java][ImageUtils]**
```
drawable2Bitmap : drawable转bitmap
bitmap2Drawable : bitmap转drawable
view2Bitmap     : view转Bitmap
```

> - **网络相关→[NetworkUtils.java][NetworkUtils]**
```
checkNetState : 检测网络连接
```

> - **正则表达式相关→[RegexUtils.java][RegexUtils]**
```
isMobileSimple  : 验证手机号（简单）
isMobileExact   : 验证手机号（精确）
isTel           : 验证电话号码
isIDCard15      : 验证身份证号码15位
isIDCard18      : 验证身份证号码18位
isEmail         : 验证邮箱
isURL           : 验证URL
isZh            : 验证汉字
isUsername      : 验证用户名
isDate          : 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
isIP            : 验证IP地址
isMatch         : 判断是否匹配正则
getMatches      : 获取正则匹配的部分
getSplits       : 获取正则匹配分组
getReplaceFirst : 替换正则匹配的第一部分
getReplaceAll   : 替换所有正则匹配的部分
```

> - **命令相关→[ShellUtils.java][ShellUtils]**
```
execProceeBuilderCommand : 执行命令
execRuntimeCommand       : 执行命令
```

> - **SpannableString相关→[SpannableStringUtils.java][SpannableStringUtils]**
```
```

> - **StringUtils相关→[StringUtils.java][StringUtils]**
```
isEmpty : 判断字符串是否为空
```

> - **时间相关→[TimeUtils.java][TimeUtils]**
```
millisec2String  : 时间戳转时间字符串
string2Date      : 时间字符串转Date类型
string2Millisec  : 时间字符串转毫秒时间戳
date2String      : Date转时间字符串
getTimeSpan      : 获取两个时间差
getNowTimeMillis : 获取当前毫秒时间戳
getNowTimeDate   : 获取当前Date时间
getNowTimeString : 获取当前时间字符串
getTimeSpanByNow : 获取某时间与当前时间的差
isSameDay        : 判断时间是否是同一天
isToday          : 判断时间是否是今天
isLeapYear       : 判断是否是闰年
getWeek          : 获取星期几
getWeekOfMonth   : 获取月份中第几周
getWeekOfYear    : 获取年份中的第几周
getChineseZodiac : 获取生肖
getZodiac        : 获取星座
```

<br><br>

|            版本          |                              描述                               |
|------------------------- | -------------------------------------------------------------- |
| [1.1.0][BaseToolsV1.1.0] | Utils增加一些常用的工具类:应用、数据库、分辨率、文件、正则表达式、命令、时间等 |
| [1.0.0][BaseToolsV1.0.0] | 创建ListView、GridView、RecyclerView的通用适配器，一些辅助方法 |

#### 参考
> - [张鸿洋][ZhangHongYang]
> - [布兰柯基][Blankj]


<!-- 引用网站链接 -->
[Bintray]:https://bintray.com/veizhang/maven/basetools "Bintray"
[issues]:https://github.com/VeiZhang/BaseToolsLibrary/issues
[forks]:https://github.com/VeiZhang/BaseToolsLibrary/network/members
[stars]:https://github.com/VeiZhang/BaseToolsLibrary/stargazers

<!-- 图片链接 -->
[icon_Bintray]:https://img.shields.io/badge/Bintray-v1.1.0-brightgreen.svg
[icon_issues]:https://img.shields.io/github/issues/VeiZhang/BaseToolsLibrary.svg
[icon_forks]:https://img.shields.io/github/forks/VeiZhang/BaseToolsLibrary.svg?style=social
[icon_stars]:https://img.shields.io/github/stars/VeiZhang/BaseToolsLibrary.svg?style=social

<!-- 版本 -->
[BaseToolsV1.1.0]:https://bintray.com/veizhang/maven/basetools/1.1.0
[BaseToolsV1.0.0]:https://bintray.com/veizhang/maven/basetools/1.0.0

<!-- 大神引用 -->
[ZhangHongYang]:https://github.com/hongyangAndroid/baseAdapter "通用适配器"
[Blankj]:https://github.com/Blankj/AndroidUtilCode "常用工具类"

<!-- 代码引用 -->
[GridAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/GridAdapterActivity.java
[RecyclerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/RecyclerAdapterActivity.java

[ActivityUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ActivityUtils.java
[AppUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/AppUtils.java
[ConvertUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ConvertUtils.java
[DBUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/DBUtils.java
[DensityUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/DensityUtils.java
[EmptyUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/EmptyUtils.java
[FileUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/FileUtils.java
[HandlerUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/HandlerUtils.java
[ImageUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ImageUtils.java
[NetworkUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/NetworkUtils.java
[RegexUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/RegexUtils.java
[ShellUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ShellUtils.java
[SpannableStringUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/SpannableStringUtils.java
[StringUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/StringUtils.java
[TimeUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/TimeUtils.java
