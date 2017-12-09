package com.aaron.interview.fragment;

import android.view.View;
import android.widget.TextView;

import com.aaron.aaronlibrary.base.fragment.BaseScreenFragment;
import com.aaron.aaronlibrary.bean.BaseBean;
import com.aaron.aaronlibrary.http.BaseMap;
import com.aaron.aaronlibrary.http.PostCall;
import com.aaron.aaronlibrary.http.ServerUrl;
import com.aaron.interview.R;


/**
 * 主页Fragment
 * Created by Aaron on 22.11.2017.
 */
public class MainFragment extends BaseScreenFragment{
    public static final String CLOSE = "Close";
    public static final String MAIN = "Main";
    public static final String WORK = "Work";
    public static final String CHAT = "Chat";
    public static final String CONTACT = "Case";
    public static final String SETTING = "Setting";
    public static final String PARTY = "Party";
    public static final String MOVIE = "Movie";

    private TextView introTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void findViews(View view) {
        super.findViews(view);
        introTextView = view.findViewById(R.id.intro);
        view.findViewById(R.id.start).setOnClickListener(this);
    }

    @Override
    protected void init() {
        super.init();
//        getData();
    }

    private void getData() {
        PostCall.post(mContext, ServerUrl.getMainContent(), new BaseMap(), new PostCall.PostResponse<BaseBean>() {
            @Override
            public void onSuccess(int statusCode, byte[] responseBody, BaseBean bean) {

            }

            @Override
            public void onFailure(int statusCode, byte[] responseBody) {

            }
        }, new String[]{"", ""}, true, BaseBean.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.start:
                break;
        }
    }
}

