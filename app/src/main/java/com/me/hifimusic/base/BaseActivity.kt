package com.me.hifimusic.base

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

public open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    public fun showToast(msg: String,length: Int){
        Toast.makeText(this,msg,length).show()
    }
}