package com.excellence.tooldemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.excellence.basetoolslibrary.utils.ActivityUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
	private static final String TAG = MainActivity.class.getSimpleName();

	private ListView mListView = null;
	private Map<String, String> mActivityNameList = null;
	private Map<String, Class<? extends Activity>> mActivityClsList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		initActivityList();
	}

	private void init()
	{
		mListView = (ListView) findViewById(R.id.function_list);
		mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.function_list)));
		mListView.setOnItemClickListener(this);
	}

	/**
	 * 关联界面名字、Activity名字
	 * 关联Activity名字、Activity.class类
	 */
	private void initActivityList()
	{
		mActivityNameList = new HashMap<>();
		mActivityClsList = new HashMap<>();
		String[] functionNames = getResources().getStringArray(R.array.function_list);
		String[] activityNames = getResources().getStringArray(R.array.function_activity_list);
		for (int i = 0; i < functionNames.length; i++)
		{
			mActivityNameList.put(functionNames[i], activityNames[i]);
			try
			{
				// 根据Activity类名搜索Activity.class类
				mActivityClsList.put(activityNames[i], (Class<? extends Activity>) Class.forName(activityNames[i]));
			}
			catch (Exception e)
			{
				mActivityClsList.put(activityNames[i], null);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		/**
		 * ①List可存储Activity.class
		 * ②List存储Activity名字，通过名字查找Activity.class
		 */
		ActivityUtils.startAnotherActivity(this, mActivityClsList.get(mActivityNameList.get(parent.getItemAtPosition(position))));
	}

}
