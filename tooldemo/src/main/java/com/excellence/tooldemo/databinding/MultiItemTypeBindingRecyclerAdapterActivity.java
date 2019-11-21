package com.excellence.tooldemo.databinding;

import android.os.Bundle;

import com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingRecyclerAdapter;
import com.excellence.tooldemo.R;
import com.excellence.tooldemo.bean.databinding.Flower;
import com.excellence.tooldemo.bean.databinding.Rose;
import com.excellence.tooldemo.bean.databinding.Tulip;
import com.excellence.tooldemo.databinding.MultiItemTypeBindingAdapterActivity.RoseViewDelegate;
import com.excellence.tooldemo.databinding.MultiItemTypeBindingAdapterActivity.TulipViewDelegate;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MultiItemTypeBindingRecyclerAdapterActivity extends AppCompatActivity {

    private ActivityMultiItemTypeBindingRecyclerAdapterBinding mBinding = null;
    private List<Flower> mFlowers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_multi_item_type_binding_recycler_adapter);

        initAdapter();
    }

    private void initAdapter() {
        mFlowers = new ArrayList<>();
        mFlowers.add(new Rose("RecyclerView, I am Rose", R.drawable.blue));
        mFlowers.add(new Rose("Hello", R.drawable.blue));
        mFlowers.add(new Tulip("e...", R.drawable.purple));
        mFlowers.add(new Rose("King is mine", R.drawable.blue));
        mFlowers.add(new Tulip("Nice", R.drawable.purple));
        MultiItemTypeBindingRecyclerAdapter<Flower> adapter = new MultiItemTypeBindingRecyclerAdapter<>(mFlowers);
        adapter.addItemViewDelegate(new RoseViewDelegate());
        adapter.addItemViewDelegate(new TulipViewDelegate());
        mBinding.setAdapter(adapter);
        mBinding.setLayoutManager(new LinearLayoutManager(this));
    }

}
