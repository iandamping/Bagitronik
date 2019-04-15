package com.binar.bagitronik.ui.fragment.home

import com.binar.bagitronik.base.BaseFragmentView
import com.binar.bagitronik.model.product.ProductType
import com.binar.bagitronik.model.product.UserProducts

/**
 *
Created by Ian Damping on 25/03/2019.
Github = https://github.com/iandamping
 */
interface HomeFragmentView : BaseFragmentView {
    fun onFailedGetData(msg: String)
    fun onGetPerangkatKomputerData(data: List<UserProducts>)
    fun onGetTeleponSelularData(data: List<UserProducts>)
    fun onGetKameraData(data: List<UserProducts>)
    fun onGetAksesorisElektronikData(data: List<UserProducts>)
    fun onGetTypeData(data: List<ProductType>?)

}