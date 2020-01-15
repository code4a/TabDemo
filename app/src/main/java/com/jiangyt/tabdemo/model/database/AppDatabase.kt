package com.jiangyt.tabdemo.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jiangyt.tabdemo.model.Post
import com.jiangyt.tabdemo.model.PostDao

/**
 * Desc: com.jiangyt.tabdemo.model.database
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
@Database(entities = [Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}