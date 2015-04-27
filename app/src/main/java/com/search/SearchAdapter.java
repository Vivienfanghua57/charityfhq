package com.search;

import java.util.ArrayList;
import java.util.List;

import com.example.charitydemo.R;
import com.main.SearchThreadPost;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<SearchData> mOriginalValues;
    private List<SearchData> mObjects;
    private final Object mLock = new Object();
    private int mMaxMatch = 10;
    private OnClickListener mOnClickListener;

    public SearchAdapter(Context context, int maxMatch,
                         OnClickListener onClickListener) {
        this.mContext = context;
        this.mMaxMatch = maxMatch;
        this.mOnClickListener = onClickListener;
        initSearchHistory();
        mObjects = mOriginalValues;
    }

    @Override
    public int getCount() {
        Log.i("cyl", "getCount");
        return null == mObjects ? 0 : mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mObjects ? 0 : mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AutoHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.search_item, parent, false);
            holder = new AutoHolder();
            holder.content = (TextView) convertView
                    .findViewById(R.id.content);
//			holder.div = (Button) convertView
//					.findViewById(R.id.searchitem_div1);
            convertView.setTag(holder);
        } else {
            holder = (AutoHolder) convertView.getTag();
        }

        SearchData data = mObjects.get(position);
        holder.content.setText(data.getContent());
        return convertView;
    }

    /**
     * 读取历史搜索记录
     */
    public void initSearchHistory() {
        SharedPreferences sp = mContext.getSharedPreferences(
                SearchThreadPost.SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SearchThreadPost.SEARCH_HISTORY, "");
        String[] hisArrays = longhistory.split(",");
        mOriginalValues = new ArrayList<SearchData>();
        if (hisArrays.length == 0||hisArrays[0].equals("")) {
            return;
        }
        for (int i = 0; i < hisArrays.length; i++) {
            mOriginalValues.add(new SearchData().setContent(hisArrays[i]));
        }
    }


    public void performFiltering(CharSequence prefix) {
        if (prefix == null || prefix.length() == 0) {
            synchronized (mLock) {
                mObjects = mOriginalValues;
            }
        } else {
            String prefixString = prefix.toString().toLowerCase();
            int count = mOriginalValues.size();
            ArrayList<SearchData> newValues = new ArrayList<SearchData>(
                    count);
            for (int i = 0; i < count; i++) {
                final String value = mOriginalValues.get(i).getContent();
                final String valueText = value.toLowerCase();
                if (valueText.contains(prefixString)) {

                }
                if (valueText.startsWith(prefixString)) {
                    newValues.add(new SearchData().setContent(valueText));
                } else {
                    final String[] words = valueText.split(" ");
                    final int wordCount = words.length;
                    for (int k = 0; k < wordCount; k++) {
                        if (words[k].startsWith(prefixString)) {
                            newValues.add(new SearchData()
                                    .setContent(value));
                            break;
                        }
                    }
                }
                if (mMaxMatch > 0) {
                    if (newValues.size() > mMaxMatch - 1) {
                        break;
                    }
                }
            }
            mObjects = newValues;
        }
        notifyDataSetChanged();
    }

    private class AutoHolder {
        TextView content;
        Button div;
    }
}
