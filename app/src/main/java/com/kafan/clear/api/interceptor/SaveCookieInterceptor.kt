package com.kafan.clear.api.interceptor

import com.kafan.clear.utilities.SAVE_USER_LOGIN_KEY
import com.kafan.clear.utilities.SAVE_USER_REGISTER_KEY
import com.kafan.clear.utilities.SET_COOKIE_KEY
import com.kafan.clear.utilities.encodeCookie
import com.kafan.clear.utilities.saveCookie
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 保存Cookie
 *
 */
class SaveCookieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url.toString()
        val domain = request.url.host
        // set-cookie maybe has multi, login to save cookie
        if ((requestUrl.contains(SAVE_USER_LOGIN_KEY)
                    || requestUrl.contains(SAVE_USER_REGISTER_KEY))
            && response.headers(SET_COOKIE_KEY).isNotEmpty()
        ) {
            val cookies = response.headers(SET_COOKIE_KEY)
            val cookie = encodeCookie(cookies)
            saveCookie(requestUrl, domain, cookie)
        }
        return response
    }
}