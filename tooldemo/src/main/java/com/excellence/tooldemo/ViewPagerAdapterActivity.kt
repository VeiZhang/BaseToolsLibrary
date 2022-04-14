package com.excellence.tooldemo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.excellence.basetoolslibrary.pageradapter.BasePagerAdapter

class ViewPagerAdapterActivity : AppCompatActivity() {

    private var mViewPager: ViewPager? = null
    private var mAdapter: NumAdapter? = null
    private var mPageCount = 1
    private var mNumScale = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_adapter)
        mViewPager = findViewById<View>(R.id.view_pager) as ViewPager
        mAdapter = NumAdapter(mPageCount)
        mViewPager!!.adapter = mAdapter
    }

    private inner class NumAdapter(pageCount: Int) : BasePagerAdapter(pageCount) {
        override fun loadView(context: Context, pageIndex: Int): View {
            // 加载每页
            val textView = TextView(context)
            textView.text = (pageIndex * mNumScale).toString()
            textView.setOnClickListener {
                mNumScale *= 2
                mPageCount += 1
                // 刷新每页
                mAdapter!!.notifyNewData(mPageCount)
            }
            return textView
        }
    }
}