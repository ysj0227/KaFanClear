package com.kafan.clear.utilities

import com.tencent.mmkv.MMKV

object SettingUtil {

    private val setting = MMKV.defaultMMKV()


    /**
     * 获取列表动画
     */
    fun getListAnimal() = setting.decodeString("list_animal", "无")
    fun setListAnimal(animal: String) = setting.encode("list_animal", animal)
}