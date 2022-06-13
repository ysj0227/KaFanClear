package com.kafan.clear.data

/**
 * @author ysj
 * @date 2021/6/29
 * @description
 **/
//
data class CommonData(
    var type: Int,
    var type_name: String,
    var total: Int,
    var current_page: Int,
    var page_size: Int,
    var pages: Int,
)

//搜索发现
data class SearchSuggest(
    var suggest: List<String>
)