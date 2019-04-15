package com.binar.bagitronik.ui.fragment.profile

import android.content.Context
import android.view.View
import com.binar.bagitronik.BagitronikApp
import com.binar.bagitronik.api.ApiClient
import com.binar.bagitronik.api.ApiInterface
import com.binar.bagitronik.base.BaseFragmentPresenter
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.executes
import com.binar.bagitronik.model.profile.UserDataLogin

/**
 *
Created by Ian Damping on 31/03/2019.
Github = https://github.com/iandamping
 */
class ProfilePresenter(private val mView: ProfileView) : BaseFragmentPresenter() {
    private var ctx: Context? = null
    private val userData = BagitronikApp.gson.fromJson(BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)
    private var apiWithHeader: ApiInterface? = null

    override fun onAttach(context: Context?) {
        this.ctx = context
        setBaseDialog(ctx, Constant.dialogCommonResponse)
        apiWithHeader = ApiClient.getRetrofitWithMultiPart(userData.token).create(ApiInterface::class.java)
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }

    fun getInitailUserData() {
        setDialogShow(false)
        if (apiWithHeader != null) {
            apiWithHeader?.getUserByID(userData?.username?.id?.toString())?.executes({
                setDialogShow(true)
                mView.onFailGetData(it.localizedMessage)
            }, {
                setDialogShow(true)
                if (it.code() == 200) {
                    mView.onSuccesGetData(it.body())
                } else {
                    mView.onFailGetData("Error Happen")
                }
            })
        }
    }
}