package com.excellence.basetoolslibrary.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/4/14
 *     desc   : 开启dataBinding，ListView、GridView通用适配器
 * </pre>
 */
open class CommonBindingAdapter<T> : MultiItemTypeBindingAdapter<T> {

    private val mLayoutId: Int
    private val mVariableId: Int

    constructor(data: Array<T>?, @LayoutRes layoutId: Int, variableId: Int) : this(if (data == null) null else listOf(*data), layoutId, variableId)

    constructor(data: List<T>?, @LayoutRes layoutId: Int, variableId: Int) : super(data) {
        mLayoutId = layoutId
        mVariableId = variableId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val binding: ViewDataBinding?

        binding = if (convertView == null) {
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), mLayoutId, parent, false)
        } else {
            DataBindingUtil.getBinding(convertView)
        }
        binding!!.setVariable(mVariableId, getItem(position))
        return binding.root
    }
}