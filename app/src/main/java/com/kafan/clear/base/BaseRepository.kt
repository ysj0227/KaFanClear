package com.kafan.clear.base

import android.util.Log
import com.google.gson.Gson
import com.kafan.clear.api.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

open class BaseRepository {

    suspend fun <T : Any> request(call: suspend () -> ResponseData<T>): ResponseData<T> {
        return withContext(Dispatchers.IO) {
            call.invoke()
        }.apply {
            Log.d("接口返回数据----->", this.toString())
            //这儿可以对返回结果errorCode做一些特殊处理，比如token失效等，可以通过抛出异常的方式实现
            //例：当token失效时，后台返回errorCode 为 100，下面代码实现,再到baseActivity通过观察error来处理
            when (code) {
                //一般0和200是请求成功，直接返回数据
                0, 200, 20000 -> this
                100, 401 -> throw TokenInvalidException(message)
                403 -> throw NoPermissionsException(message)
                404 -> throw NotFoundException(message)
                500 -> throw InterfaceErrException(message)
                504 -> throw TimeOutErrException(message)
                else -> throw Exception(message)
            }
        }
    }

    fun <T> createRequestBody(t: T): RequestBody {
        return Gson().toJson(t)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    class TokenInvalidException(msg: String = "token失效，请重新登录") : Exception(msg)
    class NoPermissionsException(msg: String = "您没有操作权限，请联系管理员开通") : Exception(msg)
    class NotFoundException(msg: String = "请求的地址不存在") : Exception(msg)
    class InterfaceErrException(msg: String = "接口请求出错") : Exception(msg)
    class TimeOutErrException(msg: String = "连接超时") : Exception(msg)
}