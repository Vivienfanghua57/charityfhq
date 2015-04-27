package com.adapter;

import com.adapter.FocusAdapter.ViewHolder;
import com.example.charitydemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RankAdapter extends BaseAdapter{
    LayoutInflater inflater;
    private Context context;

    public RankAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return 10;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.rank_item, null);
            holder = new ViewHolder();
            holder.rank_position = (TextView) convertView.findViewById(R.id.rank_position);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.rank_position.setText(position+1+"");
        return convertView;
    }
    class ViewHolder{
        TextView rank_position;
    }
}
