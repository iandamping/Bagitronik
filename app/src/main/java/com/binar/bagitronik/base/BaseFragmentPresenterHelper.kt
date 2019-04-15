package com.binar.bagitronik.base

import android.content.Context
import android.view.View

/**
 *
Created by Ian Damping on 25/03/2019.
Github = https://github.com/iandamping
 */
interface BaseFragmentPresenterHelper {
    fun onAttach(context: Context?)
    fun onCreateView(view: View)
}