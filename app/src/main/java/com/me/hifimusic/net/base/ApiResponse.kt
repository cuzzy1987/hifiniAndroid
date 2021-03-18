package com.me.hifimusic.net.base

import android.content.Context
import android.util.Log
import com.me.hifimusic.widget.LoadingDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class ApiResponse<T> (private val context: Context): Observer<T> {

    private val TAG: String = "ApiResponse"
    var loadingDialog: LoadingDialog = LoadingDialog.create(context,"",false)

    abstract fun success(data: T)
    abstract fun fail(code: Int,msg: String)

    override fun onSubscribe(d: Disposable) {
        Log.e(TAG,"on subscribe")
    }

    override fun onNext(t: T) {
        Log.e(TAG,"on next ${t.toString()}")
        success(t)
    }

    override fun onComplete() {
        Log.e(TAG,"on complete")
//        loadingDialog.hide()
    }

    override fun onError(e: Throwable) {
        Log.e(TAG,"on error ${e.message}")
        fail(-1, "on fail")
    }


}