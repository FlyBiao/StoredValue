package com.sw.android.storedvalue.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author FGB
 * Description
 * Created at 2017/7/20 13:35
 * Version 1.0
 */

public class TradeVipIdBean {
    /**
     * Field : VipId
     * Value : 543868
     */

    private String Field;
    private String Value;


    public JSONObject getVipIdInfo(){
        JSONObject obj=new JSONObject();
        try {
            obj.put("Field",getField());
            obj.put("Value",getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public void setField(String Field) {
        this.Field = Field;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public String getField() {
        return Field;
    }

    public String getValue() {
        return Value;
    }
}
