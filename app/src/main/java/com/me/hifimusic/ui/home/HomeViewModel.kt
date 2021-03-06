package com.me.hifimusic.ui.home

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.hifimusic.base.NetworkScheduler
import com.me.hifimusic.bean.HotAuthor
import com.me.hifimusic.bean.IndexContentItemBean
import com.me.hifimusic.bean.NavBean
import com.me.hifimusic.net.base.ApiResponse
import com.me.hifimusic.net.base.Network
import com.me.hifimusic.util.FancyLog
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.lang.Exception

class HomeViewModel : ViewModel() {

    /**
    DisplayMetrics.density 字段根据当前像素密度指定将 dp 单位转换为像素时所必须使用的缩放系数。
    在中密度屏幕上，DisplayMetrics.density 等于 1.0；
    在高密度屏幕上，它等于 1.5；在超高密度屏幕上，等于 2.0；
    在低密度屏幕上，等于 0.75。
    此数字是一个系数，用其乘以 dp 单位，即可得出当前屏幕的实际像素数。
     * */

    val TAG: String = HomeViewModel::class.java.name
    var mTab: MutableList<NavBean> = mutableListOf()
    val dashBoard = MutableLiveData<MutableList<HotAuthor>>().apply {

    }

    val tabs = MutableLiveData<MutableList<NavBean>>().apply {
        Log.e(TAG,"on apply ")
    }

    val items = MutableLiveData<MutableList<IndexContentItemBean>>().apply {
    }

    fun getData(context: Context) {
        Log.e(TAG," on get data start ")
        Network.instance.api.getIndexList()
            .compose(NetworkScheduler.compose())
//            .bindUntilEvent(this.activity,ActivityEvent.DESTROY)
            .subscribe(object : ApiResponse<String>(context){
                override fun fail(code: Int, msg: String) {
                }
                override fun success(data: String) {
                    try {

                    val document = Jsoup.parseBodyFragment(data)
                    FancyLog.log_d<HomeFragment>(data)
                    val body = document.body()
                    var bodySize = body.childrenSize()
                    Log.e(TAG,"bodySize=> $bodySize")
                    var navbody = body.getElementById("header").getElementById("nav")
                    // 获取ul  <ul class="navbar-nav mr-auto">
                    var navBlock = navbody.getElementsByClass("navbar-nav mr-auto")
                    // 获取表格下的a标签
                    var navAList = navBlock[0].getElementsByTag("a")
                    var navLiList = navBlock[0].getElementsByTag("li")
                    Log.e(TAG,"length of ul=> ${navAList.size}")
                    loop@ for(i in 0 until navAList.size){
                        var item = navAList[i]
                        //获取a标签内容
                        Log.e(TAG,"item nav=> ${item.text()}")
                    }

                    // 获取fid
                    loop@ for (i in 0 until navLiList.size){
                        var item = navLiList[i]
                        Log.e(TAG,"item fid => ${item.attr("fid")}")
                    }
//                    var mTab: MutableList<NavBean> = mutableListOf()
                    loop@ for (i in 0 until navAList.size) {
                        var bean = NavBean()
                        bean.id = navLiList[i].attr("fid")
                        bean.name = navAList[i].text()
                        mTab.add(bean)
                        tabs.postValue(mTab)
                    }

                        initItemsData(body)
                    }catch (e: Exception){
                        Log.e(TAG,"has error=> ${e.toString()}")
                    }

                }

            })
    }

    private fun initItemsData(body: Element?) {
        /**

            条目结构
            -div carBody
            --ul []
            ---li
            ----a <img> avatar
            ----div class=media-body

         */
        val container: Element? = body?.getElementById("body")?.getElementsByClass("container")?.get(0)
        val row = container?.getElementsByClass("row")?.get(0)
        Log.e(TAG,"row=> $row")
        val cardBody: Element? = row?.getElementsByClass("col-lg-9 main")?.get(0)?.getElementsByClass("card card-threadlist ")?.get(0)?.getElementsByClass("card-body")?.get(0)?.getElementsByClass("list-unstyled threadlist mb-0")?.get(0)
        Log.e(TAG,"cardBody=> $cardBody")
        val itemDiv = cardBody?.getElementsByClass("media-body")
        Log.e(TAG,"item length${itemDiv?.size}items=> $itemDiv")


        val contentBeans = mutableListOf<IndexContentItemBean>()
        if (itemDiv != null)
        loop@ for (i in 0 until itemDiv.size){
            val itemA = itemDiv[i].getElementsByClass("subject break-all")[0].select("a").first()
            val link = itemA.attr("href")
            var name = itemA.select("span")?.first()?.text()
            val authorElement = cardBody?.getElementsByClass("ml-1 mt-1 mr-3")?.get(i)
            val authorLink = authorElement?.attr("href")
            val authorImage = authorElement?.getElementsByTag("img")?.first()?.attr("src")

            if (TextUtils.isEmpty(name)){
                name = itemA.text()
            }
            var item = IndexContentItemBean(name,(i+1).toString(),link,authorImage,"",authorLink)
            Log.e(TAG,"real items = ${item.toString()}")

            contentBeans.add(item)

        }

        initHotAuthor(row)

        items.postValue(contentBeans)
    }

    private fun initHotAuthor(row: Element?) {
        Log.e(TAG,"row initHotAuthor => $row")
        var hotList = mutableListOf<HotAuthor>()

        var cardDiv = row?.getElementsByClass("col-lg-3 d-none d-lg-block aside")?.first()
        Log.e(TAG,"cardDiv_$cardDiv")


        // select通过class查找某一标签
        var tagDiv = row?.select("div[class=card]")?.first()

        Log.e(TAG, "tagDiv=> $tagDiv")

//        var asideElement = cardDiv?.getElementsByClass("card")?.first()
//        Log.e(TAG,"aside_$asideElement")
        var hotDiv = tagDiv?.getElementById("taghot")
        Log.e(TAG,"hotDiv_$hotDiv")
        var hotElements = hotDiv?.select("ul")?.first()
            ?.select("li")

        Log.e(TAG,"hotElements\n$hotElements")
        hotElements?.let {
            loop@ for (i in 0 until hotElements.size){
                var liElement = hotElements.select("li")[i]
                var aElement = liElement.select("a")?.first()
                var name = aElement?.text()
                var id = aElement?.attr("href")
                var hotAuthor = HotAuthor(name!!,id!!)
                hotList.add(hotAuthor)
                Log.e(TAG,"item=> $hotAuthor")
            }
        }

        dashBoard.postValue(hotList)
    }


    fun initTabLayout(mTab: MutableList<NavBean>): String {
        Log.d(TAG,"=初始化= length=> "+mTab.size)
//        var tabLayout = rootView.findViewById<View>(R.id.tabLayout) as TabLayout
        var text :String = ""
        loop@ for (i in 0 until mTab.size){
            Log.e(TAG,"tab item => ${mTab[i].toString()}")

//            var tabItem = tabLayout.newTab()
//            tabItem.text = mTab[i].name
//            tabLayout.addTab(tabItem)
            text = if(i == mTab.size-1){
                text + mTab[i].name
            }else{
//                text + "," + mTab[i].name
                text + mTab[i].name + ","
            }
            Log.d(TAG, "text=> $text")
        }
        Log.d(TAG, "text= $text")

        return text
    }


}