package com.binar.bagitronik.ui.activity.detailbarter

import com.binar.bagitronik.base.BaseView
import com.binar.bagitronik.model.dealings.*

/**
 *
Created by Ian Damping on 02/04/2019.
Github = https://github.com/iandamping
 */
interface DetailBarterView : BaseView {
    fun onFailedGetDetailedData(msg: String)
    fun onGetTukarData(data: BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>?, status: Boolean?)
}