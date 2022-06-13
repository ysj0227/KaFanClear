package com.kafan.clear.utilities.statusbar

import android.app.Activity
import android.view.View

/**
 * @author ysj
 * @date 2021/6/25
 * @description
 */
object StatusBarFont {
    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    fun setBarFont(activity: Activity, fontIconDark: Boolean) {
        if (fontIconDark) {
            activity.window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }
}