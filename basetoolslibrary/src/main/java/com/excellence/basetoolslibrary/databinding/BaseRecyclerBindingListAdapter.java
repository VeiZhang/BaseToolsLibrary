package com.excellence.basetoolslibrary.databinding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/11/14
 *     desc   : 开启dataBinding，单类型布局RecyclerView {@link ListAdapter}通用适配器，继承{@link ListAdapter}，使用内部的Diff
 *
 *              拓展：ViewDataBinding绑定生命周期LifecycleOwner [可选]
 * </pre> 
 */
public abstract class BaseRecyclerBindingListAdapter<T> extends MultiItemTypeBindingRecyclerListAdapter<T> {

    private int mLayoutId;
    private int mVariableId;

    public BaseRecyclerBindingListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback,
                                          @LayoutRes int layoutId, int variableId) {
        this(diffCallback, layoutId, variableId, null);
    }

    public BaseRecyclerBindingListAdapter(@NonNull AsyncDifferConfig<T> config,
                                          @LayoutRes int layoutId, int variableId) {
        this(config, layoutId, variableId, null);
    }

    public BaseRecyclerBindingListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback,
                                          @LayoutRes int layoutId, int variableId,
                                          LifecycleOwner lifecycleOwner) {
        super(diffCallback, lifecycleOwner);
        mLayoutId = layoutId;
        mVariableId = variableId;
    }

    public BaseRecyclerBindingListAdapter(@NonNull AsyncDifferConfig<T> config,
                                          @LayoutRes int layoutId, int variableId,
                                          LifecycleOwner lifecycleOwner) {
        super(config, lifecycleOwner);
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
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(mVariableId, getItem(position));
        binding.executePendingBindings();
        setViewListener(holder, position);
    }
}
