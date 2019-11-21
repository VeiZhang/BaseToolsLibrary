package com.excellence.tooldemo.databinding;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.excellence.basetoolslibrary.databinding.MultiItemTypeBindingAdapter;
import com.excellence.basetoolslibrary.databinding.base.ItemViewDelegate;
import com.excellence.tooldemo.BR;
import com.excellence.tooldemo.R;
import com.excellence.tooldemo.bean.databinding.Flower;
import com.excellence.tooldemo.bean.databinding.Rose;
import com.excellence.tooldemo.bean.databinding.Tulip;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MultiItemTypeBindingAdapterActivity extends AppCompatActivity {

    private ActivityMultiItemTypeBindingAdapterBinding mBinding = null;
    private List<Flower> mFlowers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_multi_item_type_binding_adapter);

        initAdapter();
    }

    private void initAdapter() {
        mFlowers = new ArrayList<>();
        mFlowers.add(new Rose("I am Rose", R.drawable.blue));
        mFlowers.add(new Rose("Hello", R.drawable.blue));
        mFlowers.add(new Tulip("e...", R.drawable.purple));
        mFlowers.add(new Rose("King is mine", R.drawable.blue));
        mFlowers.add(new Tulip("Nice", R.drawable.purple));
        MultiItemTypeBindingAdapter<Flower> adapter = new MultiItemTypeBindingAdapter<>(mFlowers);
        adapter.addItemViewDelegate(new RoseViewDelegate());
        adapter.addItemViewDelegate(new TulipViewDelegate());
        mBinding.setAdapter(adapter);
        mBinding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MultiItemTypeBindingAdapterActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class RoseViewDelegate implements ItemViewDelegate<Flower> {
        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_rose;
        }

        @Override
        public int getItemVariable() {
            return BR.rose;
        }

        @Override
        public boolean isForViewType(Flower item, int position) {
            return item instanceof Rose;
        }
    }

    public static class TulipViewDelegate implements ItemViewDelegate<Flower> {
        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_tulip;
        }

        @Override
        public int getItemVariable() {
            return BR.tulip;
        }

        @Override
        public boolean isForViewType(Flower item, int position) {
            return item instanceof Tulip;
        }
    }

}
