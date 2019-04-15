package com.binar.bagitronik.ui.activity.detail

import com.binar.bagitronik.base.BaseView
import com.binar.bagitronik.model.product.UserProducts

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
interface DetailProductView : BaseView {
    fun onSuccessGetDetailedData(data: UserProducts?)
    fun onGetAllListData(data: List<UserProducts>?)
    fun onSuccessCreateTukar(data: String?)
    fun onSuccessCreateDonasi(data: String?)
    fun onFailedGetDetailedData(msg: String)
}