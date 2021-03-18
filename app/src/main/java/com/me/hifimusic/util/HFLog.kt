package com.me.hifimusic.util

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy


class HFLog {

    companion object {

        fun init(){
            /**
             * FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(1)         // (Optional) How many method line to show. Default 2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
            .logStrategy(new CustomLogCatStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
            .tag("logger")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build();
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
             */

            val strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(1)
                .methodOffset(7)
//            .logStrategy(CustomLogCatStrategy())
                .tag("=log__")
                .build()
            Logger.addLogAdapter(AndroidLogAdapter(strategy))
        }

        // 是否是调试模式
        var isDebug = true

        inline fun d(TAG: String, msg: String) {
            if (isDebug) {
                Logger.d(TAG, msg)
            }
        }

        inline fun i(TAG: String, msg: String) {
            if (isDebug) {
                Logger.i(TAG, msg)
            }
        }

        inline fun e(TAG: String, msg: String) {
            if (isDebug) {
                Logger.e(TAG, msg)
            }
        }

        inline fun v(TAG: String, msg: String) {
            if (isDebug) {
                Logger.v(TAG, msg)
            }
        }

        inline fun w(TAG: String, msg: String) {
            if (isDebug) {
                Logger.w(TAG, msg)
            }
        }

        inline fun json(TAG: String, msg: String?) {
            if (isDebug) {
                Logger.json(msg)
            }
        }

        inline fun xml(TAG: String, msg: String) {
            if (isDebug) {
                Logger.xml(msg)
            }
        }
    }

}