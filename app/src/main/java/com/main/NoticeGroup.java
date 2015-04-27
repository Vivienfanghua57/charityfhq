package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.adapter.NoticeGroupAdapter;
import com.example.charitydemo.R;

public class NoticeGroup extends Activity{
    private ListView listView;
    private NoticeGroupAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_group);
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        listView = (ListView)findViewById(R.id.notice_group_listview);
        adapter = new NoticeGroupAdapter(this);
        listView.setAdapter(adapter);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still, R.anim.push_right_out);
            return false;
        }
        return false;
    }
}
