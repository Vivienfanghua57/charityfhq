package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import com.adapter.TopicAdapter;
import com.example.charitydemo.R;

public class Topic extends Activity{
    private ListView listView;
    private TopicAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic);
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        listView = (ListView)findViewById(R.id.topic_listview);
        adapter = new TopicAdapter(this);
        listView.setAdapter(adapter);
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
