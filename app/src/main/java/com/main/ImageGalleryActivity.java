package com.main;

import java.io.File;

import com.example.charitydemo.R;
import com.util.BounceBackViewPager;
import com.util.ZoomImageView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ImageGalleryActivity extends Activity implements
        OnPageChangeListener{


    private BounceBackViewPager viewPager;
    private TextView pageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_gallery);
        int imagePosition = getIntent().getIntExtra("image_position", 0);
        pageText = (TextView) findViewById(R.id.page_text);
        viewPager = (BounceBackViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(imagePosition);
        viewPager.setOnPageChangeListener(this);
        viewPager.setEnabled(false);
//		pageText.setText((imagePosition + 1) + "/" + Images.imageUrls.length);
        pageText.setText((imagePosition + 1) + "/" + "3");
    }

//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		// TODO 自动生成的方法存根
//		
//		return false;
//	}

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//			String imagePath = getImagePath(Images.imageUrls[position]);
//			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Bitmap bitmap=BitmapFactory.decodeResource(ImageGalleryActivity.this.getResources(),R.drawable.test1);
            if (bitmap == null) {
//				bitmap = BitmapFactory.decodeResource(getResources(),
//						R.drawable.empty_photo);
            }
            View view = LayoutInflater.from(ImageGalleryActivity.this).inflate(
                    R.layout.zoom_image_layout, null);
            ImageView zoomImageView = (ImageView) view
                    .findViewById(R.id.zoom_image_view);
            zoomImageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                    overridePendingTransition(R.anim.slide_still,
                            R.anim.minifyandalpha);
                }
            });
            zoomImageView.setImageBitmap(bitmap);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
//			return Images.imageUrls.length;
            return 3;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }


//	private String getImagePath(String imageUrl) {
//		int lastSlashIndex = imageUrl.lastIndexOf("/");
//		String imageName = imageUrl.substring(lastSlashIndex + 1);
//		String imageDir = Environment.getExternalStorageDirectory().getPath()
//				+ "/PhotoWallFalls/";
//		File file = new File(imageDir);
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//		String imagePath = imageDir + imageName;
//		return imagePath;
//	}

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int currentPage) {
        pageText.setText((currentPage + 1) + "/" + "3");
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.slide_still,
                    R.anim.minifyandalpha);
            return false;
        }
        return false;
    }

//	@Override
//	public void onClick(View v) {
//		// TODO 自动生成的方法存根
//		switch(v.getId()){
//		case R.id.zoom_image_view:
//			finish();
//			overridePendingTransition(R.anim.slide_still,
//					R.anim.minifyandalpha);
//			break;
//		}
//	}


}