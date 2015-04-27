package com.main;

import com.example.charitydemo.R;
import com.util.PopupWindowUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class Settings extends Activity implements OnClickListener{
    private RelativeLayout settings_rel2,settings_rel3,settings_rel4,settings_rel5,
            settings_rel6,settings_rel7,settings_rel9,settings_rel10;
    public static Settings instance = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        instance = this;
        initview();
    }
    private void initview() {
        // TODO 自动生成的方法存根
        settings_rel2 =(RelativeLayout)findViewById(R.id.settings_rel2);
        settings_rel3 =(RelativeLayout)findViewById(R.id.settings_rel3);
        settings_rel4 =(RelativeLayout)findViewById(R.id.settings_rel4);
        settings_rel5 =(RelativeLayout)findViewById(R.id.settings_rel5);
        settings_rel6 =(RelativeLayout)findViewById(R.id.settings_rel6);
        settings_rel7 =(RelativeLayout)findViewById(R.id.settings_rel7);
        settings_rel9 =(RelativeLayout)findViewById(R.id.settings_rel9);
        settings_rel10 =(RelativeLayout)findViewById(R.id.settings_rel10);
        settings_rel2.setOnClickListener(this);
        settings_rel3.setOnClickListener(this);
        settings_rel4.setOnClickListener(this);
        settings_rel5.setOnClickListener(this);
        settings_rel6.setOnClickListener(this);
        settings_rel7.setOnClickListener(this);
        settings_rel9.setOnClickListener(this);
        settings_rel10.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
            case R.id.settings_rel2:
                Intent intent=new Intent(this,IDManage.class);
                startActivity(intent);
                break;
            case R.id.settings_rel3:

                break;
            case R.id.settings_rel4:

                break;
            case R.id.settings_rel5:

                break;
            case R.id.settings_rel6:

                break;
            case R.id.settings_rel7:

                break;
            case R.id.settings_rel9:

                break;
            case R.id.settings_rel10:
                PopupWindowUtil.showDialog(Settings.this,R.layout.settings,R.layout.dialog2);
                PopupWindowUtil.dialog2_quite.setText("确定");
                PopupWindowUtil.dialog2_content.setText("确认退出微慈善？");
                break;
            case R.id.dialog2_cancel:
                PopupWindowUtil.popupWindow.dismiss();

                break;
            case R.id.dialog2_confirm:
                PopupWindowUtil.popupWindow.dismiss();
                finish();
                MainActivity.instance.finish();
                break;
            default:
                break;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still, R.anim.push_right_out);
            return false;
        }
        return false;
    }
}
