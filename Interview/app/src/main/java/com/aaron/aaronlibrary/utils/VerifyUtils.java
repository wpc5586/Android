package com.aaron.aaronlibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 各种验证工具
 *
 * @author Aaron
 */
public class VerifyUtils {

    /**
     * 手机验证
     */
    public static boolean isPhone(String phone) {
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

}
