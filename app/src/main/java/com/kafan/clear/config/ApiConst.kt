package com.kafan.clear.config

import android.content.Context

class ApiConst : BaseConfig() {
    /**
     * 开发
     */
    override fun initDev(context: Context?, env: String?) {
        OPEN_LOG = true
        BASE_URL = "https://apis.shdisny.com/"
    }

    /**
     * 线上
     */
    override fun initRelease(context: Context?, env: String?) {
        OPEN_LOG = false
        BASE_URL = "https://apps.3dmyun.com/"
    }

    companion object {
        /**
         * app url
         */
        var BASE_URL: String? = null

        /**
         * 是否开启log
         */
        var OPEN_LOG = false
    }

}