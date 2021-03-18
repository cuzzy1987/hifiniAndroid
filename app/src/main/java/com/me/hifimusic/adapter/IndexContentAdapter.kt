package com.me.hifimusic.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.me.hifimusic.R
import com.me.hifimusic.bean.IndexContentItemBean

class IndexContentAdapter(layoutResId: Int) :
    BaseQuickAdapter<IndexContentItemBean, BaseViewHolder>(layoutResId) {

    val TAG:String = "IndexContentAdapter"

    override fun convert(holder: BaseViewHolder, item: IndexContentItemBean) {

        Log.e(TAG,"item ${item.toString()}")
        holder.setText(R.id.topicTv,item.name).setText(R.id.subTv,item.id)
        var imageView = holder.getView<ImageView>(R.id.headIv) as ImageView
        Glide.with(context).load("https://hifini.com/${item.authorImage}")
            .placeholder(R.mipmap.logo)
            .thumbnail(0.3f)
//            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .apply(RequestOptions.bitmapTransform(RoundedCorners(18)))
            .into(imageView)

    }

    override fun setOnItemClick(v: View, position: Int) {
        super.setOnItemClick(v, position)

    }


}