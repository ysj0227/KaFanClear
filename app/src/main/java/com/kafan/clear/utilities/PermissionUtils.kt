package com.kafan.clear.utilities

import android.Manifest
import android.content.Context
import android.content.UriPermission
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat


object PermissionUtils {


    /**
     * 文件管理-根目录
     */
    val uriRoot: Uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3A")

    /**
     * 文件管理-Android/data目录
     */
    val uriAndroidData: Uri =
        Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata")

    /**
     * 读写权限申请
     */
    private val permissionArray = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )
    private var mPermissionList = arrayListOf<String>()


    /**
     * 检查单个权限是否申请
     * @return 申请结果
     */
    private fun checkPermission(context: Context, permissionName: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permissionName
        ) == PackageManager.PERMISSION_GRANTED
    }


    /**
     * 是否有文件管理权限
     */
    fun hasManageAllFilesPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                return true
            }
        } else {
            return true
        }
        return false
    }


    /**
     * 是否有读写权限
     */
    fun hasReedWritePermission(context: Context): Boolean {
        mPermissionList.clear()
        for (i in permissionArray.indices) {
            if (!checkPermission(context, permissionArray[i]))
                mPermissionList.add(permissionArray[i])
        }
        return mPermissionList.size <= 0
    }

}