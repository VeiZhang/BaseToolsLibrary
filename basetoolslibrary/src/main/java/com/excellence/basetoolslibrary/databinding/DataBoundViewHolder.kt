package com.excellence.basetoolslibrary.databinding

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.excellence.basetoolslibrary.databinding.lifecycle.ViewLifecycleOwner

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/24
 *     desc   : RecyclerView基于生命周期的ViewHolder，用于DataBinding绑定和解绑周期
 * </pre>
 */
class DataBoundViewHolder : RecyclerViewHolder, LifecycleObserver {

    private val mParentLifecycleOwner: LifecycleOwner
    private var mViewLifecycleOwner: ViewLifecycleOwner? = null

    constructor(binding: ViewDataBinding, parentLifecycleOwner: LifecycleOwner) : super(binding) {
        mParentLifecycleOwner = parentLifecycleOwner
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onParentLifecycleChange(source: LifecycleOwner,
                                event: Lifecycle.Event) {
        mViewLifecycleOwner?.handleLifecycleEvent(event)
    }

    @CallSuper
    override fun markAttachedToWindow() {
        mViewLifecycleOwner = ViewLifecycleOwner()
        mViewLifecycleOwner?.let {
            it.initialize()
            it.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }
        mBinding.lifecycleOwner = mViewLifecycleOwner
        mParentLifecycleOwner.lifecycle.addObserver(this)
    }

    @CallSuper
    override fun markDetachedFromWindow() {
        mParentLifecycleOwner.lifecycle.removeObserver(this)
        mViewLifecycleOwner?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        mViewLifecycleOwner = null
    }

    fun getViewLifecycleOwner(): ViewLifecycleOwner? {
        return mViewLifecycleOwner
    }
}