package com.sw.android.storedvalue.bean;

import com.sw.android.storedvalue.base.BaseBean;

/**
 * Created by FGB on 2016/3/9.
 */
public class UserBean extends BaseBean {
    public TokenBean TModel;

    public class TokenBean{
        public String UserTicket;//用户票据
    }

}
