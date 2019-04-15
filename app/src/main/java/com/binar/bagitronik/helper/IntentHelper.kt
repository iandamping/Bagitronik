package com.binar.bagitronik.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.binar.bagitronik.R

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
inline fun <reified T : Activity> FragmentActivity.startActivity(
        options: Bundle? = null, noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.init()
    startActivity(intent, options)
}


inline fun <reified T : Activity> Context.startActivity(
        options: Bundle? = null, noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.init()
    startActivity(intent, options)
}


inline fun <reified T : Activity> FragmentActivity.startActivityWithData() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Any> newIntent(ctx: Context): Intent = Intent(ctx, T::class.java)

fun FragmentManager.switchFragment(savedInstanceState: Bundle?, target: Fragment) {
    if (savedInstanceState == null) {
        this.beginTransaction().replace(R.id.main_container, target).commit()
    }
}
