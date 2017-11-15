package com.aaron.aaronlibrary.easeui.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.aaron.aaronlibrary.base.fragment.BaseFragment;
import com.aaron.aaronlibrary.easeui.widget.EaseTitleBar;
import com.aaron.interview.R;
import com.aaron.interview.fragment.ChatFragment;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * 环信UI基类
 * Created by Aaron on 2017/11/7.
 */

public abstract class EaseBaseFragment extends BaseFragment{

    protected EaseTitleBar titleBar;
    protected InputMethodManager inputMethodManager;
    protected FrameLayout containerView;
    protected Bitmap bitmap;

    public void showTitleBar(){
        if(titleBar != null){
            titleBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideTitleBar(){
        if(titleBar != null){
            titleBar.setVisibility(View.GONE);
        }
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected abstract void initView();

    protected abstract void setUpView();

    protected abstract int getLayoutId();

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void findViews(View view) {
        //noinspection ConstantConditions
        containerView = view.findViewById(R.id.container);
        containerView.addView(View.inflate(mContext, getLayoutId(), null));
        titleBar = (EaseTitleBar) view.findViewById(R.id.title_bar);
    }

    @Override
    protected void init() {
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        initView();
        setUpView();
    }
}
