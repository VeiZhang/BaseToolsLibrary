package com.excellence.basetoolslibrary.databinding

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
interface OnItemFocusChangeListener {

    fun onItemFocusChange(binding: ViewDataBinding, v: View, hasFocus: Boolean, position: Int)

}