package com.main;

import com.example.charitydemo.R;
import com.util.MyScrollView;
import com.util.MyScrollView.onTurnListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ProfileGroup extends Activity implements OnClickListener, onTurnListener{
    private GridView grid_main;
    private MyGridAdapter adapter;
    MyScrollView myScrollView;
    ImageView pro_bg;
    View line;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_group);
        grid_main = (GridView) findViewById(R.id.grid_main);
        adapter = new MyGridAdapter();
        grid_main.setAdapter(adapter);
        pro_bg = (ImageView) findViewById(R.id.pro_bg);
        myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
        line = (View) findViewById(R.id.line);
        myScrollView.setTurnListener(this);
        myScrollView.setImageView(pro_bg);
        myScrollView.setLine(line);

    }
    @Override
    public void onTurn() {


    }

    class MyGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 8;
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
            View view = View.inflate(ProfileGroup.this, R.layout.profile_group_item, null);

            return view;
        }
    }
    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根

    }
}