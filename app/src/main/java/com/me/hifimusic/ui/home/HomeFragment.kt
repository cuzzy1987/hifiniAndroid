package com.me.hifimusic.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.me.hifimusic.R
import com.me.hifimusic.adapter.IndexContentAdapter
import com.me.hifimusic.base.BaseActivity
import com.me.hifimusic.base.BaseFragment
import com.me.hifimusic.bean.IndexContentItemBean
import com.me.hifimusic.bean.NavBean
import com.me.hifimusic.ui.search.SearchActivity
import com.me.hifimusic.ui.WebActivity
import com.me.hifimusic.ui.login.LoginActivity
import com.me.hifimusic.ui.player.PlayerActivity
import com.me.hifimusic.util.GlideUtils
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_search_layout.*

class HomeFragment : BaseFragment() {

    val TAG: String = "HomeFragment"
    lateinit var mAdapter: IndexContentAdapter
    private lateinit var homeViewModel: HomeViewModel

    var curTag :String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        activity!!.window.statusBarColor = resources.getColor(R.color.color_race_yellow)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        initView()
        initData()
        initSearch()
        initLogin()
        initTest()
    }

    private fun initTest() {
    }

    private fun initLogin() {
        GlideUtils.loadRoundImageViaDrawable(avatarIv,this.context!!,R.mipmap.avatar)
        avatarIv.setOnClickListener {
            LoginActivity.startActivity(this.context!!)
        }
    }

    private fun initSearch() {
        searchContentRl.setOnClickListener { SearchActivity.startActivity(context!!) }
    }

    private fun initData() {
        mAdapter = IndexContentAdapter(R.layout.item_index_content_layout)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = mAdapter
        if(context != null)homeViewModel.getData(activity as Context)


    }

    private fun initView() {
        homeViewModel.tabs.observe(this, Observer {
            initTabs(homeViewModel.tabs)
        })
        homeViewModel.items.observe(this, Observer {
            initItems(homeViewModel.items)
        })
    }

    private fun initItems(items: MutableLiveData<MutableList<IndexContentItemBean>>) {
        // 处理item事件
        Log.e(TAG,"item size=> ${items.value!!.size}")
        if (items.value != null){
            mAdapter.data = items.value!!
            mAdapter.notifyDataSetChanged()
        }
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val url = (adapter.data[position] as IndexContentItemBean).link
            Log.e(TAG,"url=$url")
            WebActivity.startActivity(this.context!!,url!!)
            //            PlayerActivity.startActivity(this.context!!,"")

        }


        mAdapter.setOnItemLongClickListener{
            adapter, view, position ->
            (activity as BaseActivity).showToast("长安点赞三连",Toast.LENGTH_LONG)
            val id = (adapter.data[position] as IndexContentItemBean).link
            PlayerActivity.startActivity(this.context!!,id!!)
            true
        }
    }

    private fun initTabs(tabs: MutableLiveData<MutableList<NavBean>>) {
        val tabList = tabs.value
        if (tabList!=null){
            loop@ for (i in 0 until tabList.size){
                val tabItem = tabLayout.newTab()
                val iconText = tabList[i].name
                tabItem.text = iconText
                tabLayout.addTab(tabItem)
                tabItem.customView = initTabItem(iconText)
            }
        }
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    private fun initTabItem(iconText: String): View? {
        val itemView = layoutInflater.inflate(R.layout.tab_item_layout,null)
        val iconTv = itemView.findViewById<TextView>(R.id.icon_text)
        iconTv.text = iconText
        return itemView
    }


}