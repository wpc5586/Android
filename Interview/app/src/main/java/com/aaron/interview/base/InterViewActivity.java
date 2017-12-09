package com.aaron.interview.base;

import android.os.Build;

import com.aaron.aaronlibrary.base.activity.BaseActivity;
import com.aaron.aaronlibrary.utils.MathUtils;
import com.aaron.interview.InterViewApplication;
import com.aaron.interview.R;
import com.aaron.interview.activity.LoginActivity;

/**
 * Activity基类
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
        actionbarView.getLayoutParams().height = MathUtils.dip2px(mContext, 55);
        // 设置状态栏背景颜色
        if (Build.VERSION.SDK_INT < 21)
            initSystemBar();
    }

    protected void logout() {
        popAllActivityExceptMain();
        startMyActivity(LoginActivity.class);
        InterViewApplication.getInstance().logout();
    }
}
