package com.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.adapter.LoadAdapter;
import com.chat.MessageEntity;
import com.entity.FocusEntity;
import com.entity.GoodsEntity;
import com.entity.GoodsParaEntity;
import com.entity.LoadEntity;
import com.example.charitydemo.R;
import com.util.FormatTools;
import com.util.MyGridView;
import com.util.PickerView;
import com.util.PreferenceConstants;
import com.util.PickerView.onSelectListener;
import com.util.PopupWindowUtil;
import com.util.T;

public class NewThreadGoodsDes extends Activity implements OnClickListener{
    private EditText goods_addition,goods_count;
    private TextView addition_count;
    private MyGridView grid_load;
    private LoadAdapter adapter;
    private RelativeLayout gd_rel2_1;
    //	private PopupWindow popupWindow;
//	private View contentView;
    private Button camera, video, select,dismiss;
    private TextView goods_character1,goods_character2,goods_character3,goods_character4,goods_type,
            goods_param1,goods_param2,goods_param3,confirm;
    //标记特征选择的是哪个
    private int FLAG=0;
    public List<LoadEntity> loads = new ArrayList<LoadEntity>();
    private File file;
    //照片或者视频名称
    private String name_v,name_p;
    private String path;
    private PickerView pickview;
    private String forum;
    List<String> data = new ArrayList<String>();
    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 1:
                    adapter = new LoadAdapter(loads,mHandle,NewThreadGoodsDes.this);
                    grid_load.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }

    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newthread_goodsdescription);
        for (int i = 0; i < 10; i++)
        {
            data.add("测试");
        }
//		forum=getIntent().getStringExtra("FORUM");
        initview();
    }

    private void initview() {
        // TODO 自动生成的方法存根
        addition_count = (TextView)findViewById(R.id.addition_count);
        confirm = (TextView)findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        goods_type = (TextView)findViewById(R.id.goods_type);
//		goods_type.setText(forum);

        goods_param1 = (TextView)findViewById(R.id.goods_param1);
        goods_param2 = (TextView)findViewById(R.id.goods_param2);
        goods_param3 = (TextView)findViewById(R.id.goods_param3);
        goods_character1 = (TextView)findViewById(R.id.goods_character1);
        goods_character2 = (TextView)findViewById(R.id.goods_character2);
        goods_character3 = (TextView)findViewById(R.id.goods_character3);
        goods_character4 = (TextView)findViewById(R.id.goods_character4);
        //物品数量
        goods_count=(EditText)findViewById(R.id.goods_character5);
        goods_character1.setOnClickListener(this);
        goods_character2.setOnClickListener(this);
        goods_character3.setOnClickListener(this);
        goods_character4.setOnClickListener(this);
        //还未选择板块前隐藏前三个特征
        gd_rel2_1 = (RelativeLayout)findViewById(R.id.gd_rel2_1);
        gd_rel2_1.setVisibility(View.GONE);
        goods_addition = (EditText)findViewById(R.id.goods_addition);
        goods_addition.addTextChangedListener(new TextWatcher_Enum());
        grid_load = (MyGridView) findViewById(R.id.grid_load);
        adapter = new LoadAdapter(loads,mHandle,NewThreadGoodsDes.this);
        grid_load.setAdapter(adapter);
        grid_load.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                if(position==loads.size()){
                    PopupWindowUtil.showDialog(NewThreadGoodsDes.this,R.layout.newthread_goodsdescription,R.layout.avatarpopup);
//					video = (Button) PopupWindowUtil.contentView.findViewById(R.id.video);
//					video.setOnClickListener(NewThreadGoodsDes.this);
//					camera = (Button) PopupWindowUtil.contentView.findViewById(R.id.camera);
//					camera.setOnClickListener(NewThreadGoodsDes.this);
//					select = (Button) PopupWindowUtil.contentView.findViewById(R.id.select);
//					select.setOnClickListener(NewThreadGoodsDes.this);
//					dismiss = (Button) PopupWindowUtil.contentView.findViewById(R.id.dismiss);
//					dismiss.setOnClickListener(NewThreadGoodsDes.this);
                }else{
                    play(loads.get(position).path,loads.get(position).type);
                }
            }
        });
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(0, R.anim.slide_down_out);
            return false;
        }
        return false;
    }
    // TextWatcher接口
    class TextWatcher_Enum implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            addition_count.setText((int) goods_addition.length() + "/100");
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
            case R.id.video:
                PopupWindowUtil.popupWindow.dismiss();
                Intent intentVideoCapture = new Intent(
                        MediaStore.ACTION_VIDEO_CAPTURE);
                name_v = PreferenceConstants.TEMP_PATH_VIDEO+ String.valueOf(System.currentTimeMillis())+".3gp";
                file = new File(name_v);
                Uri uri_v = Uri.fromFile(file);
                intentVideoCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri_v);
                startActivityForResult(intentVideoCapture, 2);
                break;
            case R.id.camera:
                PopupWindowUtil.popupWindow.dismiss();
                Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                name_p = PreferenceConstants.TEMP_PATH_IMAGE+ String.valueOf(System.currentTimeMillis())+".jpg";
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
                startActivityForResult(intentFromGallery, 0);
                break;
            case R.id.goods_character1:
                Intent intent=new Intent(NewThreadGoodsDes.this,ForumActivity.class);
//		forum=PopupWindowUtil.pickview.getSelected();
//		intent.putExtra("FORUM", forum);
                startActivityForResult(intent,3);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//		PopupWindowUtil.showSelectWindow(NewThreadGoodsDes.this,R.layout.newthread_goodsdescription,R.layout.pickview_onedimension,data);
//		showSelectWindow(R.layout.pickview_onedimension);
                break;
            case R.id.goods_character2:
                FLAG=1;
                PopupWindowUtil.showBottomView(NewThreadGoodsDes.this,R.layout.newthread_goodsdescription,R.layout.pickview_onedimension,data);
//		showSelectWindow(R.layout.pickview_threedimension);
                break;
            case R.id.goods_character3:
                FLAG=2;
                PopupWindowUtil.showBottomView(NewThreadGoodsDes.this,R.layout.newthread_goodsdescription,R.layout.pickview_onedimension,data);
//		showSelectWindow(R.layout.pickview_onedimension);
                break;
            case R.id.goods_character4:
                FLAG=3;
                PopupWindowUtil.showBottomView(NewThreadGoodsDes.this,R.layout.newthread_goodsdescription,R.layout.pickview_onedimension,data);
//		showSelectWindow(R.layout.pickview_onedimension);
                break;
            case R.id.pick_complete:
                PopupWindowUtil.popupWindow.dismiss();
                switch(FLAG){
                    case 1:
                        goods_param1.setText(PopupWindowUtil.pickview.getSelected());
                        break;
                    case 2:
                        goods_param2.setText(PopupWindowUtil.pickview.getSelected());
                        break;
                    case 3:
                        goods_param3.setText(PopupWindowUtil.pickview.getSelected());
                        break;
                    default:
                        break;
                }
                break;
            case R.id.confirm:
                String frontimg_path = "";
                String type=goods_type.getText().toString(),
                        descr=goods_addition.getText().toString(),
                        count=goods_count.getText().toString(),
                        param1=goods_param1.getText().toString(),
                        param2=goods_param2.getText().toString(),
                        param3=goods_param3.getText().toString();
                if(loads.size()>0){
                    frontimg_path=loads.get(0).getPath();
                }
                int i=0;
                while(i<loads.size()){
                    GoodsParaEntity goodspara=new GoodsParaEntity(NewThreadGoods.goods.size(), NewThreadGoods.goodsparas.size(), loads.get(i).getPath());
                    NewThreadGoods.goodsparas.add(goodspara);
                    i++;
                }
                GoodsEntity good=new GoodsEntity(NewThreadGoods.goods.size(), "", 0, descr,  type,
                        "",count,  param1,  param2,  param3, frontimg_path);
                NewThreadGoods.goods.add(good);
//		Intent data=new Intent();
//		data.putExtra("ifChange", "true");
//		setResult(1, data);
                finish();
                overridePendingTransition(0, R.anim.slide_down_out);
                break;
            default:
                break;
        }
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
//				BitmapFactory.Options opts2 = new BitmapFactory.Options();
//				opts2.inJustDecodeBounds = true;
//				BitmapFactory.decodeFile(name_p, opts2);
//
//				opts2.inSampleSize = FormatTools.computeSampleSize(opts2, -1,
//						128 * 128);
//				opts2.inJustDecodeBounds = false;
//				try {
                    Bitmap bmp = FormatTools.CompressImage(name_p);
                    setLoad(bmp, name_p, "p");
//				} catch (OutOfMemoryError err) {
//				}
                    break;
                // 拍摄视频
                case 2:
                    Bitmap bmp1 = FormatTools.getBitmapsFromVideo(name_v);
                    setLoad(bmp1, name_v, "v");
                    break;
                case 3:
//				forum=getIntent().getStringExtra("forum");
                    forum=data.getStringExtra("forum");
                    goods_type.setText(forum);
                    if(gd_rel2_1.getVisibility()==View.GONE){
                        gd_rel2_1.setVisibility(View.VISIBLE);
                    }
                    break;
                default:

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void setLoad(Bitmap bitmap, String temppath, String loadType) {
        LoadEntity load=new LoadEntity(bitmap,temppath, loadType);
        loads.add(load);
        adapter = new LoadAdapter(loads,mHandle,this);
        grid_load.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
//		FLAG++;
    }
}
