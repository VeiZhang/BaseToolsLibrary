package com.excellence.tooldemo.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.excellence.basetoolslibrary.databinding.BaseRecyclerBindingAdapter;
import com.excellence.tooldemo.BR;
import com.excellence.tooldemo.R;
import com.excellence.tooldemo.bean.databinding.Flower;

import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerBindingAdapterActivity extends AppCompatActivity
{
	private ActivityBaseRecyclerBindingAdapterBinding mBinding = null;
	private List<Flower> mFlowers = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_recycler_binding_adapter);

		initAdapter();
	}

	private void initAdapter()
	{
		mFlowers = new ArrayList<>();
		for (int i = 0; i < 10; i++)
			mFlowers.add(new Flower("Flower" + i, R.drawable.logo));
		BaseRecyclerBindingAdapter<Flower> adapter = new BaseRecyclerBindingAdapter<>(mFlowers, R.layout.item_flower, BR.flower);
		mBinding.setAdapter(adapter);
		mBinding.setLayoutManager(new LinearLayoutManager(this));
	}
}
