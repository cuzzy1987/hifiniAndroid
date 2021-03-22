package com.me.hifimusic.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.me.hifimusic.R
import com.me.hifimusic.adapter.IndexContentAdapter
import com.me.hifimusic.base.BaseActivity
import com.me.hifimusic.bean.IndexContentItemBean
import com.me.hifimusic.ui.WebActivity
import com.me.hifimusic.ui.player.PlayerActivity
import com.me.hifimusic.util.HFLog
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    var page: Int = 1

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, SearchActivity::class.java))
        }

        fun startActivity(context: Context,name: String,id: String){
            val intent = Intent(context,SearchActivity::class.java)
            intent.putExtra("name",name)
            intent.putExtra("id",id)
            context.startActivity(intent)
        }
    }

    private val TAG: String = SearchActivity::class.java.name
    lateinit var searchViewModel: SearchViewModel
    lateinit var mAdapter: IndexContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initData()
        initView()
    }

    private fun initData() {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        searchViewModel.items.observe(this, Observer {
            initItems(searchViewModel.items)
        })

        val name = intent.getStringExtra("name")
        Log.e(TAG, "name=> $name")
        name?.let {
            searchViewModel.getSearchData(this,name.toString(),page.toString())
            contentEt.hint = name
        }
    }

    private fun initItems(items: MutableLiveData<MutableList<IndexContentItemBean>>) {
        HFLog.e(TAG,"item size=> ${items.value!!.size}")
        if (items.value != null){
            mAdapter.data = items.value!!
            mAdapter.notifyDataSetChanged()
        }

        mAdapter.setOnItemClickListener {adapter, view, position ->
            var url = (adapter.data[position] as IndexContentItemBean).link
            HFLog.e(TAG,"url=$url")
            WebActivity.startActivity(this,url!!)
        }

        mAdapter.setOnItemLongClickListener{
                adapter, _, position ->
            val id = (adapter.data[position] as IndexContentItemBean).link
            PlayerActivity.startActivity(this,id!!)
            true
        }
    }

    private fun initView() {
        this.window.statusBarColor = resources.getColor(R.color.color_race_yellow)
//        contentEt.requestFocus()
        mAdapter = IndexContentAdapter(R.layout.item_index_content_layout)
        initRecyclerView()

        contentEt.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
//                v.scaleY = 2f
            }else{
//                v.scaleY = 1f
            }
        }

        contentEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                searchViewModel.getSearchData(this,contentEt.text.toString(),page.toString())
                false
            }else{
                true
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
    }
}
