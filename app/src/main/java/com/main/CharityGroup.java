package com.main;

import com.adapter.CharityGroupAdapter;
import com.example.charitydemo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CharityGroup extends Activity{
    private ListView listView;
    private CharityGroupAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charitygroup);
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        listView = (ListView)findViewById(R.id.charitygroup_listview);
        adapter = new CharityGroupAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                Intent intent = new Intent(CharityGroup.this, ProfileGroup.class);
                startActivity(intent);
            }
        });
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
