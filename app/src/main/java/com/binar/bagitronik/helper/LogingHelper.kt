package com.binar.bagitronik.helper

import android.util.Log

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
inline fun <reified T> T.logD(msg: String?) {
    val tag = T::class.java.simpleName
    Log.d(tag, msg)
}

inline fun <reified T> T.logE(msg: String?) {
    val tag = T::class.java.simpleName
    Log.e(tag, msg)
}