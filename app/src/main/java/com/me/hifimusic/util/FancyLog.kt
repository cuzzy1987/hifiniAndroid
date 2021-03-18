package com.me.hifimusic.util

import android.util.Log

class FancyLog{

    companion object {
        val isOpenLog = true
        val length = 3 * 1024


        inline fun <reified T> log_i(log: Any): String {

            val i: String = if (isOpenLog) {
                Log.i(T::class.simpleName, log.toString())
                "complete"
            } else {
                "quit"
            }

            return i

        }

        inline fun <reified T> log_d(log: Any): String {
            val d: String = if (isOpenLog) {
                var string: String = log.toString()
                val strLength = string.length
                if (strLength > length) {
                    while (string.length > length) {
                        //先输出可满足长度的日志
                        Log.d(T::class.simpleName, string.substring(0, length))
                        string = string.substring(length, string.length)
                    }
                } else {
                    Log.w(T::class.simpleName, log.toString())
                }
                "complete"
            } else {
                "quit"
            }
            return d
        }

        inline fun <reified T> log_e(log: Any): String {
            val e: String = if (isOpenLog) {
                Log.e(T::class.simpleName, log.toString())
                "complete"
            } else {
                "quit"
            }
            return e
        }

    }
}