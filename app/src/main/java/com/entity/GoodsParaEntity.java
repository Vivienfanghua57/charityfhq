package com.entity;

public class GoodsParaEntity {
	private int gID;
	private int prID;
	private String value;

	public GoodsParaEntity(int gID, int prID, String value) {
		super();
		this.gID = gID;
		this.prID = prID;
		this.value = value;
	}

	public int getGID() {
		return gID;
	}

	public void setGID(int gID) {
		this.gID = gID;
	}

	public int getPRID() {
		return prID;
	}

	public void setPRID(int prID) {
		this.prID = prID;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
