package com.binar.bagitronik.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/*
Created by ian 10/March/2019
 */
@Parcelize
data class ProductType(
        @field:SerializedName("id") val id: Int?,
        @field:SerializedName("tipe") val tipe: String?,
        @field:SerializedName("created_at") val createdAt: String?,
        @field:SerializedName("updated_at") val updatedAt: String?
) : Parcelable {
}