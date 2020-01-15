package com.jiangyt.tabdemo.model

import androidx.room.Dao
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.Query

/**
 * Desc: com.jiangyt.tabdemo.model
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
@Dao
interface PostDao {
    @get:Query("SELECT * FROM post")
    val all: MutableList<Post>

    @Insert
    fun insertAll(vararg users: Post)
}