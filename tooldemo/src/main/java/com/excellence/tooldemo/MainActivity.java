package com.excellence.tooldemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.excellence.basetoolslibrary.utils.ActivityUtils;

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
		mListView = (ListView) findViewById(R.id.function_list);
		mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.function_list)));
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		switch (position)
		{
		case 0:
			ActivityUtils.startAnotherActivity(this, GridAdapterActivity.class);
			break;

		case 1:
			ActivityUtils.startAnotherActivity(this, RecyclerAdapterActivity.class);
		default:
			break;
		}
	}

}
