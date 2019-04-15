package com.binar.bagitronik.ui.activity.detaildonasi

import android.content.Context
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.api.ApiClient
import com.binar.bagitronik.api.ApiInterface
import com.binar.bagitronik.base.BasePresenter
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.executes
import com.binar.bagitronik.helper.logD
import com.binar.bagitronik.model.profile.UserDataLogin

/**
 *
Created by Ian Damping on 02/04/2019.
Github = https://github.com/iandamping
 */
class DetailDonasiPresenter(private val mView: DetailDonasiView) : BasePresenter() {
    private var apiWithHeader: ApiInterface? = null
    private val userData = gson.fromJson(prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)

    override fun onCreate(context: Context) {
        setBaseDialog(context, Constant.dialogCommonResponse)
        logD(userData?.token + " ini user token")
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
//                        getUserProfile(it.body()?.productNya?.dealing?.user_id)
                        mView.onGetDonasiData(it.body(), true)
                    } else {
                        mView.onGetDonasiData(it.body(), false)
                    }
                } else {
                    mView.onFailedGetDetailedData("Error Happen")
                }
            } else {
                mView.onFailedGetDetailedData("Error Happen")
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
                        mView.onGetDonasiData(it.body(), true)
                    } else {
                        mView.onGetDonasiData(it.body(), false)
                    }
                } else {
                    mView.onFailedGetDetailedData("Error Happen")
                }
            } else {
                mView.onFailedGetDetailedData("Error Happen")
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
                        mView.onGetDonasiData(it.body(), true)
                    } else {
                        mView.onGetDonasiData(it.body(), false)
                    }
                } else {
                    mView.onFailedGetDetailedData("Error Happen")
                }
            } else {
                mView.onFailedGetDetailedData("Error Happen")
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
                        mView.onGetDonasiData(it.body(), true)
                    } else {
                        mView.onGetDonasiData(it.body(), false)
                    }
                } else {
                    mView.onFailedGetDetailedData("Error Happen")
                }
            } else {
                mView.onFailedGetDetailedData("Error Happen")
            }
        })
    }

//    fun getUserProfile(userId: Int?) {
//        if (apiWithHeader != null) {
//            apiWithHeader?.getUserByID(userId.toString())?.executes({
//                setDialogShow(true)
//            }, {
//                setDialogShow(true)
//                if (it.code() == 200) {
//                    mView.onGetUserRequestData(it.body()?.userImage?.url,it.body()?.firstName,it.body()?.address)
//                }
//            })
//        }
//    }
}