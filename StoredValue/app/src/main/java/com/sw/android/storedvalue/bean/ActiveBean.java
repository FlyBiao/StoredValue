package com.sw.android.storedvalue.bean;

import com.sw.android.storedvalue.base.BaseBean;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：激活posBean
 * 创建日期：2016/11/8 11:43
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class ActiveBean extends BaseBean {
    public TokenBean TModel;//用户票据

    public class TokenBean {
        public String UserTicket;//用户票据

        public String getUserTicket() {
            return UserTicket;
        }

        public void setUserTicket(String userTicket) {
            UserTicket = userTicket;
        }
    }
}
