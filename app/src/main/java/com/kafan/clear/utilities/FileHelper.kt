package com.kafan.clear.utilities

import android.os.Environment
import com.kafan.clear.base.BaseApplication
import java.io.File

/**
 * @author ysj
 * @date 2021/7/29
 * @description
 **/
class FileHelper {

    companion object {
        var EXTERNAL_STORAGE_DIR: String = ""
        var APP_DATA_ROOT_PATH: String = ""
        var LOG_PATH: String = ""
        var DATA_PATH: String = ""
        var SDCARD_PATH: String = ""
        var SDCARD_CACHE_IMAGE_PATH: String = ""
        var FILE_PATH: String = ""
        var DOWNLOAD_PATH: String = ""

        val initializer by lazy { FileHelper() }
    }

    /**
     * 初始化本地缓存路径
     */
    init {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            EXTERNAL_STORAGE_DIR = Environment.getExternalStorageDirectory().toString()//ExternalStorage
            SDCARD_PATH = "$EXTERNAL_STORAGE_DIR/ClearMobile/" // sd卡目录
            SDCARD_CACHE_IMAGE_PATH = SDCARD_PATH + "cache/image/" // sd卡下图片文件缓存
            LOG_PATH = SDCARD_PATH + "log/" // sd卡Log
            FILE_PATH = SDCARD_PATH + "files/" //sd卡文件
            DOWNLOAD_PATH = SDCARD_PATH + "download/" //sd卡下载文件夹

            DATA_PATH = BaseApplication.mContext.filesDir.parent!! // DATA
            APP_DATA_ROOT_PATH = "$DATA_PATH/ClearMobile/" // ClearMobile

            createDir(SDCARD_PATH) // sd卡文件夹
            val file = File(APP_DATA_ROOT_PATH)
            file.setExecutable(true, true)
            file.setReadable(true, true)
            file.setWritable(true, true)
            if (!file.exists()) {
                file.mkdirs()
            }
            createDir()
        }
    }


    /**
     * 没有权限时，外部路径会创建失败
     */
    private fun createDir() {
        createDir(LOG_PATH)
        createDir(FILE_PATH)
        createDir(DOWNLOAD_PATH)
        createDir(SDCARD_CACHE_IMAGE_PATH)
    }

    /**
     * 创建文件
     */
    private fun createDir(path: String): File? {
        if (path.isEmpty()) return null
        val file = File(path)
        var hasFile = true
        if (!file.exists()) {
            hasFile = file.mkdirs()
        }
        return if (hasFile) file else null
    }

}