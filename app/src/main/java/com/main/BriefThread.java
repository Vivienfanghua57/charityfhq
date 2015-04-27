package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import com.adapter.BriefThreadAdapter;
import com.example.charitydemo.R;

public class BriefThread extends Activity{
    private ListView listView;
    private BriefThreadAdapter adapter;
    private String title;
    private TextView bt_title;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.briefthread);
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        bt_title = (TextView)findViewById(R.id.bt_title);
        title=getIntent().getStringExtra("TITLE");
        bt_title.setText(title);
        listView = (ListView)findViewById(R.id.brief_thread_listview);
        adapter = new BriefThreadAdapter(this);
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
