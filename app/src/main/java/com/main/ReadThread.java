package com.main;

import java.util.ArrayList;
import java.util.List;

import com.adapter.ReadThreadAdapter;
import com.entity.ForumPostEntity;
import com.entity.ForumThreadExtendEntity;
import com.example.charitydemo.R;
import com.main.GoodsDetail.MyGridAdapter;
import com.util.ActionItem;
import com.util.FormatTools;
import com.util.MemoryCache;
import com.util.MyApp;
import com.util.MyScrollView;
import com.util.PopupWindowUtil;
import com.util.PreferenceConstants;
import com.util.RoundImageView;
import com.util.ScaleUtil;
import com.util.CommentPopup;
import com.util.CommentPopup.OnItemOnClickListener;
import com.util.T;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReadThread extends Activity implements OnClickListener,
        OnTouchListener, OnItemOnClickListener {
    private MyApp app;
    private MemoryCache memory;
    private int position,tid;//threads的position
    private ForumThreadExtendEntity threadhead;
    private View read_rel1;
    private GridView grid_goods;
    private MyGridAdapter gridadapter;
    private ListView listview;
    private ReadThreadAdapter adapter;
    private TextView reply_txt,send_confirm;
    private RelativeLayout read_rel2, read_rel3;
    private Button read_more;
    private EditText sendpost;
    private CommentPopup titlePopup;
    private List<ForumPostEntity> posts = new ArrayList<ForumPostEntity>();
    private RoundImageView thread_avatar;
    private TextView thread_author,thread_title,thread_content;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readthread);
        app=(MyApp) getApplication();
        memory=app.getMemoryCache();
        position=getIntent().getIntExtra("position",0);
        threadhead=app.getThreads().get(position);
        tid=threadhead.getTid();
        temp_init();
        initview();
    }

    public void temp_init() {
        for (int i = 0; i < 4; i++) {
            ForumPostEntity post = new ForumPostEntity();

            post.setFid(i);
            post.setAuthor("lee" + i);
            post.setAuthorid(i);
            // post.setSubject();
            post.setMessage("暑假想去陕西支教，请问有没有一起组队" + i);
            // post.setDateline();
            posts.add(post);
        }
    }
    private void initview() {
        read_rel1 = (View) findViewById(R.id.read_rel1);
        read_more = (Button) findViewById(R.id.read_more);
        read_more.setOnClickListener(this);
        listview = (ListView) findViewById(R.id.read_listview);
        adapter = new ReadThreadAdapter(posts,this, this);
        listview.addHeaderView(LayoutInflater.from(this).inflate(
                R.layout.threadhead, null));
        initThreadHead();


        listview.setOnTouchListener(this);
        read_rel2 = (RelativeLayout) findViewById(R.id.read_rel2);
        read_rel3 = (RelativeLayout) findViewById(R.id.read_rel3);
        sendpost = (EditText) findViewById(R.id.sendpost);

        send_confirm= (TextView) findViewById(R.id.send_confirm);
        send_confirm.setOnClickListener(this);
        reply_txt = (TextView) findViewById(R.id.reply_txt);
        reply_txt.setOnClickListener(this);

        listview.setAdapter(adapter);

        titlePopup = new CommentPopup(this, ScaleUtil.dip2px(this, 165),
                ScaleUtil.dip2px(this, 40));
        titlePopup
                .addAction(new ActionItem(this, "赞", R.drawable.circle_praise));
        titlePopup.addAction(new ActionItem(this, "评论",
                R.drawable.circle_comment));
        titlePopup.setItemOnClickListener(this);
    }
    private void initThreadHead(){
        thread_avatar = (RoundImageView)findViewById(R.id.thread_avatar);
        thread_author = (TextView) findViewById(R.id.thread_author);
        thread_title = (TextView) findViewById(R.id.thread_title);
        thread_content = (TextView) findViewById(R.id.thread_content);
        //直接从本地读取,后期再优化
        thread_avatar.setImageBitmap(FormatTools.loadImageByLocal(memory, threadhead.getAvatarurl(), PreferenceConstants.AVATAR_PATH));
        thread_author.setText(threadhead.getAuthor());
        thread_title.setText(threadhead.getSubject());
        thread_content.setText(threadhead.getFirstcontent());
        // 初始化物品附件
        grid_goods = (GridView) findViewById(R.id.grid_goods);
        gridadapter = new MyGridAdapter();
        grid_goods.setAdapter(gridadapter);
        grid_goods.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                Intent intent = new Intent(ReadThread.this, GoodsDetail.class);
                startActivity(intent);
                overridePendingTransition(R.anim.magnifyandalpha,
                        R.anim.slide_still);
            }

        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.expan_txt:
                Intent intent1 = new Intent(this, ReadPost.class);
                startActivity(intent1);
                break;
            case R.id.read_reply:
                // showPopUp(v);
                // titlePopup.setAnimationStyle(R.style.cricleBottomAnimation);
                titlePopup.show(v);
                break;
            case R.id.send_confirm:
                if(sendpost.getText().toString().equals(""))
                {
                    T.showShort(getApplicationContext(), "请输入回帖信息！");
                }else{
                    ForumPostEntity post = new ForumPostEntity();

//				post.setTid(i);
//				post.setAuthor("lee" + i);
//				post.setAuthorid(i);
                    // post.setSubject();
                    post.setMessage(sendpost.getText().toString());
                    // post.setDateline();
                    posts.add(post);

                    hideView();
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.reply_txt:
                read_rel2.setVisibility(View.GONE);
                read_rel3.setVisibility(View.VISIBLE);
                read_rel3.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                break;
            case R.id.read_more:
                PopupWindowUtil.showDropWindow(ReadThread.this,read_rel1,R.layout.dialog4);
                break;
            case R.id.participate:
//                PopupWindowUtil.showDropWindow(ReadThread.this,read_rel1,R.layout.dialog4);
                break;
            case R.id.share:
//                PopupWindowUtil.showDropWindow(ReadThread.this,read_rel1,R.layout.dialog4);
                break;
            case R.id.collect:
//                PopupWindowUtil.showDropWindow(ReadThread.this,read_rel1,R.layout.dialog4);
                break;
            case R.id.warning:
//                PopupWindowUtil.showDropWindow(ReadThread.this,read_rel1,R.layout.dialog4);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
            case R.id.read_listview:
                hideView();
                break;
            default:
                break;
        }
        return false;
    }
    private void hideView(){
        if (read_rel3.getVisibility() == View.VISIBLE) {
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                try {
                    inputMethodManager.hideSoftInputFromWindow(
                            (ReadThread.this).getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {

                }
            }
            read_rel3.setVisibility(View.GONE);
            read_rel2.setVisibility(View.VISIBLE);
        }
    }
    class MyGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 5;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = View.inflate(ReadThread.this,
                    R.layout.goods_detail_item, null);
            return view;
        }
    }

    @Override
    public void onItemClick(ActionItem item, int position) {
        // TODO 自动生成的方法存根

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still,
                    R.anim.push_right_out);
            return false;
        }
        return false;
    }
}
