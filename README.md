# BaseToolsLibrary 常用的工具

<!--[Maven](https://bintray.com/veizhang/maven/BaseTools "Bintray")-->
[Maven Control][bintray][1.0.1]
<!-- you should configure jcenter repository-->
##导入Android Studio
添加jCenter远程依赖到module里的build.gradle：
```
  dependencies {
    compile 'com.excellence:BaseTools:1.0.1'
  }
```
或者直接添加本地Library依赖
```
    compile project(':basetoolslibrary')
```

##1.CommonAdapter

####gridview，listview的通用适配器

示例：[AdapterActivity][AdapterActivity]


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
    ViewHolder内有一些方便的方法
    
    public <T extends View> T getView(int viewId); 用于获取Item内的子控件，参数为控件的id
    public ViewHolder setText(int viewId, int strId); 用于设置文本，参数控件id、字符串id
    public ViewHolder setBackgroundResource(int viewId, int resId); 用于设置背景图片，参数控件id、图片id
    public ViewHolder setImageResource(int viewId, int resId); 用于设置ImageView图片资源，参数控件id、图片id
    * 
    * 
    * 
    其他方法有待增加
```


```刷新
    刷新适配器
    adapter.notifyNewData(data);
```


[bintray]:https://bintray.com/veizhang/maven/BaseTools "Bintray"
[AdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/AdapterActivity.java
