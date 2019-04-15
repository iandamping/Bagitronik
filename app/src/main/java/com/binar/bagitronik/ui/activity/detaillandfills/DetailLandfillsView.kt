package com.binar.bagitronik.ui.activity.detaillandfills

import com.binar.bagitronik.base.BaseView
import com.binar.bagitronik.model.landfills.LandfillsData

/**
 *
Created by Ian Damping on 10/04/2019.
Github = https://github.com/iandamping
 */
interface DetailLandfillsView : BaseView {
    fun onGetDetailLandFills(data: LandfillsData?)
    fun onFailGetData(msg: String)
}