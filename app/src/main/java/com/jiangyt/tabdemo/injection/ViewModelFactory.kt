package com.jiangyt.tabdemo.injection

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.jiangyt.tabdemo.model.database.AppDatabase
import com.jiangyt.tabdemo.ui.post.PostListViewModel

/**
 * Desc: com.jiangyt.tabdemo.injection
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
class ViewModelFactory(private val fragment: Fragment) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostListViewModel::class.java)) {
            val db = Room.databaseBuilder(
                fragment.activity!!.applicationContext,
                AppDatabase::class.java,
                "posts"
            ).build()
            return PostListViewModel(db.postDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}