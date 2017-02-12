package com.excellence.tooldemo;

import java.util.List;

import com.excellence.basetoolslibrary.baseadapter.CommonAdapter;
import com.excellence.basetoolslibrary.baseadapter.ViewHolder;
import com.excellence.basetoolslibrary.utils.ActivityUtils;
import com.excellence.basetoolslibrary.utils.AppUtils;

import android.content.Context;
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

	private static final int APP_TYPE_ALL = 0;
	private static final int APP_TYPE_SYSTEM = 1;
	private static final int APP_TYPE_USER = 2;

	private Button mRefreshBtn = null;
	private GridView mGridView = null;
	private AppGridAdapter mAppGridAdapter = null;
	private List<ResolveInfo> mAppList = null;
	private PackageManager mPackageManager = null;
	private int mAppType = APP_TYPE_ALL;

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
		if (mAppList != null)
			mAppList.clear();

		switch (mAppType % 3)
		{
		case APP_TYPE_ALL:
			mAppList = AppUtils.getAllInstalledApps(this);
			mRefreshBtn.setText(R.string.all_apps);
			break;

		case APP_TYPE_SYSTEM:
			mAppList = AppUtils.getSystemInstalledApps(this);
			mRefreshBtn.setText(R.string.system_apps);
			break;

		case APP_TYPE_USER:
			mAppList = AppUtils.getUserInstalledApps(this);
			mRefreshBtn.setText(R.string.user_apps);
			break;
		}
		mAppType++;

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
