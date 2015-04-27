package com.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adapter.NoticeAdapter;
import com.chat.ChatActivity;
import com.chat.MessageEntity;
import com.db.DatabaseHelper;
import com.entity.ChatListEntity;
import com.example.charitydemo.R;
import com.main.NoticeComment;
import com.main.NoticeFocus;
import com.main.NoticeGroup;
import com.main.NoticeTrade;
import com.swipemenulistview.SwipeMenu;
import com.swipemenulistview.SwipeMenuCreator;
import com.swipemenulistview.SwipeMenuItem;
import com.swipemenulistview.SwipeMenuListView;
import com.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.util.MyApp;
import com.util.PreferenceConstants;
import com.baoyz.widget.PullRefreshLayout;
public class FragmentNotice extends Fragment implements OnClickListener{
    private MyApp app;
    protected View mMainView;
    protected Context mContext;
    public  SwipeMenuListView listView;
    public   NoticeAdapter adapter;
    private RelativeLayout nh_rel1,nh_rel2,nh_rel3,nh_rel4;
    private SQLiteDatabase db;
    public  List<ChatListEntity> chats = new ArrayList<ChatListEntity>();
    private PullRefreshLayout layout;
//	private ReceiveBroadCast receiveBroadCast;

    public static FragmentNotice newInstance() {
        FragmentNotice newFragment = new FragmentNotice();
        return newFragment;
    }

    public FragmentNotice() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        /** 注册广播 */
//        receiveBroadCast = new ReceiveBroadCast();  
//        IntentFilter filter = new IntentFilter(ChatActivity.class);  
//        filter.addAction("com.fragment");    //只有持有相同的action的接受者才能接收此广播  
//        activity.registerReceiver(receiveBroadCast, filter);
        super.onAttach(activity);
        app=(MyApp) activity.getApplication();
        mContext = activity.getApplicationContext();
    }
    public  void updateUI(){
        onStart();
        onResume();
    }
//	  class ReceiveBroadCast extends BroadcastReceiver  
//      {  
//              @Override  
//              public void onReceive(Context context, Intent intent)  
//              {  
//                  //得到广播中得到的数据，并显示出来  
////                  String gasname = intent.getExtras().getString("gasName");  
////                  String address = intent.getExtras().getString("address");  
////                   
////                  gasadderss.setText("地址：\n  "+address);  
////                  gasName.setText(gasname);  
////            	  adapter.notifyDataSetChanged();
//            	  Log.i("=====", "===");
//              }  
//      }  

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(PreferenceConstants.ACCOUNT.equals("")){
            mMainView = inflater.inflate(R.layout.tab_notice2, container, false);
        }else{
            mMainView = inflater.inflate(R.layout.tab_notice, container, false);
            DatabaseHelper database = new DatabaseHelper(mContext);
            db = database.getReadableDatabase();

            listView = (SwipeMenuListView)mMainView.findViewById(R.id.notice_listview);
            listView.addHeaderView(LayoutInflater.from(mContext).inflate(
                    R.layout.notice_head, null));
            nh_rel1=(RelativeLayout)mMainView.findViewById(R.id.nh_rel1);
            nh_rel2=(RelativeLayout)mMainView.findViewById(R.id.nh_rel2);
            nh_rel3=(RelativeLayout)mMainView.findViewById(R.id.nh_rel3);
            nh_rel4=(RelativeLayout)mMainView.findViewById(R.id.nh_rel4);
            nh_rel1.setOnClickListener(this);
            nh_rel2.setOnClickListener(this);
            nh_rel3.setOnClickListener(this);
            nh_rel4.setOnClickListener(this);

            layout = (PullRefreshLayout) mMainView.findViewById(R.id.swipeRefreshLayout);
            layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    layout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            layout.setRefreshing(false);
                        }
                    }, 4000);
                }
            });
            layout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

            // step 1. create a MenuCreator
            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem openItem = new SwipeMenuItem(
                            mContext);
                    // set item background
                    openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                            0xCE)));
                    // set item width
                    openItem.setWidth(dp2px(90));
                    // set item title
                    openItem.setTitle("置顶");
                    // set item title fontsize
                    openItem.setTitleSize(18);
                    // set item title font color
                    openItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(openItem);

                    // create "delete" item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            mContext);
                    // set item background
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                            0x3F, 0x25)));
                    // set item width
                    deleteItem.setWidth(dp2px(90));
                    // set a icon
                    deleteItem.setIcon(R.drawable.ic_delete);
                    // add to menu
                    menu.addMenuItem(deleteItem);
                }
            };
            // set creator
            listView.setMenuCreator(creator);

            // step 2. listener item click event
            listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//				ApplicationInfo item = mAppList.get(position);
                    switch (index) {
                        case 0:
                            // open
//					open(item);
                            break;
                        case 1:
                            // delete
////					delete(item);
//					mAppList.remove(position);
//					mAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            });

            // set SwipeListener
            listView.setOnSwipeListener(new OnSwipeListener() {

                @Override
                public void onSwipeStart(int position) {
                    // swipe start
                }

                @Override
                public void onSwipeEnd(int position) {
                    // swipe end
                }
            });
        }

        return mMainView;
    }
    /** 初始化控件 **/
    private void initView() {

        adapter = new NoticeAdapter(mContext,chats,app,listView);
//		adapter.setOnRightItemClickListener(new NoticeAdapter.onRightItemClickListener() {
//        	
//            @Override
//            public void onRightItemClick(View v, int position) {
//            	
//                Toast.makeText(getActivity(), "删除第  " + (position+1)+" 对话记录",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        listView.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                position -= 1;
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("CHATID", Integer.parseInt(chats.get(position).getChatTo_ID()));
                intent.putExtra("CHATNAME", chats.get(position).getChatTo());
                intent.putExtra("CHATAVATAR", chats.get(position).getAvatar());
                startActivity(intent);//scaleandalpha
                getActivity().overridePendingTransition(R.anim.magnifyandalpha,
                        R.anim.slide_still);
//				Log.i("", position + "");
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
            case R.id.nh_rel1:
                Intent intent1=new Intent(getActivity(),NoticeFocus.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            case R.id.nh_rel2:
                Intent intent2=new Intent(getActivity(),NoticeGroup.class);
                startActivity(intent2);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            case R.id.nh_rel3:
                Intent intent3=new Intent(getActivity(),NoticeComment.class);
                startActivity(intent3);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            case R.id.nh_rel4:
                Intent intent4=new Intent(getActivity(),NoticeTrade.class);
                startActivity(intent4);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.slide_still);
                break;
            default:
                break;
        }
    }
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
//         Log.e("HJJ", "ArrayListFragment **** onStart...");  
        super.onStart();
        if(PreferenceConstants.ACCOUNT.equals("")){

        }else{
            //从本地数据库获取私信列表
            chats=DatabaseHelper.getChatID(db, PreferenceConstants.ACCOUNT);
            for(int i=0;i<chats.size();i++){
                chats=DatabaseHelper.getChatLists(db, i, chats);
            }
            initView();
        }

    }
//       
//     @Override  
//     public void onResume() {  
//         Log.e("HJJ", "ArrayListFragment **** onResume...");  
//         // TODO Auto-generated method stub  
//         super.onResume();  
//     }  
//       
//     @Override  
//     public void onPause() {  
//         Log.e("HJJ", "ArrayListFragment **** onPause...");  
//         // TODO Auto-generated method stub  
//         super.onPause();  
//     }  
//       
//     @Override  
//     public void onStop() {  
//         Log.e("HJJ", "ArrayListFragment **** onStop...");  
//         // TODO Auto-generated method stub  
//         super.onStop();  
//     }  
//       
////     @Override  
////     public void onDestroyView() {  
////         Log.e("HJJ", "ArrayListFragment **** onDestroyView...");  
////         // TODO Auto-generated method stub  
////         super.onDestroyView();  
////     }  
//       
//     @Override  
//     public void onDestroy() {  
//         // TODO Auto-generated method stub  
//         Log.e("HJJ", "ArrayListFragment **** onDestroy...");  
//         super.onDestroy();  
//     }  
//       
//     @Override  
//     public void onDetach() {  
//         Log.e("HJJ", "ArrayListFragment **** onDetach...");  
//         // TODO Auto-generated method stub  
//         super.onDetach();  
//     }
//	 /**
//     *注销广播
//     * */  
//    @Override  
//    public void onDestroyView() {  
////       getActivity().unregisterReceiver(receiveBroadCast);  
//       super.onDestroyView();  
//    }  

}
