package com.excellence.tooldemo.bean.databinding;

import android.view.View;
import android.widget.ImageView;

import com.excellence.tooldemo.BR;

import androidx.annotation.DrawableRes;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/17
 *     desc   :
 * </pre>
 */

public class Flower extends BaseObservable {

    protected String mName = null;
    protected int mIconRes;

    public Flower(String name, @DrawableRes int iconRes) {
        mName = name;
        mIconRes = iconRes;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getIconRes() {
        return mIconRes;
    }

    public void setIconRes(@DrawableRes int iconRes) {
        mIconRes = iconRes;
    }

    @BindingAdapter("img")
    public static void loadImg(ImageView imageView, @DrawableRes int resId) {
        imageView.setImageResource(resId);
    }

    public void onImageClick(View view) {
        String flag = "[点击]";
        if (mName.contains(flag))
            setName(mName.replace(flag, ""));
        else
            setName(mName + flag);
        notifyPropertyChanged(BR.name);
    }
}
