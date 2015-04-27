package com.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.entity.ForumPostEntity;
import com.example.charitydemo.R;

public class ReadThreadAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private OnClickListener OnClickListener;
    private Context context;
    private List<ForumPostEntity> posts;

    public ReadThreadAdapter(List<ForumPostEntity> posts, Context context,
                             OnClickListener onClickListener) {
        inflater = LayoutInflater.from(context);
        this.OnClickListener = onClickListener;
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO 自动生成的方法存根
        return posts.get(position);
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.post_item, null);
            holder = new ViewHolder();
            holder.expan_txt = (TextView) convertView
                    .findViewById(R.id.expan_txt);
            holder.read_reply = (Button) convertView
                    .findViewById(R.id.read_reply);
            holder.pi_div1 = (Button) convertView.findViewById(R.id.pi_div1);
            holder.post_content = (TextView) convertView
                    .findViewById(R.id.post_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 这里的目的是为了head和listview的第一个item交接时的边界看起来正常
        if (position == 0) {
            holder.pi_div1.setVisibility(View.GONE);
        }
        holder.post_content.setText(posts.get(position).getMessage());
        holder.expan_txt.setOnClickListener(OnClickListener);
        holder.read_reply.setOnClickListener(OnClickListener);
        return convertView;
    }

    class ViewHolder {
        TextView expan_txt, post_content;
        Button read_reply, pi_div1;
    }
}
