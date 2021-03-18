package com.me.hifimusic.ui.player

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.me.hifimusic.base.NetworkScheduler
import com.me.hifimusic.bean.MusicData
import com.me.hifimusic.net.base.ApiResponse
import com.me.hifimusic.net.base.Network
import org.jsoup.Jsoup
import java.lang.Exception
import java.util.HashMap

class PlayerViewModel: ViewModel() {

    private val TAG: String = PlayerViewModel::class.java.simpleName

    var script: String = ""

    var music = MutableLiveData<MusicData>().apply {  }

    fun getPlayUrl(id: String,context: Context){
        Network.instance.api
            .getPlayerInfo(id)
            .compose(NetworkScheduler.compose())
            .subscribe(object: ApiResponse<String>(context){
                override fun success(data: String) {
                    Log.e(TAG,"success_$data")
                    initUrl(data,context)
//                    getResource("get_music.php?key=MZaSbSv1nMWXskAyolbkkQmzoztVLLpZ2ts5G+AjjL8Vn6aOtZ/phtg4lhOOb+0c",context)
                }

                override fun fail(code: Int, msg: String) {

                }

            })
    }

    // 获取真正的播放地址
    private fun getResource(musicData: MusicData, context: Context){
        Log.d(TAG,"请求地址是$musicData")

        Network.instance.api
            .getResource(initMusicPaths(musicData.url),initMusicParams(musicData.url))
            .compose(NetworkScheduler.compose())
            .subscribe(object: ApiResponse<String>(context){
                override fun success(data: String) {
                    Log.e(TAG,"endData= $data")
                }

                override fun fail(code: Int, msg: String) {
                }

            })
    }

    private fun initMusicParams(url: String): HashMap<String, String> {
        var params: HashMap<String,String> = HashMap()

        try {

            if (url.contains("?")){
                val param  = url.substring(IntRange(url.indexOfFirst{it == '?'}+1,url.length-1))
                if(param.contains("=")){
                    var key = param.substring(IntRange(0,param.indexOfFirst { it == '=' }-1))
                    var value = param.substring((param.indexOfFirst { it == '=' })+1)
                    Log.d(TAG,"key= ${key}__value= $value")
                    params[key] = value
                }
            }
        }catch (e: Exception){
            e.message?.let {
                Log.d(TAG,e.message)
            }
        }
        return params
    }

    /**
     * get_music.php?key=k+Jk+g8fVRQ204o5cYpdj9SYuqG2Hmrzk4NiAIxjMecM3YnpEWFr5gCxJAM
     */
    private fun initMusicPaths(url: String): String {
        var path: String = ""
        try{
            if (url.contains("?")){
                path = url.substring(IntRange(0,(url.indexOfFirst{it == '?'})-1))
            }
        }catch (e: Exception){
            e.message?.let {
                Log.d(TAG,e.message)
            }
        }
        Log.d(TAG,"music path = $path")
        return path
    }

    private fun initUrl(data: String,context: Context) {
        Log.e(TAG,"-=initUrl=-")
        var doc = Jsoup.parseBodyFragment(data)
        var element = doc.body().getElementById("body")?.
            getElementsByClass("container")?.first()?.
            allElements?.first()?.
//            getElementsByClass("row u_2006")?.first()?.
            getElementsByClass("col-lg-9 main")?.first()?.
            getElementsByClass("jan card card-thread")?.first()?.
            getElementsByClass("card-body")?.first()?.
            getElementsByClass("message break-all")?.first()

        Log.e(TAG,"script_$element")

        var scripts = element?.select("script")

        Log.e(TAG,"scriptList_$scripts")
        var position = -1
        scripts?.let {
            loop@for(i in 0 until scripts.size){
                var item = scripts[i]
                if (!item.hasAttr("src")){
//                    script = item
                    Log.e(TAG,"i=$i")
                    position = i
                    break
                }
            }

            if (position > -1){
                var msc = scripts[position].toString()
                Log.e(TAG,"!!script_${msc}")

                /**
                 * <script>
                var ap4 = new APlayer({
                element: document.getElementById('player4'),
                narrow: false,
                autoplay: false,
                preload: 'none',
                showlrc: false,
                mutex: true,
                theme: '#ad7a86',
                music: [
                {
                title: '蓝色土耳其',
                author:'周传雄',
                url: 'get_music.php?key=/a9WzaRsvxtwMIw70Qy8lS1dAw3NOS5Rf/EQqsxLJN/eC2B5x9pfRRjWGJr7f6+yJ//N6b0O741Rn3NFyH2m+g',
                pic: 'https://y.gtimg.cn/music/photo_new/T002R300x300M000001Vj7aX0PixwV.jpg'
                },

                ]
                });
                </script>
                 */
                // 取第一个右括号
                // 取最后一个左括号
                var start: Int = msc.indexOfLast { it == '{' }
                var end: Int = msc.indexOfFirst { it == '}' }
                var mscObjStr = msc.subSequence(start,end+1)
                Log.e(TAG,"mscObjStr$mscObjStr")

                var musicData: MusicData? = Gson().fromJson(mscObjStr.toString(),MusicData::class.java)
//                musicObj.getAsJsonObject(mscObjStr.toString())
//                Log.e(TAG,musicObj.asString)
                Log.e(TAG,"musicData$musicData")

                musicData?.let {
                    if (musicData.url.startsWith("get_music")){
                        // url是getMusic开头需要再次请求获取真正的播放地址
                        getResource(musicData,context)
                    }else{
                        music.postValue(musicData)
                    }
                }
            }


        }

    }

}