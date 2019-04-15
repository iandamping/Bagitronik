package com.binar.bagitronik.model.profile

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 09/04/2019.
Github = https://github.com/iandamping
 */
data class RoleUser(
        @field:SerializedName("id") val id: Int?,
        @field:SerializedName("role") val role: String?
) {
}