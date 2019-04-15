package com.binar.bagitronik.ui.activity.register

import com.binar.bagitronik.base.BaseView

/**
 *
Created by Ian Damping on 05/04/2019.
Github = https://github.com/iandamping
 */
interface RegisterView : BaseView {
    fun onSuccessRegister()
    fun onFailedRegister(msg: String)
}