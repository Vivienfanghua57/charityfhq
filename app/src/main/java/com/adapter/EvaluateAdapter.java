package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.charitydemo.R;
import com.util.RoundAngleImageView;

public class EvaluateAdapter extends BaseAdapter{
    LayoutInflater inflater;
    private Context context;

    public EvaluateAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.evaluate_item, null);
            holder = new ViewHolder();
            holder.evaluate_status = (TextView) convertView.findViewById(R.id.evaluate_status);
            holder.trade_author = (TextView) convertView.findViewById(R.id.trade_author);
            holder.trade_time = (TextView) convertView.findViewById(R.id.trade_time);
            holder.trade_thread_title = (TextView) convertView.findViewById(R.id.trade_thread_title);
            holder.trade_thread_content = (TextView) convertView.findViewById(R.id.trade_thread_content);
            holder.trade_type = (TextView) convertView.findViewById(R.id.trade_type);
            holder.trade_count = (TextView) convertView.findViewById(R.id.trade_count);
            holder.trade_evaluate = (Button) convertView.findViewById(R.id.trade_evaluate);
            holder.trade_avatar = (RoundAngleImageView) convertView.findViewById(R.id.trade_avatar);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        switch(position){
            case 0:
                holder.trade_author.setText("方华强");
                holder.trade_time.setText("16:30");
                holder.trade_thread_title.setText("青山小学慈善组招募志愿者");
                holder.trade_thread_content.setText("所得捐助奖励给表现优异的一年级小学生们");
                holder.trade_avatar.setImageResource(R.drawable.g);
                break;
            case 1:
                holder.trade_author.setText("李舒");
                holder.evaluate_status.setText("已评价");
                holder.trade_time.setText("2015-5-10");
                holder.trade_evaluate.setVisibility(View.GONE);
                holder.trade_thread_title.setText("送水果看望孤寡老人");
                holder.trade_thread_content.setText("大家有没有推荐的比较需要人照顾的敬老院，我想和同伴们去看望看望他们");
                holder.trade_type.setText("水果");
                holder.trade_count.setText("100");
                holder.trade_avatar.setImageResource(R.drawable.f);
                break;
            default:
                break;

        }
        return convertView;
    }

    class ViewHolder{
        TextView evaluate_status;
        TextView trade_author;
        TextView trade_time,trade_thread_title,trade_thread_content,trade_type,trade_count;
        Button trade_evaluate;
        RoundAngleImageView trade_avatar;
    }

}
