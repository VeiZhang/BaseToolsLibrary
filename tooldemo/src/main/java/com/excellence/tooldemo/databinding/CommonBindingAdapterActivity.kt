package com.excellence.tooldemo.databinding

import android.os.Bundle
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.excellence.basetoolslibrary.databinding.CommonBindingAdapter
import com.excellence.tooldemo.BR
import com.excellence.tooldemo.R
import com.excellence.tooldemo.bean.databinding.Flower
import java.util.*

class CommonBindingAdapterActivity : AppCompatActivity() {

    private var mBinding: ActivityCommonBindingAdapterBinding? = null
    private var mFlowers: MutableList<Flower> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_common_binding_adapter)
        initAdapter()
    }

    private fun initAdapter() {
        for (i in 0..9) {
            mFlowers.add(Flower("Flower$i", R.drawable.logo))
        }

        val adapter = CommonBindingAdapter(mFlowers, R.layout.item_flower, BR.flower)
        mBinding!!.adapter = adapter
        mBinding!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val flower = mFlowers[position]
            val name = flower.name
            val flag = "[点击]"
            if (name!!.contains(flag)) {
                flower.name = name.replace(flag, "")
            } else {
                flower.name = name + flag
            }
            (parent.adapter as BaseAdapter).notifyDataSetChanged()
            Toast.makeText(this@CommonBindingAdapterActivity, "点击了$position", Toast.LENGTH_SHORT).show()
        }
    }
}