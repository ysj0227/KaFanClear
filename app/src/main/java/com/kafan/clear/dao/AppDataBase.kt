package com.kafan.clear.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kafan.clear.base.BaseApplication
import com.kafan.clear.data.User

/**
 * @author ysj
 * @date 2021/6/22
 * @description
 **/
@Database(
    entities = [User::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        val instance = Single.single
    }

    private object Single {
        val single: AppDataBase = Room.databaseBuilder(
            BaseApplication.mContext,
            AppDataBase::class.java,
            "cloud_store.db"
        )
            .allowMainThreadQueries()
            .build()
    }


}