package com.binar.bagitronik.model.dealings

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
data class RoleDealings(
        @field:SerializedName("id") var id: Int?,
        @field:SerializedName("role") var role: String?
) {
}