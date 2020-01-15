package com.jiangyt.tabdemo.network

import com.jiangyt.tabdemo.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Desc: com.jiangyt.tabdemo.network
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
interface PostApi {

    @GET("/posts")
    fun getPosts(): Observable<MutableList<Post>>
}