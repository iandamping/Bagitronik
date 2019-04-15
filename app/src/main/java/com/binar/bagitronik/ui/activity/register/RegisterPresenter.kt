package com.binar.bagitronik.ui.activity.register

import android.content.Context
import com.binar.bagitronik.BagitronikApp
import com.binar.bagitronik.BagitronikApp.Companion.api
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.base.BasePresenter
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.executes

/**
 *
Created by Ian Damping on 05/04/2019.
Github = https://github.com/iandamping
 */
class RegisterPresenter(private val mView: RegisterView) : BasePresenter() {

    override fun onCreate(context: Context) {
        setBaseDialog(context, "a")
        mView.initView()
    }

    fun createUser(data: Map<String, String>) {
        setDialogShow(false)
        api.createUser(data).executes({
            setDialogShow(true)
            mView.onFailedRegister(it.localizedMessage)
        }, {
            setDialogShow(true)
            if (it.code() == 201) {
                prefHelper.saveStringInSharedPreference(Constant.userPrefKey, BagitronikApp.gson.toJson(it.body()))
                mView.onSuccessRegister()
            } else {
                mView.onFailedRegister("Error Happen")
            }
        })
    }
}