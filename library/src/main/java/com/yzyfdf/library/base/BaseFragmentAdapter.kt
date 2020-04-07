package com.yzyfdf.library.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import com.yzyfdf.library.utils.ListUtil
import java.util.*

/**
 * 该类内的每一个生成的 Fragment 都将保存在内存之中，
 * 因此适用于那些相对静态的页，数量也比较少的那种；
 * 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，
 * 应该使用FragmentStatePagerAdapter。
 */
open class BaseFragmentAdapter : FragmentPagerAdapter {

    private var fragmentList: List<Fragment>? = ArrayList()
    private var mTitles: List<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        this.fragmentList = fragmentList
    }

    constructor(fm: FragmentManager, fragmentList: List<Fragment>, mTitles: List<String>) : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        this.mTitles = mTitles
        setFragments(fm, fragmentList, mTitles)
    }

    //刷新fragment
    private fun setFragments(fm: FragmentManager, fragments: List<Fragment>, mTitles: List<String>) {
        this.mTitles = mTitles
        if (this.fragmentList != null) {
            val ft: FragmentTransaction? = fm.beginTransaction()
            for (f in this.fragmentList!!) {
                ft!!.remove(f)
            }
            ft!!.commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        this.fragmentList = fragments
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (!ListUtil.isEmpty(mTitles)) mTitles!![position] else ""
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

}
