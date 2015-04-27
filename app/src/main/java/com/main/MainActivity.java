package com.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anim.MagnifyAnimation;
import com.chat.ChatActivity;
import com.chat.MessageEntity;
import com.db.DatabaseHelper;
import com.emoji.FaceConversionUtil;
import com.entity.UserEntitiy;
import com.example.charitydemo.R;
import com.fragment.FragmentNotice;
import com.fragment.FragmentFind;
import com.fragment.FragmentHome;
import com.fragment.FragmentMe;
import com.util.BadgeView;
import com.util.IndexViewPager;
import com.util.MemoryCache;
import com.util.MyApp;
import com.util.MyFragmentPagerAdapter;
import com.util.PreferenceConstants;

public class MainActivity extends FragmentActivity implements OnClickListener {

    public static MainActivity instance = null;
    private ArrayList<Fragment> fragmentList;
    private IndexViewPager mTabPager;
    private RelativeLayout mTab5,mTab6, mTab7, mTab8;
    private ImageView mTab1, mTab2, mTab3, mTab4;
    private TextView mText1, mText2, mText3, mText4;
    private int currIndex = 0;// 当前页卡编号
    private long mExitTime;
    private ImageView new_thread, pro_bg;
    // RedPointView btnPoint1;
    private SQLiteDatabase db;
    //	public UserEntitiy user;
    private MyApp app;
    public MemoryCache memoryCache=new MemoryCache();
    //建立文件目录
    public String temp_path_image=PreferenceConstants.TEMP_PATH_IMAGE;
    public String temp_path_video=PreferenceConstants.TEMP_PATH_VIDEO;
    public String avatar_path=PreferenceConstants.AVATAR_PATH;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        instance = this;
        app=(MyApp) getApplication();
        app.setMemoryCache(memoryCache);


        InitTabsView();
        InitViewPager();
        DatabaseHelper database = new DatabaseHelper(this);
        db = database.getReadableDatabase();
//		for (int i = 0; i < 5; i++) {
//			user = new UserEntitiy(i, "张孟玉" + i, i + "", "");
//			// user.userID=i;user.userName="张孟玉"+i;user.password=i+"";user.userAvatar="";
//			DatabaseHelper.addUser(db, user);
//		}
        DatabaseHelper.addFocus(db);
        // DatabaseHelper.addUser(db);
        // DatabaseHelper.addFocus(db);
        // database.onUpgrade(db, 0, 1);
        // btnPoint1 = new RedPointView(this, mTab3);
        // btnPoint1.setSizeContent(7);
        // btnPoint1.setColorContent(Color.WHITE);
        // btnPoint1.setColorBg(Color.RED);
        // btnPoint1.setPosition(Gravity.RIGHT, Gravity.TOP);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 开启线程，导入表情
                FaceConversionUtil.getInstace().getFileText(getApplication());
                //开启线程，建立文件目录,"."表示隐藏文件
                File path1=new File(temp_path_image);
                File path2=new File(temp_path_video);
                File path3=new File(avatar_path);
                if(!(path1.exists())){
                    path1.mkdirs();
                }
                if(!(path2.exists())){
                    path2.mkdirs();
                }
                if(!(path3.exists())){
                    path3.mkdirs();
                }
            }
        }).start();
        // if(isFirstRun()){
        // copyDb();
        // }
        // findViewById(R.id.th_map).setOnClickListener(this);;
        // map.setOnClickListener(this);
        // pro_bg=(ImageView)findViewById(R.id.pro_bg);
        // final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
        // R.drawable.pro_bg);
        // pro_bg.getViewTreeObserver().addOnGlobalLayoutListener(new
        // OnGlobalLayoutListener() {
        // boolean isFirst = true;// 默认调用两次，这里只让它执行一次回调
        //
        // @Override
        // public void onGlobalLayout() {
        // if (isFirst) {
        // isFirst = false;
        // // 现在布局全部完成，可以获取到任何View组件的宽度、高度、左边、右边等信息
        // rsBlur2(bitmap, pro_bg);
        // }
        // }
        // });
        new_thread = (ImageView) findViewById(R.id.newthread);
        new_thread.setOnClickListener(this);

    }

    // private void rsBlur2(Bitmap bm, ImageView view) {
    // Bitmap outputBitmap = Bitmap.createBitmap((int) (view.getMeasuredWidth()
    // / 1), (int) (view.getMeasuredHeight() / 1),
    // Bitmap.Config.ARGB_8888);
    //
    // RenderScript rs = RenderScript.create(getApplicationContext());
    // ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs,
    // Element.U8_4(rs));
    // Allocation tmpIn = Allocation.createFromBitmap(rs, bm);
    // Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
    // theIntrinsic.setRadius(25.f);
    // theIntrinsic.setInput(tmpIn);
    // theIntrinsic.forEach(tmpOut);
    // tmpOut.copyTo(outputBitmap);
    // bm.recycle();
    // view.setImageDrawable(new BitmapDrawable(getResources(), outputBitmap));
    // rs.destroy();
    //
    // }
    // /**程序是否是第一次运行**/
    // private boolean isFirstRun(){
    // SharedPreferences sharedPreferences = this.getSharedPreferences("share",
    // MODE_PRIVATE);
    // boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
    // Editor editor = sharedPreferences.edit();
    // if(isFirstRun){
    // editor.putBoolean("isFirstRun", false);
    // editor.commit();
    // }
    // return isFirstRun;
    // }
    // /**复制数据库**/
    // private void copyDb(){
    // File dbFile = getDatabasePath("express.db");
    // CopyFile.copyFileFromResToPhone(dbFile.getParent(),
    // dbFile.getName(),
    // getResources().openRawResource(R.raw.express));
    // }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != RESULT_CANCELED) {
            switch (resultCode) {
                case 1:
                    mTabPager.setCurrentItem(0, false);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newthread:
                Animation anim = MagnifyAnimation.clickAnimation(300);
                anim.setAnimationListener(new AnimationListener() {

                    public void onAnimationStart(Animation animation) {

                    }

                    public void onAnimationRepeat(Animation animation) {

                    }

                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(MainActivity.this,
                                NewThread.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up_in,
                                R.anim.slide_still);
                    }
                });
                new_thread.startAnimation(anim);
                break;
            default:
                break;
        }
    }

    /*
     * 初始化标签名
     */
    public void InitTabsView() {
        mTab5 = (RelativeLayout) findViewById(R.id.mainpage_img1);
        mTab6 = (RelativeLayout) findViewById(R.id.contact_img1);
        mTab7 = (RelativeLayout) findViewById(R.id.findpage_img1);
        mTab8 = (RelativeLayout) findViewById(R.id.mypage_img1);
        mTab1 = (ImageView) findViewById(R.id.mainpage_img);
        mTab2 = (ImageView) findViewById(R.id.contact_img);
        mTab3 = (ImageView) findViewById(R.id.findpage_img);
        mTab4 = (ImageView) findViewById(R.id.mypage_img);
        mText1 = (TextView) findViewById(R.id.tab_hometxt);
        mText2 = (TextView) findViewById(R.id.tab_contacttxt);
        mText3 = (TextView) findViewById(R.id.tab_findtxt);
        mText4 = (TextView) findViewById(R.id.tab_mytxt);
        mTab5.setOnClickListener(new MyOnClickListener(0));
        mTab6.setOnClickListener(new MyOnClickListener(1));
        mTab7.setOnClickListener(new MyOnClickListener(2));
        mTab8.setOnClickListener(new MyOnClickListener(3));
    }

    public void InitViewPager() {
        mTabPager = (IndexViewPager) findViewById(R.id.tabpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        fragmentList = new ArrayList<Fragment>();
        Fragment firstFragment = FragmentHome.newInstance();
        Fragment secondFragment = FragmentNotice.newInstance();
        Fragment thirdFragment = FragmentFind.newInstance();
        Fragment fourthFragment = FragmentMe.newInstance();
        fragmentList.add(firstFragment);
        fragmentList.add(secondFragment);
        fragmentList.add(thirdFragment);
        fragmentList.add(fourthFragment);

        // 给ViewPager设置适配器
        mTabPager.setAdapter(new MyFragmentPagerAdapter(
                getSupportFragmentManager(), fragmentList));
        mTabPager.setCurrentItem(0, false);// 设置当前显示标签页为第一页

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
            mTabPager.setCurrentItem(index, false);
        }
    };

    public class MyOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    mTab1.setImageDrawable(getResources().getDrawable(
                            R.drawable.home2));
                    mText1.setTextColor(getResources().getColor(
                            R.color.tabtxt_selected));
                    if (currIndex == 1) {
                        mTab2.setImageDrawable(getResources().getDrawable(
                                R.drawable.notice1));
                        mText2.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    } else if (currIndex == 2) {
                        mTab3.setImageDrawable(getResources().getDrawable(
                                R.drawable.find1));
                        mText3.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    } else if (currIndex == 3) {
                        mTab4.setImageDrawable(getResources().getDrawable(
                                R.drawable.me1));
                        mText4.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    }
                    break;
                case 1:
                    mTab2.setImageDrawable(getResources().getDrawable(
                            R.drawable.notice2));
                    mText2.setTextColor(getResources().getColor(
                            R.color.tabtxt_selected));
                    if (currIndex == 0) {
                        mTab1.setImageDrawable(getResources().getDrawable(
                                R.drawable.home1));
                        mText1.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    } else if (currIndex == 2) {
                        mTab3.setImageDrawable(getResources().getDrawable(
                                R.drawable.find1));
                        mText3.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    } else if (currIndex == 3) {
                        mTab4.setImageDrawable(getResources().getDrawable(
                                R.drawable.me1));
                        mText4.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    }
                    break;
                case 2:
                    mTab3.setImageDrawable(getResources().getDrawable(
                            R.drawable.find2));
                    mText3.setTextColor(getResources().getColor(
                            R.color.tabtxt_selected));
                    if (currIndex == 0) {
                        mTab1.setImageDrawable(getResources().getDrawable(
                                R.drawable.home1));
                        mText1.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    } else if (currIndex == 1) {
                        mTab2.setImageDrawable(getResources().getDrawable(
                                R.drawable.notice1));
                        mText2.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    } else if (currIndex == 3) {
                        mTab4.setImageDrawable(getResources().getDrawable(
                                R.drawable.me1));
                        mText4.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    }
                    break;
                case 3:
                    mTab4.setImageDrawable(getResources().getDrawable(
                            R.drawable.me2));
                    mText4.setTextColor(getResources().getColor(
                            R.color.tabtxt_selected));
                    if (currIndex == 0) {
                        mTab1.setImageDrawable(getResources().getDrawable(
                                R.drawable.home1));
                        mText1.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    } else if (currIndex == 1) {
                        mTab2.setImageDrawable(getResources().getDrawable(
                                R.drawable.notice1));
                        mText2.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
                    } else if (currIndex == 2) {
                        mTab3.setImageDrawable(getResources().getDrawable(
                                R.drawable.find1));
                        mText3.setTextColor(getResources().getColor(
                                R.color.tabtxt_normal));
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

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                // Object mHelperUtils;
                Toast.makeText(this, getApplication().getText(R.string.again_to_exit), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onResume() {
        super.onResume();

        BadgeView badge;
        View target;

        // *** test linear layout container ***

        target = findViewById(R.id.contact_img1);
        badge = new BadgeView(this, target);
        badge.setWidth(30);
        badge.setHeight(30);
        badge.setBadgeMargin(20, 15);
        badge.setText("3");
        badge.setTextSize(10);
        badge.show();
    }

}