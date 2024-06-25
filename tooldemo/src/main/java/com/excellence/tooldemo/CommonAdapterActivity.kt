package com.excellence.tooldemo

import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.excellence.basetoolslibrary.baseadapter.CommonAdapter
import com.excellence.basetoolslibrary.baseadapter.ViewHolder
import com.excellence.basetoolslibrary.utils.ActivityUtils.startAnotherActivity
import com.excellence.basetoolslibrary.utils.AppUtils.getAllApps
import com.excellence.basetoolslibrary.utils.AppUtils.getAllInstalledApps
import com.excellence.basetoolslibrary.utils.AppUtils.getRunningApps
import com.excellence.basetoolslibrary.utils.AppUtils.getSystemInstalledApps
import com.excellence.basetoolslibrary.utils.AppUtils.getUserInstalledApps
import com.excellence.tooldemo.RecyclerAdapterActivity.Companion.APP_TYPE_ALL
import com.excellence.tooldemo.RecyclerAdapterActivity.Companion.APP_TYPE_RUNNING
import com.excellence.tooldemo.RecyclerAdapterActivity.Companion.APP_TYPE_SYSTEM
import com.excellence.tooldemo.RecyclerAdapterActivity.Companion.APP_TYPE_USER

class CommonAdapterActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {

    private var mRefreshBtn: Button? = null
    private var mGridView: GridView? = null
    private var mAppGridAdapter: AppGridAdapter? = null
    private var mAppList: List<ResolveInfo>? = null
    private var mPackageManager: PackageManager? = null
    private var mAppType = APP_TYPE_ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_adapter)
        init()
        setAdapter()
    }

    private fun init() {
        mPackageManager = packageManager
        mRefreshBtn = findViewById<View>(R.id.refresh_btn) as Button
        mGridView = findViewById<View>(R.id.adapter_gridview) as GridView
        mRefreshBtn!!.setOnClickListener(this)
        mGridView!!.onItemClickListener = this
    }

    private fun setAdapter() {
        // 模拟刷新
        when (mAppType % 5) {
            APP_TYPE_ALL -> {
                mAppList = getAllInstalledApps(this)
                mRefreshBtn!!.setText(R.string.all_apps)
            }
            APP_TYPE_SYSTEM -> {
                mAppList = getSystemInstalledApps(this)
                mRefreshBtn!!.setText(R.string.system_apps)
            }
            APP_TYPE_USER -> {
                mAppList = getUserInstalledApps(this)
                mRefreshBtn!!.setText(R.string.user_apps)
            }
            APP_TYPE_RUNNING -> {
                mAppList = getRunningApps(this)
                mRefreshBtn!!.setText(R.string.running_apps)
            }
            else -> {
                val packageList = getAllApps(this)
                for (item in packageList) {
                    println(item.packageName)
                }
                mAppList = ArrayList()
                mRefreshBtn!!.setText(R.string.all_packages)
            }
        }
        mAppType++
        if (mAppGridAdapter == null) {
            mAppGridAdapter = AppGridAdapter(mAppList, android.R.layout.activity_list_item)
            mGridView!!.adapter = mAppGridAdapter
        } else {
            mAppGridAdapter!!.notifyNewData(mAppList)
        }
    }

    override fun onClick(v: View) {
        setAdapter()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        val item = mAppList!![position]
        println(item.activityInfo.packageName)
        val result = startAnotherActivity(this, item.activityInfo.packageName)
        if (!result) {
            Toast.makeText(this, item.loadLabel(packageManager).toString() + "打开失败", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class AppGridAdapter(data: List<ResolveInfo>?, layoutId: Int) : CommonAdapter<ResolveInfo>(data, layoutId) {
        override fun convert(viewHolder: ViewHolder, item: ResolveInfo?, position: Int) {
            val iconView = viewHolder.getView<ImageView>(android.R.id.icon)
            iconView!!.setImageDrawable(item!!.loadIcon(mPackageManager))
            viewHolder.setText(android.R.id.text1, item.loadLabel(mPackageManager))
        }
    }

    companion object {

        private val TAG = CommonAdapterActivity::class.java.simpleName

    }
}