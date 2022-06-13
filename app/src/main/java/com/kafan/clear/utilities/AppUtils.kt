package com.kafan.clear.utilities

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.blankj.utilcode.util.Utils

/**
 * @author ysj
 * @date 2021/7/16
 * @description
 **/
object AppUtils {
    /**
     * 获取manifest文件meta值
     */
    fun getMetaValue(context: Context, keyName: String?, defValue: String?): String? {
        var value: Any? = null
        val applicationInfo: ApplicationInfo
        try {
            applicationInfo = context.packageManager.getApplicationInfo(
                context.packageName, PackageManager.GET_META_DATA
            )
            value = applicationInfo.metaData[keyName]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return value?.toString() ?: defValue
    }

    fun getAppLastUpdateTime(packageName: String): Long {
        if (packageName.isNullOrEmpty()) {
            return -1
        }
        try {
            val pm = Utils.getApp().packageManager
            val pi = pm.getPackageInfo(packageName, 0)
            return pi.lastUpdateTime ?: -1
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return -1
    }
}