package com.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adapter.LoadAdapter;
import com.db.DatabaseHelper;
import com.entity.LoadEntity;
import com.example.charitydemo.R;
import com.util.FormatTools;
import com.util.PickerView;
import com.util.PopupWindowUtil;
import com.util.PreferenceConstants;
import com.util.T;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewThread extends Activity implements OnClickListener {

    private EditText newthr1_title, newthr1_content;
    private TextView thrcancel, nextstep, nt_txtcount, newthr1_timetxt;
    public static String basepath;
    private ImageView nt_time;
    private TextView locationview;
    private File file;
    private String path;
    LinearLayout nt_lin1, nt_lin2;
    //	public List<LoadEntity> load = new ArrayList<LoadEntity>();
    //照片或者视频绝对路径
    String name_v,name_p;
    public static SQLiteDatabase db;
    private PickerView pickview;
    private GridView grid_load;
    private LoadAdapter adapter;
    public List<LoadEntity> loads = new ArrayList<LoadEntity>();

    //	//建立各级目录
//	public static String FirstFolder="CharityGroup";//一级目录  
//    public static String SecondFolder="temp";//二级目录
//    public static String ThirdFolder="image";//三级目录
//    public String ALBUM_PATH=Environment.getExternalStorageDirectory()+File.separator+FirstFolder+File.separator;  
//    public String Second_PATH=ALBUM_PATH+SecondFolder+File.separator;
//    public String Third_PATH=Second_PATH+ThirdFolder+File.separator;
    //接收到删除图片后发送的handler
    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                //adapter删除图片的message
                case 1:
                    adapter = new LoadAdapter(loads,mHandle,NewThread.this);
                    grid_load.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }

    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newthread1);
        DatabaseHelper database = new DatabaseHelper(this);
        db = database.getReadableDatabase();
        initview();
    }

    private void initview() {

        nextstep = (TextView) findViewById(R.id.nextstep);
        nextstep.setOnClickListener(this);
        newthr1_timetxt = (TextView) findViewById(R.id.newthr1_timetxt);
        locationview=(TextView)findViewById(R.id.newthr1_loctxt);
//		newthr1_timetxt.setText(TimeRender.getDay());
        nt_txtcount = (TextView) findViewById(R.id.nt_txtcount);
        newthr1_title = (EditText) findViewById(R.id.newthr1_title);
        newthr1_content = (EditText) findViewById(R.id.newthr1_content);
        newthr1_content.addTextChangedListener(new TextWatcher_Enum());
        nt_time = (ImageView) findViewById(R.id.nt_time);
        grid_load = (GridView) findViewById(R.id.grid_load);
        adapter = new LoadAdapter(loads,mHandle,NewThread.this);
        grid_load.setAdapter(adapter);
        grid_load.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                if(position==loads.size()){
                    PopupWindowUtil.showDialog(NewThread.this, R.layout.newthread1, R.layout.avatarpopup);
                }else{
                    play(loads.get(position).path,loads.get(position).type);
                }
            }
        });
        thrcancel = (TextView) findViewById(R.id.thrcancel);
        thrcancel.setOnClickListener(this);
        nt_time.setOnClickListener(this);
        locationview.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(NewThread.this,SelectLocation.class);
                startActivity(intent);
            }
        });
        basepath = Environment.getExternalStorageDirectory().getPath();
        Log.i("se", basepath);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thrcancel:
                PopupWindowUtil.showDialog(NewThread.this, R.layout.newthread1, R.layout.dialog2);
                break;
            case R.id.dialog2_cancel:
                PopupWindowUtil.popupWindow.dismiss();
                //释放静态变量所占内存
                NewThreadGoods.goods.clear();
                NewThreadGoods.goodsparas.clear();
                finish();
                overridePendingTransition(0, R.anim.slide_down_out);
                break;
            case R.id.dialog2_confirm:
                PopupWindowUtil.popupWindow.dismiss();
                finish();
                overridePendingTransition(0, R.anim.slide_down_out);
                break;
            case R.id.video:
                PopupWindowUtil.popupWindow.dismiss();
                Intent intentVideoCapture = new Intent(
                        MediaStore.ACTION_VIDEO_CAPTURE);
                name_v = buildpath(true);
                file = new File(name_v);
                Uri uri_v = Uri.fromFile(file);
                intentVideoCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri_v);
                startActivityForResult(intentVideoCapture, 2);
                break;
            case R.id.camera:
                PopupWindowUtil.popupWindow.dismiss();
//			if (isVideo) {
//
//			} else {
//
//			}
                Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                name_p = buildpath(false);
                file = new File(name_p);

                Uri uri_c = Uri.fromFile(file);
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri_c);
                startActivityForResult(intentFromCapture, 1);
                break;
            case R.id.dismiss:
                PopupWindowUtil.popupWindow.dismiss();
                break;
            case R.id.select:
                PopupWindowUtil.popupWindow.dismiss();
                Intent intentFromGallery = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentFromGallery.setType("video/*;image/*");
//			intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intentFromGallery, 0);
                break;
            case R.id.nt_time:
                List<String> data = new ArrayList<String>();

                for (int i = 0; i <= 30; i++)
                {
                    if(i<10){
                        data.add("0" + i);
                    }else{
                        data.add(i+"");
                    }

                }
                PopupWindowUtil.showBottomView(NewThread.this, R.layout.newthread1, R.layout.pickview_onedimension, data);
                break;
            case R.id.nextstep:
                Intent intent=new Intent(this,NewThreadGoods.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.pick_complete:
                PopupWindowUtil.popupWindow.dismiss();
                newthr1_timetxt.setText(PopupWindowUtil.pickview.getSelected()+"天");
                break;
            default:
                break;
        }
    }

//	

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            PopupWindowUtil.showDialog(NewThread.this,R.layout.newthread1,R.layout.dialog2);
            //第一种方法
//            final AlertDialog dlg = new AlertDialog.Builder(this).create();
//            dlg.show();
//            Window window = dlg.getWindow();
//            // *** 主要就是在这里实现这种效果的.
//            // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
//            window.setContentView(R.layout.dialog2);
            //第二种方法
//            Dialog dialog = new Dialog(NewThread.this,R.style.MyDialog);
//            //设置它的ContentView
//            dialog.setContentView(R.layout.dialog2);
//
//            dialog.show();
            return false;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                // 本地上传
                case 0:
                    final Uri uri1 = data.getData();
                    path = getImagePath(uri1);
                    //格式写死
                    if(path!=null){


                        if(FormatTools.isVideo(path).equals("y")){
                            Bitmap bmp2 = FormatTools.getBitmapsFromVideo(path);
//					selectImage(bmp2, true, path);
                            setLoad(bmp2, path, "v");
                        }else if(FormatTools.isVideo(path).equals("n")){
//				BitmapFactory.Options opts = new BitmapFactory.Options();
//				opts.inJustDecodeBounds = true;
//				BitmapFactory.decodeFile(path, opts);
//
//				opts.inSampleSize = FormatTools.computeSampleSize(opts, -1,
//						128 * 128);
//				opts.inJustDecodeBounds = false;
//				try {
                            Bitmap bmp = FormatTools.CompressImage(path);
//					selectImage(bmp, false, path);
                            setLoad(bmp, path, "p");
//				} catch (OutOfMemoryError err) {
//				}
                        }else{
                            T.showShort(getApplicationContext(), "暂不支持上传此格式的视频或图片");
                        }
                    }else{
                        T.showShort(getApplicationContext(), "请选择图片或者视频");
                    }
                    break;
                // 拍照
                case 1:
//				string = basepath + "/CharityGroup/image/temp.jpeg";
                    // path = basepath + "/CharityGroup/image/temp2.jpeg";
//				BitmapFactory.Options opts2 = new BitmapFactory.Options();
//				opts2.inJustDecodeBounds = true;
//				BitmapFactory.decodeFile(name_p, opts2);
//
//				opts2.inSampleSize = FormatTools.computeSampleSize(opts2, -1,
//						128 * 128);
//				opts2.inJustDecodeBounds = false;
//				try {
                    Bitmap bmp = FormatTools.CompressImage(name_p);
//					selectImage(bmp, false, name_p);
                    setLoad(bmp, name_p, "p");
//					String testpath=FileByMD5.md5sum(name_p);
//					Log.e("eeeeeeeeeee", testpath);
//				} catch (OutOfMemoryError err) {
//				}
                    break;
                // 拍摄视频
                case 2:
//				string = basepath + "/CharityGroup/video/temp.3gp";
                    Bitmap bmp1 = FormatTools.getBitmapsFromVideo(name_v);
                    setLoad(bmp1, name_v, "v");
                    break;
                default:

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getImagePath(Uri uri) {
        String[] prj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, prj, null, null, null);
        if(cursor!=null){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);

        }else{
            return null;
        }
    }

    public void setLoad(Bitmap bitmap, String temppath, String loadType) {
        LoadEntity load=new LoadEntity(bitmap,temppath, loadType);
        loads.add(load);
        adapter = new LoadAdapter(loads,mHandle,this);
        grid_load.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
//		FLAG++;
    }

    // TextWatcher接口
    class TextWatcher_Enum implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            nt_txtcount.setText((int) newthr1_content.length() + "/300");
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }


    public void play(String temppath, String type) {
        Intent intent = new Intent();
        File file=new File(temppath);
        intent.setAction(Intent.ACTION_VIEW);// 为Intent设置动作
        if(type.equals("v"))
            intent.setDataAndType(Uri.fromFile(file),"video/*");
        else{
            intent.setDataAndType(Uri.fromFile(file),"image/*");
        }
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
    public String  buildpath(Boolean isvideo){
        String finalpath,strend;
        String folder;
        if(isvideo){
            folder=PreferenceConstants.TEMP_PATH_VIDEO;
            //视频格式
            strend=".3gp";
        }
        else{
            folder=PreferenceConstants.TEMP_PATH_IMAGE;
            //图片格式
            strend=".jpg";
        }
//		Third_PATH=Second_PATH+ThirdFolder+File.separator;
//		File path_v1 = new File(ALBUM_PATH);
//		if (!path_v1.exists()){
//			path_v1.mkdir();}
//		File path_v2=new File(Second_PATH);
//		if (!path_v2.exists()){
//			path_v2.mkdir();}
        File temp_file=new File(folder);
        if (!temp_file.exists()){
            temp_file.mkdirs();
        }
        finalpath = temp_file + "/" + String.valueOf(System.currentTimeMillis())+strend;

        return finalpath;
    }

}
