package com.binar.bagitronik.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.binar.bagitronik.helper.Constant.switchBackToMain
import com.binar.bagitronik.helper.fullScreenAnimation
import com.binar.bagitronik.helper.startActivity
import com.binar.bagitronik.helper.switchFragment
import com.binar.bagitronik.ui.activity.uploadpage.UploadActivity
import com.binar.bagitronik.ui.fragment.home.HomeFragment
import com.binar.bagitronik.ui.fragment.landfills.LandfillsFragment
import com.binar.bagitronik.ui.fragment.myproduct.MyProductPagerFragment
import com.binar.bagitronik.ui.fragment.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var switchBack: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(com.binar.bagitronik.R.layout.activity_main)
        initBottomNav()

        switchBack = intent.getStringExtra(switchBackToMain)
        moveToSpesificFragment(switchBack)
    }

    private fun moveToSpesificFragment(dataCallback: String?) {
        if (dataCallback != null && dataCallback.contentEquals("1")) {
            supportFragmentManager.switchFragment(null, HomeFragment())
            bottom_navigation.selectedItemId = com.binar.bagitronik.R.id.navigation_home
        } else if (dataCallback != null && dataCallback.contentEquals("2")) {
            supportFragmentManager.switchFragment(null, LandfillsFragment())
            bottom_navigation.selectedItemId = com.binar.bagitronik.R.id.navigation_locator
        } else if (dataCallback != null && dataCallback.contentEquals("5")) {
            supportFragmentManager.switchFragment(null, ProfileFragment())
            bottom_navigation.selectedItemId = com.binar.bagitronik.R.id.navigation_profile
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return when (p0.itemId) {
            com.binar.bagitronik.R.id.navigation_home -> {
                supportFragmentManager.switchFragment(null, HomeFragment())
                true
            }
            com.binar.bagitronik.R.id.navigation_locator -> {
                supportFragmentManager.switchFragment(null, LandfillsFragment())
                true
            }
            com.binar.bagitronik.R.id.navigation_upload -> {
                startActivity<UploadActivity>().also {
                    finish()
                }
                true
            }
            com.binar.bagitronik.R.id.navigation_barang -> {
                supportFragmentManager.switchFragment(null, MyProductPagerFragment())
                true
            }
            com.binar.bagitronik.R.id.navigation_profile -> {
                supportFragmentManager.switchFragment(null, ProfileFragment())
                true
            }
            else -> super.onOptionsItemSelected(p0)
        }
    }

//    private fun loadMainFragment(savedInstanceState: Bundle?) {
//        if (savedInstanceState == null) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .replace(com.binar.bagitronik.R.id.main_container, MyProductPagerFragment(), MyProductPagerFragment::class.java.simpleName)
//                    .commit()
//        }
//    }

    private fun initBottomNav() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(com.binar.bagitronik.R.id.main_container, HomeFragment())
                .commit()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setMessage("Anda yakin ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Iya") { dialog, id ->
                    super.onBackPressed()

                }
                .setNegativeButton("Tidak", null)
                .show()
    }
}
