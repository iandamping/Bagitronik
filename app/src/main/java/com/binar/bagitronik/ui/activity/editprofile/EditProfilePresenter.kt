package com.binar.bagitronik.ui.activity.editprofile

import android.content.Context
import com.binar.bagitronik.BagitronikApp.Companion.api
import com.binar.bagitronik.BagitronikApp.Companion.gson
import com.binar.bagitronik.BagitronikApp.Companion.prefHelper
import com.binar.bagitronik.api.ApiClient
import com.binar.bagitronik.api.ApiInterface
import com.binar.bagitronik.base.BasePresenter
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.Constant.dialogCommonResponse
import com.binar.bagitronik.helper.Constant.userPrefKey
import com.binar.bagitronik.helper.executes
import com.binar.bagitronik.helper.logD
import com.binar.bagitronik.model.profile.AllUser
import com.binar.bagitronik.model.profile.User
import com.binar.bagitronik.model.profile.UserDataLogin
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 *
Created by Ian Damping on 31/03/2019.
Github = https://github.com/iandamping
 */
class EditProfilePresenter(private val mView: EditProfileView) : BasePresenter() {
    private var builder: MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM)
    private val userData = gson.fromJson(prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)
    private var apiWithHeader: ApiInterface? = null
    private var passedData: AllUser? = null
    private var tmpUserData: User? = User()
    private var tmpUserLogin: UserDataLogin? = UserDataLogin()
    override fun onCreate(context: Context) {
        setBaseDialog(context, dialogCommonResponse)
        apiWithHeader = ApiClient.getRetrofitWithMultiPart(userData.token).create(ApiInterface::class.java)
        logD(userData.token + " ini token ku")
        mView.initView()
        onGetCitiesData()
//        onGetDefaultUserData()
    }

    fun onGetPassedData(data: String?) {
        this.passedData = gson.fromJson(data, AllUser::class.java)
        if (passedData != null) {
            if (passedData?.email != null) {
                mView.onGetPassedData(passedData)
            }
        }
    }

//    private fun onGetDefaultUserData() {
//        val userData = gson.fromJson(prefHelper.getStringInSharedPreference(userPrefKey), UserDataLogin::class.java)
//        userData.let {
//            mView.onGetDefaultData(it.username)
//        }
//    }

    private fun onGetCitiesData() {
        setDialogShow(false)
        api.getAllCities().executes({
            setDialogShow(true)
            mView.onFailedGetData(it.localizedMessage)
        }, {
            if (it.code() == 200) {
                setDialogShow(true)
                mView.onGetCitiesData(it.body())
            } else {
                mView.onFailedGetData("Error Happen")
            }
        })
    }

    fun updateUser(userEmail: String?, userPassword: String?, firstName: String?, userAddress: String?, userPhoneNumber: String?, cityID: Int?, images: File?) {
        setDialogShow(false)
        if (images != null) {
            builder.addFormDataPart("email", userEmail)
                    .addFormDataPart("password", userPassword)
                    .addFormDataPart("first_name", firstName)
                    .addFormDataPart("address", userAddress)
                    .addFormDataPart("phone", userPhoneNumber)
                    .addFormDataPart("city_id", cityID.toString())
                    .addFormDataPart("images", "bagi_tronik.jpeg", RequestBody.create(MediaType.parse("image/*"), images))
            val requestBody = builder.build()
            if (apiWithHeader != null) {
                apiWithHeader?.updateUserData(userData?.username?.id?.toString(), requestBody)?.executes({
                    setDialogShow(true)
                    mView.onFailedGetData(it.localizedMessage)
                }, {
                    setDialogShow(true)
                    if (it.code() == 200) {
                        tmpUserData?.id = it.body()?.id
                        tmpUserData?.email = it.body()?.email
                        tmpUserData?.firstName = it.body()?.firstName
                        tmpUserData?.phone = it.body()?.phone
                        tmpUserData?.cityId = it.body()?.cityUser?.id
                        tmpUserData?.roleId = it.body()?.roleUser?.id
                        tmpUserLogin?.token = userData?.token
                        tmpUserLogin?.username = tmpUserData
                        prefHelper.saveStringInSharedPreference(userPrefKey, gson.toJson(tmpUserLogin))
                        mView.onSuccesUpdateData("Unggah berhasil")
                    } else {
                        mView.onFailedGetData("Error Happen")
                    }

                })
            }
        } else if (images == null) {
            builder.addFormDataPart("email", userEmail)
                    .addFormDataPart("password", userPassword)
                    .addFormDataPart("first_name", firstName)
                    .addFormDataPart("address", userAddress)
                    .addFormDataPart("phone", userPhoneNumber)
                    .addFormDataPart("city_id", cityID.toString())
            val requestBody = builder.build()
            if (apiWithHeader != null) {
                apiWithHeader?.updateUserData(userData?.username?.id?.toString(), requestBody)?.executes({
                    setDialogShow(true)
                    mView.onFailedGetData(it.localizedMessage)
                }, {
                    setDialogShow(true)
                    if (it.code() == 200) {
                        tmpUserData?.id = it.body()?.id
                        tmpUserData?.email = it.body()?.email
                        tmpUserData?.firstName = it.body()?.firstName
                        tmpUserData?.phone = it.body()?.phone
                        tmpUserData?.cityId = it.body()?.cityUser?.id
                        tmpUserData?.roleId = it.body()?.roleUser?.id
                        tmpUserLogin?.token = userData?.token
                        tmpUserLogin?.username = tmpUserData
                        prefHelper.saveStringInSharedPreference(userPrefKey, gson.toJson(tmpUserLogin))
                        mView.onSuccesUpdateData("Unggah berhasil")
                    } else {
                        mView.onFailedGetData("Error Happen")
                    }

                })
            }
        }
    }
}