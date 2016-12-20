package com.excellence.tooldemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.excellence.basetoolslibrary.utils.PackageUtils;

import java.util.List;

public class RecyclerAdapterActivity extends AppCompatActivity implements View.OnClickListener
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
			mAppList = PackageUtils.getResolveInfoApps(this);
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
	}

	@Override
	public void onClick(View v)
	{
		setAdapter();
	}

	private class AppRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>
	{
		private Context mContext = null;
		private List<ResolveInfo> mAppList = null;
		private int mLayoutId = 0;
		private PackageManager mPackageManager = null;

		public AppRecyclerAdapter(Context context, List<ResolveInfo> appList, int layoutId)
		{
			mContext = context;
			mAppList = appList;
			mLayoutId = layoutId;
			mPackageManager = context.getPackageManager();
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
		{
			View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
			return new ViewHolder(itemView);
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position)
		{
			holder.mTextView.setText(mAppList.get(position).loadLabel(mPackageManager));
			holder.mImageView.setImageDrawable(mAppList.get(position).loadIcon(mPackageManager));
			holder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Toast.makeText(mContext, "position " + position + " : " + holder.mTextView.getText(), Toast.LENGTH_SHORT).show();
				}
			});
		}

		@Override
		public int getItemCount()
		{
			return mAppList == null ? 0 : mAppList.size();
		}

		public void notifyNewData(List<ResolveInfo> appList)
		{
			mAppList = appList;
			notifyDataSetChanged();
		}
	}

	private class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView mTextView;
		ImageView mImageView;

		public ViewHolder(View itemView)
		{
			super(itemView);
			mTextView = (TextView) itemView.findViewById(android.R.id.text1);
			mImageView = (ImageView) itemView.findViewById(android.R.id.icon);
		}
	}
}
