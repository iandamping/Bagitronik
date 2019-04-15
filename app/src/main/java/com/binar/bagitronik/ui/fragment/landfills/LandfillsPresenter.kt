package com.binar.bagitronik.ui.fragment.landfills

import android.content.Context
import android.view.View
import com.binar.bagitronik.BagitronikApp.Companion.api
import com.binar.bagitronik.base.BaseFragmentPresenter
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.executes

/**
 *
Created by Ian Damping on 10/04/2019.
Github = https://github.com/iandamping
 */
class LandfillsPresenter(private val mView: LandfillsView) : BaseFragmentPresenter() {
    private var ctx: Context? = null
    override fun onAttach(context: Context?) {
        this.ctx = context
        setBaseDialog(ctx, Constant.dialogCommonResponse)
        onGetLandfillsData()
    }

    override fun onCreateView(view: View) {
        mView.initView(view)
    }

    fun onGetLandfillsData() {
        setDialogShow(false)
        api.getAllLandfillsData().executes({
            setDialogShow(true)
            mView.onFailGetData(it.localizedMessage)
        }, {
            setDialogShow(true)
            if (it.code() == 200) {
                mView.onSuccesGetData(it.body())
            } else {
                mView.onFailGetData("Terjadi kesalahan")
            }
        })
    }
}