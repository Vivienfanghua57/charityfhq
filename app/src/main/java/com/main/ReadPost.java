package com.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.adapter.ReadPostAdapter;
import com.entity.ForumPostEntity;
import com.entity.ForumThreadEntity;
import com.example.charitydemo.R;

public class ReadPost extends Activity {
	private ListView listview;
	private ReadPostAdapter adapter;
	private TextView expan_txt;
	private Button pi_div1, pi_div3, pi_div4;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readpost);

		initview();
	}

	private void initview() {
		listview = (ListView) findViewById(R.id.post_listview);
		adapter = new ReadPostAdapter(this);
		listview.addHeaderView(LayoutInflater.from(this).inflate(
				R.layout.post_item, null));
		expan_txt = (TextView) findViewById(R.id.expan_txt);
		pi_div1 = (Button) findViewById(R.id.pi_div1);
		pi_div3 = (Button) findViewById(R.id.pi_div3);
		pi_div4 = (Button) findViewById(R.id.pi_div4);
		expan_txt.setVisibility(View.GONE);
		pi_div1.setVisibility(View.GONE);
		pi_div3.setVisibility(View.GONE);
		pi_div4.setVisibility(View.GONE);
		listview.setAdapter(adapter);
	}
}
