package com.excellence.basetoolslibrary.recycleradapter;

import android.view.View;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/24
 *     desc   :
 * </pre> 
 */
public interface OnItemFocusChangeListener {
    void onItemFocusChange(RecyclerViewHolder viewHolder, View v, boolean hasFocus, int position);
}