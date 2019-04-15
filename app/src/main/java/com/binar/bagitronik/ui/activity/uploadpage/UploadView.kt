package com.binar.bagitronik.ui.activity.uploadpage

import com.binar.bagitronik.base.BaseView
import com.binar.bagitronik.model.product.ProductType
import com.binar.bagitronik.model.product.UserProducts

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
interface UploadView : BaseView {
    fun onGetDataType(data: List<ProductType>?)
    fun onSuccessUpload(userId: Int?)
    fun onSuccessDeleteProduct(msg: String)
    fun onFailedDeleteProduct(msg: String)
    fun onFailedUpload(msg: String)
    fun onGetPassedDataType(data: UserProducts?)
}