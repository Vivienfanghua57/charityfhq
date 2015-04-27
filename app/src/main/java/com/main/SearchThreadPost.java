package com.main;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.charitydemo.R;
import com.search.SearchAdapter;
import com.search.SearchData;

public class SearchThreadPost extends Activity implements OnClickListener{
    public static final String SEARCH_HISTORY = "search_history";
    private ListView listview;
    private TextView search;
    private TextView search_edit;
    private RelativeLayout search_rel;
    private SearchAdapter adapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_threadpost);
        initview();
    }
    private void initview() {
        adapter = new SearchAdapter(this, -1, this);
        listview = (ListView) findViewById(R.id.search_listview);
        listview.addHeaderView(LayoutInflater.from(this).inflate(
                R.layout.searchhead, null));
        listview.addFooterView(LayoutInflater.from(this).inflate(
                R.layout.searchfoot, null));
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long arg3) {
                position-=1;
                SearchData data = (SearchData) adapter.getItem(position);
                search_edit.setText(data.getContent());
                search.performClick();
            }
        });
        search_rel = (RelativeLayout) findViewById(R.id.search_rel);
        search_rel.setOnClickListener(this);
        search = (TextView) findViewById(R.id.search);
        search.setOnClickListener(this);
        search_edit = (TextView) findViewById(R.id.search_edit);
        search_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                adapter.performFiltering(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                saveSearchHistory();
                adapter.initSearchHistory();
                break;
            case R.id.search_rel:
                deleteSearchHistory();
                //adapter.performFiltering("");
                //adapter.notifyDataSetChanged();
                adapter.initSearchHistory();
                adapter.performFiltering("");
                break;
            default:
                break;

        }
    }
    /*
     * 清除记录
     */
    private void deleteSearchHistory() {
        SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SEARCH_HISTORY, "");
        editor.commit();
    }
    /*
     * 保存搜索记录
     */
    private void saveSearchHistory() {
        String text = search_edit.getText().toString().trim();
        if (text.length() < 1) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longhistory.split(",");
        ArrayList<String> history = new ArrayList<String>(
                Arrays.asList(tmpHistory));
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (text.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
            history.add(0, text);
        }
        if (history.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        } else {
            sp.edit().putString(SEARCH_HISTORY, text + ",").commit();
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still,
                    R.anim.animation_search_3);
            return false;
        }
        return false;
    }
}
