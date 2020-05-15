package com.sixt.codingtask.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
@BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = true)
fun ImageView.imageUrl(url: String, drawable: Drawable) {
    this.load(url) {

        crossfade(true)
        placeholder(drawable)
        error(drawable)
    }
}