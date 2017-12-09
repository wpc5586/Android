package com.aaron.interview.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aaron.aaronlibrary.base.activity.BaseActivity;
import com.aaron.aaronlibrary.base.fragment.BaseScreenFragment;
import com.aaron.interview.InterViewApplication;
import com.aaron.interview.R;
import com.aaron.interview.activity.MainActivity;

/**
 * 设置Fragment
 * Created by Aaron on 22.11.2017.
 */
public class SettingFragment extends BaseScreenFragment{

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void findViews(View view) {
        super.findViews(view);
        findViewAndSetListener(R.id.exit);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.exit:
                showAlertDialog("退出应用", "确定要退出应用吗？", "是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showAlertDialog("退出应用", "需要退出应用并退出账户吗？", "是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BaseActivity.popAllActivityExceptMain();
                                MainActivity.getInstance().finish();
                                MainActivity.instance = null;
                                InterViewApplication.getInstance().logout();
                            }
                        }, "否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BaseActivity.popAllActivityExceptMain();
                                MainActivity.getInstance().finish();
                                MainActivity.instance = null;
                            }
                        }, true);
                    }
                }, "否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, true);
                break;
        }
    }
}

