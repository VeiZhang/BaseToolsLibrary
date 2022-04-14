package com.excellence.tooldemo.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingRecyclerAdapter
import com.excellence.tooldemo.R
import com.excellence.tooldemo.bean.databinding.Flower
import com.excellence.tooldemo.bean.databinding.Rose
import com.excellence.tooldemo.bean.databinding.Tulip
import com.excellence.tooldemo.databinding.MultiItemTypeBindingAdapterActivity.RoseViewDelegate
import com.excellence.tooldemo.databinding.MultiItemTypeBindingAdapterActivity.TulipViewDelegate
import java.util.*

class MultiItemTypeBindingRecyclerAdapterActivity : AppCompatActivity() {

    private var mBinding: ActivityMultiItemTypeBindingRecyclerAdapterBinding? = null
    private var mFlowers: MutableList<Flower> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_multi_item_type_binding_recycler_adapter)
        initAdapter()
    }

    private fun initAdapter() {
        mFlowers.add(Rose("RecyclerView, I am Rose", R.drawable.blue))
        mFlowers.add(Rose("Hello", R.drawable.blue))
        mFlowers.add(Tulip("e...", R.drawable.purple))
        mFlowers.add(Rose("King is mine", R.drawable.blue))
        mFlowers.add(Tulip("Nice", R.drawable.purple))

        val adapter = MultiItemTypeBindingRecyclerAdapter(mFlowers)
        adapter.addItemViewDelegate(RoseViewDelegate())
        adapter.addItemViewDelegate(TulipViewDelegate())
        mBinding!!.adapter = adapter
        mBinding!!.layoutManager = LinearLayoutManager(this)
    }
}