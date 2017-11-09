package com.aaron.interview.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.aaron.aaronlibrary.base.fragment.BaseFragment;
import com.aaron.interview.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * 设置Fragment
 * Created by Aaron on 22.11.2017.
 */
public class SettingFragment extends BaseFragment implements ScreenShotable {

    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;

    public static SettingFragment newInstance(int resId) {
        SettingFragment mainFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    protected void findViews(View view) {
        this.containerView = view.findViewById(R.id.container);
        mImageView = view.findViewById(R.id.image_content);
    }

    @Override
    protected void init() {
//        res = getArguments().getInt(Integer.class.getName());
        res = R.mipmap.ic_launcher;
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setImageResource(res);
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                SettingFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

