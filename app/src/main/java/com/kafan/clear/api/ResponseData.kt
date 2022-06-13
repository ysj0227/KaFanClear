package com.kafan.clear.api

import java.io.Serializable

data class ResponseData<out T>(
    val code: Int,
    val message: String,
    val data: T
)


// 通用的带有列表数据的实体
data class BaseListResponseBody<T>(
    val total: Int,
    val current_page: Int,
    val page_size: Int,
    val pages: Int,
    val list: List<T>
)

//Apks
data class ApksListBean(
    val total: Int,
    val current_page: Int,
    val page_size: Int,
    val pages: Int,
    var list: List<UpdateItem>
)

data class UpdateItem(
    val app_id: Long,
    val app_name: String,
    val app_icon: String,
    val app_size: String,
    val app_brief: String,
    val app_package_name: String,
    val app_cover: String,
    val app_download_url: String,
    val app_version: String,
    val app_version_old: String,
)

// 类型集合
data class Category(
    val cat_id: Int,
    val cat_name: String,
)

// H5
data class WebUrl(
    val faq: String,
    val report: String,
    val about: String,
)



