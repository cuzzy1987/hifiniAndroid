package com.me.hifimusic.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.me.hifimusic.R
import com.me.hifimusic.base.BaseActivity
import com.me.hifimusic.config.Constants
import com.me.hifimusic.util.AppPreference
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.include_tab_layout.*

class WebActivity : BaseActivity() {

    val TAG = "WebActivity"

    lateinit var url: String

    companion object{
        fun startActivity(context: Context,url: String){
            var intent: Intent = Intent(context,WebActivity::class.java)
            intent.putExtra("url",url)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)window.decorView.systemUiVisibility =  View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        initData()
        initView()
    }

    private fun initView() {
        backRl.setOnClickListener{
            onBackPressed()
        }
        initWebView()
        url = "https://hifini.com/${url}"
        initCookie(url)
        webView.loadUrl(url)
        titleTv.text = webView.title
    }

    private fun initCookie(url: String) {
        val cookies = AppPreference.getInstance().getList(Constants.cookies)
        val cookieManager = CookieManager.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            cookieManager.removeAllCookies(null)
            cookieManager.flush()
        }else{
            cookieManager.removeSessionCookie()
            CookieSyncManager.getInstance().sync()
        }

        cookieManager.setAcceptCookie(true)
        cookies?.let {
            loop@ for (i in 0 until cookies.size){
                cookieManager.setCookie(url,cookies[i])
            }
        }
    }

    private fun initWebView() {
        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccess = true
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true

        webView.webViewClient = object: WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
    }

    private fun initData() {
        url = intent.getStringExtra("url")!!
        Log.e(TAG,"url$url")
    }

    override fun onBackPressed() {
        webView?.let {
            if (webView.canGoBack()){
                webView.goBack()
            }else{
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView?.let {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webView.clearHistory()
            (webView.parent as ViewGroup).removeView(webView)
            webView.destroy()
        }
    }
}
