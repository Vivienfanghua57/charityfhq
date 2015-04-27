package com.main;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.entity.GoodsEntity;
import com.entity.GoodsParaEntity;
import com.example.charitydemo.R;
import com.util.FormatTools;
import com.util.MyScrollView;
import com.util.MyScrollView.onTurnListener;

public class GoodsDetail extends Activity implements OnClickListener,
        onTurnListener {
    private GridView grid_goods;
    private MyGridAdapter adapter;
    MyScrollView goods_detail_scrollview;
    ImageView pro_bg;
    View line;
    // goodsentity位置
    private int position;
    private GoodsEntity good;
    private GoodsParaEntity goodspara;
    private List<GoodsParaEntity> goodsparas = new ArrayList<GoodsParaEntity>();
    private TextView goods_detail_forum, goods_detail_param1,
            goods_detail_param2, goods_detail_param3, goods_detail_count;
    private EditText goods_detail_addition;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail);
        position = getIntent().getIntExtra("position", -1);
        if (position != -1) {
            good = NewThreadGoods.goods.get(position);
            int i = 0;
            while (i < NewThreadGoods.goodsparas.size()) {
                if (NewThreadGoods.goodsparas.get(i).getGID() == position) {
                    goodspara = NewThreadGoods.goodsparas.get(i);
                    goodsparas.add(goodspara);
                }
                i++;
            }
        }
        if (good != null) {
            goods_detail_forum = (TextView) findViewById(R.id.goods_detail_forum);
            goods_detail_param1 = (TextView) findViewById(R.id.goods_detail_param1);
            goods_detail_param2 = (TextView) findViewById(R.id.goods_detail_param2);
            goods_detail_param3 = (TextView) findViewById(R.id.goods_detail_param3);
            goods_detail_count = (TextView) findViewById(R.id.goods_detail_param4);
            goods_detail_addition = (EditText) findViewById(R.id.goods_detail_addition);
            goods_detail_forum.setText(good.getType());
            goods_detail_param1.setText(good.getParam1());
            goods_detail_param2.setText(good.getParam2());
            goods_detail_param3.setText(good.getParam3());
            goods_detail_count.setText(good.getCount());
            goods_detail_addition.setText(good.getDescr());
        }

        grid_goods = (GridView) findViewById(R.id.grid_goods);
        adapter = new MyGridAdapter();
        grid_goods.setAdapter(adapter);
        pro_bg = (ImageView) findViewById(R.id.pro_bg);
        goods_detail_scrollview = (MyScrollView) findViewById(R.id.goods_detail_scrollview);
        line = (View) findViewById(R.id.line);
        goods_detail_scrollview.setTurnListener(this);
        goods_detail_scrollview.setImageView(pro_bg);
        goods_detail_scrollview.setLine(line);

    }

    @Override
    public void onTurn() {

    }

    class MyGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return goodsparas.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return goodsparas.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(GoodsDetail.this).inflate(
                        R.layout.goods_detail_item, null);
                holder = new ViewHolder();
                holder.img = (ImageView) convertView
                        .findViewById(R.id.goods_detail_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String path = goodsparas.get(position).getValue();
            Bitmap bmp = null;
            if (FormatTools.isVideo(path).equals("y")) {
                bmp = FormatTools.getBitmapsFromVideo(path);
            } else if (FormatTools.isVideo(path).equals("n")) {
                bmp = FormatTools.CompressImage(path);
            }
//			 Bitmap bmp = FormatTools.CompressImage(path);
            holder.img.setImageBitmap(bmp);
//			ImageLoadAsyncTask task = new ImageLoadAsyncTask(holder.img);
//			task.execute(position);

            return convertView;
        }

        class ViewHolder {
            ImageView img;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still, R.anim.minifyandalpha);
        }
        return false;
    }
    /**
     *
     * 此类负责异步加载图片，本地图片不采用异步加载，加载速度也会很慢
     *
     */
    class ImageLoadAsyncTask extends AsyncTask<Integer, Void, Bitmap> {
        private ImageView img;

        // private ProgressBar progressBar;

        public ImageLoadAsyncTask(ImageView img) {
            super();
            this.img = img;
            // this.progressBar = progressBar;

        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            // TODO 自动生成的方法存根
            Bitmap bmp = null;
            String path = goodsparas.get(params[0]).getValue();
            // 判断是视频还是图片
            if (FormatTools.isVideo(path).equals("y")) {
                bmp = FormatTools.getBitmapsFromVideo(path);
            } else{
                bmp = FormatTools.CompressImage(path);
            }
            // bmp =
            // FormatTools.CompressImage(goodsparas.get(params[0]).getValue());
            return bmp;
        }

        /**
         * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
         * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
         */
        @Override
        protected void onPostExecute(Bitmap bmp) {
            img.setImageBitmap(bmp);
        }

    }
}
