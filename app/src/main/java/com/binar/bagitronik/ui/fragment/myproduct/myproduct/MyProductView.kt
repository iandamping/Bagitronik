package com.binar.bagitronik.ui.fragment.myproduct.myproduct

import com.binar.bagitronik.base.BaseFragmentView
import com.binar.bagitronik.model.product.UserProducts

/**
 *
Created by Ian Damping on 30/03/2019.
Github = https://github.com/iandamping
 */
interface MyProductView : BaseFragmentView {
    fun onGetAllListData(data: List<UserProducts>?)
    fun onGetEmptyListData()
    fun onFailedGetData(msg: String)
}