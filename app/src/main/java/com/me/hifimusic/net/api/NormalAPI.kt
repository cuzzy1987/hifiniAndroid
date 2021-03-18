package com.me.hifimusic.net.api

import io.reactivex.Observable
import okhttp3.RequestBody
import org.jsoup.nodes.Document
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

interface NormalAPI {

    // 默认条目
    @GET("/")
    fun getIndexList(): Observable<String>

    // 搜索
    @GET("/search-{keywords}-1-1-{page}.htm")
    fun searchResults(@Path("keywords") keywords: String,@Path("page") page: String): Observable<String>

    // 登录
    @FormUrlEncoded
    @POST("/user-login.htm")
    fun login(@FieldMap params: HashMap<String,String>): Observable<String>


    //获取播放信息
    @GET("/{id}")
    fun getPlayerInfo(@Path("id") id: String):Observable<String>


//    例子 get_music.php?key=k+Jk+g8fVRQ204o5cYpdj9SYuqG2Hmrzk4NiAIxjMecM3YnpEWFr5gCxJAM
    //获取播放地址
    @GET("/{path}")
    fun getResource(@Path("path") param: String,@QueryMap params: HashMap<String,String>): Observable<String>
}