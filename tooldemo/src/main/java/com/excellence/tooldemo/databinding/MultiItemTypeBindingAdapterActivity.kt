package com.excellence.tooldemo.databinding

import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingAdapter
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegateImp
import com.excellence.tooldemo.BR
import com.excellence.tooldemo.R
import com.excellence.tooldemo.bean.databinding.Flower
import com.excellence.tooldemo.bean.databinding.Rose
import com.excellence.tooldemo.bean.databinding.Tulip
import java.util.*

class MultiItemTypeBindingAdapterActivity : AppCompatActivity() {

    private var mBinding: ActivityMultiItemTypeBindingAdapterBinding? = null
    private var mFlowers: MutableList<Flower> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_multi_item_type_binding_adapter)
        initAdapter()
    }

    private fun initAdapter() {
        mFlowers.add(Rose("I am Rose", R.drawable.blue))
        mFlowers.add(Rose("Hello", R.drawable.blue))
        mFlowers.add(Tulip("e...", R.drawable.purple))
        mFlowers.add(Rose("King is mine", R.drawable.blue))
        mFlowers.add(Tulip("Nice", R.drawable.purple))
        val adapter = MultiItemTypeBindingAdapter(mFlowers)
        adapter.addItemViewDelegate(RoseViewDelegate())
        adapter.addItemViewDelegate(TulipViewDelegate())
        mBinding!!.adapter = adapter
        mBinding!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> Toast.makeText(this@MultiItemTypeBindingAdapterActivity, "点击了$position", Toast.LENGTH_SHORT).show() }
    }

    class RoseViewDelegate : ItemViewDelegateImp<Flower>() {
        override fun getItemViewLayoutId(): Int {
            return R.layout.item_rose
        }

        override fun getItemVariable(): Int {
            return BR.rose
        }

        override fun isForViewType(item: Flower?, position: Int): Boolean {
            return item is Rose
        }
    }

    class TulipViewDelegate : ItemViewDelegateImp<Flower>() {
        override fun getItemViewLayoutId(): Int {
            return R.layout.item_tulip
        }

        override fun getItemVariable(): Int {
            return BR.tulip
        }

        override fun isForViewType(item: Flower?, position: Int): Boolean {
            return item is Tulip
        }
    }
}