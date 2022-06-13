package com.kafan.clear.api

import android.util.Log
import com.kafan.clear.api.interceptor.CacheInterceptor
import com.kafan.clear.api.interceptor.HeaderInterceptor
import com.kafan.clear.api.interceptor.SaveCookieInterceptor
import com.kafan.clear.api.logging.Level
import com.kafan.clear.api.logging.LoggingInterceptor
import com.kafan.clear.base.BaseApplication
import com.kafan.clear.config.ApiConst
import com.kafan.clear.utilities.DEFAULT_TIMEOUT
import com.kafan.clear.utilities.MAX_CACHE_SIZE
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val TAG: String = RetrofitClient::class.java.simpleName

    //retrofit对象
    private var retrofit: Retrofit? = null

    //请求的api，可以根据不同的场景设置多个
    val service: ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(ApiConst.BASE_URL!!)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }
        return retrofit!!
    }

    /**
     * 获取 OkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        SSLHelper.configTrustAll(builder)
        builder.apply {
            addInterceptor(loggingInterceptor())
            addInterceptor(HeaderInterceptor())
            addInterceptor(SaveCookieInterceptor())
            addInterceptor(CacheInterceptor())
            //cache(cache())  //添加缓存
            connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true) // 错误重连
            // cookieJar(CookieManager())
            addInterceptor(
                LoggingInterceptor.Builder()//构建者模式
                    .loggable(ApiConst.OPEN_LOG) //是否开启日志打印
                    .setLevel(Level.BASIC) //打印的等级
                    .log(Platform.INFO) // 打印类型
                    .request("Request") // request的Tag
                    .response("Response")// Response的Tag
                    .build()
            )
        }
        return builder.build()
    }

    /**
     * 设置请求的缓存的大小跟位置
     */
    private fun cache(): Cache {
        val cacheFile = File(BaseApplication.mContext.cacheDir, "cache")
        return Cache(cacheFile, MAX_CACHE_SIZE)
    }

    /**
     * log interceptor
     */
    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("$TAG RespLog ", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}