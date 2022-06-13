package com.kafan.clear.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author ysj
 * @date 2021/6/22
 * @description
 **/
@Entity(tableName = "User")
data class User(
    @PrimaryKey
    var id: String,
    var name: String
)
