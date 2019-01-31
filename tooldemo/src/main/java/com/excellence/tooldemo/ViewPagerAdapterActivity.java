package com.excellence.tooldemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.excellence.basetoolslibrary.pageradapter.BasePagerAdapter;

public class ViewPagerAdapterActivity extends AppCompatActivity {
    
    private ViewPager mViewPager = null;
    private NumAdapter mAdapter = null;
    private int mPageCount = 1;
    private int mNumScale = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_adapter);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new NumAdapter(mPageCount);
        mViewPager.setAdapter(mAdapter);
    }

    private class NumAdapter extends BasePagerAdapter {

        public NumAdapter(int pageCount) {
            super(pageCount);
        }

        @Override
        protected View loadView(Context context, int pageIndex) {
            // 加载每页
            TextView textView = new TextView(context);
            textView.setText(String.valueOf(pageIndex * mNumScale));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNumScale *= 2;
                    mPageCount += 1;
                    // 刷新每页
                    mAdapter.notifyNewData(mPageCount);
                }
            });
            return textView;
        }
    }
}
