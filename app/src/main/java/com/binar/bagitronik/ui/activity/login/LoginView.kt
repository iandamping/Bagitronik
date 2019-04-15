package com.binar.bagitronik.ui.activity.login

import com.binar.bagitronik.base.BaseView

/*
Created by ian 10/March/2019
 */
interface LoginView : BaseView {
    fun onSuccessLogin(msg: String)
    fun onFailedLogin(msg: String)
}