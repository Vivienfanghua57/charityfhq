package com.main;

import java.util.ArrayList;

import com.example.charitydemo.R;
import com.fragment.FragmentRankDay;
import com.fragment.FragmentRankTotal;
import com.util.IndexViewPager;
import com.util.MyFragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RankList extends FragmentActivity implements OnClickListener{
    private ArrayList<Fragment> fragmentList;
    private ViewPager mTabPager;
    private TextView mText1,mText2;
    private int currIndex = 0;// 当前页卡编号
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranklist);
        InitTabsView();
        InitViewPager();
    }
    /*
        * 初始化标签名
        */
    public void InitTabsView(){
        mText1=(TextView)findViewById(R.id.dayrank);
        mText2=(TextView)findViewById(R.id.totalrank);
        mText1.setOnClickListener(new MyOnClickListener(0));
        mText2.setOnClickListener(new MyOnClickListener(1));
    }
    public void InitViewPager(){
        mTabPager = (ViewPager)findViewById(R.id.rankpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        fragmentList = new ArrayList<Fragment>();
        Fragment firstFragment= FragmentRankDay.newInstance();
        Fragment secondFragment = FragmentRankTotal.newInstance();
        fragmentList.add(firstFragment);
        fragmentList.add(secondFragment);
        //给ViewPager设置适配器
        mTabPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mTabPager.setCurrentItem(0,false);//设置当前显示标签页为第一页

    }
    /**
     * 头标点击监听
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            mTabPager.setCurrentItem(index,false);
        }
    };


    public class MyOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    mText1.setTextColor(getResources().getColor(R.color.tabtxt_selected));
                    if (currIndex == 1) {
                        mText2.setTextColor(getResources().getColor(R.color.common_coffee));
                    }
                    break;
                case 1:
                    mText2.setTextColor(getResources().getColor(R.color.tabtxt_selected));
                    if (currIndex == 0) {
                        mText1.setTextColor(getResources().getColor(R.color.common_coffee));
                    }
                    break;
            }
            currIndex = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still,
                    R.anim.minifyandalpha);
            return false;
        }
        return false;
    }
}
