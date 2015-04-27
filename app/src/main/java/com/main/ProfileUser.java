package com.main;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anim.RotateAnimation;
import com.anim.RotateAnimation.InterpolatedTimeListener;
import com.chat.ChatActivity;
import com.db.DatabaseHelper;
import com.example.charitydemo.R;
import com.util.FormFile;
import com.util.MyApp;
import com.util.MyScrollView;
import com.util.MyScrollView.onTurnListener;
import com.util.FormatTools;
import com.util.MD5Util;
import com.util.MemoryCache;
import com.util.PopupWindowUtil;
import com.util.PreferenceConstants;
import com.util.RoundImageView;
import com.util.SocketHttpRequester;
import com.util.T;

public class ProfileUser extends Activity implements OnClickListener,
        onTurnListener {

    private RoundImageView cir_proavatar;
    private RelativeLayout pro_rel2;
    private Button pro_edit;
    private TextView camera1, album, look, pro_focus, pro_chat, prousername;
    private File file = null;
    private int UID;
    private String USERNAME= "";
    private String URL= "";
    //	private String name = "";
    private Boolean ifOwn = false,isFocus=false;
    private int IMAGE_REQUEST_CODE = 0, CAMERA_REQUEST_CODE = 1,
            RESULT_REQUEST_CODE = 2;
    private MyScrollView myScrollView;
    private ImageView pro_bg;
    private View line;
    private MemoryCache memoryCache;
    private SQLiteDatabase db;
    private MyApp app;
    private Handler handler;
    private String flag;
    //获取头像
    private Bitmap bmp;
    //上传头像
    private Bitmap tempbmp;
    private String returnAvatarUrl="";
    private String filepath=PreferenceConstants.AVATAR_PATH+"tempavatar.png";

//	private int drawable_id[] = { R.drawable.m, R.drawable.f };
//
//	private int current_id;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);
        UID = getIntent().getIntExtra("UID",0);
        USERNAME = getIntent().getStringExtra("USERNAME");
        URL = getIntent().getStringExtra("AVATARURL");
//		name = getIntent().getStringExtra("FOCUSNAME");
        app=(MyApp)getApplication();
        memoryCache=app.getMemoryCache();

        DatabaseHelper database = new DatabaseHelper(this);
        db = database.getReadableDatabase();
        initView();
    }

    private void initView() {
        //是否关注过
//		isFocus=DatabaseHelper.isExit(db,PreferenceConstants.ACCOUNT, ID );

        pro_bg = (ImageView) findViewById(R.id.pro_bg);
        // imageicon = (ImageView) findViewById(R.id.imageview1);
        myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
        line = (View) findViewById(R.id.line);
        myScrollView.setTurnListener(this);
        myScrollView.setImageView(pro_bg);
        myScrollView.setLine(line);

        pro_rel2 = (RelativeLayout) findViewById(R.id.pro_rel2);
        pro_edit = (Button) findViewById(R.id.pro_edit);
        pro_edit.setOnClickListener(this);


        ifOwn = USERNAME.equals(PreferenceConstants.ACCOUNT);
        if (ifOwn) {
            pro_rel2.setVisibility(View.GONE);
            pro_edit.setVisibility(View.VISIBLE);
        }

        cir_proavatar = (RoundImageView) findViewById(R.id.cir_proavatar);
        try{

            Bitmap bmp=FormatTools.loadimage(app.getMemoryCache(), URL, PreferenceConstants.AVATAR_PATH);
            if(bmp!=null){
                cir_proavatar.setImageBitmap(bmp);
            }
        }catch(NullPointerException e){

        }

        cir_proavatar.setOnClickListener(this);



        prousername = (TextView) findViewById(R.id.prousername);
        prousername.setText(USERNAME);
        pro_chat = (TextView) findViewById(R.id.pro_chat);
        pro_chat.setOnClickListener(this);
        pro_focus = (TextView) findViewById(R.id.pro_focus);
//		if(isFocus){
//			pro_focus.setText("取消关注");
//		}
        pro_focus.setOnClickListener(this);

        new Thread(getProfileRunnable).start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //获取用户信息
                if (msg.what == 1) {
                    try{
                        flag = msg.getData().getString("strResult");
                    }catch(NullPointerException e){

                    }
                    if (flag.equals("1")) {
                        cir_proavatar.setImageBitmap(bmp);
                    }
                }
                //上传头像
                else if(msg.what == 2){
                    try{
                        flag = msg.getData().getString("result");
                    }catch(NullPointerException e){

                    }
                    if (flag.equals("1")) {
//							cir_proavatar.setImageBitmap(bmp);
                        PopupWindowUtil.popupWindow.dismiss();
                        cir_proavatar.setImageBitmap(tempbmp);
                        T.showShort(getApplicationContext(), "上传成功！");
                    }
                    if (flag.equals("0")) {
                        PopupWindowUtil.popupWindow.dismiss();
                        T.showShort(getApplicationContext(), "上传失败！");
                    }
                }
            }
        };
    }

    @Override
    public void onTurn() {
//		RotateAnimation animation = new RotateAnimation();
//		animation.setFillAfter(true);
//		animation.setInterpolatedTimeListener(this);
//		cir_proavatar.startAnimation(animation);
//
//		current_id = current_id < drawable_id.length - 1 ? ++current_id : 0;

    }

//	@Override
//	public void interpolatedTime(float interpolatedTime) {
//		// 监听到翻转进度过半时，更新图片内容，
//		if (interpolatedTime > 0.5f) {
//			Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//					drawable_id[current_id]);
//			cir_proavatar.setImageBitmap(bitmap);
//		}
//

    //	}
    private Runnable getProfileRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO 自动生成的方法存根
            getProfile();
        }

    };
    public void getProfile() {

        String httpUrl="http://"+getResources().getString(R.string.server_ip)+":7070/user-manager-methods-action!ShowProfile";
        HttpPost httpRequest = new HttpPost(httpUrl);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", USERNAME));
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
        String strResult = "";
        try {
            httpResponse = httpClient.execute(httpRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//			try {
                strResult = EntityUtils.toString(httpResponse.getEntity(),
                        "UTF-8");
//				 if(strResult.equals("0")){
//					 
//				 }else{
                JSONArray jsonArray;
                jsonArray = new JSONArray(strResult);
                JSONObject obj = (JSONObject) jsonArray.get(0);
                String username=obj.getString("username");
                String email=obj.getString("email");
                String avatarurl=obj.getString("avatarurl");
                if(!avatarurl.equals(URL)){
                    File avatar_file=new File(PreferenceConstants.AVATAR_PATH);

                    if (!avatar_file.exists()){
                        avatar_file.mkdirs();
                    }
                    bmp=FormatTools.loadimageByInternet(memoryCache,avatarurl,PreferenceConstants.AVATAR_PATH);
                    if(bmp!=null){
                        if(ifOwn){
                            app.getMember().setAvatarurl(avatarurl);
                            DatabaseHelper.updateAvatarUrl(db, username, avatarurl);
                        }
                    }
                    strResult="1";
                }
            }
        } catch (JSONException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }catch (IOException e) {
//			Log.e("eeeeeeeeeee", e.getMessage());
//			if(e.getMessage().contains("time")){
//				//连接超时
////			strResult="2";
//			}
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
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
            case R.id.cir_proavatar:
                if (ifOwn) {
                    PopupWindowUtil.showDialog(ProfileUser.this,
                            R.layout.profile_user, R.layout.dialog3);
                } else {
                    Intent intent = new Intent(ProfileUser.this, HDPicture.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.magnifyandalpha,
                            R.anim.slide_still);
                }
                break;
            case R.id.album:
                PopupWindowUtil.popupWindow.dismiss();
                Intent intentFromGallery = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                break;
            case R.id.camera1:
                PopupWindowUtil.popupWindow.dismiss();
                Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(filepath);
                Uri uri_c = Uri.fromFile(file);
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri_c);
                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                break;
            case R.id.look:
                PopupWindowUtil.popupWindow.dismiss();
                Intent intent1 = new Intent(ProfileUser.this, HDPicture.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.magnifyandalpha,
                        R.anim.slide_still);
                break;
            case R.id.pro_chat:
                Intent intent2 = new Intent(ProfileUser.this, ChatActivity.class);
                intent2.putExtra("CHATID", UID);
                intent2.putExtra("CHATNAME", USERNAME);
                intent2.putExtra("CHATAVATAR", URL);
                startActivity(intent2);
                break;
            case R.id.pro_focus:
//			if(isFocus){
//				//已经关注则取消
//				pro_focus.setText("关注");
//				DatabaseHelper.delFocus(db, PreferenceConstants.ACCOUNT, ID);
//			}else{
//				//没有关注则加入关注列表(暂未实现)
//				
//			}

                break;
            default:
                break;
        }
    }

    private Runnable loadRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO 自动生成的方法存根
            uploadFile(file);
        }

    };
    public void uploadFile(File imageFile) {
        String result="0";
        try {
            String requestUrl = "http://"+getResources().getString(R.string.server_ip)+":7070/user-upload-methods-action!UploadUserFiles";
            // 请求普通信息
            Map<String,String> params = new HashMap<String, String>();
            // params.put("filename", "张三");
            params.put("username", USERNAME);
            params.put("uploadType", "0");
            params.put("accessType", "android");
//            params.put("fileName", imageFile.getName());
            // 上传文件
            FormFile formfile = new FormFile(imageFile.getName(), imageFile,
                    "userfile", "application/octet-stream");

            returnAvatarUrl=SocketHttpRequester.post(requestUrl, params, formfile);
            if(!returnAvatarUrl.equals("")&&!returnAvatarUrl.equals("1")&&!returnAvatarUrl.equals("2")&&!returnAvatarUrl.equals("3")){
                //存入缓存并且将刚才存到文件路径下的头像重命名为刚得到的url，并修改数据库以及application里的url
                memoryCache.put(returnAvatarUrl, tempbmp);
                file.renameTo(new File(PreferenceConstants.AVATAR_PATH+MD5Util.getMD5String(returnAvatarUrl)));
                app.getMember().setAvatarurl(returnAvatarUrl);
                DatabaseHelper.updateAvatarUrl(db, USERNAME, returnAvatarUrl);
                result="1";
            }

        } catch (Exception e) {
//            Log.i("444", "upload error");
            e.printStackTrace();
        }

//        Log.i("455", "upload end");
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        message.what = 2;
        bundle.putString("result", result);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            tempbmp = extras.getParcelable("data");
//			Drawable drawable = new BitmapDrawable(photo);
            if (PreferenceConstants.isNetAvailable) {
                PopupWindowUtil.showDialog(ProfileUser.this,
                        R.layout.profile_user, R.layout.dialog1);
                PopupWindowUtil.dialog1_content.setText("正在上传头像...");
                FormatTools.saveBitmap(tempbmp, PreferenceConstants.AVATAR_PATH, "tempavatar.png");
                // 创建file对象
                file = new File(filepath);
                if (!file.exists()) {
                    Toast.makeText(ProfileUser.this, "文件不存在",
                            Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(UploadActivity.this,"文件存在",Toast.LENGTH_LONG).show();
//	                uploadFile(file);
                    new Thread(loadRunnable).start();
//	                Toast.makeText(ProfileUser.this, "文件上传成功", 1).show();
                }
//				cir_proavatar.setImageBitmap(bmp);
                // DatabaseHelper.update(db, "user", "avatar", "username",
                // drawable, loginuser);
//				Intent intent = new Intent();
//				intent.putExtra("ifChange", "yes");
//				// 通过Intent对象返回结果，调用setResult方法
//				setResult(2, intent);

            } else {
                Toast.makeText(ProfileUser.this, "无法修改头像，请检查网络设置！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case 0:
                    startPhotoZoom(data.getData());
                    break;
                case 1:
                    startPhotoZoom(Uri.fromFile(file));
                    break;
                case 2:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still, R.anim.push_right_out);
            return false;
        }
        return false;
    }
    protected void onDestroy() {
        super.onDestroy();
//		if (file != null) {
//			if (file.exists())
//				file.delete();
//		}
        db.close();
    };
}
