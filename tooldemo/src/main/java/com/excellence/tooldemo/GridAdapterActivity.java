package com.excellence.tooldemo;

import java.util.Collections;
import java.util.List;

import com.excellence.basetoolslibrary.baseadapter.CommonAdapter;
import com.excellence.basetoolslibrary.baseadapter.ViewHolder;
import com.excellence.basetoolslibrary.utils.ActivityUtils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GridAdapterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{
	private static final String TAG = GridAdapterActivity.class.getSimpleName();

	private Button mRefreshBtn = null;
	private GridView mGridView = null;
	private AppGridAdapter mAppGridAdapter = null;
	private List<ResolveInfo> mAppList = null;
	private PackageManager mPackageManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_adapter);

		init();
		setAdapter();
	}

	private void init()
	{
		mPackageManager = getPackageManager();
		mRefreshBtn = (Button) findViewById(R.id.refresh_btn);
		mGridView = (GridView) findViewById(R.id.adapter_gridview);
		mRefreshBtn.setOnClickListener(this);
		mGridView.setOnItemClickListener(this);
	}

	private void setAdapter()
	{
		// 模拟刷新
		if (mAppList == null)
			mAppList = getAppList();
		else
			mAppList = null;

		if (mAppGridAdapter == null)
		{
			mAppGridAdapter = new AppGridAdapter(this, mAppList, android.R.layout.activity_list_item);
			mGridView.setAdapter(mAppGridAdapter);
		}
		else
			mAppGridAdapter.notifyNewData(mAppList);
	}

	@Override
	public void onClick(View v)
	{
		setAdapter();
	}

	private List<ResolveInfo> getAppList()
	{
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> allResolveInfos = mPackageManager.queryIntentActivities(mainIntent, 0);
		Collections.sort(allResolveInfos, new ResolveInfo.DisplayNameComparator(mPackageManager));
		return allResolveInfos;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		boolean result = ActivityUtils.startAnotherActivity(this, mAppList.get(position).activityInfo.packageName);
		if (!result)
			Toast.makeText(this, mAppList.get(position).loadLabel(getPackageManager()) + "打开失败", Toast.LENGTH_SHORT).show();
	}

	private class AppGridAdapter extends CommonAdapter<ResolveInfo>
	{
		public AppGridAdapter(Context context, List<ResolveInfo> datas, int layoutId)
		{
			super(context, datas, layoutId);
		}

		@Override
		public void convert(ViewHolder viewHolder, ResolveInfo item, int position)
		{
			ImageView iconView = viewHolder.getView(android.R.id.icon);
			iconView.setImageDrawable(item.loadIcon(mPackageManager));
			viewHolder.setText(android.R.id.text1, item.loadLabel(mPackageManager));
		}
	}
}
