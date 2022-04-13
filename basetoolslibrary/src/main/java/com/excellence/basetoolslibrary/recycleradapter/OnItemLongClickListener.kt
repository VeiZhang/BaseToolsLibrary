package com.excellence.basetoolslibrary.recycleradapter

import android.view.View

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/24
 *     desc   :
 * </pre>
 */
interface OnItemLongClickListener {
    fun onItemLongClick(viewHolder: RecyclerViewHolder, v: View, position: Int): Boolean
}