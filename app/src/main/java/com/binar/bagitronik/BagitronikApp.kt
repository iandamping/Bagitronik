package com.binar.bagitronik

import android.support.multidex.MultiDexApplication
import com.binar.bagitronik.api.ApiClient
import com.binar.bagitronik.api.ApiInterface
import com.binar.bagitronik.helper.Constant
import com.binar.bagitronik.helper.PreferenceHelper
import com.binar.bagitronik.model.profile.UserDataLogin
import com.crashlytics.android.Crashlytics
import com.google.gson.Gson
import io.fabric.sdk.android.Fabric

class BagitronikApp : MultiDexApplication() {
    companion object {
        lateinit var gson: Gson
        lateinit var api: ApiInterface
        lateinit var prefHelper: PreferenceHelper
        var userData: UserDataLogin? = null
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        prefHelper = PreferenceHelper(this)
        gson = Gson()
        userData = BagitronikApp.gson.fromJson(prefHelper.getStringInSharedPreference(Constant.userPrefKey), UserDataLogin::class.java)
        api = ApiClient.getRetrofit().create(ApiInterface::class.java)
    }
}