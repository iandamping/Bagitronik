package com.binar.bagitronik.model.dealings

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
data class StatusDealings(
        @field:SerializedName("id") var id: Int?,
        @field:SerializedName("status") var status: String?,
        @field:SerializedName("created_at") var createdAt: String?,
        @field:SerializedName("updated_at") var updatedAt: String?

)