package com.binar.bagitronik.helper

import android.widget.ImageView
import com.binar.bagitronik.R
import com.squareup.picasso.Picasso

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
fun ImageView.loadUrl(url: String?) {
    url?.let {
        Picasso.get().load(it).placeholder(R.drawable.empty_image).resize(600, 600)
                .centerCrop().into(this)
    }
}

fun ImageView.loadUrlOnlyForFullScreen(url: String?) {
    url?.let {
        Picasso.get().load(it).into(this)
    }
}

fun ImageView.loadDrawable(id: Int?) {
    id?.let { Picasso.get().load(it).placeholder(R.drawable.empty_image).into(this) }
}

fun ImageView.loadDrawableForIcon(id: Int?) {
    id?.let { Picasso.get().load(it).placeholder(R.drawable.empty_image).fit().into(this) }
}

fun ImageView.loadResizeHelper(imageId: Int?) {
    imageId?.let {
        Picasso.get().load(imageId).resize(600, 600)
                .onlyScaleDown()
                .centerCrop()
                .into(this)
    }
}