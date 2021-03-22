package com.me.hifimusic.ui.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.task.DownloadTask
import kotlinx.android.synthetic.main.activity_player.*
import com.dueeeke.videocontroller.StandardVideoController
import com.me.hifimusic.R
import com.me.hifimusic.base.BaseActivity
import com.me.hifimusic.widget.DashboardView
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import java.io.File





//todo 先获取播放地址 播放。然后下载资源文件

class PlayerActivity : BaseActivity(){
//    ,DownloadListener {
//
//    override fun onDownloadStart(
//        url: String?,
//        userAgent: String?,
//        contentDisposition: String?,
//        mimetype: String?,
//        contentLength: Long
//    ) {
//        Log.e("PlayerActivity ","url$url")
//    }


    private lateinit var handler: Handler

    private var url: String = "https://ws.stream.qqmusic.qq.com/C400004SYnFK4AN85O.m4a?guid=9527&vkey=3C92CD433C714733FD263AE7D337E6E2D432CE28CA4CF15614ADDC70B9E0CA54AE2EFE556AA51F80B3E69465365A3E9EC40B4873B2B00F67&uin=7983&fromtag=86"

    companion object{
        fun startActivity(context: Context,id: String){
            var intent: Intent = Intent(context,PlayerActivity::class.java)
            intent.putExtra("id",id)
            context.startActivity(intent)
        }
    }

    lateinit var viewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = resources.getColor(R.color.color_black)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        initPermission()
        initData()
        initView()
    }

    private fun initPermission() {
        AndPermission.with(this)
            .runtime()
            .permission(Permission.Group.STORAGE)
            .onGranted { permissions ->

                // Storage permission are allowed.
            }
            .onDenied { permissions ->
                // Storage permission are not allowed.
            }
            .start()
    }

    private fun initData() {
        handler = Handler()

//        url = "https://hifini.com/get_music.php?key=krwz9AtNAUE204o5cYpdj8DDuLn7UzSoj8MuFpA9bfsN1NGvRWxrpgjWKXrMCOnuSMrqF8A3O/X1uvg60VDJYGU"
        viewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)
        val id = intent.getStringExtra("id")
        id?.let { viewModel.getPlayUrl(id,this) }
        // takeIf takeUnless


        Aria.download(this).register()
    }

    private fun initView() {

        initPlayer()
//        initDownload()
        initDashboardView()
    }

    private fun initDashboardView() {
//        var array = arrayOf<String>("1111111","222222","33333","4444444","55555555")
////        var array = arrayOf<String>()
//
//        dashboardView.setTextArray(array)
//        dashboardView.setOnItemClickListener(object: DashboardView.OnItemClickListener{
//            override fun onItemClick(position: Int) {
//                Log.e("bobobobobob","position_$position")
//            }
//
//        })
    }

    private fun initDownload() {
        var path = "${filesDir}/music/nmsl.mp3"
        var file = File(path,"demo")
        if (!file.exists()){
            file.mkdir()
        }
        Aria.download(this)
            .load(url)
            .setFilePath(path,true)
            .create()
    }

//
//    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning fun running(task: DownloadTask) {
        if(task.key == url){

        }
        var p = task.percent	//任务进度百分比
        var speed: String = task.convertSpeed    //转换单位后的下载速度，单位转换需要在配置文件中打开
        var speed1: Long = task.speed //原始byte长度速度

        Log.e("download ","running ${task.toString()}")
    }

    @Download.onTaskComplete fun taskComplete(task: DownloadTask) {
        //在这里处理任务完成的状态
        Log.d("download ","onComplete ${task.toString()} ")
    }

    private fun initPlayer() {
//        "http://m10.music.126.net/20210306200033/fb2872b008e2088562e41a5c01db7471/ymusic/obj/w5zDlMODwrDDiGjCn8Ky/2969444142/ef0d/c300/2606/1ad5e7abf88b5b3c0399f8806f697329.mp3"
//        val url = "http://m10.music.126.net/20210306182646/ee6126c5b1ffd0e90991bed2f6ddf358/ymusic/obj/w5zDlMODwrDDiGjCn8Ky/2969444142/ef0d/c300/2606/1ad5e7abf88b5b3c0399f8806f697329.mp3"
//        player.setUrl(url) //设置视频地址
        val controller = StandardVideoController(this)
        controller.addDefaultControlComponent("标题", false)
//        controller.background = GlideUtils.loadUrl2Drawable("https://p2.music.126.net/94GmQM2QkeB0G3TqJ0IFdQ==/109951165081557942.jpg?param=130y130")
//        controller.background = resources.getDrawable(R.mipmap.pbg)
        player.setVideoController(controller) //设置控制器
//        player.start() //开始播放，不调用则不自动播放
    }



    override fun onResume() {
        super.onResume()
//        handler.postDelayed( { dashboardView.resume() }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
//        dashboardView.destory()
    }

}
