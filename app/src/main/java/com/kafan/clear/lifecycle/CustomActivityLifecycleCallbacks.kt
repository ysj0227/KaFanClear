package com.kafan.clear.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

/**
 * @author ysj
 * @date 2021/6/23
 * @description 监听app前后台切换
 */
object CustomActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    private val tag = CustomActivityLifecycleCallbacks.javaClass.simpleName
    private var activityCounts = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (activityCounts++ == 0) {
            Log.d(tag, "app foreground")
        }
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        if (--activityCounts == 0) {
            Log.d(tag, "app background")
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    fun isAppForeground(): Boolean {
        return activityCounts > 0
    }

}