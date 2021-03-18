package com.me.hifimusic.net.base

import android.util.Log
import com.me.hifimusic.BuildConfig
import com.me.hifimusic.config.Constants
import com.me.hifimusic.net.api.NormalAPI
import com.me.hifimusic.util.AppPreference
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class Network private constructor(){

    lateinit var api: NormalAPI

    private object Holder {
        val INSTANCE = Network()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    fun init(){

        val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG)HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            ))
            .addInterceptor(CookieRequestInterceptor())
//            .addInterceptor(CookieResponseInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hifini.com")
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(NormalAPI::class.java)
    }


    /**
     * .cookieJar(object : CookieJar {
    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
    var list: MutableList<Cookie> = mutableListOf()
    return list
    }

    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
    cookies.forEach { Log.e("save from response ","cookie$it") }
    var cookieList = mutableListOf<String>()
    for (i in 0 until cookies.size){
    var value = cookies[i]
    Log.e("NetWork","cookie value=> $value")
    cookieList.add(value.toString())
    }
    }

    })
     */

}