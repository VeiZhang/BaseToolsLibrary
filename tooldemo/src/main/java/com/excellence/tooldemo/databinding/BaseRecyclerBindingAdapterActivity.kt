package com.excellence.tooldemo.databinding

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.excellence.basetoolslibrary.databinding.BaseRecyclerBindingAdapter
import com.excellence.basetoolslibrary.databinding.OnItemClickListener
import com.excellence.basetoolslibrary.databinding.OnItemFocusChangeListener
import com.excellence.basetoolslibrary.databinding.OnItemLongClickListener
import com.excellence.tooldemo.BR
import com.excellence.tooldemo.R
import com.excellence.tooldemo.bean.databinding.Flower
import java.util.*

class BaseRecyclerBindingAdapterActivity : AppCompatActivity() {

    private var mBinding: ActivityBaseRecyclerBindingAdapterBinding? = null
    private var mFlowers: MutableList<Flower> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_recycler_binding_adapter)
        initAdapter()
    }

    private fun initAdapter() {
        for (i in 0..9) {
            mFlowers.add(Flower("Flower x Flower = Flowers $i", R.drawable.logo))
        }
        val adapter = BaseRecyclerBindingAdapter(mFlowers, R.layout.item_flower, BR.flower)
        mBinding!!.adapter = adapter
        mBinding!!.layoutManager = LinearLayoutManager(this)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(binding: ViewDataBinding, v: View, position: Int) {
                Toast.makeText(this@BaseRecyclerBindingAdapterActivity, "点击事件$position", Toast.LENGTH_SHORT).show()
            }
        })
        adapter.setOnItemLongClickListener(object : OnItemLongClickListener {
            override fun onItemLongClick(binding: ViewDataBinding, v: View, position: Int): Boolean {
                Toast.makeText(this@BaseRecyclerBindingAdapterActivity, "长按事件$position", Toast.LENGTH_SHORT).show()
                return false
            }
        })
        adapter.setOnItemFocusChangeListener(object : OnItemFocusChangeListener {
            override fun onItemFocusChange(binding: ViewDataBinding, v: View, hasFocus: Boolean, position: Int) {
                val itemFlowerBinding = binding as ItemFlowerBinding
                itemFlowerBinding.text.isSelected = hasFocus
                if (hasFocus) {
                    Toast.makeText(this@BaseRecyclerBindingAdapterActivity, "焦点事件 $position", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}