package com.yzyfdf.libsample.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.yzyfdf.library.base.BaseFragment;
import com.yzyfdf.libsample.R;
import com.yzyfdf.libsample.adapter.TabAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author sjj , 2019/6/5 16:26
 * //todo
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private boolean isFirst = true;

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    private void getData() {
        isFirst = false;
        ArrayList<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            fragments.add(InfoFragment.getInstance(true));
            titles.add("标题" + (i + 1));
        }

        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(tabAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mTabLayout.setViewPager(mViewPager, titles.toArray(new String[titles.size()]));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        System.out.println("hidden = " + hidden);
        if (isFirst && !hidden) {
            getData();
        }
    }
}
