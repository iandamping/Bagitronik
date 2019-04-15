package com.binar.bagitronik.model.dealings

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
data class ProvinceDealings(
        @field:SerializedName("id") val id: Int?,
        @field:SerializedName("province") val province: String?
) {
}