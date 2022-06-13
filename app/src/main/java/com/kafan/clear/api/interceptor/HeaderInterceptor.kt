package com.kafan.clear.api.interceptor

import com.kafan.clear.utilities.ARTICLE_WEBSITE
import com.kafan.clear.utilities.COOKIE_NAME
import com.kafan.clear.utilities.MyMMKV.Companion.mmkv
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 头部拦截器
 *
 */
class HeaderInterceptor : Interceptor {
    //test addHeader
    private val API_KEY = "d6fd31ff-2b46-4600-b25d-cbcd09f0ac14"
    private val HEADER_API_KEY = "x-api-key"

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader("Content-type", "application/json; charset=utf-8")
            .addHeader(HEADER_API_KEY, API_KEY)
//         .header("token", token)
//         .method(request.method, request.body)

        val domain = request.url.host
        val url = request.url.toString()
        //需要添加cookie的接口加上cookie
        if (domain.isNotEmpty() && (url.contains(ARTICLE_WEBSITE))
        ) {
            val spDomain = mmkv.decodeString(domain) ?: ""
            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
            if (cookie.isNotEmpty()) {
                // 将 Cookie 添加到请求头
                builder.addHeader(COOKIE_NAME, cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}