package com.example.lenovo.rsssensorsdetect;

/**
 * Created by lenovo on 2017/3/17.
 */

public class GlobalPara {
    public long timeOfStartScan=0;
    public long timeSinceStart=0;
    public int position_index=1;

    private volatile static GlobalPara globalPara = null;
    public static GlobalPara getInstance() {
        if (globalPara == null) {
            synchronized (GlobalPara.class) {
                if (globalPara == null) {
                    globalPara = new GlobalPara();
                }
            }
        }
        return globalPara;
    }
}
