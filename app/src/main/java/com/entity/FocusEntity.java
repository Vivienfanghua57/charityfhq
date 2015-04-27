package com.entity;

public class FocusEntity {
//	public int uid;
	public String focusname;
	public int focusID;
	public long focusTime;
	public int fType;
	public String initial;
	
	public FocusEntity(String focusname, int focusID,long focusTime,String initial,int fType) {
//		this.uid = uid;
		this.focusname = focusname;
		this.focusID = focusID;
		this.focusTime = focusTime;
		this.initial=initial;
		this.fType = fType;
	}
	
//	public int getID() {
//		return uid;
//	}
//
//	public void setID( int uid) {
//		this.uid = uid;
//	}
	public String getInitial() {
		return initial;
	}

	public void setInitial( String initial) {
		this.initial = initial;
	}
	public String getFocusName() {
		return focusname;
	}

	public void setFocusName( String focusname) {
		this.focusname = focusname;
	}
	public int getFocusID() {
		return focusID;
	}

	public void setFocusID( int focusID) {
		this.focusID = focusID;
	}
	public long getFocusTime() {
		return focusTime;
	}

	public void setFocusTime( long focusTime) {
		this.focusTime = focusTime;
	}
	public int getFType() {
		return fType;
	}

	public void setFType( int fType) {
		this.fType = fType;
	}
}
