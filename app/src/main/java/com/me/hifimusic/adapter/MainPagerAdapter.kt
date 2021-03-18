package com.me.hifimusic.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainPagerAdapter(var mList: MutableList<Fragment>, var tabs: MutableList<String>,
                       private val fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

    /**
     * 重写instantiateItem用于初始化fragment方式
     * 重写destroyItem用于控制销毁操方式
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment: Fragment = super.instantiateItem(container, position) as Fragment
        fm.beginTransaction().show(fragment).commit()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment: Fragment = `object`as Fragment
        fm.beginTransaction().hide(fragment).commit()
    }

    override fun getPageTitle(position: Int): String = tabs[position]

}