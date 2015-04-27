package com.util;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.charitydemo.R;
import com.main.ForumActivity;
import com.main.Login;
import com.main.MainActivity;
import com.main.NewThread;
import com.main.NewThreadGoodsDes;
import com.util.PickerView.onSelectListener;

public class PopupWindowUtil {
    public static PopupWindow popupWindow;
    public static View contentView;
    public static PickerView pickview;
    public static Button video, camera, select, dismiss;
    public static TextView dialog1_content,pick_complete,dialog2_content,dialog2_cancel, dialog2_quite,camera1, album,look;

    /**
     *
     * @param context
     * @param mainview
     * @param popupwindow
     * //dialog弹出
     */
    public static void showDIalog(final Context context, int mainview,
                                  int popupwindow,String content)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            try{
                inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
            catch(Exception e){

            }
        }
        LayoutInflater layoutInfalter = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainView = layoutInfalter.inflate(mainview, null);
        contentView = layoutInfalter.inflate(popupwindow, null);// R.layout.avatarpopup
        TextView titleView=(TextView)contentView.findViewById(R.id.locationdlg_content);
        titleView.setText(content);
        popupWindow = new PopupWindow(contentView, LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x50000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAtLocation(mainView, Gravity.BOTTOM | Gravity.CENTER,
                0, 0);
        contentView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                popupWindow.dismiss();
                return false;
            }

        });
        popupWindow.update();
    }
    public static void showDialog(final Context context, int mainview,
                                        int popupwindow) {
        // final Context context=activity.getApplicationContext();
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            try{
                inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
            catch(Exception e){

            }
        }
        LayoutInflater layoutInfalter = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainView = layoutInfalter.inflate(mainview, null);
        contentView = layoutInfalter.inflate(popupwindow, null);// R.layout.avatarpopup
        popupWindow = new PopupWindow(contentView, LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
//        popupWindow.setAnimationStyle(R.style.AnimBottom);
        popupWindow.setFocusable(true);

        popupWindow.setOutsideTouchable(true);

        ColorDrawable dw = new ColorDrawable(0x50000000);
        popupWindow.setBackgroundDrawable(dw);

        popupWindow.showAtLocation(mainView, Gravity.BOTTOM | Gravity.CENTER,
                0, 0);
        switch (popupwindow) {
            case R.layout.avatarpopup:
                video = (Button) PopupWindowUtil.contentView
                        .findViewById(R.id.video);
                video.setOnClickListener((OnClickListener) context);
                camera = (Button) PopupWindowUtil.contentView
                        .findViewById(R.id.camera);
                camera.setOnClickListener((OnClickListener) context);
                select = (Button) PopupWindowUtil.contentView
                        .findViewById(R.id.select);
                select.setOnClickListener((OnClickListener) context);
                dismiss = (Button) PopupWindowUtil.contentView
                        .findViewById(R.id.dismiss);
                dismiss.setOnClickListener((OnClickListener) context);
                break;
//            case R.layout.pickview_onedimension:
//                pick_complete = (TextView) PopupWindowUtil.contentView.findViewById(R.id.pick_complete);
//                pick_complete.setOnClickListener((OnClickListener) context);
//                pickview = (PickerView) contentView.findViewById(R.id.pickview);
//                pickview.setData(data);
//                pickview.setOnSelectListener(new onSelectListener() {
//
//                    @Override
//                    public void onSelect(String text) {
//                        // Toast.makeText(context, "选择了 " + text + " 分",
//                        // Toast.LENGTH_SHORT).show();
////					ForumActivity.forum=text;
//                    }
//                });
//                pickview.setSelected(0);
//                break;
            case R.layout.dialog1:
                dialog1_content=(TextView) PopupWindowUtil.contentView
                        .findViewById(R.id.dialog1_content);
                break;
            case R.layout.dialog2:
                dialog2_content=(TextView) PopupWindowUtil.contentView
                        .findViewById(R.id.dialog2_content);
                dialog2_cancel = (TextView) PopupWindowUtil.contentView
                        .findViewById(R.id.dialog2_cancel);
                dialog2_cancel.setOnClickListener((OnClickListener) context);
                dialog2_quite = (TextView) PopupWindowUtil.contentView
                        .findViewById(R.id.dialog2_confirm);
                dialog2_quite.setOnClickListener((OnClickListener) context);
                break;
            case R.layout.dialog3:
                album = (TextView) contentView.findViewById(R.id.album);
                album.setOnClickListener((OnClickListener) context);
                camera1 = (TextView) contentView.findViewById(R.id.camera1);
                camera1.setOnClickListener((OnClickListener) context);
                look = (TextView) contentView.findViewById(R.id.look);
                look.setOnClickListener((OnClickListener) context);
                break;
            default:
                break;
        }
        contentView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                popupWindow.dismiss();
                return false;
            }

        });
        popupWindow.update();
    }

    /**
     *
     * @param context
     * @param mainview
     * @param popupwindow
     * @param data
     * //底部画出的popupwindow
     */
    public static void showBottomView(final Context context, int mainview,
    int popupwindow, List<String> data) {
        // final Context context=activity.getApplicationContext();
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            try{
                inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
            catch(Exception e){

            }
        }
        LayoutInflater layoutInfalter = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainView = layoutInfalter.inflate(mainview, null);
        contentView = layoutInfalter.inflate(popupwindow, null);// R.layout.avatarpopup
        popupWindow = new PopupWindow(contentView, LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        popupWindow.setAnimationStyle(R.style.AnimBottom);
        popupWindow.setFocusable(true);

        popupWindow.setOutsideTouchable(true);

        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);

        popupWindow.showAtLocation(mainView, Gravity.BOTTOM | Gravity.CENTER,
                0, 0);
        switch (popupwindow) {
            case R.layout.pickview_onedimension:
                pick_complete = (TextView) PopupWindowUtil.contentView.findViewById(R.id.pick_complete);
                pick_complete.setOnClickListener((OnClickListener) context);
                pickview = (PickerView) contentView.findViewById(R.id.pickview);
                pickview.setData(data);
                pickview.setOnSelectListener(new onSelectListener() {

                    @Override
                    public void onSelect(String text) {
                        // Toast.makeText(context, "选择了 " + text + " 分",
                        // Toast.LENGTH_SHORT).show();
//					ForumActivity.forum=text;
                    }
                });
                pickview.setSelected(0);
                break;
            default:
                break;
        }
        contentView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                popupWindow.dismiss();
                return false;
            }

        });
        popupWindow.update();
    }

    /**
     *
     * @param context
     * @param mainview
     * @param popupwindow
     * 菜单弹出
     */
    public static void showDropWindow(final Context context, View mainview,int popupwindow) {
        // final Context context=activity.getApplicationContext();
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            try{
                inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
            catch(Exception e){

            }
        }
        LayoutInflater layoutInfalter = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View mainView = layoutInfalter.inflate(mainview, null);
        contentView = layoutInfalter.inflate(popupwindow, null);// R.layout.avatarpopup
        popupWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);

        popupWindow.setOutsideTouchable(true);

        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);

        popupWindow.showAsDropDown(mainview);
        switch (popupwindow) {
            case R.layout.dialog4:
                TextView participate,share,collect,warning;
                participate = (TextView) contentView.findViewById(R.id.participate);
                participate.setOnClickListener((OnClickListener) context);
                share = (TextView) contentView.findViewById(R.id.share);
                share.setOnClickListener((OnClickListener) context);
                collect = (TextView) contentView.findViewById(R.id.collect);
                collect.setOnClickListener((OnClickListener) context);
                warning = (TextView) contentView.findViewById(R.id.warning);
                warning.setOnClickListener((OnClickListener) context);
                break;
            default:
                break;
        }
        contentView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                popupWindow.dismiss();
                return false;
            }

        });
        popupWindow.update();
    }
}
