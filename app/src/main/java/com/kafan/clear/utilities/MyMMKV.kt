package com.kafan.clear.utilities

import com.tencent.mmkv.MMKV

/**
 * MMKV
 * Created by wq on 2021-06-24.
 */
class MyMMKV {

    companion object {
        private const val fileName = "cloud_store_mvvm"

        /**
         * 初始化mmkv
         */
        val mmkv: MMKV
            get() = MMKV.mmkvWithID(fileName)

        /**
         * 删除全部数据(传了参数就是按key删除)
         */
        fun deleteKeyOrAll(key: String? = null) {
            if (key == null) mmkv.clearAll()
            else mmkv.removeValueForKey(key)
        }

        /** 查询某个key是否已经存在
         *
         * @param key
         * @return
         */
        fun contains(key: String) = mmkv.contains(key)
    }
}