package com.binar.bagitronik.ui.fragment.myproduct.tukar

import com.binar.bagitronik.base.BaseFragmentView
import com.binar.bagitronik.model.dealings.*

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
interface TukarView : BaseFragmentView {
    fun onGetTukar(data: List<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>?)
    fun onFailedGetDetailedData(msg: String)
    fun onGetEmptyListData()
}