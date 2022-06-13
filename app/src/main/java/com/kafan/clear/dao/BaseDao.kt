package com.kafan.clear.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * @author ysj
 * @date 2021/6/22
 * @description
 **/
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: MutableList<T>)

    @Delete
    fun delete(element: T)

    @Delete
    fun deleteList(elements: MutableList<T>)

    @Delete
    fun deleteSome(vararg elements: T)

    @Update
    fun update(element: T)
}