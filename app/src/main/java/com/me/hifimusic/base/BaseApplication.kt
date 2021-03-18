package com.me.hifimusic.base

import android.app.Application
import android.content.Context
import com.dueeeke.videoplayer.exo.ExoMediaPlayerFactory
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory
import com.dueeeke.videoplayer.player.AndroidMediaPlayerFactory
import com.dueeeke.videoplayer.player.VideoViewConfig
import com.dueeeke.videoplayer.player.VideoViewManager
import com.me.hifimusic.net.base.Network
import com.me.hifimusic.util.HFLog
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class BaseApplication : Application() {

    companion object {
        private var _context: Application? = null
        fun instance(): Context {
            return _context!!
        }
    }



    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init() {
        _context = this
        initLog()
        initNetwork()
        initPlayer()

    }

    private fun initNetwork() {
        Network.instance.init()
    }

    private fun initPlayer() {
        VideoViewManager.setConfig(
            VideoViewConfig.newBuilder()
            //使用使用IjkPlayer解码
            .setPlayerFactory(IjkPlayerFactory.create())
            //使用ExoPlayer解码
//            .setPlayerFactory(ExoMediaPlayerFactory.create())
            //使用MediaPlayer解码
//            .setPlayerFactory(AndroidMediaPlayerFactory.create())
            .build())
    }

    private fun initLog() {
        HFLog.init()
    }





}