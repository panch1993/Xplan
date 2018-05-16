package com.pans.xplan.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.pans.xplan.interfaces.GetTitleImpl

/**
 * @author android01
 * @date 2018/5/15.
 * @time 下午2:38.
 */
class MainPagerAdapter(fm: FragmentManager, val list: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence {
        val getTitleImpl = list[position] as GetTitleImpl
        return getTitleImpl.getTitleString()
    }
}