package com.binar.bagitronik.model.dealings

import com.binar.bagitronik.model.product.UserProducts
import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
data class UserDealings(
        @field:SerializedName("id") val id: Int?,
        @field:SerializedName("username") val username: String?,
        @field:SerializedName("email") val email: String?,
        @field:SerializedName("first_name") val firstName: String?,
        @field:SerializedName("last_name") val lastName: String?,
        @field:SerializedName("address") val address: String?,
        @field:SerializedName("phone") val phone: String?,
        @field:SerializedName("nik") val nik: String?,
        @field:SerializedName("images") val images:UserProducts.ImagesData,
        @field:SerializedName("role") val roleId: RoleDealings?,
        @field:SerializedName("city") val cityId: CityDealings?
) {
}