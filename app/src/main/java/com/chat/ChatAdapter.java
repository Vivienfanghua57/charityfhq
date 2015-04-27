package com.chat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.emoji.FaceConversionUtil;
import com.example.charitydemo.R;
import com.util.RoundAngleImageView;
import com.util.TimeRender;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter{
    //	public static interface IMsgViewType
//	{
//		int IMVT_COM_MSG = 0;
//		int IMVT_TO_MSG = 1;
//	}
    private Context context;
    private LayoutInflater inflater;
    private List<MessageEntity> listMsg;
    Boolean isIn=false;


    public ChatAdapter(Context context, List<MessageEntity> listMsg) {
        this.context = context;
        this.listMsg = listMsg;
        //inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listMsg.size();
    }

    @Override
    public Object getItem(int position) {
        return listMsg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO 自动生成的方法存根
        this.inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (listMsg.get(position).type==0) {
            convertView = this.inflater.inflate(R.layout.chat_chat_in, null);
            isIn=true;
        } else {
            convertView = this.inflater.inflate(R.layout.chat_chat_out, null);
            isIn=false;
        }
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView message = (TextView) convertView.findViewById(R.id.message);
        RoundAngleImageView userhead=(RoundAngleImageView) convertView.findViewById(R.id.useravatar);


//		ImageView userhead =(ImageView) convertView
//				.findViewById(R.id.useravatar);
//		TextView date = (TextView) convertView
//				.findViewById(R.id.date);
//		TextView message = (TextView) convertView
//				.findViewById(R.id.message);
        if(position!=0){
            SimpleDateFormat y = new SimpleDateFormat("yyyy");
            SimpleDateFormat M = new SimpleDateFormat("MM");
            SimpleDateFormat d = new SimpleDateFormat("dd");
            SimpleDateFormat H = new SimpleDateFormat("HH");
            SimpleDateFormat m = new SimpleDateFormat("mm");
            Date predate = new Date(listMsg.get(position-1).date);
            Date cudate = new Date(listMsg.get(position).date);
            int temp1 = Integer.parseInt(y.format(cudate))
                    - Integer.parseInt(y.format(predate));
            int temp2 = Integer.parseInt(M.format(cudate))
                    - Integer.parseInt(M.format(predate));
            int temp3 = Integer.parseInt(d.format(cudate))
                    - Integer.parseInt(d.format(predate));
            int temp4 = Integer.parseInt(H.format(cudate))
                    - Integer.parseInt(H.format(predate));
            int temp5 = Integer.parseInt(m.format(cudate))
                    - Integer.parseInt(m.format(predate));
            if(temp1==0&&temp2==0&&temp3==0&&temp4==0&&temp5<5)
            {
                date.setVisibility(View.GONE);
            }
            else{
                date.setText(TimeRender.getChatTime(listMsg.get(position).date));
            }
        }else{
            date.setText(TimeRender.getChatTime(listMsg.get(position).date));
        }
//		date.setText(listMsg.get(position).date);

        SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(context, listMsg.get(position).msg);
        message.setText(spannableString);
//		if(isIn){
//			//head=DatabaseHelper.getFriendAvatar(db, listMsg.get(position).userid);
//		}
//		else{
//			//head=DatabaseHelper.getUserAvatar(db, listMsg.get(position).userid);
//		}
//		//userhead.setImageDrawable(head);
        return convertView;
    }

}
