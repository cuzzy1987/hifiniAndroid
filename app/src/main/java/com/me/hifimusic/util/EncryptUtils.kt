package com.me.hifimusic.util

import java.security.MessageDigest

object EncryptUtils {


    fun sha256(intput:String): String {

        val instance = MessageDigest.getInstance("SHA-256")

        val digest = instance.digest(intput.toByteArray())

        println("SHA-256加密前 ： " + digest.size)

        return toHex(digest)

    }


    fun md5(input:String): String {

        val md = MessageDigest.getInstance("MD5")

        val digest = md.digest(input.toByteArray())

        println("MD5加密前 ： " + digest.size)

        return toHex(digest)

    }

    fun sha1(intput:String): String {

        val instance = MessageDigest.getInstance("SHA-1")

        val digest = instance.digest(intput.toByteArray())

        println("SHA-1加密前 ： " + digest.size)

        return toHex(digest)

    }

    fun toHex(byteArray:ByteArray): String {

        var str =with(StringBuilder()){

            //转为字符串

            byteArray.forEach {

                //        println(it)

                var value =it

                var hex = value.toInt() and  (0xFF)

                val toHexString = Integer.toHexString(hex)

                if (toHexString.length ==1){

                    this.append("0" + toHexString)

                }else{

                    this.append(toHexString)

                }

            }

            println(this.length)

            return this.toString()

        }

        return str

    }
}