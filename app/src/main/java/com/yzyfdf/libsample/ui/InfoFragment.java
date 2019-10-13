package com.yzyfdf.libsample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.ItemDecoration.LuDividerDecoration;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.yzyfdf.library.base.BaseFragment;
import com.yzyfdf.library.base.BaseRvAdapter;
import com.yzyfdf.library.rx.BaseRxSubscriber;
import com.yzyfdf.library.rx.RxHelper;
import com.yzyfdf.libsample.R;
import com.yzyfdf.libsample.adapter.InfoAdapter;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * @author sjj , 2019/4/25 14:17
 * 首页feed
 */
public class InfoFragment extends BaseFragment {

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.recyclerView)
    LuRecyclerView mRecyclerView;

    private InfoAdapter mAdapter;

    /**
     * 第一次请求数据
     */
    private boolean isFirst = true;
    /**
     * 页面绘制完成
     */
    private boolean isViewed = false;
    /**
     * 用户可见
     */
    private boolean mIsVisibleToUser = false;

    /**
     * 是否需要懒加载 首页的需要
     */
    private boolean needLazy = true;


    private int pageNo = 1;
    private final int pageSize = 20;
    private boolean more = true;

    private OnInitHeaderListener mOnInitHeaderListener;


    public static InfoFragment getInstance(boolean needLazy) {
        InfoFragment fragment = new InfoFragment();
        fragment.needLazy = needLazy;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        pageNo = 1;
        mHolder.showLoading();

        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mRecyclerView.setNoMore(false);
                getData();
            }
        });

        initLuRecyclerView(mRecyclerView, new LinearLayoutManager(mContext));
        mAdapter = new InfoAdapter(mContext, mRxManager);
        LuRecyclerViewAdapter adapter = new LuRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(adapter);

        mAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(String s, int i) {
                if (i % 2 == 1) {
                    needLogin(s, i);
                } else {
                    needPerm(s, i);
                }
            }
        });

        //初始化头部 由外部自定义
        if (mOnInitHeaderListener != null) {
            View view = mOnInitHeaderListener.onInit(mRecyclerView);
            adapter.addHeaderView(view);
        }

        LuDividerDecoration divider = new LuDividerDecoration.Builder(getContext(), adapter)
                .setHeight(R.dimen.delive_height)
                .setLeftPadding(R.dimen.dp16).setRightPadding(R.dimen.dp16)
                .setColorResource(R.color.line_color)
                .build();
        mRecyclerView.addItemDecoration(divider);

        mRecyclerView.setOnLoadMoreListener(() -> {
            if (more) {
                pageNo++;
                getData();
            } else {
                mRecyclerView.setNoMore(true);
            }
        });
        mRecyclerView.setOnNetWorkErrorListener(() -> getData());

        isViewed = true;
        //单页面使用时 不需要懒加载
        if (!needLazy || (isFirst && mIsVisibleToUser)) {
            getData();
        }
    }

//    @PermissionsRequest(value = {Manifest.permission.CAMERA,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_PHONE_STATE})
    private void needPerm(String s, int i) {
        showShortToast("夜间模式");
        startActivity(new Intent(mContext, BaseWebActivity.class)
                .putExtra("url", "https://view.officeapps.live.com/op/view.aspx?src=https://oss.neets.cc/test/xx.docx"));
//        Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Mobile Safari/537.36
//        Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36
    }

//    @LoginIntercept
    private void needLogin(String s, int i) {
        showShortToast("默认主题");
        startActivity(new Intent(mContext, BaseWebActivity.class)
                .putExtra("url", "https://view.officeapps.live.com/op/view.aspx?src=https://oss.neets.cc/test/1.xlsx"));
    }

    /**
     * 获取页面数据
     */
    private void getData() {
        isFirst = false;

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < pageSize; i++) {
            list.add("内容" + (i + 1));
        }

        //多线程
        System.out.println("InfoFragment.getData" + "----" + System.currentTimeMillis());
        Flowable.just("内容")
                .flatMap((Function<String, Publisher<String>>) s ->
                        Flowable.range(1, pageSize)
                                .map(integer -> {
                                    System.out.println("integer = " + integer);
                                    return integer;
                                })
                                //                                .concatMapEager//按顺序执行
                                .flatMap((Function<Integer, Publisher<String>>) i ->
                                        Flowable.just(i)
                                                .observeOn(Schedulers.io())
                                                .map(index -> {
                                                    Thread.sleep(300);
                                                    System.out.println("数据: index = " + index + "，线程：" + Thread.currentThread().getName());
                                                    if (index == 6) {
                                                        throw new NullPointerException("报个错");
                                                    }
                                                    return s + index;
                                                })
                                                .onErrorReturnItem("出错了"))
                                .observeOn(AndroidSchedulers.mainThread())
                                .map(str -> {
                                    //每个任务单独更新ui
                                    System.out.println("ui: s = " + str);
                                    return str;
                                })
                                .observeOn(Schedulers.io()))
                .toSortedList(String::compareTo)//自己排序
                .toObservable()
                .compose(RxHelper::logAndThread)
                .subscribe(new BaseRxSubscriber<List<String>>(mContext, mRxManager) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        System.out.println("InfoFragment.onSubscribe" + "----" + System.currentTimeMillis());
                    }

                    @Override
                    protected void servicesError(HttpException e) {

                    }


                    @Override
                    protected void _onNext(List<String> strings) {
                        System.out.println("InfoFragment._onNext" + "----" + System.currentTimeMillis());
                        setData(strings);
                    }

                    @Override
                    protected void _onError(String message) {
                        System.out.println("message = " + message);
                    }
                });


        //        Observable.just("内容")
        //                .flatMap(new Function<String, ObservableSource<String>>() {
        //                    @Override
        //                    public ObservableSource<String> apply(String s) throws Exception {
        //                        return Observable.range(1, pageSize)
        //                                .map(new Function<Integer, Integer>() {
        //                                    @Override
        //                                    public Integer apply(Integer integer) throws Exception {
        //                                        System.out.println("integer = " + integer);
        //                                        return integer;
        //                                    }
        //                                })
        ////                                .concatMapEager(new Function<Integer, ObservableSource<String>>() {//按顺序
        //                                .flatMap(new Function<Integer, ObservableSource<String>>() {
        //                                    @Override
        //                                    public ObservableSource<String> apply(Integer i) throws Exception {
        //                                        return Observable.just(i)
        //                                                .observeOn(Schedulers.io())
        //                                                .map(new Function<Integer, String>() {
        //                                                    @Override
        //                                                    public String apply(Integer index) throws Exception {
        //                                                        Thread.sleep(300);
        //                                                        System.out.println("数据: index = " + index + "，线程：" + Thread.currentThread().getName());
        //                                                        if (index == 6) {
        //                                                            throw new NullPointerException("报个错");
        //                                                        }
        //                                                        return s + index;
        //                                                    }
        //                                                }).onErrorReturnItem("出错了1");
        //                                    }
        //                                })
        //                                .observeOn(AndroidSchedulers.mainThread())
        //                                .map(new Function<String, String>() {
        //                                    @Override
        //                                    public String apply(String s) throws Exception {
        //                                        //每个任务单独更新ui
        //                                        System.out.println("ui: s = " + s);
        //                                        return s;
        //                                    }
        //                                }).observeOn(Schedulers.io());
        //                    }
        //                }).toList()
        //                .toObservable()
        //                .compose(RxHelper::logAndThread)
        //                .subscribe(new BaseRxSubscriber<List<String>>(mContext, mRxManager) {
        //                    @Override
        //                    public void onSubscribe(Disposable d) {
        //                        super.onSubscribe(d);
        //                        System.out.println("InfoFragment.onSubscribe" + "----" + System.currentTimeMillis());
        //                    }
        //
        //                    @Override
        //                    protected void servicesError(HttpException e) {
        //
        //                    }
        //
        //
        //                    @Override
        //                    protected void _onNext(List<String> strings) {
        //                        System.out.println("InfoFragment._onNext" + "----" + System.currentTimeMillis());
        //                        setData(strings);
        //                    }
        //
        //                    @Override
        //                    protected void _onError(String message) {
        //                        System.out.println("message = " + message);
        //                    }
        //                });


    }

    private void setData(List<String> list) {

        loadComplete();

        if (pageNo == 1) {
            mAdapter.refresh(list);
            if (list == null || list.size() == 0) {
                mHolder.showEmpty();
            } else {
                mHolder.showLoadSuccess();
            }
        } else {
            mAdapter.add(list);
            mHolder.showLoadSuccess();
            if (list == null || list.size() == 0) {
                mRecyclerView.setNoMore(true);
            }
        }

        //        more = bean.isMore();
        mRecyclerView.setNoMore(!more);
    }

    private void loadComplete() {
        mSwipeRefresh.setRefreshing(false);
        mRecyclerView.refreshComplete(pageSize);
    }

    /**
     * 页面错误重试
     */
    @Override
    protected void retry() {
        super.retry();
        getData();
    }

    /**
     * 首页底部标签被重复点击时 滑到顶部
     */
    @Override
    public void onPagerReSelected() {
        if (mRecyclerView != null && mRecyclerView.getChildCount() > 0) {
            if (mRecyclerView.canScrollVertically(-1)) {
                //还能继续上滑
                //滑到顶部
                mRecyclerView.scrollToPosition(0);
            }
        }
    }

    /**
     * 初始化header
     */
    public interface OnInitHeaderListener {
        View onInit(LuRecyclerView recyclerView);
    }

    /**
     * activity嵌套时  需要添加头部  用这个
     */
    public void setOnInitHeaderListener(OnInitHeaderListener onInitHeaderListener) {
        mOnInitHeaderListener = onInitHeaderListener;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        if (isFirst && mIsVisibleToUser && isViewed) {
            getData();
        }
    }

}
