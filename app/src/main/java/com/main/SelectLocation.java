package com.main;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.adapter.LocationListAdapter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;


import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.charitydemo.R;
import com.util.PopupWindowUtil;
import com.util.T;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectLocation extends Activity implements AMapLocationListener, Runnable, PoiSearch.OnPoiSearchListener {

    private AMap aMap;
    //用于POI查询
    private PoiSearch poiSearch;
    private PoiResult poiResult;
    private PoiSearch.Query query;// Poi查询条件类
    private LatLonPoint lp = new LatLonPoint(39.908127, 116.375257);
    private List<PoiItem> poiItems;// poi数据
    private List<String>addrList=null;
    private List<LatLonPoint> pointList=null;
    //用于定位
    private LocationManagerProxy aMapLocManager = null;
    private AMapLocation aMapLocation;// 用于判断定位超时
    private Handler locationHandler = new Handler();
    private String address="";
    private double longitude=104.5,latitude=30.6;
    private boolean isFirst=true;

    private ListView locationview;
    private ImageView searchImg=null;
    private ImageView searchDelete=null;
    private EditText search_location_text=null;
    public SelectLocation() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        searchImg=(ImageView)findViewById(R.id.locationSearch);
        searchImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                poisearch();
            }
        });
        search_location_text=(EditText)findViewById(R.id.location_search_text);
        searchDelete=(ImageView)findViewById(R.id.location_search_delete);
        searchDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                address="";
                search_location_text.setText("");
            }
        });
        aMapLocManager = LocationManagerProxy.getInstance(this);
        aMapLocManager.requestLocationUpdates(
                LocationProviderProxy.AMapNetwork, 2000, 10, this);
        locationHandler.postDelayed(this, 12000);// 设置超过12秒还没有定位到就停止定位
        List<String> ss=new ArrayList<>();
        locationview=(ListView)findViewById(R.id.locationlist);
        LocationListAdapter adapter=new LocationListAdapter(SelectLocation.this,ss,locationview);
        locationview.setAdapter(adapter);
        locationview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PopupWindowUtil.showDIalog(SelectLocation.this,R.layout.activity_select_location,R.id.locationdlg,address);
                //T.show(getApplicationContext(),addrList.get(position),500);
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null &&isFirst) {
            this.aMapLocation = location;// 判断超时机制
            String desc = "";
            Bundle locBundle = location.getExtras();
            if (locBundle != null) {
                desc = locBundle.getString("desc");
            }
            if(address==desc)
                return;
            address=desc;
            longitude=location.getLongitude();
            latitude=location.getLatitude();
            search_location_text.setText(address);
            isFirst=false;
        }
    }

    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    addrList=new ArrayList<String>();
                    pointList=new ArrayList<LatLonPoint>();
                    for(int i=0;i<poiItems.size();i++)
                    {
                        addrList.add(poiItems.get(i).getTitle());
                        pointList.add(poiItems.get(i).getLatLonPoint());
                    }
                    LocationListAdapter adapter=new LocationListAdapter(SelectLocation.this,addrList,locationview);
                    locationview.setAdapter(adapter);
                }
            } else {

            }
        }
    }

    @Override
    public void onPoiItemDetailSearched(PoiItemDetail poiItemDetail, int i) {

    }

    private void poisearch()
    {
        String search=search_location_text.getText().toString();
        if(search!="")
        query = new PoiSearch.Query(search, "", "");
        query.setPageSize(40);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        query.setLimitDiscount(false);
        query.setLimitGroupbuy(false);
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        lp = new LatLonPoint(latitude,longitude);
        //poiSearch.setBound(new PoiSearch.SearchBound(lp, 2000, true));//
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void run() {
        if (aMapLocation == null) {
            Toast.makeText(SelectLocation.this,"定位失败", Toast.LENGTH_SHORT).show();
            //stopLocation();// 销毁掉定位
        }
    }
    private void stopLocation() {
        if (aMapLocManager != null) {
            aMapLocManager.removeUpdates(this);
            aMapLocManager.destory();
        }
        aMapLocManager = null;
    }
}
