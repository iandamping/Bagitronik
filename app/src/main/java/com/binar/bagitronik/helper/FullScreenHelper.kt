package com.binar.bagitronik.helper

import android.support.v4.app.FragmentActivity
import android.view.Window
import android.view.WindowManager
import com.binar.bagitronik.R

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
fun FragmentActivity.fullScreenAnimation() {
    this.overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity)
    this.requestWindowFeature(Window.FEATURE_NO_TITLE)
    this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}