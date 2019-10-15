package com.yzyfdf.libsample.ui;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yzyfdf.library.base.BaseActivity;
import com.yzyfdf.library.base.BaseFragment;
import com.yzyfdf.library.model.TabEntity;
import com.yzyfdf.libsample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    CommonTabLayout tabLayout;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private ArrayList<Integer> mIconUnselectIds = new ArrayList<>();
    private ArrayList<Integer> mIconSelectIds = new ArrayList<>();

    private HomeFragment mHomeFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initFragment();
        initTabLayout();
    }

    private void initFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.getInstance();
            fragmentList.add(mHomeFragment);
            titleList.add(mResources.getString(R.string.main_tab_info));
            mIconSelectIds.add(R.mipmap.ic_launcher);
            mIconUnselectIds.add(R.mipmap.ic_launcher_round);
        }
    }

    private void initTabLayout() {
        ArrayList<CustomTabEntity> tabEntitys = new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
            tabEntitys.add(new TabEntity(titleList.get(i), mIconSelectIds.get(i), mIconUnselectIds.get(i)));
        }

        tabLayout.setTabData(tabEntitys, this, R.id.main_container, fragmentList);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {
                Fragment fragment = fragmentList.get(position);
                if (fragment instanceof BaseFragment) {
                    ((BaseFragment) fragment).onPagerReSelected();
                }
            }
        });
    }
}
