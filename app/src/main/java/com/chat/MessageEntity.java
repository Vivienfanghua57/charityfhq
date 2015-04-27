package com.chat;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

@SuppressWarnings("serial")
public class MessageEntity implements Serializable {

    public String chatfrom;//登陆的id
    public int chatto_id;//私信的id
    public String chatto;
    public long date;
    public String msg;
    // 1 表示发出，0表示接受
    public int type;
    public Drawable head;

    public MessageEntity(String chatfrom,int chatto_id,String chatto, long date, String msg, int type) {
        this.chatfrom = chatfrom;
        this.chatto_id=chatto_id;
        this.chatto = chatto;
        this.date = date;
        this.msg = msg;
        this.type = type;
        //this.head = head;
    }
    public Drawable getHead() {
        return head;
    }

    public void setHead( Drawable head) {
        this.head = head;
    }
}
