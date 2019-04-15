package com.binar.bagitronik.ui.activity.detaillandfills

import android.content.Context
import com.binar.bagitronik.BagitronikApp.Companion.api
import com.binar.bagitronik.base.BasePresenter
import com.binar.bagitronik.helper.executes

/**
 *
Created by Ian Damping on 10/04/2019.
Github = https://github.com/iandamping
 */
class DetailLandfillsPresenter(private val mView: DetailLandfillsView) : BasePresenter() {

    override fun onCreate(context: Context) {
        setBaseDialog(context, "a")
        mView.initView()
    }

    fun onGetLandfillsData(landfillsId: Int?) {
        setDialogShow(false)
        api.getLandfillsDataById(landfillsId.toString()).executes({
            setDialogShow(true)
            mView.onFailGetData(it.localizedMessage)
        }, {
            setDialogShow(true)
            if (it.code() == 200) {
                mView.onGetDetailLandFills(it.body())
            } else {
                mView.onFailGetData("Error Happen")
            }
        })
    }
}