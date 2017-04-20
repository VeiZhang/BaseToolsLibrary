package com.excellence.tooldemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.excellence.basetoolslibrary.baseadapter.ViewHolder;
import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.baseadapter.MultiItemTypeAdapter;
import com.excellence.tooldemo.bean.ComputerData;
import com.excellence.tooldemo.bean.PurpleData;
import com.excellence.tooldemo.bean.BlueData;
import com.excellence.tooldemo.bean.People;

import java.util.ArrayList;
import java.util.List;

public class MultiItemAdapterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
	private ListView mListView = null;
	private List<People> mMessages = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_item_adapter);

		initMsg();
		mListView = (ListView) findViewById(R.id.multi_item_listview);
		mListView.setAdapter(new ChatAdapter(this, mMessages));
		mListView.setOnItemClickListener(this);
	}

	private void initMsg()
	{
		mMessages = new ArrayList<>();
		mMessages.add(new ComputerData("全军出击！"));
		mMessages.add(new BlueData("有大吗？"));
		mMessages.add(new PurpleData("30秒！"));
		mMessages.add(new BlueData("ad注意走位"));
		mMessages.add(new ComputerData("killing spree！"));
		mMessages.add(new PurpleData("我被抓了。"));
		mMessages.add(new PurpleData("救我。。。"));
		mMessages.add(new BlueData("保护我"));
		mMessages.add(new ComputerData("Legendary"));
		mMessages.add(new ComputerData("penta kill"));
		mMessages.add(new ComputerData("ACE!"));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		ChatAdapter adapter = (ChatAdapter) parent.getAdapter();
		mMessages.get(position).setMsg("要死了，要死了。。。");
		adapter.notifyNewData(mMessages);
	}

	private class ChatAdapter extends MultiItemTypeAdapter<People>
	{
		public ChatAdapter(Context context, List<People> messages)
		{
			super(context, messages);
			addItemViewDelegate(new ComputerDelegate());
			addItemViewDelegate(new BlueDelegate());
			addItemViewDelegate(new PurpleDelegate());
		}
	}

	private class ComputerDelegate implements ItemViewDelegate<People>
	{
		@Override
		public int getItemViewLayoutId()
		{
			return R.layout.item_computer;
		}

		@Override
		public boolean isForViewType(People item, int position)
		{
			return item instanceof ComputerData;
		}

		@Override
		public void convert(ViewHolder viewHolder, People item, int position)
		{
			viewHolder.setText(R.id.computer_text, item.getMsg());
		}
	}

	private class BlueDelegate implements ItemViewDelegate<People>
	{

		@Override
		public int getItemViewLayoutId()
		{
			return R.layout.item_blue;
		}

		@Override
		public boolean isForViewType(People item, int position)
		{
			return item instanceof BlueData;
		}

		@Override
		public void convert(ViewHolder viewHolder, People item, int position)
		{
			viewHolder.setText(R.id.blue_text, item.getMsg());
		}
	}

	private class PurpleDelegate implements ItemViewDelegate<People>
	{
		@Override
		public int getItemViewLayoutId()
		{
			return R.layout.item_purple;
		}

		@Override
		public boolean isForViewType(People item, int position)
		{
			return item instanceof PurpleData;
		}

		@Override
		public void convert(ViewHolder viewHolder, People item, int position)
		{
			viewHolder.setText(R.id.purple_text, item.getMsg());
		}
	}
}
