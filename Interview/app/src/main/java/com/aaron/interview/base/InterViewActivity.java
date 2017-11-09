package com.aaron.interview.base;

import com.aaron.aaronlibrary.base.activity.BaseActivity;
import com.aaron.interview.R;
import com.aaron.interview.activity.LoginActivity;

/**
 * 基类
 * Created by Aaron on 2017/10/30.
 */

public class InterViewActivity extends BaseActivity {
    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void init() {
        super.init();
        setActionbarBackground(R.color.colorPrimary);
    }

    protected void logout() {
        popAllActivityExceptMain();
        startMyActivity(LoginActivity.class);
        InterViewApplication.logout();
    }
}
