package com.entity;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.media.Image;

@SuppressWarnings("serial")
public class LoadEntity implements Serializable{
    public Bitmap bmp;
    public String path;
    //v为视频，p为照片
    public String type;

    public LoadEntity(Bitmap bmp,String path, String type) {
        this.bmp=bmp;
        this.path = path;
        this.type = type;
    }
    public Bitmap getBitmap() {
        return bmp;
    }

    public void setBitmap( Bitmap bmp) {
        this.bmp = bmp;
    }
    public String getPath() {
        return path;
    }

    public void setPath( String path) {
        this.path = path;
    }
    public String getType() {
        return type;
    }

    public void setType( String type) {
        this.type = type;
    }
}
