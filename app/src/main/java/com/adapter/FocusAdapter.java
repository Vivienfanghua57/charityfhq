package com.adapter;

import java.util.List;

import com.chat.MessageEntity;
import com.db.DatabaseHelper;
import com.entity.FocusEntity;
import com.example.charitydemo.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FocusAdapter extends BaseAdapter{
    //	private Cursor cursor;
    private Context context;
    private AlphabetIndexer indexer;
    private List<FocusEntity> friends;

    /**
     * @param cursor
     * @param context
     * @param indexer
     */
    public FocusAdapter(List<FocusEntity> friends, Context context, AlphabetIndexer indexer) {
        super();
//		this.cursor = cursor;
        this.context = context;
        this.indexer = indexer;
        this.friends =friends;
    }

    @Override
    public int getCount() {
//		return cursor.getCount();
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
//		cursor.moveToPosition(position);
//		return cursor;
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.focus_item, null);
            holder = new ViewHolder();
            holder.tvLetter = (TextView) convertView.findViewById(R.id.tvLetter);
            holder.focusname = (TextView) convertView.findViewById(R.id.tvCompanyName_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
//		cursor.moveToPosition(position);
        holder.focusname.setText(friends.get(position).getFocusName());
        //获取该item对应的字母在字母表中的位置
        int section = indexer.getSectionForPosition(position);
        //获取该字母分组中第一条数据的位置
        int pos = indexer.getPositionForSection(section);
        if(pos == position){
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(friends.get(position).getInitial());
        }else{
            holder.tvLetter.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tvLetter;
        TextView focusname;
    }
}
