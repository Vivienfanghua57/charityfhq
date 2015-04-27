package com.fragment;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.adapter.ThreadListAdapter;
import com.db.DatabaseHelper;
import com.entity.ForumPostEntity;
import com.entity.ForumThreadEntity;
import com.entity.ForumThreadExtendEntity;
import com.example.charitydemo.R;
import com.main.ImageGalleryActivity;
import com.main.ProfileUser;
import com.main.ReadThread;
import com.util.CustomListView;
import com.util.CustomListView.OnLoadListener;
import com.util.CustomListView.OnRefreshListener;
import com.util.MyApp;
import com.util.NetBroadcastReceiver;
import com.util.NetBroadcastReceiver.netEventHandler;
import com.util.FormatTools;
import com.util.MD5Util;
import com.util.NetUtil;
import com.util.PopupWindowUtil;
import com.util.PreferenceConstants;
import com.util.T;

public class FragmentHome extends Fragment implements netEventHandler,
        OnClickListener {
    private MyApp app;
    private SQLiteDatabase db;
    protected View mMainView;
    protected Context mContext;
    private CustomListView listview;
    private final int LOAD_DATA_FINISH = 0;
    private final int REFRESH_DATA_FINISH = 1;
    private final int LOAD_THREAD_FINISH = 2;//获取数据
    BaseAdapter adapter;
    public static View mNetErrorView;
    //	private int count = 5;
    private Button th_menu,th_select;
    private RelativeLayout  leftmenu_rel2, leftmenu_rel3,
            leftmenu_rel4;
    private View th_rel1;
    private DrawerLayout mDrawerLayout = null;
    private List<ForumThreadExtendEntity> threads = new ArrayList<ForumThreadExtendEntity>();
//	private ForumThreadEntity thread=new ForumThreadEntity();
//	private List<ForumPostEntity> posts = new ArrayList<ForumPostEntity>();

    // 当没有最新数据刷新时不发出handler，以免操作太频繁系统崩掉
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_DATA_FINISH:
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                        // adapter = new
                        // ThreadListAdapter(mContext,count,FragmentHome.newInstance());
                        // listview.setAdapter(adapter);
                    }
                    listview.onRefreshComplete(); // 下拉刷新完成
                    break;
                case LOAD_DATA_FINISH:
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                        // adapter = new ThreadListAdapter(mContext,count);
                        // listview.setAdapter(adapter);
                    }
                    listview.onLoadComplete(); // 加载更多完成
                    break;
                case LOAD_THREAD_FINISH:
				if(threads.size()>2){
					listview.addFooter(mContext);
				}

                    adapter = new ThreadListAdapter(mContext, threads,app,listview);
                    listview.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        };
    };

    public static FragmentHome newInstance() {
        FragmentHome newFragment = new FragmentHome();
        return newFragment;
    }

    public FragmentHome() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
        app=(MyApp) activity.getApplication();
        DatabaseHelper database = new DatabaseHelper(activity);
        db = database.getReadableDatabase();
        NetBroadcastReceiver.mListeners.add(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //用于记录fragment的状态,避免重复加载ui
        if(mMainView==null){
            mMainView=inflater.inflate(R.layout.tab_home, null);
            new Thread(loadRunnable).start();

//			temp_init();
            initview();
        }
        //缓存的mMainView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mMainView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mMainView.getParent();
        if (parent != null) {
            parent.removeView(mMainView);
        }
        return mMainView;
//		mMainView = inflater.inflate(R.layout.tab_home, container, false);
//		if(mMainView==null){
//		//开启线程接收主题列表
//		
//		}
//		return mMainView;
    }
    private Runnable loadRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO 自动生成的方法存根
            threads=DatabaseHelper.getThread(db);
            if(threads.size()==0||threads==null){
                loaddata();
            }else{
                app.setThreads(threads);

                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                message.what = 2;
//				bundle.putString("strResult", strResult);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }

    };
    //获取数据
    public void loaddata(){
        String httpUrl="http://"+getResources().getString(R.string.server_ip)+":7070/forum/thread!GetThreadsForAndroid";
        HttpPost httpRequest = new HttpPost(httpUrl);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("fid", "3"));
        params.add(new BasicNameValuePair("accessType", "refresh"));
        HttpEntity httpEntity = null;
        try {
            httpEntity = new UrlEncodedFormEntity(params, "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpRequest.setEntity(httpEntity);
        HttpClient httpClient = new DefaultHttpClient();
//			//连接超时
//			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,60000);
//			//读取超时
//			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,60000);

        HttpResponse httpResponse = null;
        //返回0则表示登陆成功，1则表示登陆失败，2表示无法访问到网络
        String strResult = null;
        try {
            httpResponse = httpClient.execute(httpRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//				try {
                strResult = EntityUtils.toString(httpResponse.getEntity(),
                        "UTF-8");
//					 if(strResult.equals("1")){
//						 return;
//					 }else{
                JSONArray jsonArray;
                jsonArray = new JSONArray(strResult);
                for (int i = 0; i < jsonArray.length(); i++) {
                    ForumThreadExtendEntity thread=new ForumThreadExtendEntity();
//					ForumPostEntity post=new ForumPostEntity();

                    JSONObject obj = (JSONObject) jsonArray.get(i);
//					}
                    thread.setTid(obj.getInt("tid"));
                    thread.setSubject(obj.getString("subject"));
                    thread.setAuthor(obj.getString("author"));
                    thread.setAuthorid(Integer.parseInt(obj.getString("authorid")));
//					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.fffffffff");
//					  format.setLenient(false);
//					  Timestamp ts = null;
//					try {
//						ts = new Timestamp(format.parse(obj.getString("dateline")).getTime());
//					} catch (ParseException e) {
//						// TODO 自动生成的 catch 块
//						e.printStackTrace();
//					}
//					thread.setDateline(ts);
//					thread.setDateline(Timestamp.valueOf(obj.getString("dateline")));
                    thread.setAvatarurl(obj.getString("avatarurl"));
                    thread.setFirstcontent(Html.fromHtml(obj.getString("firstcontent")).toString());
                    thread.setFirstimg(obj.getString("firstimg"));
//					post.setPid(obj.getInt("pid"));
//					post.setMessage(obj.getString("message"));

                    threads.add(thread);
                    DatabaseHelper.addThread(db, thread);
                }
                app.setThreads(threads);
//					Log.e("eeeeeeeeeeee",strResult);
//				}catch(IOException e){
//					e.printStackTrace();
//				}
            }
        } catch (JSONException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }catch (IOException e) {
//				Log.e("eeeeeeeeeee", e.getMessage());
//				strResult="2";
            e.printStackTrace();
        }
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        message.what = 2;
//			bundle.putString("strResult", strResult);
        message.setData(bundle);
        handler.sendMessage(message);
    }

//    public void temp_init(){
//        for(int i=0;i<6;i++){
//            ForumThreadExtendEntity thread=new ForumThreadExtendEntity();
//            ForumPostEntity post=new ForumPostEntity();
//            thread.setFid(i);
//            thread.setAuthorid(i);
//            thread.setAuthor("lee"+i);
//            thread.setSubject("明天去武汉洪山区希望小学支教吧");
////		thread.setDateline();
//            threads.add(thread);
//
////		post.setFid(i);
////		post.setAuthor("lee"+i);
////		post.setAuthorid(i);
//////		post.setSubject();
////		post.setMessage("我是武汉大学国际软件学院的学生，热爱于支教事业，希望可以"+i);
//////		post.setDateline();
////		posts.add(post);
//
//        }


//    }
    public void initview(){
        mNetErrorView = mMainView.findViewById(R.id.net_status_bar_top);
        mDrawerLayout = (DrawerLayout) mMainView
                .findViewById(R.id.drawer_layout);
        th_rel1 = (View) mMainView
                .findViewById(R.id.th_rel1);
        leftmenu_rel2 = (RelativeLayout) mMainView
                .findViewById(R.id.leftmenu_rel2);
        leftmenu_rel3 = (RelativeLayout) mMainView
                .findViewById(R.id.leftmenu_rel3);
        leftmenu_rel4 = (RelativeLayout) mMainView
                .findViewById(R.id.leftmenu_rel4);
        leftmenu_rel2.setOnClickListener(this);
        leftmenu_rel3.setOnClickListener(this);
        leftmenu_rel4.setOnClickListener(this);
        th_menu = (Button) mMainView.findViewById(R.id.th_menu);
        th_menu.setOnClickListener(this);
        th_select = (Button) mMainView.findViewById(R.id.th_select);
        th_select.setOnClickListener(this);
        if (NetUtil.getNetworkState(mContext) == NetUtil.NETWORN_NONE) {
            PreferenceConstants.isNetAvailable = false;
            mNetErrorView.setVisibility(View.VISIBLE);
        } else {
            PreferenceConstants.isNetAvailable = true;
            mNetErrorView.setVisibility(View.GONE);
        }
        listview = (CustomListView) mMainView
                .findViewById(R.id.thread_listview);
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                position--;
                Intent intent = new Intent(getActivity(), ReadThread.class);
                //将tid传递到下一个activity
//                intent.putExtra("tid", threads.get(position).getTid());
                intent.putExtra("position", position);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.slide_still);
            }

        });
        listview.setonRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO 下拉刷新
                // Log.e(TAG, "onRefresh");
                loadData(0);
            }
        });
        listview.setonLoadListener(new OnLoadListener() {

            @Override
            public void onLoad() {
                // TODO 加载更多
                // Log.e(TAG, "onLoad");
                loadData(1);
            }
        });
    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetworkState(mContext) == NetUtil.NETWORN_NONE) {
            T.showShort(mContext, R.string.net_error_tip);
            PreferenceConstants.isNetAvailable = false;
            mNetErrorView.setVisibility(View.VISIBLE);
        } else {
            PreferenceConstants.isNetAvailable = true;
            mNetErrorView.setVisibility(View.GONE);
        }
    }

    public void loadData(final int type) {
        new Thread() {
            @Override
            public void run() {

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (type == 0) { // 下拉刷新
                    // Collections.reverse(mList); //逆序
                    handler.sendEmptyMessage(REFRESH_DATA_FINISH);
                } else if (type == 1) {
                    handler.sendEmptyMessage(LOAD_DATA_FINISH);
                }

            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thread_avatar:
                Intent intent1 = new Intent(getActivity(), ProfileUser.class);
                //这里以后要传主题作者的id
                intent1.putExtra("ID","");
                startActivity(intent1);
                break;
            case R.id.thread_frontimg:
                Intent intent2 = new Intent(getActivity(),
                        ImageGalleryActivity.class);
                startActivity(intent2);
                getActivity().overridePendingTransition(R.anim.magnifyandalpha,
                        R.anim.slide_still);
                break;
            case R.id.th_menu:
                // showWindow();
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.th_select:
                PopupWindowUtil.showDropWindow(mContext, th_rel1, R.layout.dialog5);
                break;
            case R.id.leftmenu_rel2:
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.leftmenu_rel3:
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.leftmenu_rel4:
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
            default:
                break;
        }
    }

}
