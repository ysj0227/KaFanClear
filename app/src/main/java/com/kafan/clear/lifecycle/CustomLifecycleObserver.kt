package com.kafan.clear.lifecycle

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author ysj
 * @date 2021/6/23
 * @description 生命周期的监听
 * activity用法：
 * lifecycle.addObserver(CustomLifecycleObserver(this))
 */
class CustomLifecycleObserver : LifecycleObserver {
    private val tag: String? = this.javaClass.simpleName
    private val context: Context
    private var type: Int = 0

    constructor(context: Context) {
        this.context = context
    }

    constructor(context: Context, type: Int) {
        this.context = context
        this.type = type
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        Log.d(tag, "create")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        Log.d(tag, "start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        Log.d(tag, "resume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        Log.d(tag, "pause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Log.d(tag, "stop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        Log.d(tag, "destroy")
    }

}