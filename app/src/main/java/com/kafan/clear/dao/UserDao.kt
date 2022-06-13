package com.kafan.clear.dao

import androidx.room.*
import com.kafan.clear.data.User

/**
 * @author ysj
 * @date 2021/6/22
 * @description
 **/
@Dao
interface UserDao : BaseDao<User> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(cacheBean: User)

    @Query("select * from User where id =:id")
    suspend fun getUser(id: String): User?

    @Query("select * from User")
    suspend fun getAllUser(): List<User>?

    @Delete
    override fun delete(element: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override fun update(element: User)
}