package com.yzyfdf.libsample.ui

import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yzyfdf.library.base.BaseActivity
import com.yzyfdf.library.base.BaseFragment
import com.yzyfdf.library.base.BaseModel
import com.yzyfdf.library.base.BasePresenter
import com.yzyfdf.library.model.TabEntity
import com.yzyfdf.libsample.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : BaseActivity<BasePresenter<*, *>, BaseModel>() {

    private val fragmentList = ArrayList<BaseFragment<*,*>>()
    private val titleList = ArrayList<String>()
    private val mIconUnselectIds = ArrayList<Int>()
    private val mIconSelectIds = ArrayList<Int>()

    private var mHomeFragment: BaseFragment<*,*>? = null
    private var mHomeFragment2: BaseFragment<*,*>? = null

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initPresenter() {
    }

    override fun initView() {
        initFragment()
        initTabLayout()
    }

    private fun initFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment(1)
            fragmentList.add(mHomeFragment!!)
            titleList.add(mResources.getString(R.string.main_tab_info))
            mIconSelectIds.add(R.mipmap.ic_launcher)
            mIconUnselectIds.add(R.mipmap.ic_launcher_round)
        }

        if (mHomeFragment2 == null) {
            mHomeFragment2 = HomeFragment(2)
            fragmentList.add(mHomeFragment2!!)
            titleList.add(mResources.getString(R.string.main_tab_info))
            mIconSelectIds.add(R.mipmap.ic_launcher)
            mIconUnselectIds.add(R.mipmap.ic_launcher_round)
        }
    }

    private fun initTabLayout() {
        val tabEntitys = ArrayList<CustomTabEntity>()
        for (i in titleList.indices) {
            tabEntitys.add(TabEntity(titleList[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }

        tabLayout.setTabData(tabEntitys, supportFragmentManager, R.id.main_container, fragmentList)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {

            }

            override fun onTabReselect(position: Int) {
                val fragment = fragmentList[position]
                fragment.onPagerReSelected()
            }
        })
    }
}
