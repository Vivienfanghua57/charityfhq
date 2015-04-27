package com.emoji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.charitydemo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;


public class FaceConversionUtil {


    private int pageSize = 20;

    private static FaceConversionUtil mFaceConversionUtil;


    private HashMap<String, String> emojiMap = new HashMap<String, String>();


    private List<ChatEmoji> emojis = new ArrayList<ChatEmoji>();


    public List<List<ChatEmoji>> emojiLists = new ArrayList<List<ChatEmoji>>();

    private FaceConversionUtil() {

    }

    public static FaceConversionUtil getInstace() {
        if (mFaceConversionUtil == null) {
            mFaceConversionUtil = new FaceConversionUtil();
        }
        return mFaceConversionUtil;
    }


    public SpannableString getExpressionString(Context context, String str) {
        SpannableString spannableString = new SpannableString(str);
        String zhengze = "\\[[^\\]]+\\]";
        Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, sinaPatten, 0);
        } catch (Exception e) {
            Log.e("dealExpression", e.getMessage());
        }
        return spannableString;
    }


    public SpannableString addFace(Context context, int imgId,
                                   String spannableString) {
        if (TextUtils.isEmpty(spannableString)) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                imgId);
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
        ImageSpan imageSpan = new ImageSpan(context, bitmap);
        SpannableString spannable = new SpannableString(spannableString);
        spannable.setSpan(imageSpan, 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }


    private void dealExpression(Context context,
                                SpannableString spannableString, Pattern patten, int start)
            throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();

            if (matcher.start() < start) {
                continue;
            }
            String value = emojiMap.get(key);
            if (TextUtils.isEmpty(value)) {
                continue;
            }
            int resId = context.getResources().getIdentifier(value, "drawable",
                    context.getPackageName());
            if (resId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(
                        context.getResources(), resId);
                bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
                ImageSpan imageSpan = new ImageSpan(bitmap);
                int end = matcher.start() + key.length();
                spannableString.setSpan(imageSpan, matcher.start(), end,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                if (end < spannableString.length()) {
                    dealExpression(context, spannableString, patten, end);
                }
                break;
            }
        }
    }

    public void getFileText(Context context) {
        ParseData(FileUtils.getEmojiFile(context), context);
    }


    private void ParseData(List<String> data, Context context) {
        if (data == null) {
            return;
        }
        ChatEmoji emojEentry;
        try {
            for (String str : data) {
                String[] text = str.split(",");
                String fileName = text[0]
                        .substring(0, text[0].lastIndexOf("."));
                emojiMap.put(text[1], fileName);
                int resID = context.getResources().getIdentifier(fileName,
                        "drawable", context.getPackageName());

                if (resID != 0) {
                    emojEentry = new ChatEmoji();
                    emojEentry.setId(resID);
                    emojEentry.setCharacter(text[1]);
                    emojEentry.setFaceName(fileName);
                    emojis.add(emojEentry);
                }
            }
            int pageCount = (int) Math.ceil(emojis.size() / 20 + 0.1);

            for (int i = 0; i < pageCount; i++) {
                emojiLists.add(getData(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<ChatEmoji> getData(int page) {
        int startIndex = page * pageSize;
        int endIndex = startIndex + pageSize;

        if (endIndex > emojis.size()) {
            endIndex = emojis.size();
        }

        List<ChatEmoji> list = new ArrayList<ChatEmoji>();
        list.addAll(emojis.subList(startIndex, endIndex));
        if (list.size() < pageSize) {
            for (int i = list.size(); i < pageSize; i++) {
                ChatEmoji object = new ChatEmoji();
                list.add(object);
            }
        }
        if (list.size() == pageSize) {
            ChatEmoji object = new ChatEmoji();
            object.setId(R.drawable.face_del_icon);
            list.add(object);
        }
        return list;
    }
}