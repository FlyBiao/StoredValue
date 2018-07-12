package com.sw.android.storedvalue.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomUtils {

	/**
     * 产生4位随机数(0000-9999)
     * @return 4位随机数
     */
    public static String getFourRandom(){
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if(randLength<4){
          for(int i=1; i<=4-randLength; i++)
              fourRandom = "0" + fourRandom  ;
      }
        return fourRandom;
    }

    /**
     * 产生2位随机数(0000-9999)
     * @return 2位随机数
     */
    public static String getToFourRandom(){
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if(randLength<2){
            for(int i=1; i<=2-randLength; i++)
                fourRandom = "1" + fourRandom  ;
        }
        return fourRandom;
    }

    /**
     * 返回当前日期时间字符串<br>
     * 默认格式:MMddHHmmss
     *
     * @return String 返回当前字符串型日期时间
     */
    public static BigDecimal getCurrentTimeAsNumber() {
        String returnStr = String.valueOf(System.currentTimeMillis());
        SimpleDateFormat f = new SimpleDateFormat("yyMMddHH");
        Date date = new Date();
        returnStr = f.format(date);
        return new BigDecimal(returnStr);
    }

    /**
     * 返回当前日期时间字符串<br>
     * 默认格式:MMddHHmmss
     *
     * @return String 返回当前字符串型日期时间
     */
    public static BigDecimal getCurrentTimeMM() {
        String returnStr = String.valueOf(System.currentTimeMillis());
        SimpleDateFormat f = new SimpleDateFormat("MM");
        Date date = new Date();
        returnStr = f.format(date);
        return new BigDecimal(returnStr);
    }
}
