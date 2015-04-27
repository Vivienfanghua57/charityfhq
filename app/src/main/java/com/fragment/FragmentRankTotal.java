package com.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.adapter.RankAdapter;
import com.example.charitydemo.R;

public class FragmentRankTotal extends Fragment implements OnClickListener{
	protected View mMainView;
	 protected Context mContext;
	 private ListView listview;
	 private RankAdapter adapter;
	 
	 public static FragmentRankTotal newInstance() {  
		 FragmentRankTotal newFragment = new FragmentRankTotal();  
	        return newFragment;  
	    }
	    
	    public FragmentRankTotal() {
	        super();
	    }

	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        mContext = activity.getApplicationContext();
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        mMainView = inflater.inflate(R.layout.totalrank, container, false);
	        listview = (ListView)mMainView.findViewById(R.id.totalrank_listview);
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
