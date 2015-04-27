package com.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.adapter.RankAdapter;
import com.example.charitydemo.R;
import com.util.CustomListView;

public class FragmentRankDay extends Fragment implements OnClickListener{
	 protected View mMainView;
	 protected Context mContext;
	 private ListView listview;
	 private RankAdapter adapter;
	 
	 public static FragmentRankDay newInstance() {  
		 FragmentRankDay newFragment = new FragmentRankDay();  
	        return newFragment;  
	    }
	    
	    public FragmentRankDay() {
	        super();
	    }

	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        mContext = activity.getApplicationContext();
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        mMainView = inflater.inflate(R.layout.dayrank, container, false);
	        listview = (ListView)mMainView.findViewById(R.id.dayrank_listview);
			adapter = new RankAdapter(mContext);
			listview.setAdapter(adapter);
	        return mMainView;
	    }
		 @Override
		 public void onClick(View v) {
				switch (v.getId()) {
				default:
					break;
				}
			}
}
