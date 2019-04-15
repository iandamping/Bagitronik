package com.binar.bagitronik.ui.activity.listproduct

import com.binar.bagitronik.base.BaseView
import com.binar.bagitronik.model.product.UserProducts

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
interface AllProductView : BaseView {
    fun onGetAllListData(data: List<UserProducts>?)
    fun onFailedGetData(msg: String)
}