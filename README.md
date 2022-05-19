# Android通用适配器和常用的工具类

* Android通用的Adapter for ListView,GridView,RecyclerView等，支持多种ItemType布局
* Android常用的工具类集合

<br>

```
allprojects {
    repositories {
        /** Github packages **/
        maven {
            url = "https://maven.pkg.github.com/VeiZhang/GitHubPackages-Android"
            credentials {
                // 输入自己的账号和个人令牌（需要 read packages权限）
                // 可放项目根目录的gradle.properties 或者 local.properties中
                username = getPropertyValue("GITHUB_USER") ?: project.properties['GITHUB_USER']
                password = getPropertyValue("GITHUB_READ_TOKEN") ?: project.properties['GITHUB_READ_TOKEN']
            }
        }
    }
}
```
~~[![Download][icon_download]][download]~~


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
    compile 'com.excellence:basetools:_latestVersion'
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
    public AppGridAdapter(List<ResolveInfo> data, int layoutId)
    {
        super(data, layoutId);
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
    public ChatAdapter(List<People> messages)
    {
        super(messages);
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

    public AppRecyclerAdapter(List<ResolveInfo> data, int layoutId)
    {
        super(data, layoutId);
        mPackageManager = getPackageManager();
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
    public WarAdapter(List<People> data)
    {
        super(data);
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

    public NumAdapter(int pageCount)
    {
        super(pageCount);
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

> - **Activity相关→[ActivityUtils.kt][ActivityUtils]**
```
startAnotherActivity   : Activity跳转
setActivityWindowAlpha : 设置Activity窗口透明值
isActivityTopStack     : 判断Activity是否在栈顶
getLauncherActivity    : 获取某应用入口Activity
```

> - **AlphaUtils相关→[AlphaUtils.kt][AlphaUtils]**
```
setAlpha : 设置Window透明度
setAlpha : 设置Activity的Window透明度
setAlpha : 设置Dialog的Window透明度
setAlpha : 设置DialogFragment的Window透明度
```

> - **应用相关→[AppUtils.kt][AppUtils]**
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
getMaxMemory           : 当前应用分配的最大内存
getTotalMemory         : 当前应用分配的总内存
getFreeMemory          : 当前应用分配的剩余内存
```

> - **音频相关→[AudioUtils.kt][AudioUtils]**
```
getMaxVolume : 获取最大音量
getMinVolume : 获取最小音量
getVolume    : 获取音量
setVolume    : 设置音量
adjustVolume : 调整音量
```

> - **广播相关→[BroadcastUtils.kt][BroadcastUtils]**
```
registerMountAction        : USB广播
registerScreenAction       : 屏幕熄亮广播
registerPackageAction      : 安装卸载广播
registerNetworkStateAction : 网络状态广播
registerBootAction         : 开机广播
```

> - **关闭相关→[CloseUtils.kt][CloseUtils]**
```
closeIO        : 关闭IO
closeIOQuietly : 安静关闭IO
```

> - **关闭相关→[CollectionUtils.kt][CollectionUtils]**
```
removeEmptyElement : 清除集合里的空元素
listEquals         : 比较两个列表元素对象是否一致
listContentEquals  : 比较两个列表元素内容是否一致
```

> - **转换相关→[ConvertUtils.kt][ConvertUtils]**
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

> - **配置存储相关→[DBUtils.kt][DBUtils]**
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

> - **分辨率相关→[DensityUtils.kt][DensityUtils]**
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

> - **设备相关→[DeviceUtils.kt][DeviceUtils]**
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

> - **判断空相关→[EmptyUtils.kt][EmptyUtils]**
```
isEmpty    : 判断对象是否为空
isNotEmpty : 判断对象是否非空
```

> - **加密解密相关→[EncryptUtils.java][EncryptUtils]**
```
hashTemplate          : 散列加密算法
encryptMD5            : MD5加密
encryptMD5HexString   : MD5加密转16进制字符串
symmetricTemplate     : 对称加密算法
encryptDES            : DES加密
encryptDES2HexString  : DES加密转16进制字符串
decryptDES            : DES解密
decryptHexStringDES   : 16进制字符串DES解密
encrypt3DES           : 3DES加密
encrypt3DES2HexString : 3DES加密转16进制字符串
decrypt3DES           : 3DES解密
decryptHexString3DES  : 16进制字符串3DES解密
encryptAES            : AES加密
encryptAES2HexString  : AES加密转16进制字符串
decryptAES            : AES解密
decryptHexStringAES   : 16进制字符串AES解密
rsaTemplate           : 非对称加密算法
encryptRSA            : RSA加密
encryptRSA2HexString  : RSA加密转16进制
decryptRSA            : RSA解密
decryptHexStringRSA   : 16进制字符串RSA解密
```

> - **异常相关→[ExceptionUtils.kt][ExceptionUtils]**
```
printException : 打印异常信息字符串
```

> - **文件流相关→[FileIOUtils.kt][FileIOUtils]**
```
writeFile        : 将字符串、字节数组、输入流写入文件
readFile2Bytes   : 读取文件、输入流为字节数组
readFile2String  : 读取文件、输入流为字符串
copyFile         : 拷贝文件
```

> - **文件相关→[FileUtils.kt][FileUtils]**
```
createNewFile       : 创建文件
deleteFile          : 删除文件
mkDir               : 创建目录
deleteDir           : 删除目录
deletePostfixFiles  : 删除目录下的某后缀文件
formatFileSize      : 格式化文件大小
getFilesSize        : 遍历目录、获取文件大小
getFileSize         : 获取文件大小
getDirSize          : 遍历目录大小
getDirFreeSpace     : 获取目录剩余空间
getDirTotalSpace    : 获取目录总空间
getDirUsableSpace   : 获取目录可用空间
chmod               : 修改目录、文件权限
isFileExists        : 判断文件或目录是否存在
getFileLastModified : 读取文件最后的修改时间
getFileMd5          : 读取文件MD5值
```

> - **Handler相关→[HandlerUtils.java][HandlerUtils]**
```
HandlerHolder : 使用必读
```

> - **HTTP相关→[HttpUtils.kt][HttpUtils]**
```
checkURL           : 检测有效的URL
checkHttpURL       : 检测Http、Https，没有则增加前缀http://
appendURLPath      : 拼接url
isUrlExists        : 通过访问的方式检查链接是否有效
convertHttpUrl     : 转换链接中中文字符
convertInputStream : 通过类型转换流
setConnectParam    : 设置请求头信息
printHeader        : 打印全部请求头信息
getHeader          : 获取具体的请求头信息
```


> - **Image相关→[ImageUtils.kt][ImageUtils]**
```
resource2Drawable     : 资源转Drawable
resource2Bitmap       : 资源转Bitmap
drawable2Bitmap       : drawable转bitmap
bitmap2Drawable       : bitmap转drawable
view2Bitmap           : view转Bitmap
shotActivity          : Activity截图
createBitmap          : 创建空白Bitmap
addBitmapShadows      : 增加遮罩
zoomImg               : 图片等比缩小
setRendScriptCacheDir : 高斯模糊配置，Android7.0上却会导致应用 crash
```

> - **常见的Intent相关→[IntentUtils.kt][IntentUtils]**
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

> - **键盘相关→[KeyboardUtils.kt][KeyboardUtils]**
```
hideSoftInput                : 隐藏软键盘
showSoftInput                : 打开软键盘
toggleSoftInput              : 如果输入法在窗口上已经显示，则隐藏，反之则显示
clickBlankArea2HideSoftInput : 击屏幕空白区域隐藏软键盘
```

> - **键盘相关→[KeyEventUtils.kt][KeyEventUtils]**
```
listKeyUp    : 向上循环
listKeyDown  : 向下循环
listKeyLeft  : 向左循环
listKeyRight : 向右循环
listPageUp   : 上翻页循环
listPageDown : 下翻页循环
```

> - **数学函数相关→[MathUtils.kt][MathUtils]**
```
gcd       : 求最大公约数
fraction  : 约分
```

> - **多媒体相关→[MediaUtils.kt][MediaUtils]**
```
getKey      : 读取多媒体信息的键
getAlbum    : 读取多媒体信息的专辑
getArtist   : 读取多媒体信息的艺术家
getAuthor   : 读取多媒体信息的作者
getComposer : 读取多媒体信息的作曲家
getDate     : 读取多媒体信息的日期
getGenre    : 读取多媒体信息的分类
getTitle    : 读取多媒体信息的名称
getYear     : 读取多媒体信息的年份
getDuration : 读取多媒体信息的时长
getMimeType : 读取多媒体信息的类型
getHasAudio : 读取多媒体信息是否有音频
getHasVideo : 读取多媒体信息是否有视频
getWidth    : 读取多媒体信息的宽度
getHeight   : 读取多媒体信息的高度
getBitrate  : 读取多媒体信息的码率
```

> - **网络相关→[NetworkUtils.kt][NetworkUtils]**
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
readMac                 : 读取Mac地址：优先获取Eth的MAC，当Eth为空，接着获取WiFi的MAC
getMac                  : 获取Mac地址：使用Eth时读取Eth的MAC，否则读取WiFi的MAC
getWiredMac             : 获取有线Mac地址
getWirelessMac          : 获取无线Mac地址
formatTcpSpeed          : 格式化比特率
formatNetSpeed          : 格式化网速
```

> - **路径相关→[PathUtils.kt][PathUtils]**
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

> - **反射相关→[ReflectUtils.kt][ReflectUtils]**
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

> - **正则表达式相关→[RegexUtils.kt][RegexUtils]**
```
isMAC           : 验证MAC地址
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
getMatch        : 获取第一个正则匹配的部分
getMatches      : 获取正则匹配的部分
getSplits       : 获取正则匹配分组
getReplaceFirst : 替换正则匹配的第一部分
getReplaceAll   : 替换所有正则匹配的部分
```

> - **资源相关→[ResourceUtils.kt][ResourceUtils]**
```
getName            : 解析资源的全名
getEntryName       : 解析资源名
getTypeName        : 解析资源类型名
getPackageName     : 解析资源的包名
getIdentifier      : 获取资源Id
getIdentifiers     : 遍历读取资源Id
copyFileFromAssets : 拷贝assets文件到指定目录
copyFileFromRaw    : 拷贝raw资源到指定目录文件
readAsset          : 读取asset文件转字符串
getLocal           : 获取当前系统语言
getLanguage        : 获取当前系统语言
getCountry         : 获取当前系统语言国家
getIdentifier      : 跨APP，读取其他应用的资源
```

> - **命令相关→[ShellUtils.kt][ShellUtils]**
```
execProcessBuilderCommand : 执行命令
execRuntimeCommand        : 执行命令
```

> - **SD、TF等存储相关→[StorageUtils.kt][StorageUtils]**
```
getStorageList       : 获取所有的内置、外置存储设备
getStorageVolumeList : 获取存储卷的相关信息
```

> - **字符串相关→[StringUtils.kt][StringUtils]**
```
isEmpty            : 判断字符串是否为空
checkNULL          : 判断字符串是否为空，是否是"NULL"字符串
equals             : 比较字符串是否相等
equalsIgnoreCase   : 比较字符串是否相等，忽略大小写
contains           : 判断字符一是否包含字符串二
containsIgnoreCase : 判断字符一是否包含字符串二，忽略大小写
```

> - **SurfaceView相关→[SurfaceViewUtils.kt][SurfaceViewUtils]**
```
clearSurfaceView : 清除SurfaceView的最后一帧画面
```

> - **系统属性相关→[SystemPropertyUtils.java][SystemPropertyUtils]**
```
get                   : 获取String类型系统属性
getBoolean            : 获取Boolean类型系统属性
getInt                : 获取int类型系统属性
getLong               : 获取long类型系统属性
set                   : 设置系统属性
getLinuxKernelVersion : 读取kernel版本
```

> - **时间相关→[TimeUtils.java][TimeUtils]**
```
millisec2String         : 毫秒时间戳转时间字符串
sec2String              : 秒时间戳转时间字符串
string2Date             : 时间字符串转Date类型
string2Millisec         : 时间字符串转毫秒时间戳
date2String             : Date转时间字符串
getTimeSpan             : 获取两个时间差
getNowTimeMillis        : 获取当前毫秒时间戳
getNowTimeDate          : 获取当前Date时间
getNowTimeString        : 获取当前时间字符串
getTimeSpanByNow        : 获取某时间与当前时间的差
isSameDay               : 判断时间是否是同一天
isToday                 : 判断时间是否是今天
getTodayZero            : 获取当天零点
isLeapYear              : 判断是否是闰年
getWeek                 : 获取星期几
getWeekOfMonth          : 获取月份中第几周
getWeekOfYear           : 获取年份中的第几周
getChineseZodiac        : 获取生肖
getZodiac               : 获取星座
is24HoursFormat         : 判断当前时间制是否是24h
seconds2String          : 秒转 分:秒 字符串
milliSeconds2String     : 毫秒转 分:秒 字符串
seconds2HourString      : 秒转 时:分:秒 字符串
milliSeconds2HourString : 毫秒转 时:分:秒 字符串
createSimpleDateFormat  : 创建时间格式化
```

> - **View相关→[ViewUtils.kt][ViewUtils]**
```
observeViewLayout        : 监听绘制完成
observeViewLayoutForever : 监听绘制完成
```

> - **WebView相关→[WebViewUtils.java][WebViewUtils]**
```
callOnWebviewThread : 发送js指令
callJavaScript      : 发送js指令
hookWebView         : 系统应用绕过UID 是 root 检测
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
| [1.3.0][BaseToolsV1.3.0] | 转换Kotlin **2022-04-15** |
| [1.2.9][BaseToolsV1.2.9] | 新增ListAdapter，补充一些工具方法 **2020-08-24** |
| [1.2.8][BaseToolsV1.2.8] | 兼容AndroidX **2020-04-09** |
| [1.2.7][BaseToolsV1.2.7] | 新增工具类 **2019-10-15** |
| [1.2.6][BaseToolsV1.2.6] | 新增工具方法，优化Adapter **2018-9-13** |
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

[BaseToolsV1.3.0]:https://bintray.com/veizhang/maven/basetools/1.3.0
[BaseToolsV1.2.9]:https://bintray.com/veizhang/maven/basetools/1.2.9
[BaseToolsV1.2.8]:https://bintray.com/veizhang/maven/basetools/1.2.8
[BaseToolsV1.2.7]:https://bintray.com/veizhang/maven/basetools/1.2.7
[BaseToolsV1.2.6]:https://bintray.com/veizhang/maven/basetools/1.2.6
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

[CommonAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/CommonAdapterActivity.kt
[RecyclerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/RecyclerAdapterActivity.kt
[MultiItemAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/MultiItemAdapterActivity.kt
[MultiItemRecyclerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/MultiItemRecyclerAdapterActivity.kt
[ViewPagerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/ViewPagerAdapterActivity.kt
[CommonBindingAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/databinding/CommonBindingAdapterActivity.kt
[MultiItemTypeBindingAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/databinding/MultiItemTypeBindingAdapter.kt
[BaseRecyclerBindingAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/databinding/BaseRecyclerBindingAdapter.kt
[MultiItemTypeBindingRecyclerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/databinding/MultiItemTypeBindingRecyclerAdapter.kt

<!-- 常用方法 -->

[ActivityUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ActivityUtils.kt
[AlphaUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/AlphaUtils.kt
[AudioUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/AudioUtils.kt
[BroadcastUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/BroadcastUtils.kt
[AppUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/AppUtils.kt
[CloseUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/CloseUtils.kt
[CollectionUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/CollectionUtils.kt
[ConvertUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ConvertUtils.kt
[DBUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/DBUtils.kt
[DensityUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/DensityUtils.kt
[DeviceUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/DeviceUtils.kt
[EmptyUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/EmptyUtils.kt
[EncryptUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/EncryptUtils.java
[ExceptionUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ExceptionUtils.kt
[FileIOUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/FileIOUtils.kt
[FileUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/FileUtils.kt
[HandlerUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/HandlerUtils.java
[HttpUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/HttpUtils.kt
[ImageUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ImageUtils.kt
[IntentUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/IntentUtils.kt
[KeyboardUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/KeyboardUtils.kt
[KeyEventUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/KeyEventUtils.kt
[MediaUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/MediaUtils.kt
[MathUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/MathUtils.kt
[NetworkUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/NetworkUtils.kt
[PathUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/PathUtils.kt
[PinyinUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/PinyinUtils.java
[ReflectUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ReflectUtils.kt
[RegexUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/RegexUtils.kt
[ResourceUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ResourceUtils.kt
[ShellUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ShellUtils.kt
[StorageUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/StorageUtils.kt
[StringUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/StringUtils.kt
[SurfaceViewUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/SurfaceViewUtils.kt
[SystemPropertyUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/SystemPropertyUtils.java
[TimeUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/TimeUtils.java
[ViewUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ViewUtils.kt
[WebViewUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/WebViewUtils.java

<!-- 转载方法 -->

[WeakHandler]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/assist/WeakHandler.java
[HanziToPinyin]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/assist/HanziToPinyin.java
