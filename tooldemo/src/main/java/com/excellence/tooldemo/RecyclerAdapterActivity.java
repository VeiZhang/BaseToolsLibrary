package com.excellence.tooldemo;

import java.util.List;

import com.excellence.basetoolslibrary.recycleradapter.BaseRecyclerAdapter;
import com.excellence.basetoolslibrary.recycleradapter.RecyclerViewHolder;
import com.excellence.basetoolslibrary.utils.PackageUtils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerAdapterActivity extends AppCompatActivity implements View.OnClickListener, BaseRecyclerAdapter.OnItemClickListener
{
	private static final String TAG = RecyclerAdapterActivity.class.getSimpleName();

	private Button mRefreshBtn = null;
	private RecyclerView mRecyclerView = null;
	private AppRecyclerAdapter mAdapter = null;
	private List<ResolveInfo> mAppList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler);

		init();
		setAdapter();
		setListener();
	}

	private void init()
	{
		mRefreshBtn = (Button) findViewById(R.id.recycler_refresh);
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		GridLayoutManager manager = new GridLayoutManager(this, 2);
		mRecyclerView.setLayoutManager(manager);
	}

	private void setAdapter()
	{
		if (mAppList == null)
			mAppList = PackageUtils.getInstalledApps(this);
		else
			mAppList = null;

		if (mAdapter == null)
		{
			mAdapter = new AppRecyclerAdapter(this, mAppList, android.R.layout.activity_list_item);
			mRecyclerView.setAdapter(mAdapter);
		}
		else
			mAdapter.notifyNewData(mAppList);
	}

	private void setListener()
	{
		mRefreshBtn.setOnClickListener(this);
		mAdapter.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		setAdapter();
	}

	@Override
	public void onItemClick(RecyclerViewHolder viewHolder, View v, int position)
	{
		Toast.makeText(this, "position " + position + " : " + ((TextView) viewHolder.getView(android.R.id.text1)).getText(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onItemLongClick(RecyclerViewHolder viewHolder, View v, int position)
	{
		return false;
	}

	private class AppRecyclerAdapter extends BaseRecyclerAdapter<ResolveInfo>
	{
		private PackageManager mPackageManager = null;

		public AppRecyclerAdapter(Context context, List<ResolveInfo> datas, int layoutId)
		{
			super(context, datas, layoutId);
			mPackageManager = context.getPackageManager();
		}

		@Override
		public void convert(RecyclerViewHolder viewHolder, ResolveInfo item, int position)
		{
			viewHolder.setText(android.R.id.text1, item.loadLabel(mPackageManager));
			viewHolder.setImageDrawable(android.R.id.icon, item.loadIcon(mPackageManager));
		}

	}
}
