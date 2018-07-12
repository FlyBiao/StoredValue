package com.sw.android.storedvalue.bean;

import com.sw.android.storedvalue.base.BaseBean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2017/8/3 11:23
 * Version 1.0
 */

public class UserInfoBean extends BaseBean {

    public User TModel ;

    public class User implements Serializable {
        private String Mobile;//手机号
        private String Icon;//用户头像
        private String Name;//用户名称
        private String NickName;//用户昵称
        private String Sex;//性别
        private String ShopId;//店铺ID
        private String ShopName;//店铺名称
        private String TypeName;//用户身份：店员，店长
        private String TypeId;//1：店员，2：店长
        private String VipId;
        private String Ticket;//生成拉粉二维码用票
        private String GzNo;//公众号GzNo

        private String ShopPostCode;//商品提交码
        private String ShopProvince;//商品所在省
        private String ShopAddress;//商品所在地址
        private String ShopArea;//商品所在区域
        private String ShopCity;//商品所在城市

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }

        public String getIcon() {
            return Icon;
        }

        public void setIcon(String icon) {
            Icon = icon;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            Sex = sex;
        }

        public String getShopId() {
            return ShopId;
        }

        public void setShopId(String shopId) {
            ShopId = shopId;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String shopName) {
            ShopName = shopName;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String typeName) {
            TypeName = typeName;
        }

        public String getTypeId() {
            return TypeId;
        }

        public void setTypeId(String typeId) {
            TypeId = typeId;
        }

        public String getVipId() {
            return VipId;
        }

        public void setVipId(String vipId) {
            VipId = vipId;
        }

        public String getTicket() {
            return Ticket;
        }

        public void setTicket(String ticket) {
            Ticket = ticket;
        }

        public String getGzNo() {
            return GzNo;
        }

        public void setGzNo(String gzNo) {
            GzNo = gzNo;
        }

        public String getShopPostCode() {
            return ShopPostCode;
        }

        public void setShopPostCode(String shopPostCode) {
            ShopPostCode = shopPostCode;
        }

        public String getShopProvince() {
            return ShopProvince;
        }

        public void setShopProvince(String shopProvince) {
            ShopProvince = shopProvince;
        }

        public String getShopAddress() {
            return ShopAddress;
        }

        public void setShopAddress(String shopAddress) {
            ShopAddress = shopAddress;
        }

        public String getShopArea() {
            return ShopArea;
        }

        public void setShopArea(String shopArea) {
            ShopArea = shopArea;
        }

        public String getShopCity() {
            return ShopCity;
        }

        public void setShopCity(String shopCity) {
            ShopCity = shopCity;
        }
    }

}
