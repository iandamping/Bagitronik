package com.binar.bagitronik.ui.fragment.myproduct.donasi

import com.binar.bagitronik.base.BaseFragmentView
import com.binar.bagitronik.model.dealings.*

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
interface DonasiView : BaseFragmentView {
    fun onGetDonation(data: List<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>?)
    fun onFailedGetDetailedData(msg: String)
    fun onGetEmptyListData()
}