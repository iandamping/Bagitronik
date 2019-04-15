package com.binar.bagitronik.ui.activity.editprofile

import com.binar.bagitronik.base.BaseView
import com.binar.bagitronik.model.profile.AllUser
import com.binar.bagitronik.model.profile.CitiesData
import com.binar.bagitronik.model.profile.User

/**
 *
Created by Ian Damping on 31/03/2019.
Github = https://github.com/iandamping
 */
interface EditProfileView : BaseView {
    fun onFailedGetData(msg: String)
    fun onSuccesUpdateData(msg: String)
    fun onGetCitiesData(data: List<CitiesData>?)
    fun onGetDefaultData(data: User?)
    fun onGetPassedData(data: AllUser?)
}