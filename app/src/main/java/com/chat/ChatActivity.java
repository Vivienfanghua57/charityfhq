package com.chat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.db.DatabaseHelper;
import com.example.charitydemo.R;
import com.main.ProfileUser;
import com.util.PreferenceConstants;
import com.util.T;

public class ChatActivity extends Activity implements OnClickListener{

    private ChatAdapter adapter;
    public List<MessageEntity> messages = new ArrayList<MessageEntity>();
    private ListView listview;
    private EditText msgText;
    private TextView send,chatTitle;
    private SQLiteDatabase db;
    private int chatID;
    private String chatname="";
    private String chatto_avatar="";
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        DatabaseHelper database = new DatabaseHelper(this);
        db = database.getReadableDatabase();
        chatID=getIntent().getIntExtra("CHATID", 0);
        chatname=getIntent().getStringExtra("CHATNAME");
        chatto_avatar=getIntent().getStringExtra("CHATAVATAR");
        initViews();
    }
    private void initViews() {
        chatTitle = (TextView) findViewById(R.id.chatTitle);
        chatTitle.setText(chatname);
        send = (TextView) findViewById(R.id.btn_send);
        send.setOnClickListener(this);
        listview = (ListView) findViewById(R.id.listview);
        msgText = (EditText) findViewById(R.id.et_sendmessage);
        view = findViewById(R.id.ll_facechoose);
//		messages.add(new MessageEntity("周董", "sb",
//				TimeRender.getDate(), 1));
//		messages.add(new MessageEntity("周董", "sb",
//				TimeRender.getDate(), 0));
//		adapter = new ChatAdapter(getApplicationContext(), messages);
//		listview.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String content = msgText.getText().toString();

                if (content.length() > 0) {
                    // 显示发送内容
                    MessageEntity message=new MessageEntity(PreferenceConstants.ACCOUNT,chatID,chatname,System.currentTimeMillis(),
                            content,1);
                    messages.add(message);
                    // 刷新发送的消息到界面
                    adapter.notifyDataSetChanged();
                    DatabaseHelper.addChatRecord(db, message);
                    DatabaseHelper.addChatList(db, PreferenceConstants.ACCOUNT, chatID,chatto_avatar);
                    msgText.setText("");
                    listview.setSelection(listview.getCount() - 1);

                } else {
                    T.showShort(getApplicationContext(), "请输入信息！！");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        chatRecords();
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    };
    private void chatRecords() {
        // TODO 自动生成的方法存根
        messages=DatabaseHelper.readChatRec(db, PreferenceConstants.ACCOUNT,chatID);
        adapter = new ChatAdapter(getApplicationContext(), messages);
        if(messages.size()>7){
            listview.setStackFromBottom(true);
        }
        listview.setAdapter(adapter);
//		if(messages.size()-listview.getLastVisiblePosition()>1){
//			listview.setStackFromBottom(true);
//		}
    }
    public void chat_avatar(View v) {
        Intent intent = new Intent (ChatActivity.this,ProfileUser.class);
        startActivity(intent);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            }else{
                finish();
                overridePendingTransition(R.anim.slide_still,
                        R.anim.minifyandalpha);}
            return false;
        }
        return false;
    }
}
