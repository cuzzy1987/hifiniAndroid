package com.me.hifimusic.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.me.hifimusic.R

class LoadingDialog : AlertDialog {


    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading_layout)
    }

    companion object{
        fun create(context: Context,text: String,cancelable: Boolean): LoadingDialog{
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            val view: View = View.inflate(context,R.layout.dialog_loading_layout,null)
            val loadingIv = view.findViewById<ImageView>(R.id.loadingIv)
            val textView = view.findViewById<TextView>(R.id.msgTv)
            if (!TextUtils.isEmpty(text)){
                textView.text = text
            }else{
                textView.visibility = View.GONE
            }
            val anim: ObjectAnimator = ObjectAnimator.ofFloat(loadingIv, "rotation", 0f, 360f)
            anim.duration = 1000
            anim.repeatCount = -1
            anim.interpolator = LinearInterpolator()
            anim.start()
            val dialog = LoadingDialog(context,R.style.LoadingProgressDialog)
            dialog.setCancelable(false)
            dialog.setContentView(view,params)
            return dialog
        }
    }
}