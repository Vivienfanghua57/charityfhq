package com.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow.OnDismissListener;

import com.example.charitydemo.R;
import com.util.NetBroadcastReceiver.netEventHandler;
import com.util.NetUtil;
import com.util.PopupWindowUtil;
import com.util.PreferenceConstants;
import com.util.T;

public class Register extends Activity implements OnClickListener,
        netEventHandler {

    private EditText username_edit, pwd_edit, confirm_edit, email_edit;
    private Handler handler;
    private String flag;
    private Button register_btn2;
    private String id, pwd, confirm_pwd,email;
    private HttpPost httpRequest;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerbyemail);
        initview();
    }

    private void initview() {
        username_edit = (EditText) findViewById(R.id.username_edit);
        pwd_edit = (EditText) findViewById(R.id.pwd_edit);
        confirm_edit = (EditText) findViewById(R.id.confirm_edit);
        email_edit = (EditText) findViewById(R.id.email_edit);
        register_btn2 = (Button) findViewById(R.id.register_btn2);
        register_btn2.setOnClickListener(this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    try {
                        flag = msg.getData().getString("strResult");
                    } catch (NullPointerException e) {
                    }
                    if (flag.equals("1")) {

                        PopupWindowUtil.popupWindow.dismiss();
                        T.showShort(Register.this, "注册成功！");

                    } else if (flag.equals("0")) {
                        PopupWindowUtil.popupWindow.dismiss();
                        T.showShort(Register.this, "同名用户已注册！");
                    } else if (flag.equals("2")) {
                        PopupWindowUtil.popupWindow.dismiss();
                        T.showShort(Register.this, "连接超时！服务器未响应");
                    }
                }
            }
        };
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

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
            case R.id.register_btn2:
                id = username_edit.getText().toString();
                pwd = pwd_edit.getText().toString();
                confirm_pwd=confirm_edit.getText().toString();
                email = email_edit.getText().toString();
                if (PreferenceConstants.isNetAvailable) {
                    if(id.equals("")||pwd.equals("")||confirm_pwd.equals("")||email.equals("")){
                        T.showShort(getApplicationContext(), "请将信息填写完整！");
                    }else{
                        if(!pwd.equals(confirm_pwd)){
                            T.showShort(getApplicationContext(), "两次输入的密码不一样！");
                        }else{
                            if(!isEmail(email)){
                                T.showShort(getApplicationContext(), "请填写正确的邮箱！");
                            }else{
                                // 开启新线程登陆
                                new Thread(registerRunnable).start();
                                // showWindow();
                                PopupWindowUtil.showDialog(Register.this,
                                        R.layout.registerbyemail, R.layout.dialog1);
                                PopupWindowUtil.dialog1_content.setText("正在注册，请稍后...");
                                PopupWindowUtil.popupWindow
                                        .setOnDismissListener(new OnDismissListener() {

                                            @Override
                                            public void onDismiss() {
                                                // TODO 自动生成的方法存根
                                                if (httpRequest.isAborted()) {

                                                } else {
                                                    httpRequest.abort();
                                                }
                                            }

                                        });
                            }
                        }
                    }
                } else {
                    T.showShort(getApplicationContext(), R.string.net_error_tip);
                }
                break;
            default:
                break;
        }
    }
    private boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    private Runnable registerRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO 自动生成的方法存根
            register();
        }

    };

    public void register() {
        //
        String httpUrl = "http://"
                + getResources().getString(R.string.server_ip)
                + ":7070/user-manager-methods-action!Register";
        httpRequest = new HttpPost(httpUrl);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", id));
        params.add(new BasicNameValuePair("password", pwd));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("accessType", "android"));
        HttpEntity httpEntity = null;
        try {
            httpEntity = new UrlEncodedFormEntity(params, "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpRequest.setEntity(httpEntity);
        HttpClient httpClient = new DefaultHttpClient();
        // 连接超时
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        // 读取超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                60000);

        HttpResponse httpResponse = null;
        // 返回0则表示登陆失败，2表示无法访问到网络
        String strResult = "";
        try {
            httpResponse = httpClient.execute(httpRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 1表示成功，0表示失败即同名用户已注册
                strResult = EntityUtils.toString(httpResponse.getEntity(),
                        "UTF-8");
            }
        } catch (IOException e) {
            if (e.getMessage().contains("time")) {
                // 连接超时
                strResult = "2";
            }
            e.printStackTrace();
        }
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        message.what = 1;
        bundle.putString("strResult", strResult);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetworkState(getApplicationContext()) == NetUtil.NETWORN_NONE) {
            if (PopupWindowUtil.popupWindow.isShowing()) {
                PopupWindowUtil.popupWindow.dismiss();
            }
            PreferenceConstants.isNetAvailable = false;
        } else {
            PreferenceConstants.isNetAvailable = true;
        }
    }
}
