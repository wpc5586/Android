package com.aaron.interview.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.aaronlibrary.base.fragment.BaseScreenFragment;
import com.aaron.aaronlibrary.http.BaseMap;
import com.aaron.aaronlibrary.http.PostCall;
import com.aaron.aaronlibrary.http.ServerUrl;
import com.aaron.aaronlibrary.utils.AppInfo;
import com.aaron.aaronlibrary.utils.ImageUtils;
import com.aaron.aaronlibrary.utils.MathUtils;
import com.aaron.aaronlibrary.widget.viewpagercards.CardAdapter;
import com.aaron.aaronlibrary.widget.viewpagercards.CardFragmentPagerAdapter;
import com.aaron.aaronlibrary.widget.viewpagercards.ShadowTransformer;
import com.aaron.interview.R;
import com.aaron.interview.activity.AddResumeActivity;
import com.aaron.interview.bean.WorkBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作Fragment
 * Created by Aaron on 22.11.2017.
 */
public class WorkFragment extends BaseScreenFragment {

    private ViewPager viewPager;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private List<View> views = new ArrayList<>();
    private List<WorkBean.Obj.WorkList> datas;
    private int viewPagerHeight;

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
        return R.layout.fragment_work;
    }

    @Override
    protected void findViews(View view) {
        super.findViews(view);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void init() {
        super.init();
        getData();
    }

    private void getData() {
        PostCall.post(mContext, ServerUrl.getWorkResumeList(), new BaseMap(), new PostCall.PostResponse<WorkBean>() {
            @Override
            public void onSuccess(int statusCode, byte[] responseBody, WorkBean bean) {
                datas = bean.getObj().getResumeList();
                initViewPager();
            }

            @Override
            public void onFailure(int statusCode, byte[] responseBody) {

            }
        }, new String[]{"", ""}, true, WorkBean.class);
    }

    private void initViewPager() {
        viewPagerHeight = (int) (AppInfo.getScreenWidthOrHeight(mContext, false) / 1.5f);
        viewPager.getLayoutParams().height = viewPagerHeight;
        CardPagerAdapter mCardAdapter = new CardPagerAdapter();
        mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
    }

    private void addResume() {
        startMyActivity(AddResumeActivity.class);
    }

    public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

        private float mBaseElevation;

        public CardPagerAdapter() {
            if (datas == null)
                datas = new ArrayList<>();
        }

        public float getBaseElevation() {
            return mBaseElevation;
        }

        @Override
        public CardView getCardViewAt(int position) {
            return (CardView) views.get(position);
        }

        @Override
        public int getCount() {
            return datas.size() + 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.item_work, container, false);
            container.addView(view);
            bind(position, view);
            CardView cardView = (CardView) view.findViewById(R.id.cardView);
            if (mBaseElevation == 0) {
                mBaseElevation = cardView.getCardElevation();
            }
            cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
            if (views.size() > position)
                views.set(position, cardView);
            else
                views.add(cardView);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            views.set(position, null);
        }

        private void bind(int position, View view) {
            RelativeLayout rlContainer = view.findViewById(R.id.rl_container);
            RelativeLayout rlContent = view.findViewById(R.id.rl_content);
            TextView titleTextView = view.findViewById(R.id.title);
            TextView dateTextView = view.findViewById(R.id.date);
            ImageView addView = view.findViewById(R.id.add);
            ImageView bgView = view.findViewById(R.id.background);
            if (position == getCount() - 1) {
                rlContent.setVisibility(View.GONE);
                bgView.setVisibility(View.GONE);
                addView.setVisibility(View.VISIBLE);
                view.getBackground().setAlpha(150);
                addView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addResume();
                    }
                });
            } else {
                view.getBackground().setAlpha(255);
                rlContent.setVisibility(View.VISIBLE);
                bgView.setVisibility(View.VISIBLE);
                addView.setVisibility(View.GONE);
                WorkBean.Obj.WorkList item = datas.get(position);
                titleTextView.setText(item.getResumeTitle());
                dateTextView.setText(item.getCreateDate());
                ImageUtils.loadImage(mContext, item.getImageUrl(), bgView, false);
            }
        }
    }
}

