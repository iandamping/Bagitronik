package com.binar.bagitronik.ui.fragment.landfills

import com.binar.bagitronik.base.BaseFragmentView
import com.binar.bagitronik.model.landfills.LandfillsData

/**
 *
Created by Ian Damping on 10/04/2019.
Github = https://github.com/iandamping
 */
interface LandfillsView : BaseFragmentView {
    fun onFailGetData(msg: String)
    fun onSuccesGetData(data: List<LandfillsData>?)
}