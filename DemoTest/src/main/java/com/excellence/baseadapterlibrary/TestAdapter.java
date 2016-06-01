package com.excellence.baseadapterlibrary;

import android.content.Context;

import java.util.List;

/**
 * Created by ZhangWei on 2016/6/1.
 */
public class TestAdapter extends CommonAdapter<Integer>
{

	public TestAdapter(Context context, List<Integer> datas, int layoutId)
	{
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ViewHolder viewHolder, Integer item, int position)
	{
		viewHolder.setText(R.id.item_text, String.valueOf(item));
	}
}
