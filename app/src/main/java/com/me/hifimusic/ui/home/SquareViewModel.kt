package com.benlai.android.community.model
/*

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.benlai.constant.AppConst
import com.android.benlai.request.resultError
import com.android.benlai.request.resultSuccess
import com.benlai.android.community.bean.SquareBean
import com.benlai.android.community.bean.TopicData
import com.benlai.android.community.repository.CommunityRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class SquareViewModel : ViewModel() {
    private var pageIndex = 1
    private var sysNo = -1
    private var randomSeed = "0";

    private val squareBeanLiveData by lazy { MutableLiveData<SquareBean>()}
    private val squareTopicList by lazy { MutableLiveData<ArrayList<TopicData>>() }
    private val itemPositionLike by lazy { MutableLiveData<Int>() }

    private val loadTopicSize by lazy { MutableLiveData<Int>() }
    val errorInfo :ObservableField<String> = ObservableField()
    fun getSquareBeans(): MutableLiveData<SquareBean> {
        return squareBeanLiveData
    }

    fun getSquareTopicLists():MutableLiveData<ArrayList<TopicData>> {
        return squareTopicList
    }

    fun getItemPositionLikes():MutableLiveData<Int>{
        return itemPositionLike
    }

    fun getLoadTopicSizes():MutableLiveData<Int>{
        return loadTopicSize
    }

    fun getPageIndex(): Int{
        return pageIndex
    }

    */
/**
     *广场标签信息和广场轮播图
     *//*

     fun loadSquareBean(refresh:Boolean = false){
        viewModelScope.launch (Dispatchers.Main){
            CommunityRepo.getSquareInfo().resultSuccess { squareBean ->
                if(refresh){
                    reqRefreshTopicList()
                    squareBean.isOnlyRefreshList = true
                }else{
                    if(squareBean.tags!=null && squareBean.tags!!.isNotEmpty()){
                        sysNo = squareBean.tags!![0].sysNo
                        loadSquareTopicList(true)
                        squareBean.isOnlyRefreshList = false
                    }
                }
                squareBeanLiveData.postValue(squareBean)
            }.resultError {
                squareBeanLiveData.postValue(null)
                errorInfo.set(it.msg)
            }
        }
    }

    */
/**
     * 广场文章列表
     *//*

     fun loadSquareTopicList(refresh:Boolean = false) {
        if(refresh){
            randomSeed = getRandomString(10)
        }
        var lastSysNo = 0
        if(squareTopicList.value !=null && squareTopicList.value!!.size>0){
            lastSysNo = squareTopicList.value!![squareTopicList.value!!.size-1].sysNo
        }

        viewModelScope.launch (Dispatchers.Main){
            val pair = CommunityRepo.getSquareTopicList(sysNo, lastSysNo, pageIndex, AppConst.LIMIT, randomSeed)
            if(pair.second == randomSeed){
                pair.first.resultSuccess {topicList->
                    if(refresh){
                        squareTopicList.value?.clear()
                    }
                    if(squareTopicList.value!=null){ //加载更多的逻辑
                        squareTopicList.value!!.addAll(topicList)
                        squareTopicList.postValue(squareTopicList.value)
                    }else{
                        squareTopicList.postValue(topicList as java.util.ArrayList<TopicData>?)
                    }
                    loadTopicSize.postValue(topicList.size)
                }.resultError {
                    loadTopicSize.postValue(0)
                    squareTopicList.postValue(null)
                    errorInfo.set(it.msg)
                }
            }
        }
    }

    */
/**
     * 重置page 和sysNO
     * *//*

    fun updatePageAndSysNo(sysNo: Int, index: Int) {
        this.sysNo = sysNo
        this.pageIndex = index
    }

    */
/**
     * 刷新页面
     * *//*

    fun reqRefreshTopicList(){
        this.pageIndex = 1
        loadSquareTopicList(true)
    }

    */
/***
     * 加载更多
     *//*

    fun loadMore(){
        this.pageIndex += 1
        loadSquareTopicList()
    }


    */
/**
     * 点赞topic
     * *//*


    fun likeTopic(topic: TopicData, adapterPosition: Int){
        viewModelScope.launch (Dispatchers.Main){
            CommunityRepo.likeTopic(topic.sysNo).resultSuccess {
                if(!topic.like){
                    topic.likeCount+=1
                }
                topic.like = true
                itemPositionLike.postValue(adapterPosition)
            }.resultError {
                errorInfo.set(it.msg)
            }
        }
    }

    */
/**
     * 取消点赞
     * *//*

    fun cancelLikeTopic(topic: TopicData, adapterPosition: Int){
        viewModelScope.launch(Dispatchers.Main) {
            CommunityRepo.cancelLikeTopic(topic.sysNo).resultSuccess {
                if(topic.likeCount>=1 && topic.like){
                    topic.likeCount-=1
                }
                topic.like = false
                itemPositionLike.postValue(adapterPosition)
            }.resultError {
                errorInfo.set(it.msg)
            }
        }
    }

    //length用户要求产生字符串的长度
    private fun getRandomString(length:Int):String{
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val sb = StringBuffer()
        for (i in 0 until length){
            val number=random.nextInt(62);
            sb.append(str[number]);
        }
        return sb.toString();
 }
}*/