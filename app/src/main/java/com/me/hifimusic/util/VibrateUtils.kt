package com.me.hifimusic.util

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Vibrator



object VibrateUtils {

    fun longClickVibrate(context: Context){
        val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(100)
    }

}