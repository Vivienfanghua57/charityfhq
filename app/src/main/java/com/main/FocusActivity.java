package com.main;

import java.util.ArrayList;
import java.util.List;

import com.adapter.FocusAdapter;
import com.chat.ChatActivity;
import com.chat.MessageEntity;
import com.db.DatabaseHelper;
import com.entity.FocusEntity;
import com.example.charitydemo.R;
import com.indexlist.LetterView;
import com.indexlist.LetterView.OnLetterChangeListener;
import com.util.PreferenceConstants;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class FocusActivity extends Activity implements OnClickListener{
    private ListView listView;
    private FocusAdapter adapter;
    private DatabaseHelper helper;
    /** 分组辅助类 **/
    private AlphabetIndexer indexer;
    /** 字母表 **/
    private String alphabet = "↑ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
    /** 字母导航视图 **/
    private LetterView letterView;
    /** 自定义Toast **/
    private Toast toast;
    /** Toast视图中的TextView **/
    private TextView tvToast,message_count;
    private EditText focus_search;
    private Button tc_search,tc_add;

    private SQLiteDatabase db;
    private List<FocusEntity> friends = new ArrayList<FocusEntity>();
    //搜索框的y值和高度
    private float y;
    //	private int height;
    //全局布局
    private RelativeLayout tc_rel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus);
        helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        initToast();
        initViews();
    }
    /** 初始化Toast **/
    private void initToast() {
        toast = new Toast(this);
        // 设置Toast的显示时间
        toast.setDuration(Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(this).inflate(R.layout.toast, null);
        // 设置Toast的显示视图
        toast.setView(view);
        // 设置toast的显示位置位于窗口正中间
        toast.setGravity(Gravity.CENTER, 0, 0);
        tvToast = (TextView) view.findViewById(R.id.tvToast);
    }

    /** 初始化控件 **/
    private void initViews() {
        // viewOverlay = mMainView.findViewById(R.id.viewOverlay);
        // tvOverlay = (TextView) mMainView.findViewById(R.id.tvOverlay);
        // viewOverlay.setVisibility(View.GONE);
        tc_rel=(RelativeLayout)findViewById(R.id.tc_rel);
//		tc_rel1=(RelativeLayout)findViewById(R.id.tc_rel1);
        tc_search=(Button)findViewById(R.id.tc_search);

//		tc_message=(Button)mMainView.findViewById(R.id.tc_message);
        tc_add=(Button)findViewById(R.id.tc_add);

        tc_add.setOnClickListener(this);
        tc_search.setOnClickListener(this);

//		tc_message.setOnClickListener(this);
        listView = (ListView)findViewById(R.id.contact_listview);

        indexer = DatabaseHelper.getFocusIndexer(db, 0,alphabet);
        // 创建AlphabetIndexer对象需要cursor对象，排序的字段所在的位置，字母表
//		if(cursor.moveToFirst()){
//		indexer = new AlphabetIndexer(
//				cursor,
//				cursor.getColumnIndex("initial"),
//				alphabet);
//		}
        friends=DatabaseHelper.getFocus(db, PreferenceConstants.ACCOUNT);
        adapter = new FocusAdapter(friends, this, indexer);
        listView.addHeaderView(LayoutInflater.from(this).inflate(
                R.layout.focushead, null));
//		message_count=(TextView)mMainView.findViewById(R.id.message_count);message_count.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        //listview head里的控件添加监听
        focus_search = (EditText)findViewById(R.id.focus_search);
        focus_search.setOnClickListener(this);
        listView.setAdapter(adapter);
        if(indexer==null){

        }else{


            letterView = (LetterView)findViewById(R.id.letterView);
            letterView.setOnLetterChangeListener(letterChangeListener);
            listView.setOnScrollListener(scrollListener);}
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                position -= 1;
                Intent intent = new Intent(FocusActivity.this, ProfileUser.class);
                //给profile这个类穿id即可，好友以及个人资料从服务器获取,这里用于测试就传两个值
                intent.putExtra("FOCUSID", friends.get(position).getFocusID());
                intent.putExtra("FOCUSNAME", friends.get(position).getFocusName());
                startActivity(intent);
                Log.i("", position + "");
            }
        });
    }

    /** 为ListView设置的滚动监听 **/
    private OnScrollListener scrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        // firstVisibleItem 代表 第一个可见item的position
        // visibleItemCount 当前屏幕可见的item的个数
        // totalItemCount 代表ListView总的item个数
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            // 这两行代表作用是 让ListView滚动的时候控制LetterView里选中字母位置
            int section = indexer.getSectionForPosition(firstVisibleItem);
            letterView.setSelectedIndex(section);

            // //设置挤压效果
            // int nextSection = section+1;
            // int pos = indexer.getPositionForSection(nextSection);
            // //当当前第一个可见的item的下一个 正好是当前第一个可见item所在分组的下一个分组的第一个元素的时候
            // if(pos == firstVisibleItem+1){
            // //获取ListView的第一个孩子视图
            // View v = view.getChildAt(0);
            // if(v == null){
            // return;
            // }
            // int dex = v.getBottom() - tvOverlay.getHeight();
            // if(dex <= 0 ){
            // viewOverlay.setPadding(0, dex, 0, 0);
            // tvOverlay.setText(alphabet.charAt(section)+"");
            // }else{
            // viewOverlay.setPadding(0, 0, 0, 0);
            // tvOverlay.setText(alphabet.charAt(section)+"");
            // }
            // }else{
            // viewOverlay.setPadding(0, 0, 0, 0);
            // tvOverlay.setText(alphabet.charAt(section)+"");
            // }
        }
    };
    /** 为LetterView设置的监听器 **/
    private OnLetterChangeListener letterChangeListener = new OnLetterChangeListener() {

        @Override
        public void onLetterChange(int selectedIndex) {
            int position = indexer.getPositionForSection(selectedIndex);
            if(selectedIndex==0){

            }else{
                position=position+1;
            }

            listView.setSelection(position);
            tvToast.setText(alphabet.charAt(selectedIndex) + "");
            toast.show();

            // 这种Toast显示方式 不适合文字快速变化的情况
            // Toast.makeText(getApplicationContext(),
            // alphabet.charAt(selectedIndex)+"", 0).show();
        }
    };
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tc_search:
                Intent intent2=new Intent(this,SearchThreadPost.class);
                startActivity(intent2);
                this.overridePendingTransition(R.anim.magnifyandalpha,
                        R.anim.slide_still);
                break;
            case R.id.focus_search:
                //启动上顶动画
                y = listView.getY();
//				height = focus_search.getHeight();
                TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -y);
                animation.setDuration(200);
                animation.setFillAfter(true);
                animation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), SearchThreadPost.class);
                        startActivityForResult(intent, 100);
                        overridePendingTransition(R.anim.animation_search_2,R.anim.slide_still);
                    }
                });
                tc_rel.startAnimation(animation);
                break;
            case R.id.tc_add:
                Intent intent3=new Intent(this,Add.class);
                startActivity(intent3);
                this.overridePendingTransition(R.anim.magnifyandalpha,
                        R.anim.slide_still);
                break;
//			case R.id.tc_rel1:
//				
//				break;
//			case R.id.tc_rel2:
//				
//				break;
//			case R.id.tc_rel3:
//				
//				break;
//			case R.id.tc_rel4:
//				
//				break;
            default:
                break;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still, R.anim.push_right_out);
            return false;
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -y, 0);
        animation.setDuration(200);
        animation.setFillAfter(true);
        tc_rel.startAnimation(animation);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
