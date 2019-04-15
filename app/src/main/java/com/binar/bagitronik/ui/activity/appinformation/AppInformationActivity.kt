package com.binar.bagitronik.ui.activity.appinformation

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.BuildConfig
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.fullScreenAnimation
import com.binar.bagitronik.helper.startActivity
import com.binar.bagitronik.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_request.*
import org.jetbrains.anko.alert


class AppInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(com.binar.bagitronik.R.layout.activity_request)
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                startActivity<MainActivity> {
                    putExtra(Constant.switchBackToMain, "4")
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity<MainActivity> {
            putExtra(Constant.switchBackToMain, "4")
            finish()
        }
    }

    private fun initView() {
        initToolbar()
        tvLogout.setOnClickListener {
            alert("Ingin Keluar ?") {
                this.positiveButton("YA") {
                    prefHelper.deleteSharedPreference()
                    it.dismiss()
                    startActivity<MainActivity>()
                }
                this.negativeButton("Batal") {
                    it.dismiss()
                }
            }.show()
        }
        tvPrivacyPolicy.setOnClickListener {
            val url = "http://bagitronik.xyz/privacy.html"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        tvApkVersionInformation.text = "ver " + BuildConfig.VERSION_CODE

    }

    private fun initToolbar() {
        val upArrow = ContextCompat.getDrawable(this, com.binar.bagitronik.R.drawable.ic_arrow_back_blue_24dp)
        upArrow?.setColorFilter(resources.getColor(com.binar.bagitronik.R.color.colorComment), PorterDuff.Mode.SRC_ATOP)
        toolbarDetailInformation.title = "Pengaturan"
        toolbarDetailInformation.setTitleTextColor(resources.getColor(com.binar.bagitronik.R.color.colorComment))
        toolbarDetailInformation.setSubtitleTextColor(resources.getColor(com.binar.bagitronik.R.color.colorComment))
        setSupportActionBar(toolbarDetailInformation)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }
}
