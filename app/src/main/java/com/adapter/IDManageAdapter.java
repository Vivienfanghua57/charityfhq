package com.adapter;

import java.util.ArrayList;
import java.util.List;

import com.adapter.NoticeAdapter.ViewHolder;
import com.adapter.ThreadListAdapter.ImageLoadAsyncTask;
import com.entity.MemberEntity;
import com.entity.UserEntitiy;
import com.example.charitydemo.R;
import com.util.FormatTools;
import com.util.MemoryCache;
import com.util.MyApp;
import com.util.PreferenceConstants;
import com.util.RoundAngleImageView;
import com.util.RoundImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class IDManageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private Context context;
    public List<MemberEntity> members = new ArrayList<MemberEntity>();
    public String loginuser;
    private MyApp app;
    private MemoryCache memory;
    private ListView listview;

    public IDManageAdapter(Context context, List<MemberEntity> members,
                           String loginuser,MyApp app,ListView listview) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.members = members;
        this.loginuser = loginuser;
        memory=app.getMemoryCache();
        this.app=app;
        this.listview=listview;
    }

    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return members.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO 自动生成的方法存根
        return members.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO 自动生成的方法存根
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO 自动生成的方法存根
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.idmanage_item, parent, false);
            holder = new ViewHolder();
            holder.idmana_avatar = (RoundImageView) convertView
                    .findViewById(R.id.idmana_avatar);
            holder.username = (TextView) convertView
                    .findViewById(R.id.username);
            holder.userid = (TextView) convertView.findViewById(R.id.userid);
            holder.if_checked = (ImageView) convertView
                    .findViewById(R.id.if_checked);
            convertView.setTag(holder);
        } else {// 有直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }
        holder.userid.setText(members.get(position).getUid() + "");
        String username = members.get(position).getUsername();
        holder.username.setText(username);
        if (loginuser.equalsIgnoreCase(username)) {
            holder.if_checked.setVisibility(View.VISIBLE);
        } else {
            holder.if_checked.setVisibility(View.GONE);
        }

        String url=members.get(position).getAvatarurl();
        holder.idmana_avatar.setTag(url);
//		holder.idmana_avatar.setImageResource(R.drawable.m);
        if(!url.equals("")){
            Bitmap bmp=FormatTools.loadImageByLocal(memory, url, PreferenceConstants.AVATAR_PATH);
            if(bmp!=null){
                holder.idmana_avatar.setImageBitmap(bmp);
            }
            else{
                ImageLoadAsyncTask task = new ImageLoadAsyncTask();
                task.execute(position);
            }
        }
        return convertView;
    }

    class ViewHolder {
        RoundImageView idmana_avatar;
        TextView username;
        TextView userid;
        ImageView if_checked;
    }

    class ImageLoadAsyncTask extends AsyncTask<Integer, Void, Bitmap> {
        //		private RoundImageView img;
        private String url;

        public ImageLoadAsyncTask() {
            super();
//			this.img = img;

        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            // TODO 自动生成的方法存根
            url=members.get(params[0]).getAvatarurl();
            Bitmap bmp = FormatTools.loadimageByInternet(memory, url,
                    PreferenceConstants.AVATAR_PATH);

            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
//			if(bmp!=null){
//			findViewWithTag(String url).img.setImageBitmap(bmp);
            ImageView img = (ImageView) listview.findViewWithTag(url);
            img.setImageBitmap(bmp);
//			}
        }

    }
}
