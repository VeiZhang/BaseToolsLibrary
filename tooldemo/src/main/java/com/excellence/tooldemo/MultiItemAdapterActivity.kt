package com.excellence.tooldemo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.excellence.basetoolslibrary.baseadapter.MultiItemTypeAdapter
import com.excellence.basetoolslibrary.baseadapter.ViewHolder
import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegate
import com.excellence.tooldemo.bean.BlueData
import com.excellence.tooldemo.bean.ComputerData
import com.excellence.tooldemo.bean.People
import com.excellence.tooldemo.bean.PurpleData
import java.util.*

class MultiItemAdapterActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var mListView: ListView? = null
    private var mMessages: MutableList<People> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_item_adapter)
        initMsg()
        mListView = findViewById<View>(R.id.multi_item_listview) as ListView
        mListView!!.adapter = ChatAdapter(mMessages)
        mListView!!.onItemClickListener = this
    }

    private fun initMsg() {
        mMessages.add(ComputerData("全军出击！"))
        mMessages.add(BlueData("有大吗？"))
        mMessages.add(PurpleData("30秒！"))
        mMessages.add(BlueData("ad注意走位"))
        mMessages.add(ComputerData("killing spree！"))
        mMessages.add(PurpleData("我被抓了。"))
        mMessages.add(PurpleData("救我。。。"))
        mMessages.add(BlueData("保护我"))
        mMessages.add(ComputerData("Legendary"))
        mMessages.add(ComputerData("penta kill"))
        mMessages.add(ComputerData("ACE!"))
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val adapter = parent.adapter as ChatAdapter
        mMessages[position].msg = "要死了，要死了。。。"
        adapter.notifyNewData(mMessages)
    }

    private inner class ChatAdapter(messages: List<People>?) : MultiItemTypeAdapter<People?>(messages) {
        init {
            addItemViewDelegate(ComputerDelegate())
            addItemViewDelegate(BlueDelegate())
            addItemViewDelegate(PurpleDelegate())
        }
    }

    private inner class ComputerDelegate : ItemViewDelegate<People?> {
        override fun getItemViewLayoutId(): Int {
            return R.layout.item_computer
        }

        override fun isForViewType(item: People?, position: Int): Boolean {
            return item is ComputerData
        }

        override fun convert(viewHolder: ViewHolder, item: People?, position: Int) {
            viewHolder.setText(R.id.computer_text, item!!.msg)
        }
    }

    private inner class BlueDelegate : ItemViewDelegate<People?> {
        override fun getItemViewLayoutId(): Int {
            return R.layout.item_blue
        }

        override fun isForViewType(item: People?, position: Int): Boolean {
            return item is BlueData
        }

        override fun convert(viewHolder: ViewHolder, item: People?, position: Int) {
            viewHolder.setText(R.id.blue_text, item!!.msg)
        }
    }

    private inner class PurpleDelegate : ItemViewDelegate<People?> {
        override fun getItemViewLayoutId(): Int {
            return R.layout.item_purple
        }

        override fun isForViewType(item: People?, position: Int): Boolean {
            return item is PurpleData
        }

        override fun convert(viewHolder: ViewHolder, item: People?, position: Int) {
            viewHolder.setText(R.id.purple_text, item!!.msg)
        }
    }
}