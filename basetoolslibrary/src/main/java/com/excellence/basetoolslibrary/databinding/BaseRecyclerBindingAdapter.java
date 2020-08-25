package com.excellence.basetoolslibrary.databinding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/17
 *     desc   : 开启dataBinding，RecyclerView通用适配器
 *
 *              拓展：ViewDataBinding绑定生命周期LifecycleOwner [可选]
 * </pre>
 */

public class BaseRecyclerBindingAdapter<T> extends MultiItemTypeBindingRecyclerAdapter<T> {

    private int mLayoutId;
    private int mVariableId;

    public BaseRecyclerBindingAdapter(T[] data, @LayoutRes int layoutId, int variableId) {
        this(data, layoutId, variableId, null);
    }

    public BaseRecyclerBindingAdapter(List<T> data, @LayoutRes int layoutId, int variableId) {
        this(data, layoutId, variableId, null);
    }

    public BaseRecyclerBindingAdapter(T[] data, @LayoutRes int layoutId, int variableId,
                                      LifecycleOwner lifecycleOwner) {
        this(data == null ? null : Arrays.asList(data), layoutId, variableId, lifecycleOwner);
    }

    public BaseRecyclerBindingAdapter(List<T> data, @LayoutRes int layoutId, int variableId,
                                      LifecycleOwner lifecycleOwner) {
        super(data, lifecycleOwner);
        mLayoutId = layoutId;
        mVariableId = variableId;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
        return RecyclerViewHolder.getViewHolder(binding, mLifecycleOwner);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(mVariableId, getItem(position));
        binding.executePendingBindings();
        setViewListener(holder, position);
    }
}
