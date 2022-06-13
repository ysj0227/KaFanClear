package com.kafan.clear.config

/**
 * @author ysj
 * @date 2021/6/28
 * @description
 **/
object Constant {
    const val TYPE_TODAY: Int = 0
    const val TYPE_NEW_DEVICE: Int = 1
    const val TYPE_HOT_GAME: Int = 2
    const val TYPE_TOOLS: Int = 3
    const val TYPE_FRAMEWORK: Int = 4

    const val MORE_LIST_TYPE: String = "MORE_LIST_TYPE"
    const val MORE_LIST_COLUMN_ID: String = "MORE_LIST_COLUMN_ID"
    const val BUNDLE_KEYWORDS = "BUNDLE_KEYWORDS"
    const val BUNDLE_PACKAGE_INFO = "BUNDLE_PACKAGE_INFO"

    const val CONTENT_TYPE_KEY = "tid"
    const val CONTENT_CATE_KEY = "cid"

    const val TAB_TYPE_APP = "1"
    const val TAB_TYPE_GAME = "2"


    const val EVENTBUS_EXTERNAL_STORAGE = "EVENT_EXTERNAL_STORAGE"
    const val EVENTBUS_ENTER_DETAILS = "EVENTBUS_ENTER_DETAILS"

    /**
     * banner 1：转跳APP详情   2：转跳HTML页面  3.纯展示，无法点击
     */
    const val BANNER_DETAILS: Int = 1
    const val BANNER_WEB_VIEW: Int = 2
    const val BANNER_CANNOT_CLICK: Int = 3
}