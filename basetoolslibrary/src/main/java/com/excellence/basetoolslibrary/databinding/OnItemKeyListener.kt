package com.excellence.basetoolslibrary.databinding

import android.view.KeyEvent
import android.view.View
import androidx.databinding.ViewDataBinding

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/24
 *     desc   :
 * </pre>
 */
interface OnItemKeyListener {

    fun onKey(binding: ViewDataBinding, v: View, keyCode: Int, event: KeyEvent, position: Int): Boolean

}