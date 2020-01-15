package com.jiangyt.tabdemo.network

import android.text.TextUtils
import android.util.Log
import okhttp3.*

/**
 * Desc: log拦截器
 * <p>
 * @author Create by sinochem on 2020-01-08
 * <p>
 * Version: 1.0.0
 */
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 这个chain里面包含了request和response，所以你要什么都可以从这里拿
        val request: Request = chain.request()
        // 请求发起时间
        val st: Long = System.nanoTime()
        val method: String = request.method()
        if (TextUtils.equals("POST", method)) {
            val sb: StringBuilder = StringBuilder()
            if (request.body() is FormBody) {
                val body: FormBody = request.body() as FormBody
                for (index in 0..body.size()){
                    sb.append(body.encodedName(index) + "=" + body.encodedValue(index) + ",")
                }
                sb.delete(sb.length - 1, sb.length)
                Log.d("JYT",String.format("发送请求 %s on %s %n%s %nRequestParams:{%s}",
                    request.url(), chain.connection(), request.headers(), sb.toString()))
            }
        } else {
            Log.d(
                "JYT", String.format(
                    "发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()
                )
            )
        }

        val response: Response = chain.proceed(request)
        val et: Long = System.nanoTime()
        val responseBody: ResponseBody = response.peekBody(1024 * 1024)
        Log.d(
            "JYT",
            String.format(
                "接收响应: [%s] %n返回json:【%s】 %.1fms %n%s",
                response.request().url(),
                responseBody.string(),
                (et - st) / 1e6,
                response.headers()
            )
        )
        return response
    }
}