package com.me.hifimusic.ui.search

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.hifimusic.base.NetworkScheduler
import com.me.hifimusic.bean.IndexContentItemBean
import com.me.hifimusic.net.base.ApiResponse
import com.me.hifimusic.net.base.Network
import com.me.hifimusic.ui.home.HomeFragment
import com.me.hifimusic.util.FancyLog
import com.me.hifimusic.util.HFLog
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.lang.Exception


/**
 * 搜索页面数据处理
 */
class SearchViewModel: ViewModel(){


    val TAG: String = SearchViewModel::class.java.name



    val items = MutableLiveData<MutableList<IndexContentItemBean>>().apply {
    }


    fun getSearchData(context: Context,keywords: String,page: String){

        Network.instance.api.searchResults(keywords,page)
            .compose(NetworkScheduler.compose())
            .subscribe(object :ApiResponse<String>(context){
                override fun fail(code: Int, msg: String) {

                }

                override fun success(data: String) {
                    val document = Jsoup.parseBodyFragment(data)
                    FancyLog.log_d<HomeFragment>(data)
                    val body = document.body()
                    val bodySize = body.childrenSize()
                    Log.e(TAG,"bodySize=> $bodySize")
                    initItemsData(body)
                }

            })

    }

    private fun initItemsData(body: Element?) {
        try {
            val cardDiv: Element? =
                body?.getElementById("body")?.getElementsByClass("container")?.first()
                    ?.getElementsByClass("row")?.first()?.getElementsByClass("col-lg-10 mx-auto")?.first()

//            val itemNav: Elements = cardDiv.getElementsByTag("nav")

            val cardBody:Element? = cardDiv?.getElementsByClass("card search")?.first()?.getElementsByClass("card-body")?.first()
                    ?.getElementsByClass("list-unstyled threadlist mb-0")?.first()
            Log.e(TAG, "cardBody=> $cardBody")
            var itemDiv = cardBody?.getElementsByClass("media-body")
            Log.e(TAG, "item length${itemDiv?.size}items=> $itemDiv")

            var contentBeans = mutableListOf<IndexContentItemBean>()
            if (itemDiv != null)
                loop@ for (i in 0 until itemDiv.size) {
                    //条目信息
                    val itemA =
                        itemDiv[i].getElementsByClass("subject break-all")[0].select("a").first()
                    val link = itemA.attr("href")
                    var name = itemA.select("a")?.first()?.text()

                    //作者信息
                    val authorElement = cardBody?.getElementsByClass("media thread tap  ")?.get(i)
                    val authorLink = ""
                    val author = authorElement?.getElementsByClass("username text-grey mr-1  hidden-sm")?.first()?.text()
                    val authorImage = authorElement?.getElementsByTag("img")?.first()?.attr("src")

                    if (TextUtils.isEmpty(name)) {
                        name = itemA.text()
                    }
                    val item = IndexContentItemBean(
                        name,
                        author!!,
                        link,
                        authorImage,
                        "",
                        authorLink
                    )
                    HFLog.e(TAG, "real items = ${item.toString()}")
                    contentBeans.add(item)
                }
            items.postValue(contentBeans)
        }catch (e: Exception){
            Log.e(TAG,"init data has error ${e.message}")
        }
    }

}