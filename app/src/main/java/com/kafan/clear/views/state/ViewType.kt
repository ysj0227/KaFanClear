package com.kafan.clear.views.state

/**
 * 视图类型
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class ViewType {
    companion object {
        /**
         * 内容视图
         */
        var CONTENT = 0

        /**
         * 错误视图
         */
        var ERROR = 1

        /**
         * 空视图
         */
        var EMPTY = 2

        /**
         * 加载视图
         */
        var LOADING = 3

        /**
         * 网络异常视图
         */
        var NETWORK = 4
    }
}