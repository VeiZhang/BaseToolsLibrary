package com.excellence.tooldemo.bean.databinding;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/17
 *     desc   :
 * </pre>
 */

public class Flower
{
	protected String mName = null;
	protected int mIconRes;

	public Flower(String name, @DrawableRes int iconRes)
	{
		mName = name;
		mIconRes = iconRes;
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public int getIconRes()
	{
		return mIconRes;
	}

	public void setIconRes(@DrawableRes int iconRes)
	{
		mIconRes = iconRes;
	}

	@BindingAdapter("img")
	public static void loadImg(ImageView imageView, @DrawableRes int resId)
	{
		imageView.setImageResource(resId);
	}

	public void onImageClick(View view)
	{
		Toast.makeText(view.getContext(), "点击了图片", Toast.LENGTH_SHORT).show();
	}
}
