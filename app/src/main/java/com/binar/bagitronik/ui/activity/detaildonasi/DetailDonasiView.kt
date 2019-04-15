package com.binar.bagitronik.ui.activity.detaildonasi

import com.binar.bagitronik.base.BaseView
import com.binar.bagitronik.model.dealings.*

/**
 *
Created by Ian Damping on 02/04/2019.
Github = https://github.com/iandamping
 */
interface DetailDonasiView : BaseView {
    fun onGetDonasiData(data: BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>?, status: Boolean?)
    fun onFailedGetDetailedData(msg: String)
//    fun onGetUserRequestData(imageUrl:String?, userName:String?, userAddres:String?)
}