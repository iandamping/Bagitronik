package com.binar.bagitronik.ui.activity.detail

import android.content.Context
import com.binar.bagitronik.BagitronikApp.Companion.api
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.api.ApiClient
import com.binar.bagitronik.api.ApiInterface
import com.binar.bagitronik.base.BasePresenter
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.Constant.dialogCommonResponse
import com.binar.bagitronik.helper.executes
import com.binar.bagitronik.model.product.UserProducts
import com.binar.bagitronik.model.profile.UserDataLogin

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class DetailProductPresenter(private val mView: DetailProductView) :
        BasePresenter() {
    private var tempUserProducts: UserProducts? = null
    private var maps: MutableMap<String, Any> = mutableMapOf()
    private var apiWithHeader: ApiInterface? = null
    private var tempData = mutableListOf<UserProducts>()
    private val userData =
            gson.fromJson(prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

    override fun onCreate(context: Context) {
        setBaseDialog(context, dialogCommonResponse)
        apiWithHeader = ApiClient.getRetrofitWithMultiPart(userData.token).create(ApiInterface::class.java)
        onGetMyProduct(userData?.username?.id)
//        getAllDealings()
        mView.initView()
    }


    fun getDetailedProductData(id: Int?) {
        if (id == 0) {
            mView.onFailedGetDetailedData("Terjadi kesalahan")
        } else {
            setDialogShow(false)
            api.getDetailedProduct(id.toString()).executes({
                setDialogShow(true)
                mView.onFailedGetDetailedData(it.localizedMessage)
            }, {
                setDialogShow(true)
                if (it.code() == 200) {
                    mView.onSuccessGetDetailedData(it.body())
                    this.tempUserProducts = it.body()
                } else {
                    mView.onFailedGetDetailedData("Terjadi kesalahan")
                }
            })
        }
    }

    fun onEditDealingsData(productID: String?, acceptOrNot: Int?) {
        setDialogShow(false)
        if (apiWithHeader != null) {
            apiWithHeader?.editDealingsByID(productID, acceptOrNot)?.executes({
                setDialogShow(true)
                mView.onFailedGetDetailedData(it.localizedMessage)
            }, {
                setDialogShow(true)
                if (it.code() == 200) {
                    if (it.body() == null) {
                        mView.onFailedGetDetailedData("Terjadi kesalahan")
                    }
                } else {
                    mView.onFailedGetDetailedData("Terjadi kesalahan")
                }
            })
        }

    }


    private fun onGetMyProduct(userId: Int?) {
        if (apiWithHeader != null) {
            apiWithHeader?.getMyProducts()?.executes({
                mView.onFailedGetDetailedData(it.localizedMessage)
            }, {
                if (it.code() == 200) {
                    tempData.clear()
                    it.body()?.forEach { data ->
                        userId?.let {
                            if (it == data.userData?.id) {
                                tempData.add(data)
                            }
                        }
                    }
                    mView.onGetAllListData(tempData)
                } else {
                    mView.onFailedGetDetailedData("Terjadi kesalahan")
                }
            })
        }

    }

    fun createDonationDeals(notes: String, productId: Int) {
        setDialogShow(false)
        if (apiWithHeader != null) {
            maps["note"] = notes
            maps["product_id"] = productId
            maps["status_id"] = 1

            apiWithHeader?.createDealings(maps)?.executes({
                setDialogShow(true)
                mView.onFailedGetDetailedData(it.localizedMessage)
            }, {
                setDialogShow(true)
                if (it.code() == 201) {
                    when {
                        it.body()?.productNya?.category?.category.equals("Tukar", ignoreCase = true) -> {
                            mView.onSuccessCreateTukar(it.body()?.id.toString())
                        }

                        it.body()?.productNya?.category?.category.equals("Donasi", ignoreCase = true) -> {
                            mView.onSuccessCreateDonasi(it.body()?.id.toString())
                        }
                    }
                } else {
                    mView.onFailedGetDetailedData("Error Happen")
                }
            })
        }

    }

    fun createBarterDeals(notes: String, productId: Int, myProductID: Int) {
        setDialogShow(false)
        if (apiWithHeader != null) {
            maps["note"] = notes
            maps["product_id"] = productId
            maps["myproduct_id"] = myProductID
            maps["status_id"] = 1

            apiWithHeader?.createDealings(maps)?.executes({
                setDialogShow(true)
                mView.onFailedGetDetailedData(it.localizedMessage)
            }, {
                setDialogShow(true)
                if (it.code() == 201) {
                    when {
                        it.body()?.productNya?.category?.category.equals("Tukar", ignoreCase = true) -> {
                            mView.onSuccessCreateTukar(it.body()?.id.toString())
                        }

                        it.body()?.productNya?.category?.category.equals("Donasi", ignoreCase = true) -> {
                            mView.onSuccessCreateDonasi(it.body()?.id.toString())
                        }
                    }
                } else {
                    mView.onFailedGetDetailedData("Terjadi kesalahan")
                }
            })
        }

    }
}