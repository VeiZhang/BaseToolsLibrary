package com.excellence.basetoolslibrary.databinding;

import com.excellence.basetoolslibrary.databinding.lifecycle.ViewLifecycleOwner;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import static androidx.lifecycle.Lifecycle.Event.ON_CREATE;
import static androidx.lifecycle.Lifecycle.Event.ON_DESTROY;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2020/8/24
 *     desc   : RecyclerView基于生命周期的ViewHolder，用于DataBinding绑定和解绑周期
 * </pre> 
 */
public class DataBoundViewHolder extends RecyclerViewHolder implements LifecycleObserver {

    private LifecycleOwner mParentLifecycleOwner;
    private ViewLifecycleOwner mViewLifecycleOwner;

    public DataBoundViewHolder(@NonNull ViewDataBinding binding,
                               @NonNull LifecycleOwner parentLifecycleOwner) {
        super(binding);
        mParentLifecycleOwner = parentLifecycleOwner;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onParentLifecycleChange(@NonNull LifecycleOwner source,
                                        @NonNull Lifecycle.Event event) {
        mViewLifecycleOwner.handleLifecycleEvent(event);
    }

    @CallSuper
    public void markAttachedToWindow() {
        mViewLifecycleOwner = new ViewLifecycleOwner();
        mViewLifecycleOwner.initialize();
        mViewLifecycleOwner.handleLifecycleEvent(ON_CREATE);
        mBinding.setLifecycleOwner(mViewLifecycleOwner);
        mParentLifecycleOwner.getLifecycle().addObserver(this);
    }

    @CallSuper
    public void markDetachedFromWindow() {
        mParentLifecycleOwner.getLifecycle().removeObserver(this);
        mViewLifecycleOwner.handleLifecycleEvent(ON_DESTROY);
        mViewLifecycleOwner = null;
    }

    @NonNull
    public ViewLifecycleOwner getViewLifecycleOwner() {
        return mViewLifecycleOwner;
    }
}
