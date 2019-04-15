package com.binar.bagitronik.helper

import android.text.Editable
import android.text.InputFilter
import android.widget.EditText

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
fun EditText.cleanUp() {
    this.text = Editable.Factory.getInstance().newEditable("")
}

fun EditText.requestError(message: String?) {
    if (this.text.isNullOrEmpty()) {
        this.requestFocus()
        this.error = message
    }

}

fun EditText.setMaxLength(maxLength: Int) {
    filters = arrayOf(InputFilter.LengthFilter(maxLength))
}