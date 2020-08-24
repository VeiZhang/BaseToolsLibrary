package com.excellence.basetoolslibrary.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/11/14
 *     desc   : 开启dataBinding，单类型布局RecyclerView {@link ListAdapter}通用适配器，继承{@link ListAdapter}，使用内部的Diff
 * </pre> 
 */
public abstract class BaseRecyclerBindingListAdapter<T> extends MultiItemTypeBindingRecyclerListAdapter<T> {

    private int mLayoutId;
    private int mVariableId;

    public BaseRecyclerBindingListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback, @LayoutRes int layoutId, int variableId) {
        super(diffCallback);
        mLayoutId = layoutId;
        mVariableId = variableId;
    }

    public BaseRecyclerBindingListAdapter(@NonNull AsyncDifferConfig<T> config, @LayoutRes int layoutId, int variableId) {
        super(config);
        mLayoutId = layoutId;
        mVariableId = variableId;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        /**
         * 1.重写该方法，position正确，但是{@link #setViewListener(ViewDataBinding, Object)}要传入Item，而不是position
         *
         * 2.当submitList改变列表时，监听事件里面的position不对，需要纠正，
         * 可以用 {@link RecyclerView#getChildAdapterPosition(View)}
         *
         * 为了纠正position，不使用提供的position，而使用{@link List#indexOf(Object)}
         */
        T item = getItem(position);
        binding.setVariable(mVariableId, item);
        binding.executePendingBindings();
        setViewListener(binding, item);
    }
}
