package com.binar.bagitronik.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
fun ViewGroup.inflates(layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}