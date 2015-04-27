package com.adapter;


import java.util.List;

import com.entity.ForumPostEntity;
import com.entity.ForumThreadEntity;
import com.entity.ForumThreadExtendEntity;
import com.example.charitydemo.R;
import com.main.ProfileUser;
import com.util.FormatTools;
import com.util.ImageLoader;
import com.util.MD5Util;
import com.util.MemoryCache;
import com.util.MyApp;
import com.util.PreferenceConstants;
import com.util.RoundAngleImageView;
import com.util.RoundImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Message;
import android.provider.Telephony.Threads;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class ThreadListAdapter extends BaseAdapter{
    LayoutInflater inflater;
    //	public int count=5;
//	private OnClickListener OnClickListener;
//    private RoundAngleImageView thread_avatar;
    private Context context;
    private List<ForumThreadExtendEntity> threads;
    private MyApp app;
    private MemoryCache memory;
    private ListView listview;
//	private List<ForumPostEntity> posts;

    public ThreadListAdapter(Context context,List<ForumThreadExtendEntity> threads,
                             MyApp app,ListView listview) {
        inflater = LayoutInflater.from(context);
        this.threads=threads;
//		this.posts=posts;
//		this.OnClickListener = onClickListener;
        memory=app.getMemoryCache();
        this.context=context;
        this.app=app;
        this.listview=listview;
    }
    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return threads.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO 自动生成的方法存根
        return threads.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO 自动生成的方法存根
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO 自动生成的方法存根
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.thread_item, null);
            holder = new ViewHolder();
            holder.thread_avatar = (RoundImageView) convertView.findViewById(R.id.thread_avatar);
            holder.thread_frontimg = (ImageView) convertView.findViewById(R.id.thread_frontimg);
            holder.thread_author = (TextView) convertView.findViewById(R.id.thread_author);
            holder.thread_title = (TextView) convertView.findViewById(R.id.thread_title);
            holder.thread_content = (TextView) convertView.findViewById(R.id.thread_content);
            holder.thread_avatar.setOnClickListener(new ClickLisener(position));
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.thread_author.setText(threads.get(position).getAuthor());
        holder.thread_title.setText(threads.get(position).getSubject());
        //取出从服务器端获取的html标签
        holder.thread_content.setText(threads.get(position).getFirstcontent());

//		ImageLoadAsyncTask task = new ImageLoadAsyncTask(holder.thread_avatar);
//		task.execute(position);
        String  url=threads.get(position).getAvatarurl();
        holder.thread_avatar.setTag(url);
        if(url!=""){
            Bitmap bmp=FormatTools.loadImageByLocal(memory, url, PreferenceConstants.AVATAR_PATH);
            if(bmp!=null){
                holder.thread_avatar.setImageBitmap(bmp);
            }
            else{
                ImageLoadAsyncTask task = new ImageLoadAsyncTask();
                task.execute(position);
            }
        }
//		ImageLoadAsyncTask2 task2 = new ImageLoadAsyncTask2(holder.thread_frontimg);
//		task2.execute(position);
//		holder.thread_frontimg.setOnClickListener(OnClickListener);
//		ImageLoader imageLoader=new ImageLoader(context);  
//		imageLoader.DisplayRoundAngleImage(threads.get(position).getAvatarurl(), holder.thread_avatar);
        return convertView;
    }
    class ViewHolder{
        RoundImageView thread_avatar;
        ImageView thread_frontimg;
        TextView thread_author,thread_title,thread_content;
    }
    class ImageLoadAsyncTask extends AsyncTask<Integer, Void, Bitmap> {
        //		private RoundAngleImageView img;
        // private ProgressBar progressBar;
        private String url;

        public ImageLoadAsyncTask() {
            super();
//			this.img = img;
            // this.progressBar = progressBar;

        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            // TODO 自动生成的方法存根

//			MemoryCache memory=app.getMemoryCache();
            url=threads.get(params[0]).getAvatarurl();

            Bitmap bmp = FormatTools.loadimageByInternet(memory, url, PreferenceConstants.AVATAR_PATH);

            return bmp;
        }

        /**
         * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
         * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
         */
        @Override
        protected void onPostExecute(Bitmap bmp) {
            ImageView img = (ImageView) listview.findViewWithTag(url);
            img.setImageBitmap(bmp);
        }

    }
    //	class ImageLoadAsyncTask2 extends AsyncTask<Integer, Void, Bitmap> {
//		private ImageView img;
//		// private ProgressBar progressBar;
//
//		public ImageLoadAsyncTask2(ImageView img) {
//			super();
//			this.img = img;
//			// this.progressBar = progressBar;
//
//		}
//
//		@Override
//		protected Bitmap doInBackground(Integer... params) {
//			// TODO 自动生成的方法存根
//			Bitmap bmp = null;
//		
//			String avatarurl = threads.get(params[0]).getAvatarurl();
//			bmp=FormatTools.getHttpBitmap(avatarurl);
//			return bmp;
//		}
//
//		/**
//		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
//		 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
//		 */
//		@Override
//		protected void onPostExecute(Bitmap bmp) {
//			img.setImageBitmap(bmp);
//		}
//
//	}
    //单击事件实现
    class ClickLisener implements OnClickListener {
        public int position;
        public ClickLisener(int p) {
            position = p;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ProfileUser.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //这里以后要传主题作者的id
            intent.putExtra("UID",threads.get(position).getAuthorid());
            intent.putExtra("USERNAME",threads.get(position).getAuthor());
            intent.putExtra("AVATARURL",threads.get(position).getAvatarurl());
            context.startActivity(intent);
//	         Intent intent = new Intent();
//	         intent.setClass(context, CountOrder.class);
//	         Bundle b = new Bundle();
//	         b.putString("datatime", list1.get(position));
//	         intent.putExtras(b);
//	         context.startActivity(intent);
        }

    }
}
