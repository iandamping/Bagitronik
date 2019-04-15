package com.binar.bagitronik.model.profile

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 10/04/2019.
Github = https://github.com/iandamping
 */
data class CountTypeData(
        @field:SerializedName("Perangkat Komputer") val perangkatKomputer: Int?,
        @field:SerializedName("Telepon Selular") val teleponSelular: Int?,
        @field:SerializedName("Kamera") val kamera: Int?,
        @field:SerializedName("Aksesoris Elektronik") val aksesorisElektronik: Int?
) {
}