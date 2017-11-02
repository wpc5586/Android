package com.aaron.aaronlibrary.base.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.aaron.interview.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Screen基类Fragment
 * Created by Aaron on 22.11.2017.
 */
public class BaseScreenFragment extends BaseFragment implements ScreenShotable {
    private View containerView;
    private Bitmap bitmap;

    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    protected void findViews(View view) {
        containerView = view.findViewById(R.id.container);
    }

    @Override
    protected void init() {

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
                BaseScreenFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

