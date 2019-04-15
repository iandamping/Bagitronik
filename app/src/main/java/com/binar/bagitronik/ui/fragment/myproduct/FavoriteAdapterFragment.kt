package com.binar.bagitronik.ui.fragment.myproduct

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.binar.bagitronik.model.profile.UserDataLogin
import com.binar.bagitronik.ui.fragment.myproduct.donasi.DonasiFragment
import com.binar.bagitronik.ui.fragment.myproduct.myproduct.MyProductFragment
import com.binar.bagitronik.ui.fragment.myproduct.tukar.TukarFragment

class FavoriteAdapterFragment(fragmentManager: FragmentManager, private val userData: UserDataLogin?) : FragmentStatePagerAdapter(fragmentManager) {
    private val page_count: Int = 3
    private val tabTitle = arrayOf("Barangku", "Donasi", "Tukar")


    override fun getItem(position: Int): Fragment {
        var fragment: Fragment = MyProductFragment().newInstance(userData?.username?.id)
        when (position) {
            1 -> fragment = DonasiFragment()
            2 -> fragment = TukarFragment()
        }
        return fragment
    }

////todo helper
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        if (position >= count) {
//            val manager = (`object` as Fragment).fragmentManager
//            val trans = manager!!.beginTransaction()
//            trans.remove(`object`)
//            trans.commit()
//        }
//    }

    override fun getCount(): Int {
        return page_count
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitle[position]
    }
}