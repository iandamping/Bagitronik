package com.binar.bagitronik.model.landfills

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 10/04/2019.
Github = https://github.com/iandamping
 */
data class LandfillsData(
        @field:SerializedName("id") val id: Int? = null,
        @field:SerializedName("link") val link: String?,
        @field:SerializedName("created_at") val created_at: String?,
        @field:SerializedName("updated_at") val updated_at: String?,
        @field:SerializedName("longitude") val longitude: String?,
        @field:SerializedName("latitude") val latitude: String?,
        @field:SerializedName("namatempat") val namatempat: String?,
        @field:SerializedName("alamat") val alamat: String?,
        @field:SerializedName("city_id") val city_id: String?,
        @field:SerializedName("catatan") val catatan: String?,
        @field:SerializedName("namapengelola") val namapengelola: String?,
        @field:SerializedName("kontakpengelola") val kontakpengelola: String?
) {
}