package com.jiangyt.tabdemo.injection.component

import com.jiangyt.tabdemo.injection.module.NetworkModule
import com.jiangyt.tabdemo.ui.post.PostListViewModel
import com.jiangyt.tabdemo.ui.post.PostViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Desc: com.jiangyt.tabdemo.injection.component
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(postListViewModel: PostListViewModel)
    fun inject(postViewModel: PostViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}