# Android通用适配器和常用的工具类
Android通用的Adapter for ListView,GridView,RecyclerView等，支持多种ItemType布局

<br>

[![Download][icon_download]][download]


## 目录<a name="目录">
* [使用](#使用)
* [CommonAdapter](#CommonAdapter)
    * [通用适配器](#通用适配器)
    * [多布局通用适配器](#多布局通用适配器)
* [CommonBindingAdapter](#CommonBindingAdapter)
    * [DataBinding通用适配器](#DataBinding通用适配器)
    * [DataBinding多布局通用适配器](#DataBinding多布局通用适配器)
* [BaseRecyclerAdapter](#BaseRecyclerAdapter)
    * [RecyclerView通用适配器](#RecyclerView通用适配器)
    * [RecyclerView多布局通用适配器](#RecyclerView多布局通用适配器)
* [BaseRecyclerBindingAdapter](#BaseRecyclerBindingAdapter)
    * [DataBinding RecyclerView通用适配器](#DataBindingRecyclerView通用适配器)
    * [DataBinding RecyclerView多布局通用适配器](#DataBindingRecyclerView多布局通用适配器)
* [BasePagerAdapter](#BasePagerAdapter)
    * [ViewPager通用适配器](#ViewPager通用适配器)
* [Utils](#Utils)
    * [权限](#权限)
    * [常用工具类](#常用工具类)
* [Assist](#Assist)
* [版本更新](#版本更新)
* [感谢](#感谢)

<br>

<!-- you should configure jcenter repository-->
## 导入Android Studio<a name="使用">
添加jCenter远程依赖到module里的build.gradle：
```
dependencies {
    compile 'com.excellence:basetools:1.2.5'
    // 或者直接使用最新版本
    // compile 'com.excellence:basetools:_latestVersion'
}
```
或者直接添加本地Library依赖
```
compile project(':basetoolslibrary')
```

**注意**

在使用DataBinding通用适配器时，记得开启配置
```
android {
    dataBinding {
        enabled true
    }
}
```

<br>

**大家来找茬**

![icon_adapter][icon_adapter]

![icon_common_adapter][icon_common_adapter]

![icon_multi_adapter][icon_multi_adapter]

## CommonAdapter<a name="CommonAdapter">

### ListView，GridView的通用适配器<a name="通用适配器">

示例：[CommonAdapterActivity][CommonAdapterActivity]


```java
// 创建adapter类继承CommonAdapter
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


```java
// ViewHolder辅助方法
public <T extends View> T getView(int viewId);                                    // 用于获取Item内的子控件，参数为控件的id
public ViewHolder setText(int viewId, int strId);                                 // 用于设置文本，参数控件id、字符串id
public ViewHolder setBackgroundResource(int viewId, int resId);                   // 用于设置背景图片，参数控件id、图片id
public ViewHolder setImageResource(int viewId, int resId);                        // 用于设置ImageView图片资源，参数控件id、图片id
public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener);  // 用于点击事件监听
*
*
*
// 可拓展其他方法
```


```java
// 刷新适配器
adapter.notifyNewData(data);
```

### ListView，GridView的多布局通用适配器<a name="多布局通用适配器">

示例：[MultiItemAdapterActivity][MultiItemAdapterActivity]

```java
// 多布局适配器
private class ChatAdapter extends MultiItemTypeAdapter<People>
{
    public ChatAdapter(Context context, List<People> messages)
    {
        super(context, messages);
        addItemViewDelegate(new ComputerDelegate());
        addItemViewDelegate(new BlueDelegate());
        addItemViewDelegate(new PurpleDelegate());
    }
}

// 不同的布局视图
private class ComputerDelegate implements ItemViewDelegate<People>
{
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_computer;
    }

    @Override
    public boolean isForViewType(People item, int position)
    {
        return item instanceof ComputerData;
    }

    @Override
    public void convert(ViewHolder viewHolder, People item, int position)
    {
        viewHolder.setText(R.id.computer_text, item.getMsg());
    }
}
```

<br>

## CommonBindingAdapter<a name="CommonBindingAdapter">

### 开启DataBinding，ListView、GridView通用适配器<a name="DataBinding通用适配器">

示例：[CommonBindingAdapterActivity][CommonBindingAdapterActivity]

```java
// 直接创建CommonBindingAdapter
CommonBindingAdapter<Flower> adapter = new CommonBindingAdapter<>(mFlowers, R.layout.item_flower, BR.flower);
// 设置适配器，等同于ListView.setAdapter()、GridView.setAdapter()
mBinding.setAdapter(adapter);
```

### 开启DataBinding，ListView，GridView的多布局通用适配器<a name="DataBinding多布局通用适配器">

示例：[MultiItemTypeBindingAdapterActivity][MultiItemTypeBindingAdapterActivity]

```java
// 使用方式同上，主要实现ViewDelegate布局视图接口
MultiItemTypeBindingAdapter<Flower> adapter = new MultiItemTypeBindingAdapter<>(mFlowers);
adapter.addItemViewDelegate(new RoseViewDelegate());
adapter.addItemViewDelegate(new TulipViewDelegate());
mBinding.setAdapter(adapter);
```

<br>

## BaseRecyclerAdapter<a name="BaseRecyclerAdapter">

### RecyclerView的通用适配器<a name="RecyclerView通用适配器">

示例：[RecyclerAdapterActivity][RecyclerAdapterActivity]

```java
// 创建adapter类继承BaseRecyclerAdapter
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


```java
// RecyclerViewHolder辅助方法类同ViewHolder辅助方法
*
*
*
```

### RecyclerView的多布局通用适配器<a name="RecyclerView多布局通用适配器">

示例：[MultiItemRecyclerAdapterActivity][MultiItemRecyclerAdapterActivity]

```java
// 多布局适配器
private class WarAdapter extends MultiItemTypeRecyclerAdapter<People>
{
    public WarAdapter(Context context, List<People> datas)
    {
        super(context, datas);
        addItemViewDelegate(new ComputerRecyclerDelegate());
        addItemViewDelegate(new BlueRecyclerDelegate());
        addItemViewDelegate(new PurpleRecyclerDelegate());
    }
}

// 不同的布局视图
private class ComputerRecyclerDelegate implements ItemViewDelegate<People>
{
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_computer;
    }

    @Override
    public boolean isForViewType(People item, int position)
    {
        return item instanceof ComputerData;
    }

    @Override
    public void convert(RecyclerViewHolder viewHolder, People item, int position)
    {
        viewHolder.setText(R.id.computer_text, item.getMsg());
    }
}
```

<br>

##  BaseRecyclerBindingAdapter<a name="BaseRecyclerBindingAdapter">

### 开启DataBinding，RecyclerView的通用适配器<a name="DataBindingRecyclerView通用适配器">

示例：[BaseRecyclerBindingAdapterActivity][BaseRecyclerBindingAdapterActivity]

```java
// 直接创建BaseRecyclerBindingAdapter
BaseRecyclerBindingAdapter<Flower> adapter = new BaseRecyclerBindingAdapter<>(mFlowers, R.layout.item_flower, BR.flower);
// 设置适配器，等同于RecyclerView.setAdapter()
mBinding.setAdapter(adapter);
// 注意设置LayoutManager，等同于RecyclerView.setLayoutManager()
mBinding.setLayoutManager(new LinearLayoutManager(this));
```

### 开启DataBinding，RecyclerView的多布局通用适配器<a name="DataBindingRecyclerView多布局通用适配器">

示例：[MultiItemTypeBindingRecyclerAdapterActivity][MultiItemTypeBindingRecyclerAdapterActivity]

```java
// 使用方式同上，主要实现ViewDelegate布局视图接口
MultiItemTypeBindingRecyclerAdapter<Flower> adapter = new MultiItemTypeBindingRecyclerAdapter<>(mFlowers);
adapter.addItemViewDelegate(new RoseViewDelegate());
adapter.addItemViewDelegate(new TulipViewDelegate());
mBinding.setAdapter(adapter);
mBinding.setLayoutManager(new LinearLayoutManager(this));
```

<br>

## BasePagerAdapter<a name="BasePagerAdapter">

### ViewPager通用适配器<a name="ViewPager通用适配器">
示例：[ViewPagerAdapterActivity][ViewPagerAdapterActivity]

```java
private class NumAdapter extends BasePagerAdapter
{

    public NumAdapter(Context context, int pageCount)
    {
        super(context, pageCount);
    }

    @Override
    protected View loadView(Context context, int pageIndex)
    {
        // 加载每页
        TextView textView = new TextView(context);
        textView.setText(String.valueOf(pageIndex * mNumScale));
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mNumScale *= 10;
                mPageCount += 1;
                // 刷新每页
                mAdapter.notifyNewData(mPageCount);
            }
        });
        return textView;
    }
}
```

<br>

## Utils<a name="Utils">

### 权限<a name="权限">
```
<uses-permission android:name="android.permission.GET_TASKS"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
```


### 常用工具类<a name="常用工具类">

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

> - **关闭相关→[CloseUtils.java][CloseUtils]**
```
closeIO        : 关闭IO
closeIOQuietly : 安静关闭IO
```

> - **转换相关→[ConvertUtils.java][ConvertUtils]**
```
bytes2Short               : byte转short
shortToByte               : short转byte
byte2BinStr               : byte转二进制
byte2BinStr               : byte数组转二进制
str2BinStr                : 字符串转二进制字符串
bytes2HexString           : byte数组转16进制字符串
hexString2Bytes           : 16进制字符串转byte数组
string2HexString          : 字符串转16进制字符串
hexString2String          : 16进制字符串转字符串
string2Bytes              : 字符串转byte数组
bytes2String              : byte数组转字符串
byte2Int                  : byte数组转有符号int
int2Byte                  : int转4位byte数组
unintbyte2long            : 四字节byte数组转无符号long
inputStream2OutputStream  : inputStream转outPutStream
inputStream2Bytes         : inputStream转bytes
inputStream2String        : inputStream转字符串
inputStream2StringBuilder : inputStream转StringBuilder
```

> - **配置存储相关→[DBUtils.java][DBUtils]**
```
init         : 初始化，设置存储文件名
contains     : 判断键值是否存在
setSetting   : 存储配置
getString    : 读取字符串配置
getBoolean   : 读取Boolean配置
getInt       : 读取int配置
getLong      : 读取long配置
getFloat     : 读取float配置
getStringSet : 读取Set<String>配置
remove       : 删除配置
clear        : 清空配置
```

> - **分辨率相关→[DensityUtils.java][DensityUtils]**
```
getDensity      : 获取当前屏幕分辨率
getScaleDensity : 获取当前文字分辨率
getScreenWidth  : 获取屏幕宽度
getScreenHeight : 获取屏幕高度
getScreenSize   : 获取屏幕宽、高
dp2px           : dp转px
px2dp           : px转dp
sp2px           : sp转px
px2sp           : px转sp
```

> - **设备相关→[DeviceUtils.java][DeviceUtils]**
```
getIMEI            : 获取IMEI
getIMSI            : 获取IMSI
getPhone           : 获取手机号
getSIM             : 获取SIM卡序列号
getSimCountry      : 获取SIM卡国家
getSimOperator     : 获取SIM卡运营商
getSimOperatorName : 获取SIM卡运营商名字
getSimState        : 获取SIM卡状态
```

> - **判断空相关→[EmptyUtils.java][EmptyUtils]**
```
isEmpty    : 判断对象是否为空
isNotEmpty : 判断对象是否非空
```

> - **异常相关→[ExceptionUtils.java][ExceptionUtils]**
```
printException : 打印异常信息字符串
```

> - **文件流相关→[FileIOUtils.java][FileIOUtils]**
```
writeFile        : 将字符串、字节数组、输入流写入文件
readStream2Bytes : 读取输入流为字节数组
readFile2Bytes   : 读取文件为字节数组
readFile2String  : 读取文件为字符串
copyFile         : 拷贝文件
```

> - **文件相关→[FileUtils.java][FileUtils]**
```
createNewFile      : 创建文件
deleteFile         : 删除文件
mkDir              : 创建目录
deleteDir          : 删除目录
deletePostfixFiles : 删除目录下的某后缀文件
formatFileSize     : 格式化文件大小
getFilesSize       : 遍历目录、获取文件大小
getFileSize        : 获取文件大小
getDirSize         : 遍历目录大小
getDirFreeSpace    : 获取目录剩余空间
getDirTotalSpace   : 获取目录总空间
getDirUsableSpace  : 获取目录可用空间
chmod              : 修改目录、文件权限
isFileExists       : 判断文件或目录是否存在
```

> - **Handler相关→[HandlerUtils.java][HandlerUtils]**
```
HandlerHolder : 使用必读
```

> - **Image相关→[ImageUtils.java][ImageUtils]**
```
drawable2Bitmap : drawable转bitmap
bitmap2Drawable : bitmap转drawable
view2Bitmap     : view转Bitmap
```

> - **常见的Intent相关→[IntentUtils.java][IntentUtils]**
```
isIntentAvailable      : 判断Intent是否存在
startIntent            : Intent跳转
getSettingIntent       : 跳转Settings
getWiFiIntent          : 隐式开启WiFi
getDirectWiFiIntent    : 直接开启WiFi
getRoamingIntent       : 跳转到移动网络设置
getPermissionIntent    : 开启权限设置
getLocationIntent      : 开启定位设置
getBluetoothIntent     : 开启蓝牙设置
getLocaleIntent        : 开启语言设置
getAppIntent           : 跳转应用程序列表界面
getAllAppIntent        : 跳转到应用程序界面（所有的）
getInstalledAppIntent  : 跳转到应用程序界面（已安装的）
getStorageIntent       : 开启存储设置
getAccessibilityIntent : 开启辅助设置
getSearchIntent        : 跳转到搜索设置
getInputMethodIntent   : 跳转输入法设置
getInstallIntent       : 安装应用
getUninstallIntent     : 卸载应用
getShareTextIntent     : 分享文本
getShareImageIntent    : 分享图片
getDialIntent          : 跳转拨号界面
getCallIntent          : 拨打电话
getSmsIntent           : 跳转短信界面
getSendSmsIntent       : 发送短信
getEmailIntent         : 发送邮件
getCaptureIntent       : 打开相机
getVideoIntent         : 播放本地视频
getNetVideoIntent      : 播放网络视频
getAudioIntent         : 播放本地音乐
```

> - **键盘相关→[KeyboardUtils.java][KeyboardUtils]**
```
hideSoftInput                : 隐藏软键盘
showSoftInput                : 打开软键盘
toggleSoftInput              : 如果输入法在窗口上已经显示，则隐藏，反之则显示
clickBlankArea2HideSoftInput : 击屏幕空白区域隐藏软键盘
```

> - **网络相关→[NetworkUtils.java][NetworkUtils]**
```
getActiveNetworkInfo    : 获取活动的网络信息
isConnected             : 检查网络是否连接
isAvailableByPing       : 判断网络是否可用
isMobileDataEnabled     : 判断移动数据是否打开
setMobileDataEnabled    : 打开或关闭移动数据（舍弃不可用）
is4G                    : 判断是否是4G网络
isWiFiEnabled           : 判断是否打开WiFi
setWiFiEnabled          : 打开或关闭WiFi
isEthConnected          : 判断以太网是否连接
isEthAvailable          : 判断以太网是否可用
isWiFiConnected         : 判断WiFi是否连接
isWiFiAvailable         : 判断WiFi是否可用
isWiFiAvailableByPing   : 通过ping的方式判断WiFi是否可用
getNetworkOperatorName  : 获取网络运营商名称
getNetworkType          : 获取当前网络类型
getIPAddress            : 获取网络IP地址
getDomainAddress        : 根据域名获取ip
getWiredMac             : 获取有线Mac地址
getWirelessMac          : 获取无线Mac地址
```

> - **路径相关→[PathUtils.java][PathUtils]**
```
getRootPath                     : 获取根路径
getDataPath                     : 获取数据路径
getDownloadCachePath            : 获取下载缓存路径
getInternalAppDataPath          : 获取内存应用数据路径
getInternalAppCodeCacheDir      : 获取内存应用代码缓存路径
getInternalAppCachePath         : 获取内存应用缓存路径
getInternalAppDbsPath           : 获取内存应用数据库路径
getInternalAppDbPath            : 获取内存应用数据库路径
getInternalAppFilesPath         : 获取内存应用文件路径
getInternalAppSpPath            : 获取内存应用 SP 路径
getInternalAppNoBackupFilesPath : 获取内存应用未备份文件路径
getExternalStoragePath          : 获取外存路径
getExternalMusicPath            : 获取外存音乐路径
getExternalPodcastsPath         : 获取外存播客路径
getExternalRingtonesPath        : 获取外存铃声路径
getExternalAlarmsPath           : 获取外存闹铃路径
getExternalNotificationsPath    : 获取外存通知路径
getExternalPicturesPath         : 获取外存图片路径
getExternalMoviesPath           : 获取外存影片路径
getExternalDownloadsPath        : 获取外存下载路径
getExternalDcimPath             : 获取外存数码相机图片路径
getExternalDocumentsPath        : 获取外存文档路径
getExternalAppDataPath          : 获取外存应用数据路径
getExternalAppCachePath         : 获取外存应用缓存路径
getExternalAppFilesPath         : 获取外存应用文件路径
getExternalAppMusicPath         : 获取外存应用音乐路径
getExternalAppPodcastsPath      : 获取外存应用播客路径
getExternalAppRingtonesPath     : 获取外存应用铃声路径
getExternalAppAlarmsPath        : 获取外存应用闹铃路径
getExternalAppNotificationsPath : 获取外存应用通知路径
getExternalAppPicturesPath      : 获取外存应用图片路径
getExternalAppMoviesPath        : 获取外存应用影片路径
getExternalAppDownloadPath      : 获取外存应用下载路径
getExternalAppDcimPath          : 获取外存应用数码相机图片路径
getExternalAppDocumentsPath     : 获取外存应用文档路径
getExternalAppObbPath           : 获取外存应用 OBB 路径
```

> - **拼音相关→[PinyinUtils.java][PinyinUtils]**
```
ccs2Pinyin            : 中文转拼音
getPinyinHeadChar     : 获取中文首字母
getPinyinHeadChars    : 获取所有中文首字母
isAllHanzi            : 判断是否全是汉字
```

> - **反射相关→[ReflectUtils.java][ReflectUtils]**
```
getDeclaredFields      : 获取类中所有成员，能访问类中所有的字段，与public、private、protect无关，不能访问从其它类继承来的方法
getFields              : 获取类中所有的公有成员，只能访问类中声明为公有的字段，私有的字段它无法访问，能访问从其它类继承来的公有方法
setFieldValue          : 设置类中指定成员变量的值，一般是设置私有成员变量值
getFieldValue          : 获取类中指定成员变量的值，一般是获取私有成员变量值
getDeclaredMethods     : 获取类中所有方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
getMetods              : 获取类中所有的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
invokeDeclaredMethod   : 调用类中指定的方法，能访问类中所有的方法，与public、private、protect无关，不能访问从其它类继承来的方法
invokeMethod           : 调用类中指定的公有方法，只能访问类中声明为公有的方法，私有的方法它无法访问，能访问从其它类继承来的公有方法
newInstance            : 创建带参数的构造函数，返回类对象
isInstance             : 判断是否为某个类的实例
getAnnotation          : 获取存在的、指定类型的注解
getAnnotations         : 获取类中存在的所有注解
getDeclaredAnnotation  : 获取存在的、指定类型的注解，不包括继承的注解
getDeclaredAnnotations : 获取类中存在的所有注解，不包括继承的注解
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

> - **资源相关→[ResourceUtils.java][ResourceUtils]**
```
getName            : 解析资源的全名
getEntryName       : 解析资源名
getTypeName        : 解析资源类型名
getPackageName     : 解析资源的包名
getIdentifier      : 获取资源Id
copyFileFromAssets : 拷贝assets文件到指定目录
copyFileFromRaw    : 拷贝raw资源到指定目录文件
readAsset          : 读取asset文件转字符串
getLocal           : 获取当前系统语言
getLanguage        : 获取当前系统语言
getCountry         : 获取当前系统语言国家
```

> - **命令相关→[ShellUtils.java][ShellUtils]**
```
execProcessBuilderCommand : 执行命令
execRuntimeCommand        : 执行命令
```

> - **SD、TF等存储相关→[StorageUtils.java][StorageUtils]**
```
getStorageList       : 获取所有的内置、外置存储设备
getStorageVolumeList : 获取存储卷的相关信息
```

> - **字符串相关→[StringUtils.java][StringUtils]**
```
isEmpty          : 判断字符串是否为空
checkNULL        : 判断字符串是否为空，是否是"NULL"字符串
equals           : 比较字符串是否相等
equalsIgnoreCase : 比较字符串是否相等，忽略大小写
```

> - **系统属性相关→[SystemPropertyUtils.java][SystemPropertyUtils]**
```
get        : 获取String类型系统属性
getBoolean : 获取Boolean类型系统属性
getInt     : 获取int类型系统属性
getLong    : 获取long类型系统属性
set        : 设置系统属性
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

<br>

## Assist<a name="Assist">
> - **WeakHandler转载→[WeakHandler.java][WeakHandler]**
```
WeakHandler : 使用描述，性能优化，避免内存泄漏
```

> - **HanziToPinyin转载→[HanziToPinyin.java][HanziToPinyin]**
```
HanziToPinyin : Android汉字转拼音类
```

<br>

## 版本更新<a name="版本更新">
|            版本          |                              描述                               |
|------------------------- | -------------------------------------------------------------- |
| [1.2.5][BaseToolsV1.2.5] | 优化RecyclerView的监听事件，修复多布局管理器bug **2018-3-7** |
| [1.2.4][BaseToolsV1.2.4] | 新增：开启DataBinding，ListView、GridView、RecyclerView通用适配器，一些辅助方法  **2017-10-18** |
| [1.2.3][BaseToolsV1.2.3] | 新增ViewPager通用适配器；拓展工具类：文件、资源、反射、键盘等  **2017-7-21** |
| [1.2.2][BaseToolsV1.2.2] | 拓展配置文件存储  **2017-5-12** |
| [1.2.1][BaseToolsV1.2.1] | 支持通用适配器中的多种布局  **2017-4-20** |
| [1.2.0][BaseToolsV1.2.0] | 新增网络、拼音、异常打印等工具类  **2017-4-13** |
| [1.1.0][BaseToolsV1.1.0] | Utils增加一些常用的工具类:应用、数据库、分辨率、文件、正则表达式、命令、时间等  **2017-2-23** |
| [1.0.0][BaseToolsV1.0.0] | 创建ListView、GridView、RecyclerView的通用适配器，一些辅助方法  **2016-12-20** |

<br>

## 感谢<a name="感谢">
> - [张鸿洋][ZhangHongYang]
> - [布兰柯基][Blankj]
> - [马天宇][litesuits]
> - [LeBron_Six][smuyyh]

<br>

[返回目录](#目录)


<!-- 引用网站链接 -->

[download]:https://bintray.com/veizhang/maven/basetools/_latestVersion "Latest version"
[issues]:https://github.com/VeiZhang/BaseToolsLibrary/issues
[forks]:https://github.com/VeiZhang/BaseToolsLibrary/network/members
[stars]:https://github.com/VeiZhang/BaseToolsLibrary/stargazers

<!-- 图片链接 -->

[icon_download]:https://api.bintray.com/packages/veizhang/maven/basetools/images/download.svg
[icon_issues]:https://img.shields.io/github/issues/VeiZhang/BaseToolsLibrary.svg
[icon_forks]:https://img.shields.io/github/forks/VeiZhang/BaseToolsLibrary.svg?style=social
[icon_stars]:https://img.shields.io/github/stars/VeiZhang/BaseToolsLibrary.svg?style=social
[icon_adapter]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/images/adapter.png "适配器列表"
[icon_common_adapter]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/images/common_adapter.png "通用适配器"
[icon_multi_adapter]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/images/multi_adapter.png "多布局适配器"

<!-- 版本 -->

[BaseToolsV1.2.5]:https://bintray.com/veizhang/maven/basetools/1.2.5
[BaseToolsV1.2.4]:https://bintray.com/veizhang/maven/basetools/1.2.4
[BaseToolsV1.2.3]:https://bintray.com/veizhang/maven/basetools/1.2.3
[BaseToolsV1.2.2]:https://bintray.com/veizhang/maven/basetools/1.2.2
[BaseToolsV1.2.1]:https://bintray.com/veizhang/maven/basetools/1.2.1
[BaseToolsV1.2.0]:https://bintray.com/veizhang/maven/basetools/1.2.0
[BaseToolsV1.1.0]:https://bintray.com/veizhang/maven/basetools/1.1.0
[BaseToolsV1.0.0]:https://bintray.com/veizhang/maven/basetools/1.0.0

<!-- 大神引用 -->

[ZhangHongYang]:https://github.com/hongyangAndroid/baseAdapter "通用适配器"
[Blankj]:https://github.com/Blankj/AndroidUtilCode "常用工具类"
[litesuits]:https://github.com/litesuits/android-common "通用类、辅助类、工具类"
[smuyyh]:https://github.com/smuyyh/EasyAdapter "通用适配器"

<!-- 代码引用 -->

[CommonAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/CommonAdapterActivity.java
[RecyclerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/RecyclerAdapterActivity.java
[MultiItemAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/MultiItemAdapterActivity.java
[MultiItemRecyclerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/MultiItemRecyclerAdapterActivity.java
[ViewPagerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/ViewPagerAdapterActivity.java
[CommonBindingAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/databinding/CommonBindingAdapterActivity.java
[MultiItemTypeBindingAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/databinding/MultiItemTypeBindingAdapter.java
[BaseRecyclerBindingAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/databinding/BaseRecyclerBindingAdapter.java
[MultiItemTypeBindingRecyclerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/databinding/MultiItemTypeBindingRecyclerAdapter.java

<!-- 常用方法 -->

[ActivityUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ActivityUtils.java
[AppUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/AppUtils.java
[CloseUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/CloseUtils.java
[ConvertUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ConvertUtils.java
[DBUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/DBUtils.java
[DensityUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/DensityUtils.java
[DeviceUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/DeviceUtils.java
[EmptyUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/EmptyUtils.java
[ExceptionUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ExceptionUtils.java
[FileIOUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/FileIOUtils.java
[FileUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/FileUtils.java
[HandlerUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/HandlerUtils.java
[ImageUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ImageUtils.java
[IntentUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/IntentUtils.java
[KeyboardUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/KeyboardUtils.java
[NetworkUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/NetworkUtils.java
[PathUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/PathUtils.java
[PinyinUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/PinyinUtils.java
[ReflectUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ReflectUtils.java
[RegexUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/RegexUtils.java
[ResourceUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ResourceUtils.java
[ShellUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ShellUtils.java
[StorageUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/StorageUtils.java
[StringUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/StringUtils.java
[SystemPropertyUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/SystemPropertyUtils.java
[TimeUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/TimeUtils.java

<!-- 转载方法 -->

[WeakHandler]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/assist/WeakHandler.java
[HanziToPinyin]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/assist/HanziToPinyin.java