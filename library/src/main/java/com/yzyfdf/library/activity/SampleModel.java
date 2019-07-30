package com.yzyfdf.library.activity;


import com.yzyfdf.library.rx.BaseRespose;

import io.reactivex.Observable;

/**
 *
 * @author MLRC-iOS-CI
 * @date 2018/5/8
 */
public class SampleModel implements SampleContract.Model {

    @Override
    public Observable<BaseRespose> get(String s) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("s", s);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"), GsonUtil.toJson(map));
//        return Api.getInstance().getApiService()
//                .getBillTJPieMonth(body)
//                .compose(RxHelper.handleResult())
//                ;
        return null;
    }
}
