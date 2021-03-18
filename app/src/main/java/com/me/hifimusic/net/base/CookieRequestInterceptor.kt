package com.me.hifimusic.net.base

import okhttp3.Interceptor
import okhttp3.Response
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.me.hifimusic.config.Constants
import com.me.hifimusic.util.AppPreference
import com.me.hifimusic.util.FancyLog
import com.me.hifimusic.util.HFLog


// 网络请求中添加cookie
class CookieRequestInterceptor: Interceptor {

    val TAG = CookieRequestInterceptor::class.java.name

    override fun intercept(chain: Interceptor.Chain): Response {

        val cookie = AppPreference.getInstance().getList(Constants.cookies)
//        cookie?.let {
//            loop@ for (i in 0 until cookie.size){
//                Log.e(TAG,"cookie=> ${cookie[i]}")
//                if (cookie[i].contains("bbs_sid"))builder.addHeader("Cookie",cookie[i])
//            }
//        }

        var request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Connection", "keep-alive")
            .addHeader("User-Agent","PostmanRuntime/7.26.8")
            .addHeader("Cookie","bbs_sid=l7skv915ouqg55a0fa421hbm49")
            .build()
        return chain.proceed(request)
    }


}