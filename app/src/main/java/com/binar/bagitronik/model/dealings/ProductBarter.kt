package com.binar.bagitronik.model.dealings

import com.binar.bagitronik.model.product.UserProducts
import com.google.gson.annotations.SerializedName

/**
 *
Created by Ian Damping on 02/04/2019.
Github = https://github.com/iandamping
 */
data class ProductBarter(
        @field:SerializedName("id") val id: Int? = null,
        @field:SerializedName("name") val name: String?,
        @field:SerializedName("description") val description: String?,
        @field:SerializedName("images") val imagesData: UserProducts.ImagesData?,
        @field:SerializedName("user") val users: UserProductDealings?,
        @field:SerializedName("condition") val condition: ConditionDealings?,
        @field:SerializedName("category") val category: CategoryDealings?,
        @field:SerializedName("type") val type: TypeDealings?

) {
}