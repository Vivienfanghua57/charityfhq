package com.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.charitydemo.R;

public class Add extends Activity implements OnClickListener{
    private EditText add_search;
    private RelativeLayout add_rel,add_rel2;
    //搜索框的y值和高度
    private float y;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        add_rel=(RelativeLayout)findViewById(R.id.add_rel);
        add_rel2=(RelativeLayout)findViewById(R.id.add_rel2);
        add_search=(EditText)findViewById(R.id.add_search);
        add_search.setOnClickListener(this);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still,
                    R.anim.minifyandalpha);
            return false;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
            case R.id.add_search:
                y = add_rel2.getY();
//			height = focus_search.getHeight();
                TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -y);
                animation.setDuration(200);
                animation.setFillAfter(true);
                animation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), SearchMemberGroup.class);
                        startActivityForResult(intent, 100);
                        overridePendingTransition(R.anim.animation_search_2,R.anim.slide_still);
                    }
                });
                add_rel.startAnimation(animation);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -y, 0);
        animation.setDuration(200);
        animation.setFillAfter(true);
        add_rel.startAnimation(animation);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
