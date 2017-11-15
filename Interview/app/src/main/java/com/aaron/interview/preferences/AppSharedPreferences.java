package com.aaron.interview.preferences;

import android.text.TextUtils;

import com.aaron.aaronlibrary.base.utils.BaseSharedPreferences;

/**
 * 应用信息SharedPreferences
 * Created by wpc on 2016/12/13 0028.
 */
public class AppSharedPreferences extends BaseSharedPreferences {

    private static final String FIRST_START = "firstStart";

    private static final String CHANNEL_ID = "channelId"; // 百度云推送设备ID

    public static AppSharedPreferences instance = null;

    public static AppSharedPreferences getInstance() {
        if (instance == null)
            instance = new AppSharedPreferences();

        return instance;
    }

    @Override
    public String getFilename() {
        return "appInfo";
    }

    public void setStarted() {
        set(FIRST_START, "1");
    }

    public void resetStarted() {
        set(FIRST_START, "");
    }

    public String getStarted() {
        String started;
        started = get(FIRST_START);
        return started;
    }

    public boolean isStarted() {
        return !TextUtils.isEmpty(getStarted());
    }

    public void setChannelId(String channelId) {
        set(CHANNEL_ID, channelId);
    }

    public String getChannelId() {
        return get(CHANNEL_ID);
    }
}
