package com.jiangyt.tabdemo.retrofit

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


data class SearchResultEntry(val id: String, val latestVersion: String)
data class SearchResults(val docs: List<SearchResultEntry>)
data class MavenSearchResponse(val response: SearchResults)
/**
 * Desc: com.jiangyt.tabdemo.retrofit
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
interface MavenSearchService {
    @GET("/solrsearch/select?wt=json")
    fun search(@Query("q") s: String, @Query("rows") rows: Int = 20): Observable<MavenSearchResponse>
}

fun main(args: Array<String>) {
    val service = Retrofit.Builder()
        .baseUrl("http://search.maven.org")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(MavenSearchService::class.java)

    service.search("rxkotlin")
        .flatMapIterable { it.response.docs }
        .subscribe { it -> println("${it.id} (${it.latestVersion})") }
}