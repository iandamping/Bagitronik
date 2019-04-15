package com.binar.bagitronik.helper

import android.content.Context
import android.widget.Toast

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
fun Context.myToast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}