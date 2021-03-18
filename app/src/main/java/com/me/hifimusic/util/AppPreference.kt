package com.me.hifimusic.util

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Base64
import com.me.hifimusic.base.BaseApplication
import okhttp3.Cookie
import java.io.*
import java.util.*

class AppPreference private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    @Throws(IOException::class)
    fun saveHistoryAccounts(prefKey: String, accounts: LinkedList<String>) {
        val baos = ByteArrayOutputStream()
        var oos: ObjectOutputStream? = null
        try {
            oos = ObjectOutputStream(baos)
            oos.writeObject(accounts)
            val data = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
            setSharedPreferences(prefKey, data)
            oos.close()
            baos.close()
        } catch (e: IOException) {
            e.printStackTrace()
            oos?.close()
            baos?.close()
        }

    }



    @Throws(IOException::class)
    fun getList(prefKey: String): MutableList<String>? {
        var accounts: MutableList<String>? = null
        val data = sharedPreferences.getString(prefKey, null)
        if (TextUtils.isEmpty(data)) {
            return null
        }
        val base64 = Base64.decode(data!!.toByteArray(), Base64.DEFAULT)
        val bais = ByteArrayInputStream(base64)
        var ois: ObjectInputStream? = null
        try {
            ois = ObjectInputStream(bais)
            accounts = ois.readObject() as MutableList<String>
            ois.close()
            bais.close()
        } catch (e: StreamCorruptedException) {
            e.printStackTrace()
            ois?.close()
            bais?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            ois?.close()
            bais?.close()
        }

        return accounts
    }

    @Throws(IOException::class)
    fun saveList(prefKey: String, accounts: MutableList<String>?) {
        val baos = ByteArrayOutputStream()
        var oos: ObjectOutputStream? = null
        try {
            oos = ObjectOutputStream(baos)
            oos.writeObject(accounts)
            val data = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
            setSharedPreferences(prefKey, data)
            oos.close()
            baos.close()
        } catch (e: IOException) {
            e.printStackTrace()
            oos?.close()
            baos?.close()
        }

    }



    @Throws(IOException::class)
    fun getHistoryAccounts(prefKey: String): LinkedList<String>? {
        var accounts: LinkedList<String>? = null
        val data = sharedPreferences.getString(prefKey, null)
        if (TextUtils.isEmpty(data)) {
            return null
        }
        val base64 = Base64.decode(data!!.toByteArray(), Base64.DEFAULT)
        val bais = ByteArrayInputStream(base64)
        var ois: ObjectInputStream? = null
        try {
            ois = ObjectInputStream(bais)
            accounts = ois.readObject() as LinkedList<String>
            ois.close()
            bais.close()
        } catch (e: StreamCorruptedException) {
            e.printStackTrace()
            ois?.close()
            bais?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            ois?.close()
            bais?.close()
        }

        return accounts
    }

    fun setSharedPreferences(key: String?, value: String?) {
        if (key == null) {
            return
        }
        if (value != null) {
            sharedPreferences.edit().putString(key, value).apply()
        } else {
            sharedPreferences.edit().remove(key).apply()
        }
    }

    fun getSharePreferences(key: String?, value: String): String? {
        if (key == null) {
            return value
        }
        return if (!TextUtils.isEmpty(value)) {
            sharedPreferences.getString(key, value)
        } else sharedPreferences.getString(key, "")
    }

    fun getSharePreferences(key: String?): Boolean {
        return if (key == null) {
            false
        } else sharedPreferences.getBoolean(key, false)
    }

    fun getSharePreferences(key: String?, defaultValue: Boolean): Boolean {
        return if (key == null) {
            false
        } else sharedPreferences.getBoolean(key, defaultValue)
    }

    fun setSharedPreferences(key: String?, value: Boolean) {
        if (key == null) {
            return
        }
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    companion object {
        private lateinit var instance: AppPreference
        fun getInstance(): AppPreference {
//            if (null == instance) {
            return AppPreference(BaseApplication.instance())
//            }
//            return instance
        }
    }
}
