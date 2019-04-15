package com.binar.bagitronik.ui.fragment.myproduct.myproduct

import android.content.Context
import android.view.View
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.api.ApiClient
import com.binar.bagitronik.api.ApiInterface
import com.binar.bagitronik.base.BaseFragmentPresenter
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.executes
import com.binar.bagitronik.model.product.UserProducts
import com.binar.bagitronik.model.profile.UserDataLogin

/**
 *
Created by Ian Damping on 30/03/2019.
Github = https://github.com/iandamping
 */
class MyProductPresenter(private val mView: MyProductView) : BaseFragmentPresenter() {
    private var ctx: Context? = null
    private var apiWithHeader: ApiInterface? = null
    private val userData =
            gson.fromJson(prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)
    private var tempData = mutableListOf<UserProducts>()


    override fun onAttach(context: Context?) {
        this.ctx = context
        apiWithHeader = ApiClient.getRetrofitWithMultiPart(userData.token).create(ApiInterface::class.java)
    }


    override fun onCreateView(view: View) {
        mView.initView(view)
    }

    fun onGetMyProduct(userId: Int?) {
        if (apiWithHeader != null) {
            apiWithHeader?.getMyProducts()?.executes({
                mView.onFailedGetData(it.localizedMessage)
            }, {
                if (it.code() == 200) {
                    tempData.clear()
                    it.body()?.forEach { data ->
                        userId?.let {
                            if (it.equals(data.userData?.id)) {
                                tempData.add(data)
                                mView.onGetAllListData(tempData)
                            }
                        }
                    }
                    if (it.body()?.size == 0) {
                        mView.onGetEmptyListData()
                    }
                } else {
                    mView.onFailedGetData("Error Happen")
                }
            })
        }
    }
}