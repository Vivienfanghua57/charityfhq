package com.entity;

public class ChatListEntity {
	public String userID;
	public String chatto_id;
	public String chatTo;
	public Long date;
	public String last_msg;
	public String avatar;
	
	public ChatListEntity(String userID,String chatto_id,String chatTo, String last_msg, Long date,String avatar) {
		this.userID = userID;
		this.chatto_id=chatto_id;
		this.chatTo=chatTo;
		this.date = date;
		this.last_msg = last_msg;
		this.avatar=avatar;
//		this.head = head;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getChatTo_ID() {
		return chatto_id;
	}

	public void setChatTo_ID(String chatto_id) {
		this.chatto_id = chatto_id;
	}
	public String getChatTo() {
		return chatTo;
	}

	public void setChatTo(String chatTo) {
		this.chatTo = chatTo;
	}
	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
	public String getLast_Msg() {
		return last_msg;
	}

	public void setLast_Msg( String last_msg) {
		this.last_msg = last_msg;
	}
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar( String avatar) {
		this.avatar = avatar;
	}
}
