package com.binar.bagitronik.model.dealings

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
data class UserProductDealings(
        @field:SerializedName("id") val id: Int?,
        @field:SerializedName("first_name") val firstName: String?,
        @field:SerializedName("last_name") val lastName: String?,
        @field:SerializedName("phone") val phone: String?
) {
}