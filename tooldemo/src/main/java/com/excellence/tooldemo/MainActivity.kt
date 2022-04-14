package com.excellence.tooldemo

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.excellence.basetoolslibrary.utils.ActivityUtils.startAnotherActivity
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var mListView: ListView? = null
    private var mActivityNameList: MutableMap<String, String>? = null
    private var mActivityClsList: MutableMap<String, Class<out Activity>?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initActivityList()
    }

    private fun init() {
        mListView = findViewById<View>(R.id.function_list) as ListView
        mListView!!.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.function_list))
        mListView!!.onItemClickListener = this
    }

    /**
     * 关联界面名字、Activity名字
     * 关联Activity名字、Activity.class类
     */
    private fun initActivityList() {
        mActivityNameList = HashMap()
        mActivityClsList = HashMap()
        val functionNames = resources.getStringArray(R.array.function_list)
        val activityNames = resources.getStringArray(R.array.function_activity_list)
        for (i in functionNames.indices) {
            mActivityNameList!![functionNames[i]] = activityNames[i]
            try {
                // 根据Activity类名搜索Activity.class类
                mActivityClsList!![activityNames[i]] = Class.forName(activityNames[i]) as Class<out Activity>?
            } catch (e: Exception) {
                mActivityClsList!![activityNames[i]] = null
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        /**
         * ①List可存储Activity.class
         * ②List存储Activity名字，通过名字查找Activity.class
         */
        startAnotherActivity(this, mActivityClsList!![mActivityNameList!![parent.getItemAtPosition(position)]]!!)
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName

    }
}