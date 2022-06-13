package com.kafan.clear.api

import com.kafan.clear.base.BaseRepository
import com.kafan.clear.data.*
import retrofit2.http.Field

/**
 * Creator： wq
 * Date：2021-06-28
 * Time: 17:18
 * <p/>
 * Description:
 */
class ApiRepository : BaseRepository() {

    /**
     *h5 常见问题, 在线反馈，关于我们
     */
    suspend fun getWebUrl(): ResponseData<WebUrl> = request {
        RetrofitClient.service.getWebUrl()
    }
}