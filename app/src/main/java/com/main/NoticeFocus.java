package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.adapter.NoticeFocusAdapter;
import com.example.charitydemo.R;

public class NoticeFocus extends Activity{
    private ListView listView;
    private NoticeFocusAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_focus);
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        listView = (ListView)findViewById(R.id.notice_focus_listview);
        adapter = new NoticeFocusAdapter(this);
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
