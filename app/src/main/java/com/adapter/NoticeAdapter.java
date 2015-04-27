package com.adapter;

import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.adapter.ThreadListAdapter.ImageLoadAsyncTask;
import com.chat.MessageEntity;
import com.entity.ChatListEntity;
import com.example.charitydemo.R;
import com.util.FormatTools;
import com.util.MemoryCache;
import com.util.MyApp;
import com.util.PreferenceConstants;
import com.util.RoundAngleImageView;
import com.util.TimeRender;

public class NoticeAdapter extends BaseAdapter{
    LayoutInflater inflater;
    private Context mContext;
    //	private int rightWidth = 0;
    private List<ChatListEntity> chats;
    private MyApp app;
    private MemoryCache memory;
    private ListView listview;

    public NoticeAdapter(Context context,List<ChatListEntity> chats,
                         MyApp app,ListView listview) {
        inflater = LayoutInflater.from(context);
        memory=app.getMemoryCache();
        this.mContext=context;
        this.app=app;
        this.listview=listview;
        this.chats=chats;
//		this.rightWidth = rightWidth;
    }
    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return chats.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO 自动生成的方法存根
        return chats.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO 自动生成的方法存根
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO 自动生成的方法存根
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.chatlist_item, parent, false);
            holder = new ViewHolder();
//            holder.item_left = (RelativeLayout)convertView.findViewById(R.id.item_left);
//            holder.item_right = (RelativeLayout)convertView.findViewById(R.id.item_right);

            holder.chat_avatar = (RoundAngleImageView) convertView.findViewById(R.id.chat_avatar);
            holder.chat_name = (TextView)convertView.findViewById(R.id.chat_name);
            holder.chat_content = (TextView)convertView.findViewById(R.id.chat_content);
            holder.chat_time = (TextView)convertView.findViewById(R.id.chat_time);
//            holder.item_right_txt = (TextView)convertView.findViewById(R.id.item_right_txt);
//            LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
//                    LayoutParams.MATCH_PARENT);
//            holder.item_left.setLayoutParams(lp1);
//            LinearLayout.LayoutParams lp2 = new LayoutParams(rightWidth, LayoutParams.MATCH_PARENT);
//            holder.item_right.setLayoutParams(lp2);
//            
//            holder.item_right.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) {
//                        mListener.onRightItemClick(v, position);
//                    }
//                }
//            });
            convertView.setTag(holder);
        } else {// 有直接获得ViewHolder
            holder = (ViewHolder)convertView.getTag();
        }
        String avatar=chats.get(position).getAvatar();
        holder.chat_avatar.setTag(avatar);
        if(avatar!=""){
            Bitmap bmp=FormatTools.loadImageByLocal(memory, avatar, PreferenceConstants.AVATAR_PATH);
            if(bmp!=null){
                holder.chat_avatar.setImageBitmap(bmp);
            }
            else{
                ImageLoadAsyncTask task = new ImageLoadAsyncTask();
                task.execute(position);
            }
        }
        holder.chat_name.setText(chats.get(position).getChatTo());
        holder.chat_content.setText(chats.get(position).getLast_Msg());
        holder.chat_time.setText(TimeRender.getChatTime(chats.get(position).getDate()));

        return convertView;
    }

    class ViewHolder {
//    	RelativeLayout item_left;
//    	RelativeLayout item_right;

        TextView chat_name;
        TextView chat_content;
        TextView chat_time;
        RoundAngleImageView chat_avatar;

//        TextView item_right_txt;
    }
    class ImageLoadAsyncTask extends AsyncTask<Integer, Void, Bitmap> {
        //		private RoundAngleImageView img;
        // private ProgressBar progressBar;
        private String avatar;

        public ImageLoadAsyncTask() {
            super();
//			this.img = img;
            // this.progressBar = progressBar;

        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            // TODO 自动生成的方法存根

//			MemoryCache memory=app.getMemoryCache();
            avatar=chats.get(params[0]).getAvatar();

            Bitmap bmp = FormatTools.loadimageByInternet(memory, avatar, PreferenceConstants.AVATAR_PATH);

            return bmp;
        }

        /**
         * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
         * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
         */
        @Override
        protected void onPostExecute(Bitmap bmp) {
            ImageView img = (ImageView) listview.findViewWithTag(avatar);
            img.setImageBitmap(bmp);
        }

    }
//	 /**
//     * 单击事件监听器
//     */
//    private onRightItemClickListener mListener = null;
//    
//    public void setOnRightItemClickListener(onRightItemClickListener listener){
//    	mListener = listener;
//    }
//
//    public interface onRightItemClickListener {
//        void onRightItemClick(View v, int position);
//    }
}
