package com.jiangyt.tabdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Desc: com.jiangyt.tabdemo.model
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
@Entity
data class Post(
    val userId: Int,
    @field:PrimaryKey val id: Long,
    val title: String,
    val body: String
)