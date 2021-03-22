package com.me.hifimusic.bean

data class IndexContentItemBean(
    var name: String?,
    var id: String,
    var link: String?,
    var authorImage: String?,
    var author: String?,
    var authorLink: String?) {

    override fun toString(): String {
        return "IndexContentItemBean(name=$name, id='$id', link=$link, authorImage=$authorImage, author=$author, authorLink=$authorLink)"
    }
}

data class HotAuthor(
    var name:String,
    var id:String
    ){

    override fun toString(): String {
        return "HotAuthor(name='$name', id='$id')"
    }
}