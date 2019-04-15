package com.binar.bagitronik.ui.activity.uploadpage

import android.content.Context
import android.support.v4.app.FragmentActivity
import com.binar.bagitronik.BagitronikApp.Companion.api
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.api.ApiClient
import com.binar.bagitronik.api.ApiInterface
import com.binar.bagitronik.base.BasePresenter
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.Constant.uploadDialog
import com.binar.bagitronik.helper.executes
import com.binar.bagitronik.model.product.UserProducts
import com.binar.bagitronik.model.profile.UserDataLogin
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class UploadPresenter(private val mView: UploadView, private val target: FragmentActivity) : BasePresenter() {
    private var builder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
    private val userData =
            gson.fromJson(prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

    private var apiWithHeader: ApiInterface? = null
    private var passedData: UserProducts? = null
    override fun onCreate(context: Context) {
        setBaseDialog(context, uploadDialog)
        apiWithHeader = ApiClient.getRetrofitWithMultiPart(userData.token).create(ApiInterface::class.java)
        getProductData()
        mView.initView()
    }


    fun getPassedEditProductData(data: String?) {
        this.passedData = gson.fromJson(data, UserProducts::class.java)
        if (passedData != null) {
            mView.onGetPassedDataType(passedData)
        }
    }

    fun onEditProductData(
            productId: Int?,
            itemName: String,
            itemDesc: String,
            images: File?,
            type: String?,
            condition: String?,
            category: String?
    ) {
        setDialogShow(false)
        if (images != null) {
            builder.addFormDataPart("name", itemName)
                    .addFormDataPart("description", itemDesc)
                    .addFormDataPart("images", "bagi_tronik.jpeg", RequestBody.create(MediaType.parse("image/*"), images))
                    .addFormDataPart("type_id", type)
                    .addFormDataPart("condition_id", condition)
                    .addFormDataPart("category_id", category)
            val requestBody = builder.build()
            apiWithHeader?.updateDetailedProduct(productId.toString(), requestBody)?.executes({
                setDialogShow(true)
                mView.onFailedUpload(it.localizedMessage)
            }, {
                setDialogShow(true)
                if (it.code() == 200) {
                    mView.onSuccessUpload(it.body()?.id)
                } else {
                    mView.onFailedUpload("Unggah Gagal")
                }
            })
        } else if (images == null) {
            builder.addFormDataPart("name", itemName)
                    .addFormDataPart("description", itemDesc)
                    .addFormDataPart("type_id", type)
                    .addFormDataPart("condition_id", condition)
                    .addFormDataPart("category_id", category)
            val requestBody = builder.build()
            apiWithHeader?.updateDetailedProduct(productId.toString(), requestBody)?.executes({
                setDialogShow(true)
                mView.onFailedUpload(it.localizedMessage)
            }, {
                setDialogShow(true)
                if (it.code() == 200) {
                    mView.onSuccessUpload(it.body()?.id)
                } else {
                    mView.onFailedUpload("Unggah Gagal")
                }
            })
        }
    }

    private fun getProductData() {
        api.getProductType().executes({
            mView.onFailedUpload(it.localizedMessage)
        }, {
            if (it.code() == 200) {
                if (it.body() != null) {
                    mView.onGetDataType(it.body())
                }
            }
        })
    }

    fun uploadData(
            itemName: String,
            itemDesc: String,
            images: File,
            type: String?,
            condition: String?,
            category: String?
    ) {
        setDialogShow(false)
        builder.addFormDataPart("name", itemName)
                .addFormDataPart("description", itemDesc)
                .addFormDataPart("images", "bagi_tronik.jpeg", RequestBody.create(MediaType.parse("image/*"), images))
                .addFormDataPart("type_id", type)
                .addFormDataPart("condition_id", condition)
                .addFormDataPart("category_id", category)
        val requestBody = builder.build()
        apiWithHeader?.uploadData(requestBody)?.executes({
            setDialogShow(true)
            mView.onFailedUpload(it.localizedMessage)
        }, {
            setDialogShow(true)
            if (it.code() == 201) {
                mView.onSuccessUpload(it.body()?.id)
            } else {
                mView.onFailedUpload("Unggah Gagal")
            }
        })
    }

    fun deleteProduct(productId: Int?) {
        setDialogShow(false)
        if (apiWithHeader != null) {
            apiWithHeader?.deleteProduct(productId?.toString())?.executes({
                setDialogShow(true)
                mView.onFailedUpload(it.localizedMessage)
            }, {
                setDialogShow(true)
                if (it.code() == 200) {
                    if (it.body()?.status?.equals("Hapus Berhasil", ignoreCase = true)!!) {
                        mView.onSuccessDeleteProduct("Hapus produk berhasil")
                    } else {
                        mView.onFailedDeleteProduct("Produk ini sedang dibutuhkan orang lain")
                    }
                }
            })
        }
    }
}