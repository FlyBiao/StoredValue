package com.sw.android.storedvalue.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;


import com.sw.android.storedvalue.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：校验工具，比如：手机号，登录密码。。
 * 创建日期：2016/6/6 09:29
 * 版    本：1.0
 * 修订历史：
 * ================================================
 */
public class CheckUtil {
    /**
     * 手机号号码及为空判断
     *
     * @param context
     * @param str
     * @return
     */
    public static boolean phoneVerify(Context context, String str) {
        if (!AbStrUtils.isEmpty(str)) {
            if (AbRegUtils.isMobileNO(str)) { // 是否为手机号
                return true;
            } else {
                ToastUtils.show(context, context.getResources().getString(R.string.login_tel_sucs),
                        ToastUtils.CENTER);

            }
        } else {
            ToastUtils.show(context, context.getResources().getString(R.string.login_account_not_null), ToastUtils.CENTER);
        }
        return false;
    }

    /**
     * 手机号码按3－4－4格式化显示
     *
     * @author Evan 2015-8-28
     * @param num
     * @return
     */
    public static void formatPhoneNum(EditText edt, CharSequence num) {
        String contents = num.toString();
        int length = contents.length();
        if (length == 4) {
            if (contents.substring(3).equals(new String(" "))) { // -
                contents = contents.substring(0, 3);
                edt.setText(contents);
                edt.setSelection(contents.length());
            } else { // +
                contents = contents.substring(0, 3) + " " + contents.substring(3);
                edt.setText(contents);
                edt.setSelection(contents.length());
            }
        } else if (length == 9) {
            if (contents.substring(8).equals(new String(" "))) { // -
                contents = contents.substring(0, 8);
                edt.setText(contents);
                edt.setSelection(contents.length());
            } else {// +
                contents = contents.substring(0, 8) + " " + contents.substring(8);
                edt.setText(contents);
                edt.setSelection(contents.length());
            }
        }
    }

    /**
     * 密码验证
     * @param context
     * @param str
     * @return
     */
    public static boolean passwordVerify(Context context, String str) {
        int size = str.length();
        if (!AbStrUtils.isEmpty(str)) {
            if (size > 3 && size < 13) { // 密码长度4-12位
                return true;
            } else {
                ToastUtils.show(context, context.getResources().getString(R.string.login_pwd_sucs),
                        ToastUtils.CENTER);
            }
        } else {
            ToastUtils.show(context, context.getResources().getString(R.string.login_pwd_not_null), ToastUtils.CENTER);
        }
        return false;
    }

    /**
     * 验证文本
     * @param context
     * @param str
     * @return
     */
    public static boolean textVerify(Context context, String str) {
        if (!AbStrUtils.isEmpty(str)) {
            return true;
        } else {
            ToastUtils.show(context, context.getResources().getString(R.string.main_empty), ToastUtils.CENTER);
        }
        return false;
    }

    /**
     * 内容是否包含汉字
     *
     * @param str
     * @return
     */
    public static boolean stringFilter(String str) {
        String chinese = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";
        Pattern p = Pattern.compile(chinese);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 过滤输入框输入的汉字
     *
     * @param edit
     */
    public static void editTextFilterChinese(EditText edit) {
        InputFilter[] inputFilter = new InputFilter[] { new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // TODO Auto-generated method stub
                for (int i = start; i < end; i++) {
                    if (stringFilter(Character.toString(source.charAt(i)))) {
                        return "";
                    }
                }
                return null;
            }
        } };
        edit.setFilters(inputFilter);
    }

}
