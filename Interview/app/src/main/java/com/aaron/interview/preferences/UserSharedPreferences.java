package com.aaron.interview.preferences;

import android.text.TextUtils;

import com.aaron.aaronlibrary.base.utils.BaseSharedPreferences;
import com.aaron.interview.bean.LoginBean;
import com.google.gson.Gson;

/**
 * 用户信息SharedPreferences
 * Created by wpc on 2017/11/28 0028.
 */
public class UserSharedPreferences extends BaseSharedPreferences {

    public static final String LOGIN_DATA = "loginData";// 用户成功登录的信息

    public static final String LOGIN_USER_NAME = "loginUserName";// 用户输入的用户名（userId是引导接口返回的用户Id）

    public static final String LOGIN_TYPE = "loginType";// 用户最后一次登录方式（OA或4A）

    public static final String LOGIN_TYPE_OA = "oa";// OA

    public static final String LOGIN_TYPE_4A = "4a";// 4A

    public static UserSharedPreferences instance = null;

    public static UserSharedPreferences getInstance() {
        if (instance == null)
            instance = new UserSharedPreferences();

        return instance;
    }

    @Override
    public String getFilename() {
        return USER_INFO + "_" + getUserId();
    }

    public void setLoginData(LoginBean bean) {
        String data = new Gson().toJson(bean);
        set(LOGIN_DATA, data);
    }

    public LoginBean getLoginData() {
        String data = "";
        data = get(LOGIN_DATA);
        return new Gson().fromJson(data, LoginBean.class);
    }

    public void clean() {
        setUserId("");
        setPassWord("");
        setLoginData(new LoginBean());
    }
}
