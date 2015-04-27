package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.util.Log;

/**
 *
 *
 * Bitmap有关的工具类
 *
 */
public class FormatTools {
    private static FormatTools tools = new FormatTools();

    public static FormatTools getInstance() {
        if (tools == null) {
            tools = new FormatTools();
            return tools;
        }
        return tools;
    }

    // 将byte[]转换成InputStream
    public InputStream Byte2InputStream(byte[] b) {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        return bais;
    }

    // 将InputStream转换成byte[]
    public byte[] InputStream2Bytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        int readCount = -1;
        try {
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 将Bitmap转换成InputStream
    public InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // 将Bitmap转换成InputStream
    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // 将InputStream转换成Bitmap
    public Bitmap InputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    // Drawable转换成InputStream
    public InputStream Drawable2InputStream(Drawable d) {
        Bitmap bitmap = this.drawable2Bitmap(d);
        return this.Bitmap2InputStream(bitmap);
    }

    // InputStream转换成Drawable
    public Drawable InputStream2Drawable(InputStream is) {
        Bitmap bitmap = this.InputStream2Bitmap(is);
        return this.bitmap2Drawable(bitmap);
    }

    // Drawable转换成byte[]
    public static byte[] Drawable2Bytes(Drawable d) {
        Bitmap bitmap = FormatTools.drawable2Bitmap(d);
        return FormatTools.Bitmap2Bytes(bitmap);
    }

    // byte[]转换成Drawable
    public static Drawable Bytes2Drawable(byte[] b) {
        Bitmap bitmap = FormatTools.Bytes2Bitmap(b);
        return FormatTools.bitmap2Drawable(bitmap);
    }

    // Bitmap转换成byte[]
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // byte[]转换成Bitmap
    public static Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    // Drawable转换成Bitmap
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // Bitmap转换成Drawable
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable d = (Drawable) bd;
        return d;
    }

    // 获取网络图片
    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL
                    .openConnection();
            // 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            // 连接设置获得数据流
            conn.setDoInput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            // 这句可有可无，没有影响
            // conn.connect();
            // 得到数据流
            InputStream is = conn.getInputStream();
            // 解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            // 关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    // //压缩图片
    // public static void getimage(String srcPath) {
    // BitmapFactory.Options newOpts = new BitmapFactory.Options();
    // //开始读入图片，此时把options.inJustDecodeBounds 设回true了
    // newOpts.inJustDecodeBounds = true;
    // Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
    //
    // newOpts.inJustDecodeBounds = false;
    // int w = newOpts.outWidth;
    // int h = newOpts.outHeight;
    // //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
    // float hh = 800f;//这里设置高度为800f
    // float ww = 480f;//这里设置宽度为480f
    // //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
    // int be = 1;//be=1表示不缩放
    // if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
    // be = (int) (newOpts.outWidth / ww);
    // } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
    // be = (int) (newOpts.outHeight / hh);
    // }
    // if (be <= 0)
    // be = 1;
    // newOpts.inSampleSize = be;//设置缩放比例
    // //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
    // bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
    // compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    // }
    // public static void compressImage(Bitmap image) {
    // File myCaptureFile = new File("/sdcard/CharityGroup/temp2.jpeg");
    // ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // image.compress(Bitmap.CompressFormat.JPEG, 100,
    // baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
    // int options = 100;
    // while ( baos.toByteArray().length / 1024>100) {
    // //循环判断如果压缩后图片是否大于100kb,大于继续压缩
    // baos.reset();//重置baos即清空baos
    // image.compress(Bitmap.CompressFormat.JPEG, options,
    // baos);//这里压缩options%，把压缩后的数据存放到baos中
    // options -= 10;//每次都减少10
    // }
    // ByteArrayInputStream isBm = new
    // ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
    // Bitmap bitmap = BitmapFactory.decodeStream(isBm, null,
    // null);//把ByteArrayInputStream数据生成图片
    // BufferedOutputStream bos = null;
    // try {
    // bos = new BufferedOutputStream(
    // new FileOutputStream(myCaptureFile));
    // } catch (FileNotFoundException e1) {
    // // TODO 自动生成的 catch 块
    // e1.printStackTrace();
    // }
    // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
    // try {
    // bos.flush();
    // } catch (IOException e) {
    // // TODO 自动生成的 catch 块
    // e.printStackTrace();
    // }
    // try {
    // bos.close();
    // } catch (IOException e) {
    // // TODO 自动生成的 catch 块
    // e.printStackTrace();
    // }
    // //return bitmap;
    // }
    // 解决bitmapfactory内存溢出问题
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    // 压缩图片
    public static Bitmap CompressImage(String path) {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);

        opts.inSampleSize = FormatTools.computeSampleSize(opts, -1, 128 * 128);
        opts.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeFile(path, opts);
        } catch (OutOfMemoryError err) {
        }
        return bitmap;
    }

    // 取视频第一帧图片
    public static Bitmap getBitmapsFromVideo(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        // // 取得视频的长度(单位为毫秒)
        // String time = retriever
        // .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        // 取得视频的长度(单位为秒)
        // int seconds = Integer.valueOf(time) / 1000;
        // 得到每一秒时刻的bitmap比如第一秒,第二秒
        // for (int i = 1; i <= seconds; i++) {
        Bitmap bitmap = retriever.getFrameAtTime(1 * 1000 * 1000,
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        return bitmap;
    }

    // 保存bitmap到某一路径
    public static void saveBitmap(Bitmap bmp, String path, String filename) {
        File f = new File(path, filename);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static String isVideo(String path) {
        String isVideo = "";
        if (path.toLowerCase().endsWith(".3gp")
                || path.toLowerCase().endsWith(".mov")
                || path.toLowerCase().endsWith(".mp4")
                || path.toLowerCase().endsWith(".wmv")) {
            isVideo = "y";
        } else if (path.toLowerCase().endsWith(".jpg")
                || path.toLowerCase().endsWith(".jpeg")
                || path.toLowerCase().endsWith(".png")
                || path.toLowerCase().endsWith(".gif")) {
            isVideo = "n";
        }
        return isVideo;
    }

    // 接下来两个方法三级缓存加载图片，1.内存，2.文件缓存，3.网上缓存
    public static Bitmap loadimage(MemoryCache memory, String url, String path) {
        Bitmap bmp = loadImageByLocal(memory, url, path);
        if (bmp != null) {

        } else {
            bmp = loadimageByInternet(memory, url, path);
        }

        return bmp;
    }

    // 网络获取
    public static Bitmap loadimageByInternet(MemoryCache memory, String url,
                                             String path) {

        Bitmap bmp = FormatTools.getHttpBitmap(url);
        if (bmp != null) {
            try {
                memory.put(url, bmp);
                FormatTools.saveBitmap(bmp, path, MD5Util.getMD5String(url));
            } catch (NullPointerException e) {

            }

        }

        return bmp;
    }

    // 本地读取
    public static Bitmap loadImageByLocal(MemoryCache memory, String url,
                                          String path) {
        File file = new File(path);
        if (!(file.exists())) {
            file.mkdirs();
        }
        Bitmap bmp = null;
        bmp = memory.get(url);
        if (bmp != null) {

        } else {
            bmp = BitmapFactory.decodeFile(path + MD5Util.getMD5String(url));
            if (bmp != null) {
                memory.put(url, bmp);
            }
        }
        return bmp;
    }

    public static Bitmap add2Bitmap(Bitmap first, Bitmap second) {
        int width = first.getWidth() + second.getWidth();
        int height = Math.min(first.getHeight(), second.getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getWidth(), 0, null);
        return result;
    }
}
