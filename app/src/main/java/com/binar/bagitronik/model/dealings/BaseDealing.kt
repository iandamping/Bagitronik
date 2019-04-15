package com.binar.bagitronik.model.dealings

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
data class BaseDealing<U, P, T, S>(
        @field:SerializedName("id")
        val id: Int?,

        @field:SerializedName("note")
        val note: String?,

        @field:SerializedName("user")
        val user: U?,

        @field:SerializedName("productnya")
        val productNya: P?,

        @field:SerializedName("productku")
        val productKu: T?,

        @field:SerializedName("status")
        val status: S?
) {
}