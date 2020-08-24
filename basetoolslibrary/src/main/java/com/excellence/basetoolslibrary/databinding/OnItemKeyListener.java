package com.excellence.basetoolslibrary.databinding;

import android.view.KeyEvent;
import android.view.View;

import androidx.databinding.ViewDataBinding;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/24
 *     desc   :
 * </pre> 
 */
public interface OnItemKeyListener {
    boolean onKey(ViewDataBinding binding, View v, int keyCode, KeyEvent event, int position);
}