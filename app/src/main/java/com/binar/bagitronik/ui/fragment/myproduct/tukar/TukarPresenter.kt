package com.binar.bagitronik.ui.fragment.myproduct.tukar

import android.content.Context
import android.view.View
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.api.ApiClient
import com.binar.bagitronik.api.ApiInterface
import com.binar.bagitronik.base.BaseFragmentPresenter
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.executes
import com.binar.bagitronik.model.dealings.*
import com.binar.bagitronik.model.profile.UserDataLogin

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
class TukarPresenter(private val mView: TukarView) : BaseFragmentPresenter() {
    private var apiWithHeader: ApiInterface? = null
    private var tempData = mutableListOf<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>()
    private val userData =
            gson.fromJson(prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

    override fun onAttach(context: Context?) {
        apiWithHeader = ApiClient.getRetrofitWithMultiPart(userData?.token).create(ApiInterface::class.java)
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }

    fun getTukarData() {
        apiWithHeader?.getAllDealings()?.executes({
            mView.onFailedGetDetailedData(it.localizedMessage)
        }, {
            if (it.code() == 200) {
                tempData.clear()
                it.body()?.forEach { data ->
                    if (data.productNya?.category?.id == 2) {
                        if (data.productNya.users?.id == userData?.username?.id) {
                            if (data.status?.id != 4) {
                                tempData.add(data)
                            }
                        } else if (data.user?.id == userData?.username?.id) {
                            if (data.status?.id != 4) {
                                tempData.add(data)
                            }
                        }
                    }
                }
                if (tempData.size == 0) {
                    mView.onGetEmptyListData()
                } else {
                    mView.onGetTukar(tempData)
                }
            } else {
                mView.onFailedGetDetailedData("Error Happen")
            }
        })
    }
}