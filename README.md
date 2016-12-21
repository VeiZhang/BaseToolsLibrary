# BaseToolsLibrary 常用的工具

<!--[Maven](https://bintray.com/veizhang/maven/BaseTools "Bintray")-->
[Maven Control][bintray][1.1.0]
<!-- you should configure jcenter repository-->
## 导入Android Studio
添加jCenter远程依赖到module里的build.gradle：
```
  dependencies {
    compile 'com.excellence:BaseTools:1.1.0'
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

<br><br>

|            版本           |                             描述                              |
|:------------------------ | -------------------------------------------------------------:|
| [1.1.0][BaseToolsV1.1.0] | 增加RecyclerView的通用适配器和辅助方法，增加CommonAdapter里的辅助方法 |
| [1.0.1][BaseToolsV1.0.1] | 创建ListView、GridView的通用适配器，一些辅助方法                    |


### [参考][ZhangHongYang]

[bintray]:https://bintray.com/veizhang/maven/BaseTools "Bintray"
[GridAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/GridAdapterActivity.java
[RecyclerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/RecyclerAdapterActivity.java
[ZhangHongYang]:https://github.com/hongyangAndroid/baseAdapter
[BaseToolsV1.0.1]:https://bintray.com/veizhang/maven/BaseTools/1.0.1
[BaseToolsV1.1.0]:https://bintray.com/veizhang/maven/BaseTools/1.1.0