package com.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adapter.GoodsAdapter;
import com.chat.ChatActivity;
import com.entity.GoodsEntity;
import com.entity.GoodsParaEntity;
import com.example.charitydemo.R;
import com.main.NewThread.TextWatcher_Enum;
import com.util.FormatTools;
import com.util.T;

public class NewThreadGoods extends Activity implements OnClickListener{
    private ListView listView;
    private GoodsAdapter adapter;
    private RelativeLayout goods_rel;
    public static List<GoodsEntity> goods=new ArrayList<GoodsEntity>();
    public static List<GoodsParaEntity> goodsparas=new ArrayList<GoodsParaEntity>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newthread2);
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根

        listView = (ListView)findViewById(R.id.newthr2_goods_listview);
        listView.addFooterView(LayoutInflater.from(this).inflate(
                R.layout.goods_head, null));
        listView.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                Intent intent = new Intent(NewThreadGoods.this, GoodsDetail.class);
                intent.putExtra("position", position);
                startActivity(intent);
                overridePendingTransition(R.anim.magnifyandalpha,
                        R.anim.slide_still);
            }

        });
        goods_rel = (RelativeLayout)findViewById(R.id.goods_rel);
        goods_rel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_rel:
                Intent intent=new Intent(this,NewThreadGoodsDes.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_in,
                        R.anim.slide_still);
                break;
            default:
                break;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
            return false;
        }
        return false;
    }
    //		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//			if (resultCode != RESULT_CANCELED) {
//
//				switch (requestCode) {
//				// 本地上传
//				case 1:
//					adapter.notifyDataSetChanged();
////					adapter = new GoodsAdapter(goods,this);
////					listView.setAdapter(adapter);
//					break;
//				default:
//
//					break;
//				}
//			}
//			super.onActivityResult(requestCode, resultCode, data);
//		}
    //Activity创建或者从后台重新回到前台时被调用
    @Override
    protected void onStart() {
        super.onStart();
        adapter = new GoodsAdapter(goods,this);
        listView.setAdapter(adapter);
    }

}
