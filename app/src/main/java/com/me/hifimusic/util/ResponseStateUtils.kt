package com.me.hifimusic.util

import org.json.JSONException
import org.json.JSONObject

object ResponseStateUtils {

    fun notNull(json: String): Boolean {
        var jsonO: JSONObject? = null
        try {
            jsonO = JSONObject(json)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val rsp = jsonO!!.optString("rsp")
        return  "succ" == rsp
    }
}