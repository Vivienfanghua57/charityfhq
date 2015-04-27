package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adapter.ThreadListAdapter.ViewHolder;
import com.example.charitydemo.R;
import com.util.RoundAngleImageView;

public class CharityGroupAdapter  extends BaseAdapter{
    LayoutInflater inflater;
    private Context context;

    public CharityGroupAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return 4;
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
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.charitygoup_item, null);
            holder = new ViewHolder();
            holder.group_name = (TextView) convertView.findViewById(R.id.group_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        switch(position){
            case 0:
                holder.group_name.setText("洪山区义务慈善组");
                break;
            case 1:
                holder.group_name.setText("江城汉口慈善点");
                break;
            case 2:
                holder.group_name.setText("武汉市慈善总点");
                break;
            case 3:
                holder.group_name.setText("武汉市江夏区慈善工会");
                break;
        }
        return convertView;
    }
    class ViewHolder{
        TextView group_name;
    }
}
