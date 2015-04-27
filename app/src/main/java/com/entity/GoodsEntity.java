package com.entity;

public class GoodsEntity {

    private int dID;
    private String name;
    private int threadID;
    private String descr; //商品描述
    private String type;
    private String location;
    private String count;
    private String param1;
    private String param2;
    private String param3;
    private String frontimg_path;

    public GoodsEntity(int dID, String name, int threadID, String descr, String type,
                       String location,String count, String param1, String param2, String param3,String frontimg_path) {
        super();
        this.dID = dID;
        this.name = name;
        this.threadID = threadID;
        this.descr = descr;
        this.type = type;
        this.location = location;
        this.count=count;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.frontimg_path=frontimg_path;
    }

    public GoodsEntity() {
        super();
    }
    public String getFrontimg_Path() {
        return frontimg_path;
    }
    public void setFrontimg_Path(String frontimg_path) {
        this.frontimg_path = frontimg_path;
    }
    public String getParam1() {
        return param1;
    }
    public void setParam1(String param1) {
        this.param1 = param1;
    }
    public String getParam2() {
        return param2;
    }
    public void setParam2(String param2) {
        this.param2 = param2;
    }
    public String getParam3() {
        return param3;
    }
    public void setParam3(String param3) {
        this.param3 = param3;
    }
    public int getdID() {
        return dID;
    }
    public void setdID(int dID) {
        this.dID = dID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescr() {
        return descr;
    }
    public void setDescr(String descr) {
        this.descr = descr;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getThreadID() {
        return threadID;
    }
    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }


}
