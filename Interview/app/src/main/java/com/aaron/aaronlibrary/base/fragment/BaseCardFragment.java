package com.aaron.aaronlibrary.base.fragment;


import android.support.v7.widget.CardView;
import android.view.View;

/**
 * BaseCardFragment
 * @author wangpc
 */
public abstract class BaseCardFragment extends BaseFragment{

    private CardView mCardView;

    @Override
    protected void findViews(View view) {
        mCardView = (CardView) findViewById(getCardViewId());
    }

    protected abstract int getCardViewId();
    public CardView getCardView() {
        return mCardView;
    }
}
