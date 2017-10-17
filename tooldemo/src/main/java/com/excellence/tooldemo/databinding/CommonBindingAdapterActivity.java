package com.excellence.tooldemo.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.excellence.basetoolslibrary.databinding.CommonBindingAdapter;
import com.excellence.tooldemo.BR;
import com.excellence.tooldemo.R;
import com.excellence.tooldemo.bean.databinding.Flower;

import java.util.ArrayList;
import java.util.List;

public class CommonBindingAdapterActivity extends AppCompatActivity
{
	private ActivityCommonBindingAdapterBinding mBinding = null;

	private List<Flower> mFlowers = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_common_binding_adapter);

		initAdapter();
	}

	private void initAdapter()
	{
		mFlowers = new ArrayList<>();
		for (int i = 0; i < 10; i++)
			mFlowers.add(new Flower("Flower" + i, R.drawable.logo));
		CommonBindingAdapter<Flower> adapter = new CommonBindingAdapter<>(mFlowers, R.layout.item_flower, BR.flower);
		mBinding.setAdapter(adapter);
		mBinding.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Toast.makeText(CommonBindingAdapterActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
