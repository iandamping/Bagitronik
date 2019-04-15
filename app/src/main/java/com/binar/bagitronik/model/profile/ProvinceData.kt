package com.binar.bagitronik.model.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 *
Created by Ian Damping on 31/03/2019.
Github = https://github.com/iandamping
 */
@Parcelize
data class ProvinceData(
        @field:SerializedName("id") val id: Int?,
        @field:SerializedName("province") val province: String?
) : Parcelable {
}