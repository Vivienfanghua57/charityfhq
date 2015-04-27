package com.main;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.adapter.BriefThreadAdapter;
import com.adapter.TopicAdapter;
import com.example.charitydemo.R;
import com.main.ImageGalleryActivity.ViewPagerAdapter;
import com.util.CustomListView;
import com.util.CustomListView.OnLoadListener;
import com.util.CustomListView.OnRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

public class FindThread extends Activity {

    private int imageIds[];
    private String[] titles;
    private ArrayList<ImageView> images;
    private ArrayList<View> dots;
    private TextView title;
    private ViewPager mViewPager;
    private PagerAdapter adapter;
    private BriefThreadAdapter threadAdapter;
    private int oldPosition = 0;// 记录上一次点的位置
    private int currentItem; // 当前页面
    private ScheduledExecutorService scheduledExecutorService;
    public static int count;
    private ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findthread);
        listview = (ListView) findViewById(R.id.find_thread_listview);
        listview.addHeaderView(LayoutInflater.from(this).inflate(
                R.layout.topichead, null));
        threadAdapter = new BriefThreadAdapter(this);
        listview.setAdapter(threadAdapter);
        // listview.setonRefreshListener(new OnRefreshListener() {
        //
        // @Override
        // public void onRefresh() {
        // //TODO 下拉刷新
        // }
        // });
        // listview.setonLoadListener(new OnLoadListener() {
        //
        // @Override
        // public void onLoad() {
        // //TODO 加载更多
        // }
        // });
        // 图片ID
        imageIds = new int[] { R.drawable.test1, R.drawable.test2,
                R.drawable.test3, R.drawable.test5, };
        count = 4;
        // 图片标题
        titles = new String[] { "巩俐不低俗，我就不能低俗", "扑树又回来啦！再唱经典老歌引万人大合唱",
                "揭秘北京电影如何升级", "乐视网TV版大派送", };

        // 显示的图片
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            imageView.setScaleType(ScaleType.FIT_START);
            images.add(imageView);
        }

        // 显示的点
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));

        title = (TextView) findViewById(R.id.title);
        title.setText(titles[0]);

        mViewPager = (ViewPager) findViewById(R.id.vp);

        adapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                int pos = position % count;
                ImageView view = images.get(pos);
                container.removeView(view);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int pos = position % count;
                ImageView view = images.get(pos);
                view.setScaleType(ScaleType.CENTER_CROP);
                container.addView(view);
                return position;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                int pos = (Integer) object % count;
                boolean f = view == images.get(pos);
                return f;
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }
        };

        mViewPager.setAdapter(adapter);
        int maxSize = 65535;
        // 计算初始位置
        // int pos = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % count;
        // pager.setCurrentItem(pos); //
        // 这里设置为中间值后无法向左滑动，应该是viewpage的setCurrentItem方法有最大值限制
        int pos = maxSize / 2 - maxSize / 2 % count; // 计算初始位置
        mViewPager.setCurrentItem(pos);

        // mViewPager.setAdapter(adapter);
        mViewPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    // 手指在屏幕上时停止自动切换
                    case MotionEvent.ACTION_DOWN:
                        scheduledExecutorService.shutdownNow();
                        break;
                    // 手指离开屏幕时再次开始自动切换
                    case MotionEvent.ACTION_UP:
                        onStart();
                }
                return false;
            }

        });
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                title.setText(titles[position % count]);

                dots.get(oldPosition).setBackgroundResource(
                        R.drawable.dot_normal);
                dots.get(position % count).setBackgroundResource(
                        R.drawable.dot_focused);

                oldPosition = position % count;
                currentItem = position % count;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // 每隔2秒钟切换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5,
                5, TimeUnit.SECONDS);
    }

    // 切换图片
    private class ViewPagerTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            currentItem = mViewPager.getCurrentItem() + 1;
            // 更新界面
            // handler.sendEmptyMessage(0);
            handler.obtainMessage().sendToTarget();
        }

    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 设置当前页面
            mViewPager.setCurrentItem(currentItem);
        }

    };

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        // 停止图片切换
        scheduledExecutorService.shutdown();
        super.onStop();
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
