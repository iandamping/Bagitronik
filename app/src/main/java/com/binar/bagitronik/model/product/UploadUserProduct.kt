package com.binar.bagitronik.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/*
Created by ian 10/March/2019
 */
@Parcelize
data class UploadUserProduct(
        @field:SerializedName("id") val id: Int? = null,
        @field:SerializedName("name") val name: String?,
        @field:SerializedName("description") val description: String?,
        @field:SerializedName("created_at") val createdAt: String?,
        @field:SerializedName("updated_at") val updatedAt: String?,
        @field:SerializedName("user_id") val userId: Int?,
        @field:SerializedName("type_id") val typeId: Int?,
        @field:SerializedName("condition_id") val conditionId: Int?,
        @field:SerializedName("images") val images: ImagesData?,
        @field:SerializedName("category_id") val categoryId: Int?
) : Parcelable {
    @Parcelize
    data class ImagesData(@field:SerializedName("url") val url: String?) : Parcelable {
    }
}