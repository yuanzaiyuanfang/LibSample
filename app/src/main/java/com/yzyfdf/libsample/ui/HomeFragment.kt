package com.yzyfdf.libsample.ui

import com.flyco.tablayout.listener.OnTabSelectListener
import com.yzyfdf.library.base.BaseFragment
import com.yzyfdf.library.base.BaseModel
import com.yzyfdf.library.base.BasePresenter
import com.yzyfdf.libsample.R
import com.yzyfdf.libsample.adapter.TabAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * @author sjj , 2019/6/5 16:26
 * //todo
 */
class HomeFragment(val Index: Int) : BaseFragment<BasePresenter<*, *>, BaseModel>() {


    override val layoutResource: Int
        get() = R.layout.fragment_home

    override fun initPresenter() {

    }

    override fun initView() {
    }

    override fun lazyInit4Fl() {
        getData()
    }

    private fun getData() {
        val fragments = ArrayList<BaseFragment<*, *>>()
        val titles = ArrayList<String>()

        for (i in 0..4) {
            fragments.add(InfoFragment())
            titles.add("标题" + (i + 1))
        }

        val tabAdapter = TabAdapter(childFragmentManager, fragments)
        viewPager.adapter = tabAdapter
        viewPager.offscreenPageLimit = fragments.size
        tabLayout.setViewPager(viewPager, titles.toTypedArray())
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {

            }

            override fun onTabReselect(position: Int) {
                fragments[position].onPagerReSelected()
            }
        })

    }


}
