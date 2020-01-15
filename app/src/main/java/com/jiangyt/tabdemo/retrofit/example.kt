package com.jiangyt.tabdemo.retrofit

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.*
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * Desc: com.jiangyt.tabdemo.retrofit
 * <p>
 * @author Create by sinochem on 2020-01-07
 * <p>
 * Version: 1.0.0
 */
fun main(args: Array<String>) {

    val subscription = CompositeDisposable()

    val printArticle = { art: String ->
        println("--- Article --- \n${art.substring(0, 125)}")
    }

    @Suppress("ConvertLambdaToReference")
    var printIt = { it: String -> println(it) }

    subscription += asyncObservable().subscribe(printIt)

    subscription += syncObservable().subscribe(printIt)

    subscription.clear()

    simpleComposition()

    asyncWiKi("Tiger", "Elephant").subscribe(printArticle)
}

private fun URL.toScannerObservable() = Observable.create<String> { s ->
    this.openStream().use { stream ->
        Scanner(stream).useDelimiter("\\A")
            .toObservable().subscribe { s.onNext(it) }
    }
}

fun syncObservable(): Observable<String> = Observable.create { subscriber ->
    (0..75).toObservable()
        .map { "Sync value_$it" }
        .subscribe { subscriber.onNext(it) }
}

fun asyncObservable(): Observable<String> = Observable.create { subscriber ->
    thread {
        (0..75).toObservable()
            .map { "Async value_$it" }
            .subscribe { subscriber.onNext(it) }
    }
}

fun asyncWiKi(vararg articleNames: String): Observable<String> =
    Observable.create { subscriber ->
        thread {
            articleNames.toObservable()
                .flatMapMaybe { name ->
                    URL("http://en.wikipedia.org/wiki/$name").toScannerObservable().firstElement()
                }
                .subscribe { subscriber.onNext(it) }
        }
    }

fun asyncWiKiWithErrorHandling(vararg articleNames: String): Observable<String> =
    Observable.create { subscriber ->
        thread {
            articleNames.toObservable()
                .flatMapMaybe { name ->
                    URL("http://en.wikipedia.org/wiki/$name").toScannerObservable().firstElement()
                }
                .subscribe({ subscriber.onNext(it) }, { subscriber.onError(it) })
        }
    }

fun simpleComposition() {
    asyncObservable().skip(10)
        .take(5)
        .map { "${it}_xform" }
        .subscribe { println("onNext => $it") }
}

fun listOfObservable(): List<Observable<String>> = listOf(syncObservable(), syncObservable())

fun combineLatest(observables: List<Observable<String>>) {
    observables.combineLatest { it.reduce { one, two -> one + two } }.subscribe(::println)
}

fun zip(observables: List<Observable<String>>) {
    observables.zip { it.reduce { one, two -> one + two } }.subscribe(::println)
}

fun simpleObservable(): Observable<String> = (0..17).toObservable().map { "simple $it" }

fun addToCompositeSubscription() {
    val compositeSubscription = CompositeDisposable()

    Observable.just("test")
        .delay(100, TimeUnit.MILLISECONDS)
        .subscribe()
        .addTo(compositeSubscription)
}

infix inline fun <T : Any> ((T) -> Unit).andThen(crossinline block: (T) -> Unit): (T) -> Unit =
    { this(it); block(it) }