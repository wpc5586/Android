package com.aaron.interview.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.aaron.aaronlibrary.base.app.CrashApplication;
import com.aaron.aaronlibrary.utils.Constants;
import com.aaron.interview.bean.LoginBean;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * InterViewApplication
 * Created by Aaron on 2017/11/2.
 */

public class InterViewApplication extends CrashApplication {

    public static LoginBean loginBean;

    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(Constants.DEBUGABLE);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static LoginBean getLoginBean() {
        return loginBean;
    }

    public static void login(LoginBean loginBean) {
        InterViewApplication.loginBean = loginBean;
    }

    public static void logout() {
        loginBean = null;
        new Thread(){
            @Override
            public void run() {
                EMClient.getInstance().logout(true);
            }
        }.start();
    }
}
