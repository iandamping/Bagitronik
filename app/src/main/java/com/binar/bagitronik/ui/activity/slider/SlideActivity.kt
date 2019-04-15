package com.binar.bagitronik.ui.activity.slider

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.binar.bagitronik.BagitronikApp
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.Constant.alreadySlider
import com.binar.bagitronik.helper.fullScreenAnimation
import com.binar.bagitronik.helper.gone
import com.binar.bagitronik.helper.startActivity
import com.binar.bagitronik.model.profile.UserDataLogin
import com.binar.bagitronik.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_slide.*
import org.jetbrains.anko.support.v4.onPageChangeListener

class SlideActivity : AppCompatActivity(), View.OnClickListener {
    //    private var layouts: IntArray = intArrayOf(R.layout.first_slide, R.layout.second_slide, R.layout.third_slide)
    private var userData: UserDataLogin? = null
    private lateinit var dots: Array<ImageView>
    private lateinit var mAdapter: PageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_slide)
        if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        prefHelper.saveStringInSharedPreference(alreadySlider, "slider on")

        userData = BagitronikApp.gson.fromJson(
                BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey),
                UserDataLogin::class.java
        )
        mAdapter = if (!userData?.token.isNullOrBlank()) {
            PageAdapter(this, true)
        } else {
            PageAdapter(this, false)

        }
        pager.adapter = mAdapter
        createDots(0)
        btnSkip.setOnClickListener(this)
        pager.onPageChangeListener {
            onPageSelected {
                initVisibility(it)
                createDots(it)
            }
        }
    }

    private fun initVisibility(data: Int) {
        lnDots.gone()
        if (data < 2) {
            lnDots.gone()
        }
        if (data > 1) {
            btnSkip.gone()
        } else if (data == 2) {
            btnSkip.animate().alpha(1F)
            lnDots.gone()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btnSkip -> {

                startActivity<MainActivity>().also {
                    finish()
                }
//                if (userData?.token.isNullOrBlank()) {
//                    startActivity<LoginActivity>().also {
//                        finish()
//                    }
//                } else {
//                    startActivity<MainActivity>().also {
//                        finish()
//                    }
//                }

            }
        }

    }


    fun createDots(position: Int) {
        if (lnDots != null) {
            lnDots.removeAllViews()
        }
        dots = Array(3, { i -> ImageView(this) })
        for (i in 0..3 - 1) {
            dots[1] = ImageView(this)
            if (i == position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots))
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dots))
            }

            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(4, 0, 4, 0)
            lnDots.addView(dots[i], params)

        }

    }


}