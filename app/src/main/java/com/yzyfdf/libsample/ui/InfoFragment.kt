package com.yzyfdf.libsample.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.yzyfdf.library.base.BaseFragment
import com.yzyfdf.library.base.BaseModel
import com.yzyfdf.library.base.BasePresenter
import com.yzyfdf.library.rx.RxHelper
import com.yzyfdf.library.rx.subscribe2
import com.yzyfdf.library.utils.showErrTip
import com.yzyfdf.library.utils.showSuccessTip
import com.yzyfdf.library.utils.showToast
import com.yzyfdf.library.view.DividerDecoration
import com.yzyfdf.library.view.globalloading.Gloading
import com.yzyfdf.libsample.R
import com.yzyfdf.libsample.adapter.InfoAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_info.*
import java.util.*

/**
 * @author sjj , 2019/4/25 14:17
 * 首页feed
 */
class InfoFragment : BaseFragment<BasePresenter<*, *>, BaseModel>() {

    var holder: Gloading.Holder? = null
    private var mAdapter: InfoAdapter? = null

    private var pageNo = 1
    private val pageSize = 20
    private val more = true

    override val layoutResource = R.layout.fragment_info

    override fun initPresenter() {

    }

    override fun initView() {

        holder = Gloading.default.wrap(recyclerView)
                .withRetry { getData() }
        holder?.showLoading()

        pageNo = 1

        swipe_refresh?.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh?.setOnRefreshListener {
            pageNo = 1
            getData()
        }

        recyclerView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = InfoAdapter(mContext!!)
        recyclerView.adapter = mAdapter


        val divider = DividerDecoration.Builder(context!!)
                .setHeight(R.dimen.delive_height)
                .setLeftPadding(R.dimen.dp16)
                .setRightPadding(R.dimen.dp16)
                .setColorResource(R.color.line_color)
                .build()
        //        mRecyclerView.addItemDecoration(divider);

    }

    override fun initListener() {

        mAdapter?.setOnItemClickListener { adapter, view, position, item ->
            when (position % 4) {
                0 -> showToast("点击了$position")
                1 -> showSuccessTip("成功$position")
                2 -> {
                    showErrTip("失败$position")
                    holder?.showLoadFailed()
                }
                else -> {
                    mContext?.let {
                        BaseWebActivity.go(it, "")
                    }
                }
            }
        }
    }

    override fun lazyInit4Vp() {
        getData()
    }

    /**
     * 获取页面数据
     */
    private fun getData() {

        val list = ArrayList<String>()
        for (i in 0 until pageSize) {
            list.add("内容" + (i + 1))
        }

        //多线程
        println("InfoFragment.getData" + "----" + System.currentTimeMillis())
        Flowable.just("内容")
                .flatMap { s ->
                    Flowable.range(1, pageSize)
                            .map { integer ->
                                println("integer = $integer")
                                integer
                            }
                            //                                .concatMapEager//按顺序执行
                            .flatMap { i ->
                                Flowable.just(i)
                                        .observeOn(Schedulers.io())
                                        .map { index ->
                                            Thread.sleep(300)
                                            println("数据: index = " + index + "，线程：" + Thread.currentThread().name)
                                            if (index == 6) {
                                                throw NullPointerException("报个错")
                                            }
                                            s + index
                                        }
                                        .onErrorReturnItem("出错了")
                            }
                            .observeOn(AndroidSchedulers.mainThread())
                            .map { str ->
                                //每个任务单独更新ui
                                println("ui: s = $str")
                                str
                            }
                            .observeOn(Schedulers.io())
                }
                .toList()
                //            .toSortedList { obj, anotherString -> obj.compareTo(anotherString) } //自己排序
                .toObservable()
                .compose(RxHelper::threadOnly)
                .subscribe2(mRxManager, {
                    println("InfoFragment._onNext" + "----" + System.currentTimeMillis())
                    setData(it)
                }, {
                    println("message = $it")
                    holder?.showLoadFailed()
                    loadComplete()
                })

    }

    private fun setData(list: List<String>?) {

        loadComplete()

        if (pageNo == 1) {
            mAdapter?.refresh(list)
            if (list == null || list.isEmpty()) {
                holder?.showEmpty()
            } else {
                holder?.showLoadSuccess()
            }
        } else {
            mAdapter?.add(list)
            holder?.showLoadSuccess()
            if (list == null || list.isEmpty()) {
            }
        }

    }

    private fun loadComplete() {
        swipe_refresh!!.isRefreshing = false
    }

    /**
     * 首页底部标签被重复点击时 滑到顶部
     */
    override fun onPagerReSelected() {
        if (recyclerView != null && recyclerView!!.childCount > 0) {
            if (recyclerView!!.canScrollVertically(-1)) {
                //还能继续上滑
                //滑到顶部
                recyclerView!!.scrollToPosition(0)
            }
        }
    }
}
