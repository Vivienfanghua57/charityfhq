package com.main;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.db.DatabaseHelper;
import com.entity.MemberEntity;
import com.example.charitydemo.R;
import com.util.FormatTools;
import com.util.MD5Util;
import com.util.MemoryCache;
import com.util.MyApp;
import com.util.NetUtil;
import com.util.PopupWindowUtil;
import com.util.PreferenceConstants;
import com.util.RoundImageView;
import com.util.T;
import com.util.NetBroadcastReceiver.netEventHandler;

public class Login extends Activity implements OnClickListener, netEventHandler{
    private ImageView delbtn1, delbtn2;
    private EditText userid_edit, pwd_edit;
    private RelativeLayout login_rel;
    private Button login_btn2, del1, del2;
    public String id, pwd;
    private Handler handler;
    private SharedPreferences sp;
    private SQLiteDatabase db;
    // 账号管理中是否是添加账号和是否是退出登陆
    private String isExit = "", ifAdd = "";
    private String flag;
    private MemberEntity member=new MemberEntity();
    private MyApp app;
    private MemoryCache memoryCache;
    private HttpPost httpRequest;
    private RoundImageView cir_proavatar;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        app=(MyApp) getApplication();
        memoryCache=app.getMemoryCache();
        // clientSet();
        DatabaseHelper database = new DatabaseHelper(this);
        db = database.getReadableDatabase();
        // 是否是从账户管理界面跳转过来的
        if (getIntent().getStringExtra("IFEXIT") != null) {
            isExit = getIntent().getStringExtra("IFEXIT");
        }
        if (getIntent().getStringExtra("IFADD") != null) {
            ifAdd = getIntent().getStringExtra("IFADD");
        }
        initview();
    }

    private void initview() {
        login_rel = (RelativeLayout) findViewById(R.id.login_rel);
        login_btn2 = (Button) findViewById(R.id.login_btn2);
        login_btn2.setOnClickListener(this);
        delbtn1 = (ImageView) findViewById(R.id.delbtn1);
        del1 = (Button) findViewById(R.id.del1);
        del1.setOnClickListener(this);
        delbtn2 = (ImageView) findViewById(R.id.delbtn2);
        del2 = (Button) findViewById(R.id.del2);
        del2.setOnClickListener(this);
        del2.setVisibility(View.GONE);
        findViewById(R.id.register).setOnClickListener(this);
        userid_edit = (EditText) findViewById(R.id.userid_edit);
        pwd_edit = (EditText) findViewById(R.id.pwd_edit);
        cir_proavatar = (RoundImageView) findViewById(R.id.cir_proavatar);

        sp = getSharedPreferences("default_user", Activity.MODE_PRIVATE);
        id = sp.getString("user_default_id", "");
        pwd = sp.getString("user_default_pwd", "");
        if (ifAdd.equals("")) {
            if (id != "" && pwd != "") {
                userid_edit.setText(id);
                pwd_edit.setText(pwd);
            } else if (id != "" && pwd.equals("")) {
                userid_edit.setText(id);
            }
        }

        setAvatar();

        // 如果id或pwd为""则设置登陆按钮为不可点击
        if (userid_edit.getText().toString().equals("")
                || pwd_edit.getText().toString().equals("")) {
            login_btn2.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.pop2));
            login_btn2.setClickable(false);
        }
        // 如果edittext有text则将光标定位在末端
        CharSequence text = userid_edit.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }

        userid_edit.addTextChangedListener(new TextWatcher_Enum());
        pwd_edit.addTextChangedListener(new TextWatcher_Enum());
        userid_edit
                .setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            // 此处为得到焦点时的处理内容
                            // delbtn2.setVisibility(View.GONE);
                            if (!(userid_edit.getText().toString().equals(""))) {
                                delbtn1.setVisibility(View.VISIBLE);
                                del1.setVisibility(View.VISIBLE);
                            }
                        } else {
                            // 此处为失去焦点时的处理内容
                            delbtn1.setVisibility(View.GONE);
                            del1.setVisibility(View.GONE);
                        }
                    }
                });
        pwd_edit.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // delbtn1.setVisibility(View.GONE);
                    if (!(pwd_edit.getText().toString().equals(""))) {
                        delbtn2.setVisibility(View.VISIBLE);
                        del2.setVisibility(View.VISIBLE);
                    }
                } else {
                    delbtn2.setVisibility(View.GONE);
                    del2.setVisibility(View.GONE);
                }
            }
        });
        login_rel.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                login_rel.setFocusable(true);
                login_rel.setFocusableInTouchMode(true);
                login_rel.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) Login.this
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(Login.this
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    try{
                        flag = msg.getData().getString("strResult");
                    }catch(NullPointerException e){

                    }
                    if (flag.equals("1")) {

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("user_default_id", id);
                        editor.putString("user_default_pwd", pwd);
                        // editor.putInt(
                        // "user_if_autologin",
                        // 1);
                        editor.commit();
                        PreferenceConstants.ACCOUNT = id;
                        PreferenceConstants.PASSWORD = pwd;

                        PopupWindowUtil.popupWindow.dismiss();
                        T.showShort(Login.this, "登陆成功！");
                        //是否是账号管理时退出
                        if (isExit.equals("yes")) {
                            Intent intent = new Intent(Login.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        //是否是账号管理时添加
                        else if (ifAdd.equals("yes")) {
                            MainActivity.instance.finish();
                            Settings.instance.finish();
                            IDManage.instance.finish();
                            Intent intent = new Intent(Login.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        Intent data = new Intent();
                        data.putExtra("iflogin", "yes");
                        setResult(1, data);
                        finish();
                    } else if (flag.equals("0")) {
                        PopupWindowUtil.popupWindow.dismiss();
                        T.showShort(Login.this, "用户名或密码错误");
                    }
                    else if (flag.equals("2")) {
                        PopupWindowUtil.popupWindow.dismiss();
                        T.showShort(Login.this, "连接超时！服务器未响应");
                    }
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.del1:
                userid_edit.setText("");
                break;
            case R.id.del2:
                pwd_edit.setText("");
                break;
            case R.id.register:
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.login_btn2:
                if(PreferenceConstants.isNetAvailable){
                    id = userid_edit.getText().toString();
                    pwd = pwd_edit.getText().toString();
                    // 开启新线程登陆
                    new Thread(connectRunnable).start();
//			showWindow();
                    PopupWindowUtil.showDialog(Login.this,R.layout.login,R.layout.dialog1);
                    PopupWindowUtil.popupWindow.setOnDismissListener(new OnDismissListener(){

                        @Override
                        public void onDismiss() {
                            // TODO 自动生成的方法存根
                            if(httpRequest.isAborted()){

                            }
                            else{
                                httpRequest.abort();
                            }
                        }

                    });
                }else{
                    T.showShort(getApplicationContext(), R.string.net_error_tip);
                }
                break;
            default:
                break;
        }
    }

    // TextWatcher接口
    class TextWatcher_Enum implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (userid_edit.getText() != null && userid_edit.hasFocus()) {
                String input_userid = userid_edit.getText().toString();
                if (!isNullOrEmpty(input_userid)) {
                    delbtn1.setVisibility(View.VISIBLE);
                    del1.setVisibility(View.VISIBLE);
                } else {
                    delbtn1.setVisibility(View.GONE);
                    del1.setVisibility(View.GONE);
                }
            } else {
                delbtn1.setVisibility(View.GONE);
                del1.setVisibility(View.GONE);
            }
            if (pwd_edit.getText() != null && pwd_edit.hasFocus()) {
                String input_pwd = pwd_edit.getText().toString();
                if (!isNullOrEmpty(input_pwd)) {
                    delbtn2.setVisibility(View.VISIBLE);
                    del2.setVisibility(View.VISIBLE);
                } else {
                    delbtn2.setVisibility(View.GONE);
                    del2.setVisibility(View.GONE);
                }
            } else {
                delbtn2.setVisibility(View.GONE);
                del2.setVisibility(View.GONE);
            }
            if (!isNullOrEmpty(userid_edit.getText().toString())
                    && !isNullOrEmpty(pwd_edit.getText().toString())) {
                login_btn2.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_btn));
                login_btn2.setClickable(true);
            }
            //有一个没有输入，登陆按钮不可点击
            if (isNullOrEmpty(userid_edit.getText().toString())
                    || isNullOrEmpty(pwd_edit.getText().toString())) {
                login_btn2.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop2));
                login_btn2.setClickable(false);
            }

            setAvatar();

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

    //根据登陆过的用户输入的username改变显示的头像
    public void setAvatar(){
        String avatarurl=DatabaseHelper.getAvatarUrl(db, userid_edit.getText().toString());
        if(avatarurl!=""){
            Bitmap bmp=FormatTools.loadImageByLocal(memoryCache, avatarurl, PreferenceConstants.AVATAR_PATH);
            if(bmp!=null){
                cir_proavatar.setImageBitmap(bmp);
            }
        }else{
            cir_proavatar.setImageDrawable(getResources().getDrawable(R.drawable.m));
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isExit.equals("yes")) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            } else if (ifAdd.equals("yes")) {

            }
            finish();
            overridePendingTransition(R.anim.slide_still, R.anim.push_right_out);
//			}
            return false;
        }
        return false;
    }

    private Runnable connectRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO 自动生成的方法存根
            connect();
        }

    };

    public void connect() {
//		String type = "";
//		String password = "";
//		password = DatabaseHelper.getPassword(db, id);
//		if (pwd.equals(password)) {
//			type = "success";
//		} else {
//			type = "fail";
//		}
        //
        String httpUrl="http://"+getResources().getString(R.string.server_ip)+":7070/user-manager-methods-action!isLoginSucceed";
        httpRequest = new HttpPost(httpUrl);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", id));
        params.add(new BasicNameValuePair("password", pwd));
        params.add(new BasicNameValuePair("accessType", "android"));
        HttpEntity httpEntity = null;
        try {
            httpEntity = new UrlEncodedFormEntity(params, "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpRequest.setEntity(httpEntity);
        HttpClient httpClient = new DefaultHttpClient();
        //连接超时
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,60000);
        //读取超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,60000);

        HttpResponse httpResponse = null;
        //返回0则表示登陆失败，2表示无法访问到网络
        String strResult = "";
        try {
            httpResponse = httpClient.execute(httpRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//			try {
                strResult = EntityUtils.toString(httpResponse.getEntity(),
                        "UTF-8");
                if(strResult.equals("0")){

                }else{
                    JSONArray jsonArray;
                    jsonArray = new JSONArray(strResult);
                    JSONObject obj = (JSONObject) jsonArray.get(0);
                    member.setUid(Integer.parseInt(obj.getString("uid")));
                    //防止出席那大小写差异导致bug,故将id和pwd重置
                    id=obj.getString("username");
                    pwd=obj.getString("password");
                    member.setUsername(id);
                    member.setPassword(pwd);
                    member.setEmail(obj.getString("email"));
                    String avatarurl=obj.getString("avatarurl");
                    member.setAvatarurl(avatarurl);
                    File avatar_file=new File(PreferenceConstants.AVATAR_PATH);

                    if (!avatar_file.exists()){
                        avatar_file.mkdirs();
                    }
                    Bitmap bmp=FormatTools.getHttpBitmap(avatarurl);
                    //根据每个人不同的头像url进行MD5加密
                    if(bmp!=null){
                        FormatTools.saveBitmap(bmp, PreferenceConstants.AVATAR_PATH, MD5Util.getMD5String(avatarurl));
                        //写入缓存
                        memoryCache.put(avatarurl, bmp);
                    }
                    //存入本地数据库
                    DatabaseHelper.addUser(db, member);
//				app.setMemoryCache(memoryCache);
                    app.setMember(member);
                    //将返回结果置为0
                    strResult="1";

                }
//				Log.e("eeeeeeeeeeee",strResult);
//			}catch(IOException e){
//				e.printStackTrace();
//			}
            }
        } catch (JSONException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }catch (IOException e) {
//			Log.e("eeeeeeeeeee", e.getMessage());
            if(e.getMessage().contains("time")){
                //连接超时
                strResult="2";
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
            if(PopupWindowUtil.popupWindow.isShowing()){
                PopupWindowUtil.popupWindow.dismiss();
//				T.showShort(getApplicationContext(), R.string.net_error_tip);
            }
            PreferenceConstants.isNetAvailable = false;
        } else {
            PreferenceConstants.isNetAvailable = true;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
//		if(httpRequest.isAborted())
//			return;
//		else
//			httpRequest.abort();
        db.close();
    };

}
