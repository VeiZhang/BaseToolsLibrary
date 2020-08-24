package com.excellence.basetoolslibrary.databinding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2019/11/14
 *     desc   :
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
        binding.setVariable(mVariableId, getItem(position));
        binding.executePendingBindings();
        setViewListener(binding, position);
    }
}
