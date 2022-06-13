package com.kafan.clear.config

import android.content.Context
import android.text.TextUtils

/**
 * @author ysj
 * @date 2021/5/13
 * @description 环境配置
 */
abstract class BaseConfig {
    fun init(context: Context?, env: String?) {
        if (TextUtils.equals(ENV_DEV, env)) {
            initDev(context, env)
        } else if (TextUtils.equals(ENV_RELEASE, env)) {
            initRelease(context, env)
        }
    }

    abstract fun initDev(context: Context?, env: String?)
    abstract fun initRelease(context: Context?, env: String?)

    companion object {
        //测试
        const val ENV_DEV = "ENV_DEV"

        //生产
        const val ENV_RELEASE = "ENV_RELEASE"
    }
}