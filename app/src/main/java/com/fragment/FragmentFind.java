package com.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.charitydemo.R;
import com.main.FindThread;
import com.main.RankList;
import com.main.Topic;

public class FragmentFind extends Fragment implements OnClickListener{
    protected View mMainView;
    protected Context mContext;
    private ImageView find_topicimg,find_hotthreadimg,find_vol_teach_img,find_vol_old_img,find_disaimg,find_topimg;

    public static FragmentFind newInstance() {
        FragmentFind newFragment = new FragmentFind();
        return newFragment;
    }

    public FragmentFind() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.tab_find, container, false);
        initview();
        return mMainView;
    }
    public void initview(){
        find_hotthreadimg=(ImageView)mMainView.findViewById(R.id.find_hotthreadimg);
        find_hotthreadimg.setOnClickListener(this);
//	    	find_hotdonaimg.setOnTouchListener(this);
        find_topicimg=(ImageView)mMainView.findViewById(R.id.find_topicimg);
        find_topicimg.setOnClickListener(this);
        find_topimg=(ImageView)mMainView.findViewById(R.id.find_topimg);
        find_topimg.setOnClickListener(this);
        find_vol_teach_img=(ImageView)mMainView.findViewById(R.id.find_vol_teach_img);
        find_vol_teach_img.setOnClickListener(this);
        find_disaimg=(ImageView)mMainView.findViewById(R.id.find_disaimg);
        find_disaimg.setOnClickListener(this);
        find_vol_old_img=(ImageView)mMainView.findViewById(R.id.find_vol_old_img);
        find_vol_old_img.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch(v.getId()){
            case R.id.find_hotthreadimg:
                anim(find_hotthreadimg,FindThread.class);
                break;
            case R.id.find_topicimg:
                anim(find_topicimg,Topic.class);
                break;
            case R.id.find_topimg:
                anim(find_topimg,RankList.class);
                break;
            case R.id.find_vol_teach_img:
                anim(find_vol_teach_img,FindThread.class);
                break;
            case R.id.find_disaimg:
                anim(find_disaimg,FindThread.class);
                break;
            case R.id.find_vol_old_img:
                anim(find_vol_old_img,FindThread.class);
                break;
            default:
                break;
        }
    }
    public void anim(ImageView imageview, final Class<?> activity){
        AnimationSet scale_animation_set = new AnimationSet(true);
        //以自身为尺度缩放中心，从原大小尺寸逐渐缩放到0尺寸
        ScaleAnimation scale_animation = new ScaleAnimation(1.0f, 0.7f, 1.0f, 0.7f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale_animation_set.addAnimation(scale_animation);
        scale_animation.setDuration(300);//1s钟
        imageview.startAnimation(scale_animation_set);
        scale_animation_set.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(getActivity(),activity);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.magnifyandalpha,
                        R.anim.slide_still);
            }
        });
    }

//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//			// TODO 自动生成的方法存根
//			AnimationSet scale_animation_set = new AnimationSet(true);
//            //以自身为尺度缩放中心，从原大小尺寸逐渐缩放到0尺寸
//            ScaleAnimation scale_animation = new ScaleAnimation(1.0f, 0.7f, 1.0f, 0.7f, 
//                                            Animation.RELATIVE_TO_SELF, 0.5f,
//                                            Animation.RELATIVE_TO_SELF, 0.5f);  
//            scale_animation.setDuration(300);
//            scale_animation_set.addAnimation(scale_animation);    
//            scale_animation_set.setFillAfter(true);
//				switch(event.getAction()){
//				case MotionEvent.ACTION_DOWN:
//		            v.startAnimation(scale_animation_set);
////		            Intent intent=new Intent(getActivity(),Topic.class);
////    				startActivity(intent);
//					break;
//				case MotionEvent.ACTION_MOVE:
////					scale_animation_set.cancel();
//					scale_animation= new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, 
//                            Animation.RELATIVE_TO_SELF, 0.5f,
//                            Animation.RELATIVE_TO_SELF, 0.5f);
//					scale_animation.setDuration(300);
//		            scale_animation_set.addAnimation(scale_animation);    
//		            scale_animation_set.setFillAfter(true);
//		            v.startAnimation(scale_animation_set);
//					break;
//				case MotionEvent.ACTION_UP:
////					scale_animation= new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, 
////                            Animation.RELATIVE_TO_SELF, 0.5f,
////                            Animation.RELATIVE_TO_SELF, 0.5f);
////					scale_animation.setDuration(300);
////		            scale_animation_set.addAnimation(scale_animation);    
////		            scale_animation_set.setFillAfter(true);
////		            v.startAnimation(scale_animation_set);
//					break;
//				}
//			return false;
//		}


}
