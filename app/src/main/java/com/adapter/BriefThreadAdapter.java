package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adapter.FocusAdapter.ViewHolder;
import com.example.charitydemo.R;

public class BriefThreadAdapter extends BaseAdapter{
    LayoutInflater inflater;
    private Context context;

    public BriefThreadAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return 8;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.brief_thread_item, null);
            holder = new ViewHolder();
            holder.thread_time = (TextView) convertView.findViewById(R.id.thread_time);
            holder.thread_title = (TextView) convertView.findViewById(R.id.thread_title);
            holder.thread_content = (TextView) convertView.findViewById(R.id.thread_content);
//            holder.postcount = (TextView) convertView.findViewById(R.id.postcount);
//            holder.grabcount = (TextView) convertView.findViewById(R.id.grabcount);
            holder.thread_frontimg = (ImageView) convertView.findViewById(R.id.thread_frontimg);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        switch(position){
            case 0:
                holder.thread_time.setText("6:45");
                holder.thread_title.setText("这是家里闲置的几件衣服");
                holder.thread_content.setText("希望可以帮助到四川灾区的小朋友们，可以让他们不再受寒冷所困扰");
//                holder.postcount.setText("25");
//                holder.grabcount.setText("46");
                holder.thread_frontimg.setImageResource(R.drawable.test2);
                break;
            case 1:
                holder.thread_time.setText("8月26日");
                holder.thread_title.setText("送水果看望孤寡老人");
                holder.thread_content.setText("大家有没有推荐的比较需要人照顾的敬老院，我想和同伴们去看望看望他们");
//                holder.postcount.setText("65");
//                holder.grabcount.setText("78");
                holder.thread_frontimg.setImageResource(R.drawable.test6);
                break;
            default:
                break;

        }
        return convertView;
    }

    class ViewHolder{
        TextView thread_time;
        TextView thread_title;
        TextView thread_content;
        ImageView thread_frontimg;
    }
}
