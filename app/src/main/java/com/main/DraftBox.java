package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.adapter.DraftBoxAdapter;
import com.adapter.NoticeCommentAdapter;
import com.example.charitydemo.R;

public class DraftBox extends Activity{
    private ListView listView;
    private DraftBoxAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draftbox);
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        listView = (ListView)findViewById(R.id.draftbox_listview);
        adapter = new DraftBoxAdapter(this);
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
