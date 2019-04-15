package com.binar.bagitronik.model.profile

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 09/04/2019.
Github = https://github.com/iandamping
 */
data class AllUser(
        @field:SerializedName("id") val id: Int?,
        @field:SerializedName("username") val username: String?,
        @field:SerializedName("email") val email: String?,
        @field:SerializedName("first_name") val firstName: String?,
        @field:SerializedName("last_name") val lastName: String?,
        @field:SerializedName("address") val address: String?,
        @field:SerializedName("phone") val phone: String?,
        @field:SerializedName("nik") val nik: String?,
        @field:SerializedName("images") val userImage: ImageUser?,
        @field:SerializedName("products") val products: AllUserCounts?,
        @field:SerializedName("tukar") val tukar: AllUserCounts?,
        @field:SerializedName("donasi") val donasi: AllUserCounts?,
        @field:SerializedName("role") val roleUser: RoleUser?,
        @field:SerializedName("city") val cityUser: CitiesData?
) {

    data class AllUserCounts(@field:SerializedName("count") val count: Int?)
}