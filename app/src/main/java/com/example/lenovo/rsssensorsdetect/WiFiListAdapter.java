package com.example.lenovo.rsssensorsdetect;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2017/3/17.
 */

public class WiFiListAdapter extends BaseAdapter {
    private Context context;
    private List<ScanResult> scanResults;

    public WiFiListAdapter(MainActivity context, List<ScanResult> scanResults) {
        super();
        this.context = context;
        this.scanResults = scanResults;
    }

    // getcount 获取数据的个数
    @Override
    public int getCount() {
        return scanResults.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    // getView 需要构建一个View对象来显示数据源中的数据
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ScanResult scanResult = scanResults.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup group = (ViewGroup) inflater.inflate(R.layout.wifi_msg, null);
        //对于一个没有被载入或者想要动态载入的界面，都需要使用LayoutInflater.inflate()来载入

        TextView textView1 = (TextView) group.findViewById(R.id.textView1);//获得其中的界面元素
        TextView textView2 = (TextView) group.findViewById(R.id.textView2);
        textView1.setText(scanResult.SSID);
        textView2.setText(scanResult.level + " dBm");

        return group;
    }
}
