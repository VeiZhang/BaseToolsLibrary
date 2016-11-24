package com.excellence.tooldemo;

import java.util.Arrays;
import java.util.List;

import com.excellence.basetoolslibrary.CommonAdapter;
import com.excellence.basetoolslibrary.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
	private static final String TAG = MainActivity.class.getSimpleName();

	private ListView mListView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	private void init()
	{
		String[] strings = getResources().getStringArray(R.array.function_list);
		List<String> stringList = Arrays.asList(strings);
		mListView = (ListView) findViewById(R.id.function_list);
		mListView.setAdapter(new FunctionAdapter(this, stringList, android.R.layout.simple_list_item_1));
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
        Intent intent = null;
        switch (position)
        {
            case 0:
                intent = new Intent(this, AdapterActivity.class);
                break;

            default:
                break;
        }

        if (intent != null)
        {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
	}

	private class FunctionAdapter extends CommonAdapter<String>
	{
		public FunctionAdapter(Context context, List<String> datas, int layoutId)
		{
			super(context, datas, layoutId);
		}

		@Override
		public void convert(ViewHolder viewHolder, String item, int position)
		{
			viewHolder.setText(android.R.id.text1, item);
		}
	}
}
