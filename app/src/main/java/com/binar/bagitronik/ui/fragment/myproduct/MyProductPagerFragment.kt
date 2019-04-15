package com.binar.bagitronik.ui.fragment.myproduct

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.bagitronik.BagitronikApp
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.inflates
import com.binar.bagitronik.model.profile.UserDataLogin

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
class MyProductPagerFragment : Fragment() {
    private lateinit var vpFavPager: ViewPager
    private lateinit var tabFavPager: TabLayout
    private val userData = BagitronikApp.gson.fromJson(
            BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey),
            UserDataLogin::class.java
    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_my_item)
        vpFavPager = views?.findViewById(R.id.vpMyItemPage) as ViewPager
        tabFavPager = views.findViewById(R.id.tabMyItemPage) as TabLayout
        tabFavPager.setSelectedTabIndicatorColor(Color.parseColor("#1554A1"))
        tabFavPager.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#235EA7"))
        tabFavPager.setupWithViewPager(vpFavPager)
        vpFavPager.offscreenPageLimit = 3
        vpFavPager.adapter = buildAdapter()
        return views
    }


    private fun buildAdapter(): PagerAdapter {
        return FavoriteAdapterFragment(childFragmentManager, userData)
    }

}