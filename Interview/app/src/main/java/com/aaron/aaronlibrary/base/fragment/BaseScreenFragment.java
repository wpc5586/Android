package com.aaron.aaronlibrary.base.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.widget.FrameLayout;

import com.aaron.interview.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Screen基类Fragment
 * Created by Aaron on 22.11.2017.
 */
public abstract class BaseScreenFragment extends BaseFragment implements ScreenShotable {
    protected View containerView;
    protected Bitmap bitmap;

    protected abstract int getLayoutId();

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_base_screen;
    }

    @Override
    protected void findViews(View view) {
        containerView = view.findViewById(R.id.container);
        ((FrameLayout) containerView).addView(View.inflate(mContext, getLayoutId(), null));
    }

    @Override
    protected void init() {

    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                final Bitmap newBitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Canvas canvas = new Canvas(newBitmap);
                        containerView.draw(canvas);
                        bitmap = newBitmap;
                    }
                });
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

