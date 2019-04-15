package com.binar.bagitronik.ui.activity.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.R
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.Constant.alreadySlider
import com.binar.bagitronik.helper.fullScreenAnimation
import com.binar.bagitronik.helper.startActivity
import com.binar.bagitronik.model.profile.UserDataLogin
import com.binar.bagitronik.ui.activity.MainActivity
import com.binar.bagitronik.ui.activity.slider.SlideActivity

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds
    private val userData =
            gson.fromJson(prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)
    private val slideChecker = prefHelper.getStringInSharedPreference(alreadySlider)
    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            if (slideChecker.equals("slider on", ignoreCase = true)) {
                startActivity<MainActivity>().also {
                    finish()
                }

//                if (userData != null) {
//                    if (userData.token != null) {
//                        startActivity<MainActivity>().also {
//                            finish()
//                        }
//                    } else {
//                        startActivity<LoginActivity>().also {
//                            finish()
//                        }
//                    }
//                } else {
//                    startActivity<LoginActivity>().also {
//                        finish()
//                    }
//                }
            } else {
                startActivity<SlideActivity>().also {
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_splash)
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}