package com.entity;


public class ForumThreadExtendEntity extends ForumThreadEntity {
    private String avatarurl;
    private String firstcontent;
    private String firstimg;

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getFirstimg() {
        return firstimg;
    }

    public void setFirstimg(String firstimg) {
        this.firstimg = firstimg;
    }

    public String getFirstcontent() {
        return firstcontent;
    }

    public void setFirstcontent(String firstcontent) {
        this.firstcontent = firstcontent;
    }
}
