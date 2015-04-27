package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.charitydemo.R;

public class SearchMemberGroup extends Activity implements OnClickListener {
    private EditText search_edit_usergroup;
    private RelativeLayout search_rel2;
    private TextView search_user, search_group;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_membergroup);
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        search_rel2 = (RelativeLayout) findViewById(R.id.search_rel2);
        search_user = (TextView) findViewById(R.id.search_user);
        search_group = (TextView) findViewById(R.id.search_group);
        search_rel2.setVisibility(View.GONE);
        search_edit_usergroup = (EditText) findViewById(R.id.search_edit_usergroup);
        search_edit_usergroup.addTextChangedListener(new TextWatcher_Enum());
    }

    class TextWatcher_Enum implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (!isNullOrEmpty(search_edit_usergroup.getText().toString())) {
                if (search_rel2.getVisibility() == 8)
                    search_rel2.setVisibility(View.VISIBLE);
                search_user.setText("用户："+search_edit_usergroup.getText().toString());
                search_group.setText("慈善组："+search_edit_usergroup.getText().toString());
            }else{
                if (search_rel2.getVisibility() == 0)
                    search_rel2.setVisibility(View.GONE);
            }

        }

        private boolean isNullOrEmpty(String str) {
            if (null == str || "".equals(str.trim()))
                return true;
            return false;
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still,
                    R.anim.animation_search_3);
            return false;
        }
        return false;
    }
}
