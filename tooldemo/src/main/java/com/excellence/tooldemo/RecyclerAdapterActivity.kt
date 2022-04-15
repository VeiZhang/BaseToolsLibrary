package com.excellence.tooldemo

import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.excellence.basetoolslibrary.recycleradapter.BaseRecyclerAdapter
import com.excellence.basetoolslibrary.recycleradapter.OnItemClickListener
import com.excellence.basetoolslibrary.recycleradapter.RecyclerViewHolder
import com.excellence.basetoolslibrary.utils.AppUtils.getAllInstalledApps
import com.excellence.basetoolslibrary.utils.AppUtils.getSystemInstalledApps
import com.excellence.basetoolslibrary.utils.AppUtils.getUserInstalledApps

class RecyclerAdapterActivity : AppCompatActivity(), View.OnClickListener, OnItemClickListener {

    private var mRefreshBtn: Button? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: AppRecyclerAdapter? = null
    private var mAppList: List<ResolveInfo>? = null
    private var mAppType = APP_TYPE_ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        init()
        setAdapter()
        setListener()
    }

    private fun init() {
        mRefreshBtn = findViewById<View>(R.id.recycler_refresh) as Button
        mRecyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        val manager = GridLayoutManager(this, 2)
        mRecyclerView!!.layoutManager = manager
    }

    private fun setAdapter() {
        // 模拟刷新
        when (mAppType % 3) {
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
        }
        mAppType++
        if (mAdapter == null) {
            mAdapter = AppRecyclerAdapter(mAppList, android.R.layout.activity_list_item)
            mRecyclerView!!.adapter = mAdapter
        } else {
            mAdapter!!.notifyNewData(mAppList)
        }
    }

    private fun setListener() {
        mRefreshBtn!!.setOnClickListener(this)
        mAdapter!!.setOnItemClickListener(this)
    }

    override fun onClick(v: View) {
        setAdapter()
    }

    override fun onItemClick(viewHolder: RecyclerViewHolder, v: View, position: Int) {
        Toast.makeText(this, "position " + position + " : " + (viewHolder.getView<View>(android.R.id.text1) as TextView?)!!.text, Toast.LENGTH_SHORT).show()
    }

    private inner class AppRecyclerAdapter(data: List<ResolveInfo>?, layoutId: Int) : BaseRecyclerAdapter<ResolveInfo>(data, layoutId) {

        private var mPackageManager: PackageManager? = null

        override fun convert(viewHolder: RecyclerViewHolder, item: ResolveInfo?, position: Int) {
            viewHolder.setText(android.R.id.text1, item!!.loadLabel(mPackageManager))
            viewHolder.setImageDrawable(android.R.id.icon, item.loadIcon(mPackageManager))
        }

        init {
            mPackageManager = packageManager
        }
    }

    companion object {
        private val TAG = RecyclerAdapterActivity::class.java.simpleName
        private const val APP_TYPE_ALL = 0
        private const val APP_TYPE_SYSTEM = 1
        private const val APP_TYPE_USER = 2
    }
}