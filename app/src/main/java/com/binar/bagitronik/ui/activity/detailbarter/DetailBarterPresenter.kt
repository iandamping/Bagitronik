package com.binar.bagitronik.ui.activity.detailbarter

import android.content.Context
import com.binar.bagitronik.BagitronikApp
import com.binar.bagitronik.api.ApiClient
import com.binar.bagitronik.api.ApiInterface
import com.binar.bagitronik.base.BasePresenter
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.executes
import com.binar.bagitronik.model.profile.UserDataLogin

/**
 *
Created by Ian Damping on 02/04/2019.
Github = https://github.com/iandamping
 */
class DetailBarterPresenter(private val mView: DetailBarterView) : BasePresenter() {
    private var apiWithHeader: ApiInterface? = null
    private val userData = BagitronikApp.gson.fromJson(BagitronikApp.prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

    override fun onCreate(context: Context) {
        setBaseDialog(context, Constant.dialogCommonResponse)
        apiWithHeader = ApiClient.getRetrofitWithMultiPart(userData.token).create(ApiInterface::class.java)
        mView.initView()
    }

    fun onGetDealingsData(productID: String?) {
        setDialogShow(false)
        apiWithHeader?.getDealingsByID(productID)?.executes({
            setDialogShow(true)
            mView.onFailedGetDetailedData(it.localizedMessage)
        }, {
            setDialogShow(true)
            if (it.code() == 200) {
                if (it.body() != null) {
                    if (it.body()?.productNya?.users?.id == userData?.username?.id) {
                        mView.onGetTukarData(it.body(), true)
                    } else {
                        mView.onGetTukarData(it.body(), false)
                    }
                } else {
                    mView.onFailedGetDetailedData("Terjadi kesalahan")
                }
            } else {
                mView.onFailedGetDetailedData("Terjadi kesalahan")
            }
        })
    }

    fun onEditDealingsData(productID: String?, acceptOrNot: Int?) {
        setDialogShow(false)
        apiWithHeader?.editDealingsByID(productID, acceptOrNot)?.executes({
            setDialogShow(true)
            mView.onFailedGetDetailedData(it.localizedMessage)
        }, {
            setDialogShow(true)
            if (it.code() == 200) {
                if (it.body() != null) {
                    if (it.body()?.productNya?.users?.id == userData?.username?.id) {
                        mView.onGetTukarData(it.body(), true)
                    } else {
                        mView.onGetTukarData(it.body(), false)
                    }
                } else {
                    mView.onFailedGetDetailedData("Terjadi kesalahan")
                }
            } else {
                mView.onFailedGetDetailedData("Terjadi kesalahan")
            }
        })
    }

    fun onEditDealingsDataSatu(productID: String?, acceptOrNot: Int?, statusSatu: Int?) {
        setDialogShow(false)
        apiWithHeader?.editDonasiDealingsSatu(productID, acceptOrNot, statusSatu)?.executes({
            setDialogShow(true)
            mView.onFailedGetDetailedData(it.localizedMessage)
        }, {
            setDialogShow(true)
            if (it.code() == 200) {
                if (it.body() != null) {
                    if (it.body()?.productNya?.users?.id == userData?.username?.id) {
                        mView.onGetTukarData(it.body(), true)
                    } else {
                        mView.onGetTukarData(it.body(), false)
                    }
                } else {
                    mView.onFailedGetDetailedData("Terjadi kesalahan")
                }
            } else {
                mView.onFailedGetDetailedData("Terjadi kesalahan")
            }
        })
    }

    fun onEditDealingsDataDua(productID: String?, acceptOrNot: Int?, statusDua: Int?) {
        setDialogShow(false)
        apiWithHeader?.editDonasiDealingsDua(productID, acceptOrNot, statusDua)?.executes({
            setDialogShow(true)
            mView.onFailedGetDetailedData(it.localizedMessage)
        }, {
            setDialogShow(true)
            if (it.code() == 200) {
                if (it.body() != null) {
                    if (it.body()?.productNya?.users?.id == userData?.username?.id) {
                        mView.onGetTukarData(it.body(), true)
                    } else {
                        mView.onGetTukarData(it.body(), false)
                    }
                } else {
                    mView.onFailedGetDetailedData("Terjadi kesalahan")
                }
            } else {
                mView.onFailedGetDetailedData("Terjadi kesalahan")
            }
        })
    }
}