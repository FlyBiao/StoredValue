package com.sw.android.storedvalue.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/10/13 15:11
 * Version 1.0
 */

public class ShopClerkBean implements Serializable {
    private String Name;
    private String Mobile;
    private int Id;
    private int ClerkId;

    public int getClerkId() {
        return ClerkId;
    }

    public void setClerkId(int clerkId) {
        ClerkId = clerkId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
