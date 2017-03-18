package com.example.lenovo.rsssensorsdetect;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/3/17.
 * 传感器数据的处理，地磁、方向、加速度、陀螺仪、重力
 */

public class SensorDataManager {
    private SensorManager sensorManager;
    private Sensor msensor;
    private Sensor osensor;
    private Sensor asensor;
    private Sensor gsensor;
    private Sensor grasensor;
    //监视传感器事件，有onAccuracyChanged()和onSensorChanged()两个接口
    private SensorEventListener mSensorListener;
    private SensorEventListener oSensorListener;
    private SensorEventListener aSensorListener;
    private SensorEventListener gSensorListener;
    private SensorEventListener graSensorListener;

    //传感器获取的数据
    private float[] temp_m = new float[3];
    private float[] temp_o = new float[3];
    private float[] temp_a = new float[3];
    private float[] temp_g = new float[3];
    private float[] temp_gra = new float[3];


    public ArrayList<ArrayList<Integer>> dataMagnetic = new ArrayList<ArrayList<Integer>>(3);
    public ArrayList<ArrayList<Integer>> dataOrientation = new ArrayList<ArrayList<Integer>>(3);
    public ArrayList<ArrayList<Integer>> dataAccelerate = new ArrayList<ArrayList<Integer>>(3);
    public ArrayList<ArrayList<Integer>> dataGyroscope = new ArrayList<ArrayList<Integer>>(3);
    public ArrayList<ArrayList<Integer>> dataGravity = new ArrayList<ArrayList<Integer>>(3);

    private volatile static SensorDataManager sensorsDataManager = null;//修饰被不同线程访问和修改的变量。

    //getInstance方法用来实例化抽象类
    public static SensorDataManager getInstance() {
        if (sensorsDataManager == null) {
            synchronized (SensorDataManager.class) {
                if (sensorsDataManager == null) {
                    sensorsDataManager = new SensorDataManager();
                }
            }
        }
        return sensorsDataManager;
    }

    public void init(MainActivity mainActivity) {
        // 下面顺便采集些磁场和方向的数据

        //获得传感器管理器
        sensorManager = (SensorManager) mainActivity
                .getSystemService(Context.SENSOR_SERVICE);
        //注册地磁传感器
        msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        osensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        asensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        grasensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        dataInit();
        mSensorListener = new MSensorListener();
        oSensorListener = new OSensorListener();
        aSensorListener = new ASensorListener();
        gSensorListener = new GSensorListener();
        graSensorListener = new GraSensorListener();
        sensorManager.registerListener(mSensorListener, msensor,
                SensorManager.SENSOR_DELAY_NORMAL);//设置采样延时
        sensorManager.registerListener(oSensorListener, osensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(aSensorListener, asensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(gSensorListener, gsensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(graSensorListener, grasensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void dataInit() {
        for (int i = 0; i < 3; i++) {
            dataMagnetic.add(new ArrayList<Integer>());
            dataOrientation.add(new ArrayList<Integer>());
            dataAccelerate.add(new ArrayList<Integer>());
            dataGyroscope.add(new ArrayList<Integer>());
            dataGravity.add(new ArrayList<Integer>());
        }
    }

    public void dataClear() {
        for (int i = 0; i < dataMagnetic.size(); i++) {
            dataMagnetic.get(i).clear();
            dataOrientation.get(i).clear();
            dataAccelerate.get(i).clear();
            dataGyroscope.get(i).clear();
            dataGravity.get(i).clear();
        }
    }

    public void updateSensorsData() {
            // 保持和wifi的数据采集同步，不每次都更新
            if (WifiDataManager.getInstance().dataCount > dataAccelerate.get(0)
                    .size()) {
                dataMagnetic.get(0).add(
                        Integer.valueOf((int) Math.floor(temp_m[0] * 100)));//Math.floor表示向下取整
                dataMagnetic.get(1).add(
                        Integer.valueOf((int) Math.floor(temp_m[1] * 100)));
                dataMagnetic.get(2).add(
                        Integer.valueOf((int) Math.floor(temp_m[2] * 100)));
                dataOrientation.get(0).add(
                        Integer.valueOf((int) Math.floor(temp_o[0] * 100)));
                dataOrientation.get(1).add(
                        Integer.valueOf((int) Math.floor(temp_o[1] * 100)));
                dataOrientation.get(2).add(
                        Integer.valueOf((int) Math.floor(temp_o[2] * 100)));
                dataAccelerate.get(0).add(
                        Integer.valueOf((int) Math.floor(temp_a[0] * 100)));
                dataAccelerate.get(1).add(
                        Integer.valueOf((int) Math.floor(temp_a[1] * 100)));
                dataAccelerate.get(2).add(
                        Integer.valueOf((int) Math.floor(temp_a[2] * 100)));
                dataGyroscope.get(0).add(
                        Integer.valueOf((int) Math.floor(temp_g[0] * 100)));
                dataGyroscope.get(1).add(
                        Integer.valueOf((int) Math.floor(temp_g[1] * 100)));
                dataGyroscope.get(2).add(
                        Integer.valueOf((int) Math.floor(temp_g[2] * 100)));
                dataGravity.get(0).add(
                        Integer.valueOf((int) Math.floor(temp_gra[0] * 100)));
                dataGravity.get(1).add(
                        Integer.valueOf((int) Math.floor(temp_gra[1] * 100)));
                dataGravity.get(2).add(
                        Integer.valueOf((int) Math.floor(temp_gra[2] * 100)));
            }
        }

    private class MSensorListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            temp_m[0] = event.values[0];
            temp_m[1] = event.values[1];
            temp_m[2] = event.values[2];
            // 在这里显示一下地磁数据试试
            MainActivity.dataTextView.setText("地磁数据: " + temp_m[0] + " "
                    + temp_m[1] + " " + temp_m[2]);
            updateSensorsData();
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    private class OSensorListener implements SensorEventListener {//方向
        @Override
        public void onSensorChanged(SensorEvent event) {
            temp_o[0] = event.values[0];
            temp_o[1] = event.values[1];
            temp_o[2] = event.values[2];
            MainActivity.dataTextViewOri.setText("方向数据: " + temp_o[0] + " "
                    + temp_o[1] + " " + temp_o[2]);
            updateSensorsData();
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    private class ASensorListener implements SensorEventListener {//加速度
        @Override
        public void onSensorChanged(SensorEvent event) {
            temp_a[0] = event.values[0];
            temp_a[1] = event.values[1];
            temp_a[2] = event.values[2];
            MainActivity.dataTextViewAcc.setText("加速度数据: " + temp_a[0] + " "
                    + temp_a[1] + " " + temp_a[2]);
            updateSensorsData();
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    private class GSensorListener implements SensorEventListener {//陀螺仪
        @Override
        public void onSensorChanged(SensorEvent event) {
            temp_g[0] = event.values[0];
            temp_g[1] = event.values[1];
            temp_g[2] = event.values[2];
            MainActivity.dataTextViewGro.setText("陀螺仪数据: " + temp_g[0] + " "
                    + temp_g[1] + " " + temp_g[2]);
            updateSensorsData();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    private class GraSensorListener implements SensorEventListener {//重力
        @Override
        public void onSensorChanged(SensorEvent event) {
            temp_gra[0] = event.values[0];
            temp_gra[1] = event.values[1];
            temp_gra[2] = event.values[2];
            MainActivity.dataTextViewGra.setText("重力数据: " + temp_gra[0] + " "
                    + temp_gra[1] + " " + temp_gra[2]);
            updateSensorsData();
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    //注销监听器
    public void unregist() {
        sensorManager.unregisterListener(mSensorListener);
        sensorManager.unregisterListener(oSensorListener);
        sensorManager.unregisterListener(aSensorListener);
        sensorManager.unregisterListener(gSensorListener);
        sensorManager.unregisterListener(graSensorListener);
    }
}
