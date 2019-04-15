package com.binar.bagitronik.model.dealings

import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 01/04/2019.
Github = https://github.com/iandamping
 */
data class ProductDealings(
        @field:SerializedName("id") val id: Int? = null,
        @field:SerializedName("name") val name: String?,
        @field:SerializedName("description") val description: String?,
        @field:SerializedName("images") val imagesData: ImageDealings,
        @field:SerializedName("dealing") val dealing: ProductnyaDealings,
        @field:SerializedName("user") val users: UserProductDealings?,
        @field:SerializedName("condition") val condition: ConditionDealings?,
        @field:SerializedName("category") val category: CategoryDealings?,
        @field:SerializedName("type") val type: TypeDealings?

) {
    data class ProductnyaDealings(
            @field:SerializedName("id") val id: Int?,
            @field:SerializedName("note") val note: String?,
            @field:SerializedName("created_at") val created_at: String?,
            @field:SerializedName("updated_at") val updated_at: String?,
            @field:SerializedName("status_id") val status_id: Int?,
            @field:SerializedName("user_id") val user_id: Int?,
            @field:SerializedName("product_id") val product_id: Int?,
            @field:SerializedName("myproduct_id") val myproduct_id: Int?,
            @field:SerializedName("status_satu") val status_satu: Int?,
            @field:SerializedName("status_dua") val status_dua: Int?
    )
}