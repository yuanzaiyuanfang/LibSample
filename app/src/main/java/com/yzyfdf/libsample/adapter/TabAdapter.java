package com.yzyfdf.libsample.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.yzyfdf.library.base.BaseFragmentAdapter;

import java.util.List;

/**
 * @author sjj , 2019/6/10 17:50
 * //todo
 */
public class TabAdapter extends BaseFragmentAdapter {

    public TabAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm, fragmentList);
    }
}

