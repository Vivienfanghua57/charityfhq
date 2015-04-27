package com.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.entity.LoadEntity;
import com.example.charitydemo.R;

//gridview的适配器
public class LoadAdapter extends BaseAdapter {
    private Context context;
    private List<LoadEntity> loads;
    private Handler handler;
    public LoadAdapter(List<LoadEntity> loads, Handler handler, Context context) {
        super();
        this.context = context;
        this.loads = loads;
        this.handler=handler;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(loads.size()==6){
            return loads.size();
        }else{
            return loads.size()+1;
        }
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return loads.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
//    	String isVideo="";
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.loadimg_item, null);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.load_image);
            holder.load_play = (ImageView) convertView.findViewById(R.id.load_play);
            holder.del_image = (ImageView) convertView.findViewById(R.id.del_image);
            holder.del_image.setOnClickListener(new ClickLisener(position));
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //第一个图片为背景，最后一个图片为加号
        if(position>=loads.size()){
            holder.del_image.setVisibility(View.GONE);
            if(position==0){
                holder.img.setImageResource(R.drawable.load_bg);
            }
        }else{
            //是否是视频，是的话就显示播放按钮
            String isVideo=loads.get(position).type;
            if(isVideo.equals("v")){
                holder.load_play.setVisibility(View.VISIBLE);
            }
//		bmp = FormatTools.getBitmapsFromVideo(loads.get(position).path);
//		else{
            Bitmap bmp=loads.get(position).getBitmap();
//		}
            //这里要try catch
            holder.img.setImageBitmap(bmp);}
        return convertView;
    }
    class ViewHolder{
        ImageView img,load_play,del_image;
    }
    //单击事件实现
    class ClickLisener implements OnClickListener {
        public int position;
        public ClickLisener(int p) {
            position = p;
        }
        @Override
        public void onClick(View v) {
//	         Intent intent = new Intent();
//	         intent.setClass(context, CountOrder.class);
//	         Bundle b = new Bundle();
//	         b.putString("datatime", list1.get(position));
//	         intent.putExtras(b);
//	         context.startActivity(intent);
            loads.remove(position);
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);

        }

    }
}
