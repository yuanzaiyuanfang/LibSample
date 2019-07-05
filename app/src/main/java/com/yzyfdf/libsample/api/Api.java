package com.yzyfdf.libsample.api;


import android.text.TextUtils;
import android.util.SparseArray;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static final int TIME_OUT = 30;
    private ApiService mApiService;
    private OkHttpClient okHttpClient;
    private static SparseArray<Api> apiSparseArray = new SparseArray<>(HostType.values().length);


    private Api(HostType hostType) {
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                //request
                Request request = chain.request();
                LogUtils.iTag("url", request.url());

                if (request.body() != null) {
                    Buffer buffer = new Buffer();
                    request.body().writeTo(buffer);
                    LogUtils.iTag("参数", buffer.readUtf8());
                }

                //增加header
                Request.Builder builder = request.newBuilder();

                switch (hostType) {
                    case Industry:
                        builder.addHeader("X-Api-Realm","tools-matrix");
                        break;
                    case UserCenter:
                        builder.addHeader("X-Api-Realm","app-user-center");
                        break;
                }
                Request build = builder.build();

                //response
                Response response = chain.proceed(build);
                try {
                    String xToken = response.header("X-Token");
                    if (!TextUtils.isEmpty(xToken)) {
                        LogUtils.dTag("xToken", xToken);
                    }
                } catch (Exception ignored) {
                }

                return response;
            }
        };

        try {
            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            okHttpClient = new OkHttpClient.Builder()
//                    .followRedirects(false)
//                    .followSslRedirects(false)
                    //证书
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier((hostname, session) -> true)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                    //失败重连
                    .retryOnConnectionFailure(true)
                    .addInterceptor(headerInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(hostType.mHost)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mApiService = retrofit.create(ApiService.class);

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static ApiService getApiService(HostType hostType) {
        Api api = apiSparseArray.get(hostType.mIndex);
        if (api == null) {
            api = new Api(hostType);
            apiSparseArray.put(hostType.mIndex, api);
        }
        return api.mApiService;
    }

    public static OkHttpClient getOkHttpClient(HostType hostType) {
        Api api = apiSparseArray.get(hostType.mIndex);
        if (api == null) {
            api = new Api(hostType);
            apiSparseArray.put(hostType.mIndex, api);
        }
        return api.okHttpClient;
    }

    private final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
}