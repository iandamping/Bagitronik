package com.binar.bagitronik.model.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 *
Created by Ian Damping on 30/03/2019.
Github = https://github.com/iandamping
 */
@Parcelize
data class UserDataLogin(
        @field:SerializedName("token") var token: String?,
        @field:SerializedName("user") var username: User?
) : Parcelable {
    constructor() : this(null, null)

}