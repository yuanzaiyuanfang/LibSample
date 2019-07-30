package com.yzyfdf.library.rx;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxHelper {

    private static Gson gson = new Gson();
    private static String sTag = "返回值";

    public static <T> ObservableSource<T> logAndThread(Observable<T> upstream) {
        return upstream.map(t -> {
            LogUtils.dTag(sTag, gson.toJson(t));
            return t;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    public static <T> ObservableSource<T> logOnly(Observable<T> upstream) {
        return upstream.map(t -> {
            LogUtils.dTag(sTag, gson.toJson(t));
            return t;
        });
    }

    public static <T> ObservableSource<T> threadOnly(Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public static <T> SingleSource<T> logAndThread(Single<T> upstream){
        return upstream.map(t -> {
            LogUtils.dTag(sTag, gson.toJson(t));
            return t;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SingleSource<T> logOnly(Single<T> upstream){
        return upstream.map(t -> {
            LogUtils.dTag(sTag, gson.toJson(t));
            return t;
        });
    }

    public static <T> SingleSource<T> threadOnly(Single<T> upstream){
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }




    /**
     * 对服务器返回数据进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> handleResult() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.flatMap(new Function<T, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(T tBaseRespose) throws Exception {
                        LogUtils.dTag("返回值", gson.toJson(tBaseRespose));
                        //如果有自定义的返回码,正确错误都是同样的返回格式,可以在这里处理
//                        if (tBaseRespose.success()) {
                        return createData(tBaseRespose);
//                        } else {
//                            return Observable.error(new ServerException(tBaseRespose.getMessage()));
//                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {

            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(data);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }
}
