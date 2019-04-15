package com.binar.bagitronik.ui.activity.listproduct

import android.content.Context
import com.binar.bagitronik.BagitronikApp.Companion.api
import com.binar.bagitronik.base.BasePresenter
import com.binar.bagitronik.helper.executes

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class AllProductPresenter(private val mView: AllProductView) : BasePresenter() {

    override fun onCreate(context: Context) {
        mView.initView()
    }

    fun getAllDataByType(tipe: Int?) {
        if (tipe != null && tipe != 0) {
            api.getAllProduct(tipe.toString()).executes({
                mView.onFailedGetData(it.localizedMessage)
            }, {
                if (it.code() == 200) {
                    mView.onGetAllListData(it.body())
                } else {
                    mView.onFailedGetData("Error Happen")
                }
            })
        }
    }
}