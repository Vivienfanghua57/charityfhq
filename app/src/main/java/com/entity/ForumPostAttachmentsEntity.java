package com.entity;

public class ForumPostAttachmentsEntity {
	private long aId;
	private byte fType;
	private String path;
	private String fName;
	private Integer pid;

	public long getaId() {
		return aId;
	}

	public void setaId(long aId) {
		this.aId = aId;
	}

	public byte getfType() {
		return fType;
	}

	public void setfType(byte fType) {
		this.fType = fType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

}
