package com.excellence.tooldemo;

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

import com.excellence.basetoolslibrary.recycleradapter.BaseRecyclerAdapter;
import com.excellence.basetoolslibrary.recycleradapter.RecyclerViewHolder;
import com.excellence.basetoolslibrary.utils.AppUtils;

import java.util.List;

public class RecyclerAdapterActivity extends AppCompatActivity implements View.OnClickListener, BaseRecyclerAdapter.OnItemClickListener {

    private static final String TAG = RecyclerAdapterActivity.class.getSimpleName();

    private static final int APP_TYPE_ALL = 0;
    private static final int APP_TYPE_SYSTEM = 1;
    private static final int APP_TYPE_USER = 2;

    private Button mRefreshBtn = null;
    private RecyclerView mRecyclerView = null;
    private AppRecyclerAdapter mAdapter = null;
    private List<ResolveInfo> mAppList = null;
    private int mAppType = APP_TYPE_ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        init();
        setAdapter();
        setListener();
    }

    private void init() {
        mRefreshBtn = (Button) findViewById(R.id.recycler_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(manager);
    }

    private void setAdapter() {
        // 模拟刷新
        if (mAppList != null) {
            mAppList.clear();
        }

        switch (mAppType % 3) {
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

        if (mAdapter == null) {
            mAdapter = new AppRecyclerAdapter(mAppList, android.R.layout.activity_list_item);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyNewData(mAppList);
        }
    }

    private void setListener() {
        mRefreshBtn.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        setAdapter();
    }

    @Override
    public void onItemClick(RecyclerViewHolder viewHolder, View v, int position) {
        Toast.makeText(this, "position " + position + " : " + ((TextView) viewHolder.getView(android.R.id.text1)).getText(), Toast.LENGTH_SHORT).show();
    }

    private class AppRecyclerAdapter extends BaseRecyclerAdapter<ResolveInfo> {
        private PackageManager mPackageManager = null;

        public AppRecyclerAdapter(List<ResolveInfo> data, int layoutId) {
            super(data, layoutId);
            mPackageManager = getPackageManager();
        }

        @Override
        public void convert(RecyclerViewHolder viewHolder, ResolveInfo item, int position) {
            viewHolder.setText(android.R.id.text1, item.loadLabel(mPackageManager));
            viewHolder.setImageDrawable(android.R.id.icon, item.loadIcon(mPackageManager));
        }

    }
}
