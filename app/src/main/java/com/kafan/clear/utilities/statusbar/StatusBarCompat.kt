package com.kafan.clear.utilities.statusbar

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.kafan.clear.R

/**
 * @author ysj
 * @date 2021/6/25
 * @description
 **/
class StatusBarCompat {

    companion object {
        private val INVALID_VAL = -1
        private var color: Int = 0

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun compat(activity: Activity, statusColor: Int) {
            if (statusColor != INVALID_VAL) {
                activity.window.statusBarColor = ContextCompat.getColor(activity, statusColor)
            } else {
                color = ContextCompat.getColor(activity, R.color.common_primary)
                activity.window.statusBarColor = color
            }
            StatusBarFont.setBarFont(activity, true)
        }

        fun compat(activity: Activity) {
            compat(activity, INVALID_VAL)
        }


        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId =
                context.resources.getIdentifier(
                    "status_bar_height",
                    "dimen", "android"
                )
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        /**
         * 全透状态栏
         */
        fun setBarTransparent(activity: Activity) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

}