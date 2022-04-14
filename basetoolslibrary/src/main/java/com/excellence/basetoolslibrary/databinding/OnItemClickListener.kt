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
interface OnItemClickListener {

    fun onItemClick(binding: ViewDataBinding, v: View, position: Int)

}