package com.excellence.tooldemo.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.excellence.basetoolslibrary.databinding.BaseRecyclerBindingAdapter;
import com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingRecyclerAdapter;
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
			mFlowers.add(new Flower("Flower x Flower = Flowers " + i, R.drawable.logo));
		BaseRecyclerBindingAdapter<Flower> adapter = new BaseRecyclerBindingAdapter<>(mFlowers, R.layout.item_flower, BR.flower);
		mBinding.setAdapter(adapter);
		mBinding.setLayoutManager(new LinearLayoutManager(this));
		adapter.setOnItemClickListener(new MultiItemTypeBindingRecyclerAdapter.OnItemClickListener()
		{
			@Override
			public void onItemClick(ViewDataBinding binding, View v, int position)
			{
				Toast.makeText(BaseRecyclerBindingAdapterActivity.this, "点击事件" + position, Toast.LENGTH_SHORT).show();
			}
		});

		adapter.setOnItemLongClickListener(new MultiItemTypeBindingRecyclerAdapter.OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(ViewDataBinding binding, View v, int position)
			{
				Toast.makeText(BaseRecyclerBindingAdapterActivity.this, "长按事件" + position, Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		adapter.setOnItemFocusChangeListener(new MultiItemTypeBindingRecyclerAdapter.OnItemFocusChangeListener()
		{
			@Override
			public void onItemFocusChange(ViewDataBinding binding, View v, boolean hasFocus, int position)
			{
				ItemFlowerBinding itemFlowerBinding = (ItemFlowerBinding) binding;
				itemFlowerBinding.text.setSelected(hasFocus);
				if (hasFocus)
					Toast.makeText(BaseRecyclerBindingAdapterActivity.this, "焦点事件 " + position, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
