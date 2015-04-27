package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.charitydemo.R;

public class HDPicture extends Activity{
    private ImageView picture;
    private ProgressBar progressbar;
    private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hdpicture);
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        picture = (ImageView)findViewById(R.id.picture);
        progressbar = (ProgressBar)findViewById(R.id.hdpicture_progressBar);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                progressbar.setVisibility(View.GONE);
                picture.setImageResource(R.drawable.m);
            }

        }, SPLASH_DISPLAY_LENGHT);
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
}
