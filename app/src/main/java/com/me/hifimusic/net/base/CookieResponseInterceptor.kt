package com.me.hifimusic.net.base

import android.util.Log
import com.me.hifimusic.config.Constants
import com.me.hifimusic.util.AppPreference
import okhttp3.Interceptor
import okhttp3.Response

class CookieResponseInterceptor: Interceptor {

    val TAG: String = CookieResponseInterceptor::class.java.name

    // 获取登录成功后cookie数据 并存储
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val url = chain.request().url()
        Log.e(TAG,"url=${url}")
        val cookies = mutableListOf<String>()
        val headers = response.headers()
        headers?.let {
            loop@ for (i in 0 until headers.size()){
                val cookie = headers["Set-cookie"]
                cookie?.let {
                    cookies.add(cookie)
                }
            }
        }
        if (cookies.size > 0)AppPreference.getInstance().saveList(Constants.cookies,cookies)
        return response
    }

}