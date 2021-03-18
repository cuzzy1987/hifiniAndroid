package com.me.hifimusic.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.me.hifimusic.R
import com.me.hifimusic.adapter.MainPagerAdapter
import com.me.hifimusic.base.BaseActivity
import com.me.hifimusic.ui.dashboard.DashboardFragment
import com.me.hifimusic.ui.home.HomeFragment
import com.me.hifimusic.ui.notifications.NotificationsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var pagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initView() {
        val tabs = mutableListOf<String>()
        val fragments = mutableListOf<Fragment>()
        tabs.add("首页")
        tabs.add("发现")
        tabs.add("我的")
        fragments.add(HomeFragment())
        fragments.add(DashboardFragment())
        fragments.add(NotificationsFragment())

        pagerAdapter = MainPagerAdapter(fragments,tabs,supportFragmentManager)
        pagerAdapter.mList = fragments
        viewPager.offscreenPageLimit = 1
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initData() {

    }
}
