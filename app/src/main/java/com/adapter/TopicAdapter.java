package com.adapter;

import com.example.charitydemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TopicAdapter extends BaseAdapter{

    LayoutInflater inflater;

    public TopicAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return 1;
    }

    @Override
    public Object getItem(int position) {
        // TODO 自动生成的方法存根
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO 自动生成的方法存根
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO 自动生成的方法存根
        convertView = inflater
                .inflate(R.layout.topic_item, null);
        return convertView;
    }

}
