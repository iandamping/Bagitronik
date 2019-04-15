package com.binar.bagitronik.api

import com.binar.bagitronik.api.NetworkConfig.ALL_PRODUCT
import com.binar.bagitronik.api.NetworkConfig.ALL_TYPE
import com.binar.bagitronik.api.NetworkConfig.CREATE_DEALINGS
import com.binar.bagitronik.api.NetworkConfig.CREATE_USER
import com.binar.bagitronik.api.NetworkConfig.GET_CITIES
import com.binar.bagitronik.api.NetworkConfig.GET_LANDFILLS
import com.binar.bagitronik.api.NetworkConfig.GET_MYPRODUCT
import com.binar.bagitronik.api.NetworkConfig.GET_PROVINCE
import com.binar.bagitronik.api.NetworkConfig.LOGIN_USER
import com.binar.bagitronik.api.NetworkConfig.PRODUCT_TYPE
import com.binar.bagitronik.api.NetworkConfig.UPLOAD_DATA
import com.binar.bagitronik.model.dealings.*
import com.binar.bagitronik.model.deletedata.DeleteProductData
import com.binar.bagitronik.model.landfills.LandfillsData
import com.binar.bagitronik.model.product.ProductType
import com.binar.bagitronik.model.product.UploadUserProduct
import com.binar.bagitronik.model.product.UserProducts
import com.binar.bagitronik.model.profile.AllUser
import com.binar.bagitronik.model.profile.CitiesData
import com.binar.bagitronik.model.profile.ProvinceData
import com.binar.bagitronik.model.profile.UserDataLogin
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
interface ApiInterface {

    @POST(LOGIN_USER)
    fun loginUser(@Body userMap: Map<String, String>): Call<UserDataLogin>

    @POST(CREATE_USER)
    fun createUser(@Body userMap: Map<String, String>): Call<UserDataLogin>

    @GET("$CREATE_USER/{id}")
    fun getUserByID(@Path("id") userId: String?): Call<AllUser>

    @PUT("$CREATE_USER/{id}")
    fun updateUserData(@Path("id") userID: String?, @Body data: RequestBody): Call<AllUser>

    @JvmSuppressWildcards
    @POST(CREATE_DEALINGS)
    fun createDealings(@Body userMap: Map<String, Any>): Call<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>

    @GET(CREATE_DEALINGS)
    fun getAllDealings(): Call<List<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>>

    @GET("$CREATE_DEALINGS/{id}")
    fun getDealingsByID(@Path("id") productId: String?): Call<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>

    @FormUrlEncoded
    @PUT("$CREATE_DEALINGS/{id}")
    fun editDealingsByID(@Path("id") productId: String?, @Field("status_id") statusId: Int?): Call<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>

    @FormUrlEncoded
    @PUT("$CREATE_DEALINGS/{id}")
    fun editDonasiDealingsSatu(@Path("id") productId: String?, @Field("status_id") statusId: Int?, @Field("status_satu") statusSatu: Int?): Call<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>

    @FormUrlEncoded
    @PUT("$CREATE_DEALINGS/{id}")
    fun editDonasiDealingsDua(@Path("id") productId: String?, @Field("status_id") statusId: Int?, @Field("status_dua") statusSatu: Int?): Call<BaseDealing<UserDealings, ProductDealings, ProductBarter, StatusDealings>>


    @GET(GET_CITIES)
    fun getAllCities(): Call<List<CitiesData>>

    @GET(GET_PROVINCE)
    fun getAllProvince(): Call<ProvinceData>

    @POST(UPLOAD_DATA)
    fun uploadData(@Body data: RequestBody): Call<UploadUserProduct>

    @GET(PRODUCT_TYPE)
    fun getProductType(): Call<List<ProductType>>

    @DELETE("$ALL_PRODUCT/{id}")
    fun deleteProduct(@Path("id") productId: String?): Call<DeleteProductData>

    @GET(PRODUCT_TYPE)
    fun getProductTypeRx(): Observable<List<ProductType>>

    @GET(GET_MYPRODUCT)
    fun getMyProducts(): Call<List<UserProducts>>

    @GET("$ALL_TYPE/{id}")
    fun getAllProduct(@Path("id") typeId: String?): Call<List<UserProducts>>

    @GET("$ALL_TYPE/{id}")
    fun getAllType(@Path("id") typeId: String?): Observable<List<UserProducts>>

    @GET("$ALL_PRODUCT/{id}")
    fun getDetailedProduct(@Path("id") productId: String?): Call<UserProducts>

    @PUT("$ALL_PRODUCT/{id}")
    fun updateDetailedProduct(@Path("id") productId: String?, @Body data: RequestBody): Call<UserProducts>

    @GET(GET_LANDFILLS)
    fun getAllLandfillsData(): Call<List<LandfillsData>>

    @GET("$GET_LANDFILLS/{id}")
    fun getLandfillsDataById(@Path("id") landfillsId: String?): Call<LandfillsData>
}