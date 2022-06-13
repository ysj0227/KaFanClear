package com.kafan.clear.di

import android.util.Log
import com.kafan.clear.BuildConfig
import com.kafan.clear.api.ApiService
import com.kafan.clear.api.interceptor.CacheInterceptor
import com.kafan.clear.api.interceptor.HeaderInterceptor
import com.kafan.clear.api.interceptor.SaveCookieInterceptor
import com.kafan.clear.base.BaseApplication
import com.kafan.clear.utilities.BASE_URL
import com.kafan.clear.utilities.DEFAULT_TIMEOUT
import com.kafan.clear.utilities.MAX_CACHE_SIZE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Creator： wq
 * Date：2021-06-22
 * Time: 15:35
 * <p/>
 * Description:
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        val loggingInterceptor: HttpLoggingInterceptor
        if (BuildConfig.DEBUG) {
            loggingInterceptor =
                HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.d("接口请求log----->", message)
                    }
                })
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else loggingInterceptor = HttpLoggingInterceptor()

        //设置 请求的缓存的大小跟位置
        val cacheFile = File(BaseApplication.mContext.cacheDir, "cache")
        val cache = Cache(cacheFile, MAX_CACHE_SIZE)

        builder.run {
            addInterceptor(loggingInterceptor)
            addInterceptor(HeaderInterceptor())
            addInterceptor(SaveCookieInterceptor())
            addInterceptor(CacheInterceptor())
            cache(cache)  //添加缓存
            connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true) // 错误重连
            // cookieJar(CookieManager())
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}