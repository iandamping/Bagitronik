package com.binar.bagitronik.ui.fragment.profile

import com.binar.bagitronik.base.BaseFragmentView
import com.binar.bagitronik.model.profile.AllUser

/**
 *
Created by Ian Damping on 31/03/2019.
Github = https://github.com/iandamping
 */
interface ProfileView : BaseFragmentView {
    fun onFailGetData(msg: String)
    fun onSuccesGetData(data: AllUser?)
}