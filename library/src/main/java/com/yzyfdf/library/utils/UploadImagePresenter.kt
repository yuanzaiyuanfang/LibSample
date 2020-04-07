package com.yzyfdf.library.utils

import com.yzyfdf.library.rx.BaseRxSubscriber
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

/**
 * @author sjj , 2019/5/27 10:51
 * //todo
 */
class UploadImagePresenter private constructor() {

    /**
     * 上传图片
     *
     * @param type
     * @param filePath
     * @param subscriber
     */
    fun uploadImage(type: String, filePath: String,
                    subscriber: BaseRxSubscriber<List<UploadImageBean>>) {
        val map = object : HashMap<String, String>() {
            init {
                put("business", type)
            }
        }

        val list = object : ArrayList<String>() {
            init {
                add(filePath)
            }
        }

        uploadImages(map, list, subscriber)

    }

    /**
     * @param types
     * @param paths      多图
     * @param subscriber
     */
    private fun uploadImages(types: Map<String, String>, paths: List<String>,
                             subscriber: BaseRxSubscriber<List<UploadImageBean>>) {
        //这里先这样 后面改
        val map = HashMap<String, RequestBody>()
        for ((key, value) in types) {
            val body = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), value)
            map[key] = body
        }

        val list = ArrayList<MultipartBody.Part>()
        for (path in paths) {
            val file = File(path)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val part = MultipartBody.Part.createFormData("files", file.name, requestFile)
            list.add(part)
        }


        //        Api.getApiService(HostType.Industry)
        //                .uploadImages(map, list)
        //                .compose(RxHelper.logAndThread())
        //                .subscribe(subscriber);

    }

    inner class UploadImageBean {

        /**
         * imageUrl : xxx
         */

        var imageUrl: String? = null
        var origFileName: String? = null
    }

    companion object {

        val instance: UploadImagePresenter by lazy {
            UploadImagePresenter()
        }
    }
}
