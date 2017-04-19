package com.excellence.tooldemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.excellence.basetoolslibrary.baseadapter.ViewHolder;
import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.baseadapter.MultiItemTypeAdapter;
import com.excellence.tooldemo.bean.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class MultiItemAdapterActivity extends AppCompatActivity
{
	private ListView mListView = null;
	private List<ChatMessage> mMessages = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_item_adapter);

		initMsg();
		mListView = (ListView) findViewById(R.id.multi_item_listview);
		mListView.setAdapter(new ChatAdapter(this, mMessages));
	}

	private void initMsg()
	{
		mMessages = new ArrayList<>();
		mMessages.add(new ChatMessage("有大吗？", true));
		mMessages.add(new ChatMessage("30秒！", false));
		mMessages.add(new ChatMessage("ad注意走位", true));
		mMessages.add(new ChatMessage("我被抓了。", true));
		mMessages.add(new ChatMessage("ACE!", false));
	}

	private class ChatAdapter extends MultiItemTypeAdapter<ChatMessage>
	{
		public ChatAdapter(Context context, List<ChatMessage> messages)
		{
			super(context, messages);
			addItemViewDelegate(new AskerDelegate());
			addItemViewDelegate(new AnswerDelegate());
		}
	}

	private class AskerDelegate implements ItemViewDelegate<ChatMessage>
	{

		@Override
		public int getItemViewLayoutId()
		{
			return R.layout.item_asker;
		}

		@Override
		public boolean isForViewType(ChatMessage item, int position)
		{
			return item.isAsker();
		}

		@Override
		public void convert(ViewHolder viewHolder, ChatMessage item, int position)
		{
			viewHolder.setText(R.id.asker_text, item.getMsg());
		}
	}

	private class AnswerDelegate implements ItemViewDelegate<ChatMessage>
	{
		@Override
		public int getItemViewLayoutId()
		{
			return R.layout.item_answer;
		}

		@Override
		public boolean isForViewType(ChatMessage item, int position)
		{
			return !item.isAsker();
		}

		@Override
		public void convert(ViewHolder viewHolder, ChatMessage item, int position)
		{
			viewHolder.setText(R.id.answer_text, item.getMsg());
		}
	}
}
