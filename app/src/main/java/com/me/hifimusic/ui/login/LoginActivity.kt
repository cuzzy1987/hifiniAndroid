package com.me.hifimusic.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.me.hifimusic.R
import com.me.hifimusic.base.BaseActivity
import com.me.hifimusic.config.Constants
import com.me.hifimusic.util.AppPreference
import com.me.hifimusic.util.EncryptUtils
import com.me.hifimusic.util.FullScreenUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.include_tab_layout.backRl

class LoginActivity : BaseActivity() {

    val TAG: String = LoginActivity::class.java.simpleName

    lateinit var viewModel: LoginViewModel

    companion object{
        fun startActivity(context: Context){
            val intent = Intent(context,LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initData()
        initView()
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
//        viewModel.getCode(this)
    }

    private fun initScreen() {
        FullScreenUtils.fullScreen(this)
    }

    private fun initView() {
        backRl.setOnClickListener{finish()}
        initCode()
        initLogin()
        accountEt.setText("oncemore")
        passwordEt.setText("hifini")
        accountEt.setSelection("oncemore".length)
        passwordEt.setSelection("hifini".length)
    }

    private fun initLogin() {
        loginTv.setOnClickListener {
            login()
        }
    }

    private fun login() {
        var account: String = accountEt.text.toString()
        var pwd: String = passwordEt.text.toString()
        var code: String = codeEt.text.toString()
        viewModel.login(this,account,EncryptUtils.md5(pwd),code)
    }

    private fun initCode() {
        initCodeImg()
        codeIv.setOnClickListener {
            showToast("图片刷新",Toast.LENGTH_SHORT)
            initCodeImg()
        }



    }

    private fun initCodeImg() {
        val cookie = AppPreference.getInstance().getList(Constants.cookies)
        var builder = LazyHeaders.Builder()
//        cookie?.let {
//            loop@ for (i in 0 until cookie.size){
//                Log.e(TAG,"cookie value=> ${cookie[i]}")
//                if(cookie[i].contains("bbs_sid")){
//                    builder.addHeader("Cookie",cookie[i])
//                    =break
//                }
//            }
//        }
        builder.addHeader("Cookie","bbs_sid=l7skv915ouqg55a0fa421hbm49")
        Log.e(TAG,"cookie size=> ${cookie}\nbuilder${builder.build().headers}")

        val glideUrl = GlideUrl("https://hifini.com/vcode.htm",builder.build())
        Glide.with(this.baseContext).load(glideUrl)
//            .placeholder(R.mipmap.logo)
            .override(codeIv.width,codeIv.height)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(codeIv)
    }
}
