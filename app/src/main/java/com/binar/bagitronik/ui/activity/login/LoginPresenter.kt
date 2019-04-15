package com.binar.bagitronik.ui.activity.login

import android.content.Context
import com.binar.bagitronik.BagitronikApp.Companion.api
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.base.BasePresenter
import com.binar.bagitronik.helper.Constant.loginDialog
import com.binar.bagitronik.helper.Constant.userPrefKey
import com.binar.bagitronik.helper.executes

/*
Created by ian 10/March/2019
 */
class LoginPresenter(private val mView: LoginView) : BasePresenter() {
    override fun onCreate(context: Context) {
        setBaseDialog(context, loginDialog)
        mView.initView()
    }

    fun loginUser(data: Map<String, String>?) {
        setDialogShow(false)
        data?.let {
            api.loginUser(it).executes({
                setDialogShow(true)
                mView.onFailedLogin(it.localizedMessage)
            }, {
                setDialogShow(true)
                if (it.code() == 200) {
                    mView.onSuccessLogin("Anda Telah Login")
                    prefHelper.saveStringInSharedPreference(userPrefKey, gson.toJson(it.body()))
                } else {
                    mView.onFailedLogin("Email atau Kata Sandi anda salah")
                }
            })
        }
    }

}