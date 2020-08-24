package com.excellence.tooldemo;

import android.os.Bundle;
import android.view.View;

import com.excellence.basetoolslibrary.recycleradapter.MultiItemTypeRecyclerAdapter;
import com.excellence.basetoolslibrary.recycleradapter.OnItemClickListener;
import com.excellence.basetoolslibrary.recycleradapter.RecyclerViewHolder;
import com.excellence.basetoolslibrary.recycleradapter.base.ItemViewDelegate;
import com.excellence.tooldemo.bean.BlueData;
import com.excellence.tooldemo.bean.ComputerData;
import com.excellence.tooldemo.bean.People;
import com.excellence.tooldemo.bean.PurpleData;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MultiItemRecyclerAdapterActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView mRecyclerView = null;
    private WarAdapter mWarAdapter = null;
    private List<People> mMessages = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_item_recycler_adapter);

        initMsg();
        mRecyclerView = (RecyclerView) findViewById(R.id.multi_item_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWarAdapter = new WarAdapter(mMessages);
        mWarAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mWarAdapter);
    }

    private void initMsg() {
        mMessages = new ArrayList<>();
        mMessages.add(new ComputerData("欢迎来到召唤师峡谷！"));
        mMessages.add(new BlueData("1 2 3 4，提莫队长正在待命"));
        mMessages.add(new PurpleData("你就是个loser"));
        mMessages.add(new BlueData("那个，你有看到我的小熊吗！"));
        mMessages.add(new ComputerData("全军出击"));
        mMessages.add(new PurpleData("Miss，怎么可能"));
        mMessages.add(new BlueData("阿木木"));
        mMessages.add(new ComputerData("Victory!"));
    }

    @Override
    public void onItemClick(RecyclerViewHolder viewHolder, View v, int position) {
        People people = mMessages.get(position);
        people.setMsg("defeat");
        mWarAdapter.modify(people);
    }

    private class WarAdapter extends MultiItemTypeRecyclerAdapter<People> {
        public WarAdapter(List<People> data) {
            super(data);
            addItemViewDelegate(new ComputerRecyclerDelegate());
            addItemViewDelegate(new BlueRecyclerDelegate());
            addItemViewDelegate(new PurpleRecyclerDelegate());
        }
    }

    private class ComputerRecyclerDelegate implements ItemViewDelegate<People> {
        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_computer;
        }

        @Override
        public boolean isForViewType(People item, int position) {
            return item instanceof ComputerData;
        }

        @Override
        public void convert(RecyclerViewHolder viewHolder, People item, int position) {
            viewHolder.setText(R.id.computer_text, item.getMsg());
        }
    }

    private class BlueRecyclerDelegate implements ItemViewDelegate<People> {
        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_blue;
        }

        @Override
        public boolean isForViewType(People item, int position) {
            return item instanceof BlueData;
        }

        @Override
        public void convert(RecyclerViewHolder viewHolder, People item, int position) {
            viewHolder.setText(R.id.blue_text, item.getMsg());
        }
    }

    private class PurpleRecyclerDelegate implements ItemViewDelegate<People> {
        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_purple;
        }

        @Override
        public boolean isForViewType(People item, int position) {
            return item instanceof PurpleData;
        }

        @Override
        public void convert(RecyclerViewHolder viewHolder, People item, int position) {
            viewHolder.setText(R.id.purple_text, item.getMsg());
        }
    }
}
