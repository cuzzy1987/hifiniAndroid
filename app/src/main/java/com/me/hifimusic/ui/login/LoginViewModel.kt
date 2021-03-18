package com.me.hifimusic.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.hifimusic.base.NetworkScheduler
import com.me.hifimusic.bean.NavBean
import com.me.hifimusic.net.base.ApiResponse
import com.me.hifimusic.net.base.Network
import com.me.hifimusic.util.HFLog
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import org.jsoup.Jsoup

class LoginViewModel: ViewModel() {

    val TAG = "LoginViewModel"

    var result =  MutableLiveData<String>().apply {  }


    fun login(context: Context, account: String, password: String, code: String){
        val param = hashMapOf<String,String>()
        param["email"] = account
        param["password"] = password
        param["vcode"] = code

        Log.e(TAG,"login-parmas${param}")
        Network.instance.api.login(param)
            .compose(NetworkScheduler.compose())
            .subscribe(object: ApiResponse<String>(context){
                override fun fail(code: Int, msg: String) {
                    Log.e("LoginViewModel","fail=> $code msg$msg")
                }

                override fun success(data: String) {
                    Log.i("LoginViewModel","success data_${data}")
                    initLoginResult(data)
                }

            })
    }


    fun initLoginResult(data: String){
        val doc = Jsoup.parseBodyFragment(data)
        val data = doc.body().getElementById("body")?.getElementsByClass("container")?.first()
            ?.getElementsByClass("row")?.first()?.getElementsByClass("col-lg-8 mx-auto")?.first()
            ?.getElementsByClass("card mt-4")?.first()?.getElementsByClass("card-body")?.first()
            ?.getElementsByClass("card-title text-center mb-0")?.first()?.text()
        Log.e(TAG,"login result $data")

        result.postValue(data)
    }


}