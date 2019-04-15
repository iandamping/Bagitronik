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
data class User(
        @field:SerializedName("id") var id: Int?,
        @field:SerializedName("username") var username: String?,
        @field:SerializedName("email") var email: String?,
        @field:SerializedName("password_digest") var passwordDigest: String?,
        @field:SerializedName("first_name") var firstName: String?,
        @field:SerializedName("last_name") var lastName: String?,
        @field:SerializedName("address") var address: String?,
        @field:SerializedName("phone") var phone: String?,
        @field:SerializedName("nik") var nik: String?,
        @field:SerializedName("created_at") var createdAt: String?,
        @field:SerializedName("updated_at") var updatedAt: String?,
        @field:SerializedName("role_id") var roleId: Int?,
        @field:SerializedName("city_id") var cityId: Int?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null, null, null, null, null, null, null)
}