package com.yzyfdf.library.sample;


import com.yzyfdf.library.rx.BaseRespose;

import io.reactivex.Observable;


public class SampleModel implements SampleContract.Model {

    @Override
    public Observable<BaseRespose> get(String s) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("s", s);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"), GsonUtil.toJson(map));
//        return Api.getInstance().getApiService()
//                .getBillTJPieMonth(body)
//                .compose(RxHelper.logAndThread())
//                ;
        return null;
    }
}
