package com.sw.android.storedvalue.bean;


import com.sw.android.storedvalue.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description 支付方式结果Bean
 * Created at 2017/9/11 17:48
 * Version 1.0
 */

public class ResultPayCategoryBean extends BaseBean {

    public List<PayCategoryBea> TModel;


    public class PayCategoryBea implements Serializable{

        /**
         * CategoryId : 20
         * CategoryType : 4
         * Description : 银行卡支付
         * IsPractical : 1
         * Seq : 0
         */

        private int CategoryId;
        private int CategoryType;
        private String Description;
        private int IsPractical;
        private int Seq;

        public void setCategoryId(int CategoryId) {
            this.CategoryId = CategoryId;
        }

        public void setCategoryType(int CategoryType) {
            this.CategoryType = CategoryType;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public void setIsPractical(int IsPractical) {
            this.IsPractical = IsPractical;
        }

        public void setSeq(int Seq) {
            this.Seq = Seq;
        }

        public int getCategoryId() {
            return CategoryId;
        }

        public int getCategoryType() {
            return CategoryType;
        }

        public String getDescription() {
            return Description;
        }

        public int getIsPractical() {
            return IsPractical;
        }

        public int getSeq() {
            return Seq;
        }
    }
}
