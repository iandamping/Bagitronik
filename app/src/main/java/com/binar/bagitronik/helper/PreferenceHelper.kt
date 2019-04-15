package com.binar.bagitronik.helper

import android.app.Application
import android.content.Context
import com.binar.bagitronik.helper.Constant.PrefHelperInit

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
class PreferenceHelper(app: Application) {
    private val prefHelp by lazy {
        app.getSharedPreferences(PrefHelperInit, Context.MODE_PRIVATE)
    }
    private val preHelperEditor = prefHelp.edit()

    fun saveStringInSharedPreference(key: String?, value: String?) {
        preHelperEditor.putString(key, value).apply()
    }

    fun getStringInSharedPreference(key: String?): String? {
        return prefHelp.getString(key, "")
    }

    fun deleteSharedPreference() {
        preHelperEditor.clear().apply()
    }
}