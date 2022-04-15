package com.excellence.tooldemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.excellence.basetoolslibrary.recycleradapter.MultiItemTypeRecyclerAdapter
import com.excellence.basetoolslibrary.recycleradapter.OnItemClickListener
import com.excellence.basetoolslibrary.recycleradapter.RecyclerViewHolder
import com.excellence.basetoolslibrary.recycleradapter.base.ItemViewDelegate
import com.excellence.tooldemo.bean.BlueData
import com.excellence.tooldemo.bean.ComputerData
import com.excellence.tooldemo.bean.People
import com.excellence.tooldemo.bean.PurpleData
import java.util.*

class MultiItemRecyclerAdapterActivity : AppCompatActivity(), OnItemClickListener {

    private var mRecyclerView: RecyclerView? = null
    private var mWarAdapter: WarAdapter? = null
    private var mMessages: MutableList<People> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_item_recycler_adapter)
        initMsg()
        mRecyclerView = findViewById<View>(R.id.multi_item_recyclerview) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mWarAdapter = WarAdapter(mMessages)
        mWarAdapter!!.setOnItemClickListener(this)
        mRecyclerView!!.adapter = mWarAdapter
    }

    private fun initMsg() {
        mMessages.add(ComputerData("欢迎来到召唤师峡谷！"))
        mMessages.add(BlueData("1 2 3 4，提莫队长正在待命"))
        mMessages.add(PurpleData("你就是个loser"))
        mMessages.add(BlueData("那个，你有看到我的小熊吗！"))
        mMessages.add(ComputerData("全军出击"))
        mMessages.add(PurpleData("Miss，怎么可能"))
        mMessages.add(BlueData("阿木木"))
        mMessages.add(ComputerData("Victory!"))
    }

    override fun onItemClick(viewHolder: RecyclerViewHolder, v: View, position: Int) {
        val people = mMessages[position]
        people.msg = "defeat"
        mWarAdapter!!.modify(people)
    }

    private inner class WarAdapter(data: List<People>?) : MultiItemTypeRecyclerAdapter<People>(data) {
        init {
            addItemViewDelegate(ComputerRecyclerDelegate())
            addItemViewDelegate(BlueRecyclerDelegate())
            addItemViewDelegate(PurpleRecyclerDelegate())
        }
    }

    private inner class ComputerRecyclerDelegate : ItemViewDelegate<People> {
        override fun getItemViewLayoutId(): Int {
            return R.layout.item_computer
        }

        override fun isForViewType(item: People?, position: Int): Boolean {
            return item is ComputerData
        }

        override fun convert(viewHolder: RecyclerViewHolder, item: People?, position: Int) {
            viewHolder.setText(R.id.computer_text, item!!.msg)
        }
    }

    private inner class BlueRecyclerDelegate : ItemViewDelegate<People> {
        override fun getItemViewLayoutId(): Int {
            return R.layout.item_blue
        }

        override fun isForViewType(item: People?, position: Int): Boolean {
            return item is BlueData
        }

        override fun convert(viewHolder: RecyclerViewHolder, item: People?, position: Int) {
            viewHolder.setText(R.id.blue_text, item!!.msg)
        }
    }

    private inner class PurpleRecyclerDelegate : ItemViewDelegate<People> {
        override fun getItemViewLayoutId(): Int {
            return R.layout.item_purple
        }

        override fun isForViewType(item: People?, position: Int): Boolean {
            return item is PurpleData
        }

        override fun convert(viewHolder: RecyclerViewHolder, item: People?, position: Int) {
            viewHolder.setText(R.id.purple_text, item!!.msg)
        }
    }
}