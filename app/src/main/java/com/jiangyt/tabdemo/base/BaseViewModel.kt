package com.jiangyt.tabdemo.base

import androidx.lifecycle.ViewModel
import com.jiangyt.tabdemo.injection.component.DaggerViewModelInjector
import com.jiangyt.tabdemo.injection.component.ViewModelInjector
import com.jiangyt.tabdemo.injection.module.NetworkModule
import com.jiangyt.tabdemo.ui.post.PostListViewModel
import com.jiangyt.tabdemo.ui.post.PostViewModel

/**
 * Desc: com.jiangyt.tabdemo.base
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
abstract class BaseViewModel : ViewModel() {

    private val injector: ViewModelInjector =
        DaggerViewModelInjector.builder().networkModule(NetworkModule).build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is PostListViewModel -> injector.inject(this)
            is PostViewModel -> injector.inject(this)
        }
    }
}