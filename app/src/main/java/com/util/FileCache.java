package com.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

public class FileCache {
    
    private static final String TAG = "MemoryCache";
     
    private File cacheDir;  //the directory to save images
 
    /**
     * Constructor
     * @param context The context related to this cache.
     * */
    public FileCache(Context context) {
        // Find the directory to save cached images
        if (android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "CharityGroup");
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
         
        Log.d(TAG, "cache dir: " + cacheDir.getAbsolutePath());
    }
 
    /**
     * Search the specific image file with a unique key.
     * @param key Should be unique.
     * @return Returns the image file corresponding to the key.
     * */
    public File getFile(String key) {
        File f = new File(cacheDir, key);
        if (f.exists()){
            Log.i(TAG, "the file you wanted exists " + f.getAbsolutePath());
            return f;
        }else{
            Log.w(TAG, "the file you wanted does not exists: " + f.getAbsolutePath());
        }
 
        return null;
    }
 
    /**
     * Put a bitmap into cache with a unique key.
     * @param key Should be unique.
     * @param value A bitmap.
     * */
    public void put(String key, Bitmap value){
        File f = new File(cacheDir, key);
        if(!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        //Use the util's function to save the bitmap.
        if(saveBitmap(f, value))
            Log.d(TAG, "Save file to sdcard successfully!");
        else
            Log.w(TAG, "Save file to sdcard failed!");
         
    }
     
    /**
     * Clear the cache directory on sdcard.
     * */
    public void clear() {
        File[] files = cacheDir.listFiles();
        for (File f : files)
            f.delete();
    }
    public boolean saveBitmap(File file, Bitmap bitmap){
        if(file == null || bitmap == null)
            return false;
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            return bitmap.compress(CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
