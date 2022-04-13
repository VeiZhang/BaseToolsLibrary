package com.excellence.basetoolslibrary.recycleradapter

import android.view.KeyEvent
import android.view.View

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/24
 *     desc   :
 * </pre>
 */
interface OnItemKeyListener {
    fun onKey(viewHolder: RecyclerViewHolder, v: View, keyCode: Int, event: KeyEvent, position: Int): Boolean
}