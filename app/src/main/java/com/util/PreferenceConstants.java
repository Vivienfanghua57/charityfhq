package com.util;

import android.os.Environment;

public class PreferenceConstants {
    //account指登陆的用户名不是id
    public static String ACCOUNT = "";
    public static String PASSWORD = "";
    public final static String JID = "192.168.207.3";
    public final static int PORT = 5222;
    public static Boolean isNetAvailable=false;
    //建立文件目录
    public final static String BASE_PATH=Environment.getExternalStorageDirectory()+"/CharityGroup/";
    //头像缓存目录
    public final static String AVATAR_PATH=BASE_PATH+".avatar/";
    public final static String TEMP_PATH_IMAGE=BASE_PATH+".temp/image/";
    public final static String TEMP_PATH_VIDEO=BASE_PATH+".temp/video/";
}

