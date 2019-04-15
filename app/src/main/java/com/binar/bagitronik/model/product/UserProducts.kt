package com.binar.bagitronik.model.product

import android.os.Parcelable
import com.binar.bagitronik.model.profile.CitiesData
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 *
Created by Ian Damping on 26/03/2019.
Github = https://github.com/iandamping
 */
@Parcelize
data class UserProducts(
        @field:SerializedName("id") val id: Int? = null,
        @field:SerializedName("name") val name: String?,
        @field:SerializedName("description") val description: String?,
        @field:SerializedName("images") val imagesData: ImagesData?,
        @field:SerializedName("dealing") val dealingData: DealingData?,
        @field:SerializedName("user") val userData: UserData?,
        @field:SerializedName("condition") val conditionData: ConditionData?,
        @field:SerializedName("category") val categoryData: CategoryData?,
        @field:SerializedName("type") val typeData: TypeData?) : Parcelable {

    @Parcelize
    data class ImagesData(@field:SerializedName("url") val url: String?) : Parcelable

    @Parcelize
    data class UserData(
            @field:SerializedName("id") val id: Int?,
            @field:SerializedName("username") val username: String?,
            @field:SerializedName("email") val email: String?,
            @field:SerializedName("first_name") val firstName: String?,
            @field:SerializedName("last_name") val lastName: String?,
            @field:SerializedName("address") val address: String?,
            @field:SerializedName("phone") val phone: String?,
            @field:SerializedName("nik") val nik: String?,
            @field:SerializedName("images") val userImage: ImagesData?,
            @field:SerializedName("products") val products: AllUserCounts?,
            @field:SerializedName("tukar") val tukar: AllUserCounts?,
            @field:SerializedName("donasi") val donasi: AllUserCounts?,
            @field:SerializedName("role") val roleUser: RoleData?,
            @field:SerializedName("city") val cityUser: CitiesData?
    ) : Parcelable

    @Parcelize
    data class AllUserCounts(@field:SerializedName("counts") val counts: Int?) : Parcelable

    @Parcelize
    data class RoleData(
            @field:SerializedName("id") val id: Int?,
            @field:SerializedName("role") val role: String?
    ) : Parcelable

    @Parcelize
    data class ConditionData(
            @field:SerializedName("id") val id: Int? = null,
            @field:SerializedName("condition") val condition: String?
    ) : Parcelable

    @Parcelize
    data class CategoryData(
            @field:SerializedName("id") val id: Int? = null,
            @field:SerializedName("category") val category: String?
    ) : Parcelable

    @Parcelize
    data class TypeData(
            @field:SerializedName("id") val id: Int? = null,
            @field:SerializedName("tipe") val tipe: String?
    ) : Parcelable

    @Parcelize
    data class DealingData(
            @field:SerializedName("id") val id: Int? = null,
            @field:SerializedName("note") val note: String?,
            @field:SerializedName("created_at") val created_at: String?,
            @field:SerializedName("updated_at") val updated_at: String?,
            @field:SerializedName("status_id") val status_id: Int?,
            @field:SerializedName("user_id") val user_id: Int?,
            @field:SerializedName("product_id") val product_id: Int?,
            @field:SerializedName("myproduct_id") val myproduct_id: Int?
    ) : Parcelable
}