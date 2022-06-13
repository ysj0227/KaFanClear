package com.kafan.clear.utilities

import android.content.Context
import android.os.Environment
import android.os.StatFs
import java.io.File

/**
 * SDCard相关工具类
 */
class SDCardUtil {

    companion object {


        //SDCard
        /**
         * 判断SDCard是否可用
         */
        val isSDCardEnable: Boolean
            get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        /**
         * 获取SD卡路径
         */
        val sDCardPath: String
            get() = Environment.getExternalStorageDirectory().absolutePath + File.separator// 获取空闲的数据块的数量
        // 获取单个数据块的大小（byte）
        /**
         * 获取SD卡的剩余容量 单位byte
         */
        val freeSizeAll: Long
            get() {
                if (isSDCardEnable) {
                    val stat = StatFs(sDCardPath)
                    // 获取空闲的数据块的数量
                    val availableBlocks = stat.availableBlocks.toLong() - 4
                    // 获取单个数据块的大小（byte）
                    val freeBlocks = stat.availableBlocks.toLong()
                    return freeBlocks * availableBlocks
                }
                return 0
            }

        /**
         * 获取指定路径所在空间的剩余可用容量字节数，单位byte
         */
        fun getFreeSizeByPath(filePath: String): Long {
            // 如果是sd卡的下的路径，则获取sd卡可用容量
            var filePath = filePath
            filePath = if (filePath.startsWith(sDCardPath)) {
                sDCardPath
            } else { // 如果是内部存储的路径，则获取内存存储的可用容量
                Environment.getDataDirectory().absolutePath
            }
            val stat = StatFs(filePath)
            val availableBlocks = stat.availableBlocks.toLong() - 4
            return stat.blockSize * availableBlocks
        }

        /**
         * 调用该方法会返回应用程序的外部文件系统（Environment.getExternalStorageDirectory()）目录的绝对路径，它是用来存放应用的缓存文件，
         * 它和getCacheDir目录一样，目录下的文件都会在程序被卸载的时候被清除掉。
         */
        fun getSDCardCache(context: Context): File? {
            return if (isSDCardEnable) {
                context.externalCacheDir
            } else null
        }
        //系统存储
        /**
         * 获取系统存储路径
         */
        val rootDirectoryPath: String
            get() = Environment.getRootDirectory().absolutePath

        /**
         * 返回通过Context.openFileOutput()创建和存储的文件系统的绝对路径，应用程序文件，
         * 这些文件会在程序被卸载的时候全部删掉。
         */
        fun getAppFile(context: Context): File {
            return context.filesDir
        }

        /**
         * 返回应用程序指定的缓存目录，这些文件在设备内存不足时会优先被删除掉，
         * 所以存放在这里的文件是没有任何保障的，可能会随时丢掉。
         */
        fun getAppCache(context: Context): File {
            return context.cacheDir
        }

        /**
         * 这是一个可以存放你自己应用程序自定义的文件，你可以通过该方法返回的File实例来创建或者访问这个目录，
         * 注意该目录下的文件只有你自己的程序可以访问。
         */
        fun getAppDir(context: Context, path: String?): File {
            return context.getDir(path, 0)
        }
    }
}