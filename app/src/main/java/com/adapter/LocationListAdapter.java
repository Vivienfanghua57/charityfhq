package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.charitydemo.R;
import com.main.ProfileUser;

import java.util.List;

/**
 * Created by Vivien on 2015/4/22.
 */
public class LocationListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private Context context;
    private List<String> locationList;
    private ListView listview;

    public LocationListAdapter(Context context, List<String> locatios, ListView listview) {
        inflater = LayoutInflater.from(context);
        this.locationList = locatios;
        this.context = context;
        this.listview = listview;
    }

    @Override
    public int getCount() {
        return locationList.size();
    }

    @Override
    public Object getItem(int i) {
        return locationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int positon, View convertview, ViewGroup viewGroup) {
        ViewHolder holder = null;
        TextView locationitem = null;
        if (convertview == null) {
            convertview = LayoutInflater.from(context).inflate(R.layout.locationitem, null);
            locationitem=(TextView)convertview.findViewById(R.id.locationitemtext);
            holder = new ViewHolder();
            holder.locTextView = (TextView) convertview.findViewById(R.id.locationitemtext);
            locationitem.setText(locationList.get(positon));
            convertview.setTag(holder);
        } else {
            holder = (ViewHolder) convertview.getTag();
        }
        holder.locTextView.setText(locationList.get(positon));
        return convertview;
    }

    class ViewHolder {
        TextView locTextView;
    }

    class ClickLisener implements View.OnClickListener {
        public int position;

        public ClickLisener(int p) {
            position = p;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
