package com.kafan.clear.api.logging

import okhttp3.internal.platform.Platform.Companion.INFO
import java.util.logging.Level
import java.util.logging.Logger

internal open class I protected constructor() {
    companion object {
        @JvmStatic
        fun log(type: Int, tag: String?, msg: String?) {
            val logger = Logger.getLogger(tag)
            when (type) {
                INFO -> logger.log(Level.INFO, msg)
                else -> logger.log(Level.WARNING, msg)
            }
        }
    }

    init {
        throw UnsupportedOperationException()
    }
}