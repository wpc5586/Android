package com.aaron.interview.activity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.aaron.aaronlibrary.base.fragment.BaseFragment;
import com.aaron.aaronlibrary.easeui.ui.ContactListFragment;
import com.aaron.interview.R;
import com.aaron.interview.base.InterViewActivity;
import com.aaron.interview.fragment.ChatFragment;
import com.aaron.interview.fragment.MainFragment;
import com.aaron.interview.fragment.SettingFragment;
import com.aaron.interview.fragment.WorkFragment;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

/**
 * 主界面
 * Created by Aaron on 2017/10/30.
 */

public class MainActivity extends InterViewActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private int res = R.mipmap.ic_launcher;
    private LinearLayout linearLayout;
    private Map<String, BaseFragment> fragments;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {
        super.findView();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
    }

    @Override
    protected void init() {
        super.init();
        setActionbarVisibility(false);
        initFragments();
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, (ScreenShotable) fragments.get(MainFragment.MAIN), drawerLayout, this);
        registConnectionListener();
    }

    private void registConnectionListener() {
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected(final int errorCode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (errorCode == EMError.USER_REMOVED) {
                            showReloginDialog("显示帐号已经被移除，请重新登录");
                        } else if (errorCode == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                            showReloginDialog("显示帐号在其他设备登录，请重新登录");
                        } else {
                            if (NetUtils.hasNetwork(MainActivity.this))
                                System.out.println("~!~ 连接不到聊天服务器");
                            else
                                System.out.println("~!~ 当前网络不可用，请检查网络设置");
                        }
                    }
                });
            }
        });
    }

    /**
     * 显示重新登录对话框
     * @param content
     */
    private void showReloginDialog(String content) {
        if (Build.VERSION.SDK_INT >= 21)
            new AlertDialog.Builder(mContext).setTitle("提示")
                    .setMessage(content)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logout();
                        }
                    }).create().show();
        else
            new android.app.AlertDialog.Builder(mContext).setTitle("提示")
                    .setMessage(content)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logout();
                        }
                    }).create().show();

    }

    private void initFragments() {
        fragments = new HashMap<>();
        fragments.put(MainFragment.MAIN, new MainFragment());
        fragments.put(MainFragment.WORK, new WorkFragment());
        fragments.put(MainFragment.CHAT, new MainFragment());
        fragments.put(MainFragment.CASE, new MainFragment());
        fragments.put(MainFragment.SETTING, new MainFragment());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragments.get(MainFragment.MAIN))
                .commit();
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(MainFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(MainFragment.MAIN, R.drawable.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(MainFragment.WORK, R.drawable.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(MainFragment.CHAT, R.drawable.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(MainFragment.CASE, R.drawable.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(MainFragment.SETTING, R.drawable.icn_5);
        list.add(menuItem5);
//        SlideMenuItem menuItem6 = new SlideMenuItem(MainFragment.PARTY, R.drawable.icn_6);
//        list.add(menuItem6);
//        SlideMenuItem menuItem7 = new SlideMenuItem(MainFragment.MOVIE, R.drawable.icn_7);
//        list.add(menuItem7);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Aaron");
        toolbar.setBackgroundColor(getColorFromResuource(R.color.colorPrimary));
        toolbar.setTitleTextColor(getColorFromResuource(R.color.white));
        toolbar.setSubtitleTextColor(getColorFromResuource(R.color.white));
        toolbar.setDrawingCacheBackgroundColor(getColorFromResuource(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void setToolbarNavigationClickListener(View.OnClickListener onToolbarNavigationClickListener) {
                super.setToolbarNavigationClickListener(onToolbarNavigationClickListener);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.getDrawerArrowDrawable().setColor(getColorFromResuource(R.color.white)); // 图标颜色
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ScreenShotable replaceFragment(Resourceble slideMenuItem, ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.mipmap.ic_launcher ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        BaseFragment contentFragment = null;
        switch (slideMenuItem.getName()) {
            case MainFragment.MAIN:
                contentFragment = new MainFragment();
                break;
            case MainFragment.WORK:
                contentFragment = new WorkFragment();
                break;
            case MainFragment.CHAT:
                contentFragment = new ContactListFragment();
                break;
            case MainFragment.CASE:
                startMyActivity(LoginActivity.class);
                finish();
                logout();
                showToast("退出成功");
                break;
            case MainFragment.SETTING:
                contentFragment = new SettingFragment();
                break;
            default:
                contentFragment = new MainFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return (ScreenShotable) contentFragment;
    }

    private String currentTabIndex = MainFragment.MAIN;

    /**
     * 通过index显示Fragment
     *
     * @param index Fragment角标
     */
    private BaseFragment setIndexFragment(String index) {
        BaseFragment fragment = fragments.get(index);
        if (!currentTabIndex.equals(index)) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments.get(currentTabIndex));
            if (!fragment.isAdded()) {
                trx.add(R.id.container, fragment);
            }
            trx.show(fragment).commit();
        }
        currentTabIndex = index;
        return fragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case MainFragment.CLOSE:
                return screenShotable;
            default:
                return replaceFragment(slideMenuItem, screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
