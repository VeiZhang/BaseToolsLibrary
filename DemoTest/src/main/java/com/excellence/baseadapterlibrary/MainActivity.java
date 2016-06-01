package com.excellence.baseadapterlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	private GridView mGridView = null;
	private TestAdapter mAdapter = null;
	List<Integer> mDatas = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setData();
		initGridView();
		setNotifyBtn();
	}

	private void setNotifyBtn()
	{
		Button button = (Button) findViewById(R.id.btn_notify);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				setData();
				mAdapter.notifyNewData(mDatas);
			}
		});
	}

	private void initGridView()
	{
		mGridView = (GridView) findViewById(R.id.gridView);
		mAdapter = new TestAdapter(this, mDatas, R.layout.grid_item);
		mGridView.setAdapter(mAdapter);
	}

	private void setData()
	{
		if (mDatas == null)
			mDatas = new ArrayList<>();
		mDatas.clear();

		for (int i = 0; i < Math.random() * 50 + 20; i++)
			mDatas.add(i);
	}
}
