package com.excellence.basetoolslibrary.recycleradapter;

import android.view.KeyEvent;
import android.view.View;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/24
 *     desc   :
 * </pre> 
 */
public interface OnItemKeyListener {
    boolean onKey(RecyclerViewHolder viewHolder, View v, int keyCode, KeyEvent event, int position);
}