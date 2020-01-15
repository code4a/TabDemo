package com.jiangyt.tabdemo.injection.module

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jiangyt.tabdemo.network.PostApi
import com.jiangyt.tabdemo.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Desc: com.jiangyt.tabdemo.injection.module
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
@Module
@Suppress("unused")
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun providePostApi(retrofit: Retrofit): PostApi {
        return retrofit.create(PostApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideOkHttpClient(): OkHttpClient {
        var builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.addInterceptor(LoggingInterceptor.Builder()
            .loggable(true)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("JYT_REQ")
            .response("JTY_RESP")
            .build())
        return builder.build()
    }
}