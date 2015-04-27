package com.main;


import java.util.ArrayList;
import java.util.List;

import com.example.charitydemo.R;
import com.util.PopupWindowUtil;
import com.util.T;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ForumActivity extends Activity implements OnClickListener{

    private ExpandableListView expandableListView;
    private MyExpandableListViewAdapter adapter;
    private String forum="";
    //	 private List<String> group_list;
//	 private List<List<String>> item_list;
//	 private List<List<Integer>> item_list2;
    private String[] forum1={"家用电器","手机数码","电脑办公","图书音像","男装女装"};
    private String[][] forum2 = new String[][] {
            { "大家电", "生活电器", "厨房电器", "个护健康", "五金家装"},
            { "手机通讯", "手机配件", "摄影摄像", "数码配件", "智能设备", "时尚影音" },
            { "电脑整机", "电脑配件", "外设产品", "网络产品", "办公设备","文具耗材" },
            { "文    艺", "教    育", "人文社科", "经管励志", "科    技","生    活","音    像" },
            { "男装", "女装", "男鞋", "女鞋"}};
    private String[][][] forum3 =new String[][][]{
            {
                    {"电视机","冰箱","洗衣机","音响","热水器","家庭影院","消毒柜","洗碗机","空调","冰柜","酒柜","烟机/灶具","家电配件","其他大家电"},
                    {"取暖电器","净化器","加湿器","吸尘器","挂烫机/熨斗","插座","电话 机","饮水机","电风扇","收音机","其他生活电器"},
                    {"电压力锅","电饭煲","豆浆机","面包机","咖啡机","微波炉","榨汁机","电烤箱","电磁炉","煮蛋器","酸奶机","电水壶","其他厨房电器"},
                    {"剃须刀","吹风机","口腔护理","按摩椅","足浴盆","血压计","血糖仪","健康秤","理发器","卷发器","体温计","计步器","其他健康电器"},
                    {"灯具","浴霸","LED灯","水槽","水龙头","电器开关","插座","门铃","电线电缆","监控安防","家具五金","厨具五金","电动工具","手动","工具","仪器仪表","电工电料"}
            },
            {
                    {"手机","对讲机"},
                    {"电池","蓝牙耳机","充电器/数据线","手机耳机","贴膜","存储卡","保护套","车载","iPhone配件","便携/无线音箱","手机饰品"},
                    {"数码相机","单反相机","拍立得","摄像机","镜头","户外器材","影棚器材"},
                    {"存储卡","读卡器","滤镜","闪光灯","手柄","相机包","三脚架","移动电源","充电器"},
                    {"智能手环","智能手表","智能眼镜","运动跟踪器","健康检测","智能配饰","智能家居","体感车","其他配件"},
                    {"耳机/耳麦","音响/音箱","麦克风","MP3/MP4","数码相框","专业音频","苹果周边"}
            },
            {   {"笔记本","台式机","服务器"},
                    {"CPU","主板","显卡","硬盘","SSD固态硬盘","内存","机箱","显示器","光驱","散热器"},
                    {"鼠标","键盘","移动硬盘","U盘","鼠标垫","摄像头"},
                    {"路由器","交换机","网卡"},
                    {"投影机","打印机","传真设备","点钞机","碎纸机","考勤机","POS机","装订机","白板","扫描设备"},
                    {"硒鼓/粉墨","纸类","计算机","办公文具"}
            },
            {   {"小说","文学","传记","青春文学","动漫/幽默","艺术","摄影","偶像明星"},
                    {"中小学教辅","考试","中大专教辅","外语","工具书","教师用书","公务员"},
                    {"哲学宗教","历史","政治军事","文化","社会科学","心理学","古籍","法律"},
                    {"励志/成功","管理","经济","投资理财","心灵修养","职场"},
                    {"科普读物","计算机/网络","医学","工业技术","建筑","自然科学","农业","林业"},
                    {"保健","亲子家教","旅游","美食","育儿"},
                    {"音乐","影视","软件","游戏","教育音像","华语流行","儿童音乐","动画片"}
            },
            {   {"衬衫","羽绒服","棉衣","夹克","风衣","针织衫","皮衣","T恤","牛仔裤","休闲裤","毛呢大衣","西服","卫衣","短裤","羊毛衫","西裤","马甲/背心","POLO衫","运动装","中山装"},
                    {"连衣裙","针织衫","羊毛衫","卫衣","风衣","衬衫","马甲","西装","T恤","牛仔裤","休闲裤","毛呢大衣","羽绒服","皮衣","裙子","打底衫","打底裤","礼服","旗袍","短裤","正装裤"},
                    {"皮鞋","运动鞋","休闲鞋","拖鞋","男靴","雨靴"},
                    {"雪地靴","休闲鞋","皮鞋","高跟鞋","拖鞋","帆布鞋","凉鞋","雨靴"}
            }
    };
//	 private List<String> data1 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum);
//		for(int i=0;i<forum1.length;i++){
//			data1.add(forum1[i]);
//		}
//	  //随便一堆测试数据
//	  group_list = new ArrayList<String>();
//	  group_list.add("A");
//	  group_list.add("B");
//	  group_list.add("C");
//	 
//	  item_list = new ArrayList<List<String>>();
//	  item_list.add(group_list);
//	  item_list.add(group_list);
//	  item_list.add(group_list);
//	 
//	  List<Integer> tmp_list = new ArrayList<Integer>();
//	  tmp_list.add(R.drawable.ic_launcher);
//	  tmp_list.add(R.drawable.ic_launcher);
//	  tmp_list.add(R.drawable.ic_launcher);
//	 
//	  item_list2 = new ArrayList<List<Integer>>();
//	  item_list2.add(tmp_list);
//	  item_list2.add(tmp_list);
//	  item_list2.add(tmp_list);

        expandableListView = (ExpandableListView) findViewById(R.id.expandablelistView);
        adapter=new MyExpandableListViewAdapter(this);
        expandableListView.setAdapter(adapter);
        expandableListView.setGroupIndicator(null);
        for(int i = 0; i < adapter.getGroupCount(); i++){

            expandableListView.expandGroup(i);

        }
        expandableListView.setOnChildClickListener(new OnChildClickListener(){

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO 自动生成的方法存根
//			T.showShort(ForumActivity.this, forum3[groupPosition][childPosition].length+"");
//			forum=forum3[groupPosition][childPosition][0];
                List<String> data = new ArrayList<String>();
                for (int i = 0; i < forum3[groupPosition][childPosition].length; i++) {
                    data.add(forum3[groupPosition][childPosition][i]);
                }
                PopupWindowUtil.showBottomView(ForumActivity.this,R.layout.forum,R.layout.pickview_onedimension,data);
                return false;
            }

        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
            return false;
        }
        return false;
    }
    //expandablelistview的适配器
    class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

        private Context context;

        public MyExpandableListViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            return forum1.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return forum2[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return forum1[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return forum2[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
                convertView = (View) getLayoutInflater().from(context).inflate(
                        R.layout.forum_group, null);
                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView) convertView.findViewById(R.id.group_title);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            groupHolder.txt.setText(forum1[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
            if (convertView == null) {
                convertView = (View) getLayoutInflater().from(context).inflate(
                        R.layout.forum_child, null);
                itemHolder = new ItemHolder();
                itemHolder.txt = (TextView) convertView.findViewById(R.id.child_title);
//		    itemHolder.img = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            itemHolder.txt.setText(forum2[groupPosition][childPosition]);
//		   itemHolder.img.setBackgroundResource(item_list2.get(groupPosition).get(
//		     childPosition));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    class GroupHolder {
        public TextView txt;
        public ImageView img;
    }

    class ItemHolder {
        public ImageView img;
        public TextView txt;
    }

    @Override
    public void onClick(View v) {
        // TODO 自动生成的方法存根
        switch (v.getId()) {
            case R.id.pick_complete:
//				Intent intent=new Intent(ForumActivity.this,NewThreadGoodsDes.class);
                forum=PopupWindowUtil.pickview.getSelected();
//				intent.putExtra("FORUM", forum);
//				startActivity(intent);
                PopupWindowUtil.popupWindow.dismiss();
                Intent data=new Intent();
                data.putExtra("forum", forum);
                setResult(1, data);
                finish();
                overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);
                break;
            default:
                break;
        }

    }

}
