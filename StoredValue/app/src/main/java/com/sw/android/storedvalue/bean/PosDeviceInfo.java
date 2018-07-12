package com.sw.android.storedvalue.bean;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：pos 设备信息
 * 创建日期：2016/10/14 09:35
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class PosDeviceInfo {

//    "deviceType": "3",
//            "en": " 9b 43 ed 21",
//            "latitude": 22561607,
//            "loginType": 0,
//            "longitude": 114117162,
//            "mcode": "175182",
//            "mname": "四威数据",
//            "model": "WPOS3",
//            "name": "匿名用户",
//            "ota-name": "2.0.160816",
//            "ota-version": 46,
//            "snCode": "P327701602008730",
//            "wui-version": "0"

    private String deviceType;
    private String en;
    private String mcode;
    private int loginType;
    private int longitude;
    private String mcodel;
    private String mname;
    private String model;
    private String name;
    private String otaname;
    private String otaversion;
    private String snCode;
    private String wuiversion;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public String getMcodel() {
        return mcodel;
    }

    public void setMcodel(String mcodel) {
        this.mcodel = mcodel;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtaname() {
        return otaname;
    }

    public void setOtaname(String otaname) {
        this.otaname = otaname;
    }

    public String getOtaversion() {
        return otaversion;
    }

    public void setOtaversion(String otaversion) {
        this.otaversion = otaversion;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getWuiversion() {
        return wuiversion;
    }

    public void setWuiversion(String wuiversion) {
        this.wuiversion = wuiversion;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }
}
