package com.entity;

public class UserEntitiy{
	public int userID;
	public String userName;
	public String password;
	public String userAvatar;
	
	public UserEntitiy(int userID,String userName,String password, String userAvatar) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.userAvatar = userAvatar;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword( String password) {
		this.password = password;
	}
	public int getID() {
		return userID;
	}

	public void setID( int userID) {
		this.userID = userID;
	}
	public String getName() {
		return userName;
	}

	public void setName( String userName) {
		this.userName = userName;
	}
	public String getAvatar() {
		return userAvatar;
	}

	public void setAvatar( String userAvatar) {
		this.userAvatar = userAvatar;
	}
}
