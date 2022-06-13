package com.kafan.clear.api

import com.kafan.clear.data.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 *
 * Created by wq on 2021-06-24.
 */

interface ApiService {


    /**
     * 查看更多
     */
    @POST("app/lists")
    @FormUrlEncoded
    suspend fun moreData(
        @Field("column_id") id: Int,
        @Field("page") page: Int,
        @Field("page_size") page_size: Int
    ): ResponseData<CommonData>


    /**
     *分类配置信息接口
     */
    @POST("app/cat_list")
    @FormUrlEncoded
    suspend fun getCatList(@Field("type") type: String): ResponseData<List<Category>>

    /**
     *h5 常见问题, 在线反馈，关于我们
     */
    @GET("app/extra")
    suspend fun getWebUrl(): ResponseData<WebUrl>
}