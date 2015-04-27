package com.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.adapter.IDManageAdapter;
import com.chat.ChatActivity;
import com.db.DatabaseHelper;
import com.entity.MemberEntity;
import com.entity.UserEntitiy;
import com.example.charitydemo.R;
import com.util.MyApp;
import com.util.PopupWindowUtil;
import com.util.PreferenceConstants;

public class IDManage extends Activity implements OnClickListener {
    public static IDManage instance = null;
    private ListView listView;
    private IDManageAdapter adapter;
    private List<MemberEntity> members = new ArrayList<MemberEntity>();
    private String url;
    private SQLiteDatabase db;
    private SharedPreferences sp;
    //	private String loginuser;
    private RelativeLayout im_rel2,imfoot_rel1;
    private MyApp app;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idmanage);
        instance = this;
        app=(MyApp)getApplication();
        DatabaseHelper database = new DatabaseHelper(this);
        db = database.getReadableDatabase();
        sp = getSharedPreferences("default_user", Activity.MODE_PRIVATE);
        // loginuser = sp.getString("user_default_id", "");
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        members = DatabaseHelper.getMembers(db);
        listView = (ListView) findViewById(R.id.idmanage_listview);
        listView.addFooterView(LayoutInflater.from(this).inflate(
                R.layout.idmanage_foot, null));
        adapter = new IDManageAdapter(this, members, PreferenceConstants.ACCOUNT,app,listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
//				position -= 1;
//				Intent intent = new Intent(FocusActivity.this, ChatActivity.class);
//				startActivity(intent);
//				Log.i("", position + "");
            }
        });
        im_rel2 = (RelativeLayout) findViewById(R.id.im_rel2);
        im_rel2.setOnClickListener(this);
        imfoot_rel1 = (RelativeLayout) findViewById(R.id.imfoot_rel1);
        imfoot_rel1.setOnClickListener(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
            case R.id.im_rel2:
                PopupWindowUtil.showDialog(IDManage.this,R.layout.idmanage,R.layout.dialog2);
                PopupWindowUtil.dialog2_quite.setText("确定");
                PopupWindowUtil.dialog2_content.setText("确认退出登陆？");
                break;
            case R.id.imfoot_rel1:
                Intent intent2=new Intent(IDManage.this,Login.class);
                intent2.putExtra("IFADD", "yes");
                startActivity(intent2);
                break;
            case R.id.dialog2_cancel:
                PopupWindowUtil.popupWindow.dismiss();
                break;
            case R.id.dialog2_confirm:
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user_default_pwd", "");
//			editor.putInt("user_if_autologin", 0);
                editor.commit();
                PreferenceConstants.ACCOUNT="";
                PreferenceConstants.PASSWORD="";
                Intent intent1=new Intent(IDManage.this,Login.class);
                intent1.putExtra("IFEXIT", "yes");
                startActivity(intent1);
                MainActivity.instance.finish();
                Settings.instance.finish();
                finish();
                break;
            default:
                break;
        }
    };

}
