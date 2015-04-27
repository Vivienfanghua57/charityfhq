package com.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.charitydemo.R;
import com.main.BriefThread;
import com.main.FocusActivity;
import com.main.DraftBox;
import com.main.Evaluate;
import com.main.Login;
import com.main.CharityGroup;
import com.main.MyCollect;
import com.main.ProfileUser;
import com.main.Settings;
import com.util.FormatTools;
import com.util.MD5Util;
import com.util.MemoryCache;
import com.util.MyApp;
import com.util.PreferenceConstants;
import com.util.RoundImageView;

public class FragmentMe extends Fragment implements OnClickListener{
    protected View mMainView;
    protected Context mContext;
    private TextView email,tm_email,tm_username,tm_login;
    private RoundImageView useravatar;
    private RelativeLayout me_rel2,me_rel3,me_rel4,me_rel5;
    private Button tm_settings,tm_contact,tm_draft,tm_charitygroup,tm_evaluate,mythread,mycollect;
    private int LOGIN_RESULT=1,PROFILE_RESULT=2;
    private String username;
    private String avatarurl="";
    //	private MemoryCache memoryCache=new MemoryCache();
    private MyApp app;
    private ImageView me_right;

    public static FragmentMe newInstance() {
        FragmentMe newFragment = new FragmentMe();
        return newFragment;
    }

    public FragmentMe() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
        app=(MyApp)activity.getApplication();
    }
    @Override
    public void onStart() {
        super.onStart();
        if(!username.equals("")){
            //从缓存文件夹里读取用户头像
            avatarurl=app.getMember().getAvatarurl();
            if(!avatarurl.equals("")){
                try{
                    Bitmap bmp=FormatTools.loadimage(app.getMemoryCache(), avatarurl, PreferenceConstants.AVATAR_PATH);
                    if(bmp!=null){
                        useravatar.setImageBitmap(bmp);
                    }
                }catch(NullPointerException e){

                }
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.tab_me, container, false);
        username=PreferenceConstants.ACCOUNT;

        tm_login =(TextView) mMainView.findViewById(R.id.tm_login);
        me_rel3 =(RelativeLayout) mMainView.findViewById(R.id.me_rel3);
        me_rel4 =(RelativeLayout) mMainView.findViewById(R.id.me_rel4);
        me_rel5 =(RelativeLayout) mMainView.findViewById(R.id.me_rel5);

        tm_login.setOnClickListener(this);
        if(username.equals("")){
//            metext_nologin =(TextView) mMainView.findViewById(R.id.metext_nologin);
//            metext_nologin.setVisibility(View.VISIBLE);
            me_right =(ImageView) mMainView.findViewById(R.id.me_right);
            me_right.setVisibility(View.GONE);
            me_rel3.setVisibility(View.GONE);
            me_rel4.setVisibility(View.GONE);
            me_rel5.setVisibility(View.GONE);
//	        	useravatar =(RoundImageView) mMainView.findViewById(R.id.useravatar);
//	        	useravatar.setImageBitmap(FormatTools.add2Bitmap(BitmapFactory.decodeResource(getResources(),R.drawable.m), 
//	        	BitmapFactory.decodeResource(getResources(),R.drawable.f)));
        }else{
            initView();
        }
        return mMainView;
    }
    public void initView(){

        useravatar =(RoundImageView) mMainView.findViewById(R.id.useravatar);
//	    	Bitmap bmp=BitmapFactory.decodeFile(PreferenceConstants.TEMP_PATH+".avatar/"+MD5Util.getMD5String(id));


        me_rel2 =(RelativeLayout) mMainView.findViewById(R.id.me_rel2);
        me_rel2.setOnClickListener(this);
        email =(TextView) mMainView.findViewById(R.id.email);
        tm_email =(TextView) mMainView.findViewById(R.id.tm_email);
        tm_username =(TextView) mMainView.findViewById(R.id.tm_username);
        tm_login.setVisibility(View.GONE);
        email.setVisibility(View.VISIBLE);
        tm_email.setVisibility(View.VISIBLE);
        tm_username.setVisibility(View.VISIBLE);

        tm_username.setText(username);
        tm_email.setText(app.getMember().getEmail());

        tm_settings =(Button) mMainView.findViewById(R.id.tm_settings);
        tm_settings.setOnClickListener(this);
        tm_contact =(Button) mMainView.findViewById(R.id.tm_contact);
        tm_contact.setOnClickListener(this);
        tm_draft =(Button) mMainView.findViewById(R.id.tm_draft);
        tm_draft.setOnClickListener(this);
        tm_charitygroup =(Button) mMainView.findViewById(R.id.tm_charitygroup);
        tm_charitygroup.setOnClickListener(this);
        tm_evaluate =(Button) mMainView.findViewById(R.id.tm_evaluate);
        tm_evaluate.setOnClickListener(this);
        mythread =(Button) mMainView.findViewById(R.id.mythread);
        mythread.setOnClickListener(this);
        mycollect =(Button) mMainView.findViewById(R.id.mycollect);
        mycollect.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.tm_login:
                intent.setClass(getActivity(), Login.class);
                intent.putExtra("IFEXIT", "no");
                startActivityForResult(intent, LOGIN_RESULT);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            case R.id.me_rel2:
//                if(username.equals("")){
//                    intent.setClass(getActivity(), Login.class);
//                    intent.putExtra("IFEXIT", "no");
//                    startActivityForResult(intent, LOGIN_RESULT);
//                    getActivity().overridePendingTransition(R.anim.push_left_in,
//                            R.anim.slide_still);}
//                else{
                    intent.setClass(getActivity(), ProfileUser.class);
                    intent.putExtra("UID",app.getMember().getUid());
                    intent.putExtra("USERNAME",username);
                    intent.putExtra("AVATARURL",avatarurl);
//					intent.putExtra("FOCUSNAME", friends.get(position).getFocusName());
                    startActivityForResult(intent,PROFILE_RESULT);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.slide_still);
//                }
                break;
            case R.id.tm_settings:
                intent.setClass(getActivity(), Settings.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            case R.id.tm_contact:
                intent.setClass(getActivity(), FocusActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            case R.id.tm_draft:
                intent.setClass(getActivity(), DraftBox.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            case R.id.tm_charitygroup:
                intent.setClass(getActivity(), CharityGroup.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            case R.id.tm_evaluate:
                intent.setClass(getActivity(), Evaluate.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            case R.id.mythread:
                intent.setClass(getActivity(), BriefThread.class);
                intent.putExtra("TITLE", "我的主题");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            case R.id.mycollect:
                intent.setClass(getActivity(), MyCollect.class);
//			    	intent.putExtra("TITLE", "我的收藏");
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            default:
                break;
        }
    }
}
