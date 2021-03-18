package com.me.hifimusic.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.me.hifimusic.R
import java.io.InputStream
import java.net.URL

object GlideUtils {

    fun loadRoundImg(imageView: ImageView,context: Context
                     ,url: String,defImg: Int=R.mipmap.logo,defCorners: Int=18){
        Glide.with(context).load(url)
            .placeholder(defImg)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(defCorners)))
            .into(imageView)
    }

    fun loadRoundImageViaDrawable(imageView: ImageView, context: Context, drawable: Int, corners: Int=18){
        Glide.with(context)
            .load(drawable)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(corners)))
            .into(imageView)
    }

//    fun loadUrl2Drawable(url: String): Drawable{
//        val inputStream: InputStream  = URL(url).content as InputStream
//        var drawable: Drawable = Drawable.createFromStream(inputStream,"src")
//        inputStream.close()
//        return drawable
//    }
}