package com.yzyfdf.library.base


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

/**
 * FragmentStatePagerAdapter 和前面的 FragmentPagerAdapter 一样，是继承子 PagerAdapter。
 * 但和 FragmentPagerAdapter 不一样的是，
 * 正如其类名中的 'State' 所表明的含义一样，
 * 该 PagerAdapter 的实现将只保留当前页面，
 * 当页面离开视线后，就会被消除，释放其资源；
 * 而在页面需要显示时，生成新的页面(就像 ListView 的实现一样)。
 * 这么实现的好处就是当拥有大量的页面时，不必在内存中占用大量的内存。
 */
class BaseFragmentStateAdapter : FragmentStatePagerAdapter {

    private var fragmentList: List<Fragment> = ArrayList()
    private var mTitles: List<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        this.fragmentList = fragmentList
    }

    constructor(fm: FragmentManager, fragmentList: List<Fragment>, mTitles: List<String>) : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        this.mTitles = mTitles
        this.fragmentList = fragmentList
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles?.get(position) ?: ""
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

}
