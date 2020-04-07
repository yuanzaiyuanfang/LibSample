package com.yzyfdf.libsample.api


import android.text.TextUtils
import android.util.SparseArray
import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class Api private constructor(hostType: HostType) {

    private var mApiService: ApiService? = null
    private var okHttpClient: OkHttpClient? = null

    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<X509Certificate>, authType: String
        ) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<X509Certificate>, authType: String
        ) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    })


    init {
        //增加头部信息
        val headerInterceptor = Interceptor { chain ->
            //request
            val request = chain.request()
            LogUtils.iTag("url", request.url())

            if (request.body() != null) {
                val buffer = Buffer()
                request.body()!!.writeTo(buffer)
                LogUtils.iTag("参数", buffer.readUtf8())
            }

            //增加header
            val builder = request.newBuilder()

            when (hostType) {
                HostType.Industry -> builder.addHeader("X-Api-Realm", "tools-matrix")
                HostType.UserCenter -> builder.addHeader("X-Api-Realm", "app-user-center")
            }
            val build = builder.build()

            //response
            val response = chain.proceed(build)
            try {
                val xToken = response.header("X-Token")
                if (!TextUtils.isEmpty(xToken)) {
                    LogUtils.dTag("xToken", xToken)
                }
            } catch (ignored: Exception) {
            }

            response
        }

        try {
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            okHttpClient = OkHttpClient.Builder()
                //                    .followRedirects(false)
                //                    .followSslRedirects(false)
                //证书
//                .sslSocketFactory(sslSocketFactory)
//                .hostnameVerifier { hostname, session -> true }
                .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                //失败重连
                .retryOnConnectionFailure(true)
                .addInterceptor(headerInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(hostType.mHost)
                .client(okHttpClient!!)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            mApiService = retrofit.create(ApiService::class.java)

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }

    }

    companion object {

        val TIME_OUT = 30

        private val apiSparseArray = SparseArray<Api>(HostType.values().size)

        fun getApiService(hostType: HostType): ApiService? {
            var api: Api? = apiSparseArray.get(hostType.mIndex)
            if (api == null) {
                api = Api(hostType)
                apiSparseArray.put(hostType.mIndex, api)
            }
            return api.mApiService
        }

        fun getOkHttpClient(hostType: HostType): OkHttpClient? {
            var api: Api? = apiSparseArray.get(hostType.mIndex)
            if (api == null) {
                api = Api(hostType)
                apiSparseArray.put(hostType.mIndex, api)
            }
            return api.okHttpClient
        }
    }
}