package com.yzyfdf.library.utils;

import com.yzyfdf.library.rx.BaseRxSubscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author sjj , 2019/5/27 10:51
 * //todo
 */
public class UploadImagePresenter {
    private static volatile UploadImagePresenter sInstance;

    private UploadImagePresenter() {
    }

    public static UploadImagePresenter getInstance() {

        if (sInstance == null) {
            synchronized (UploadImagePresenter.class) {
                if (sInstance == null) {
                    sInstance = new UploadImagePresenter();
                }
            }
        }
        return sInstance;
    }

    /**
     * 上传图片
     *
     * @param type
     * @param filePath
     * @param subscriber
     */
    public void uploadImage(String type, String filePath, BaseRxSubscriber<List<UploadImageBean>> subscriber) {
        HashMap<String, String> map = new HashMap<String, String>() {{
            put("business", type);
        }};

        ArrayList<String> list = new ArrayList<String>() {{
            add(filePath);
        }};

        uploadImages(map, list, subscriber);

    }

    /**
     * @param types
     * @param paths      多图
     * @param subscriber
     */
    private void uploadImages(Map<String, String> types, List<String> paths, BaseRxSubscriber<List<UploadImageBean>> subscriber) {
        //这里先这样 后面改
        HashMap<String, RequestBody> map = new HashMap<>();
        for (Map.Entry<String, String> entry : types.entrySet()) {
            RequestBody body = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), entry.getValue());
            map.put(entry.getKey(), body);
        }

        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (String path : paths) {
            File file = new File(path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
            list.add(part);
        }


//        Api.getApiService(HostType.Industry)
//                .uploadImages(map, list)
//                .compose(RxHelper.handleResult())
//                .subscribe(subscriber);

    }

    public class UploadImageBean {

        /**
         * imageUrl : xxx
         */

        private String imageUrl;
        private String origFileName;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getOrigFileName() {
            return origFileName;
        }

        public void setOrigFileName(String origFileName) {
            this.origFileName = origFileName;
        }
    }
}
