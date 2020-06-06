package com.movie.xutil

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener

fun AppCompatImageView.loadImage(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .into(this)
}

@BindingAdapter("imageFromUrl", "withCrossFade", "requestListener", requireAll = false)
fun AppCompatImageView.bindImageFromUrl(
    imageUrl: String?,
    withCrossFade: Boolean = true,
    requestListener: RequestListener<Drawable>?
) {
    if (!imageUrl.isNullOrEmpty()) {
        val transitionOptions = if (withCrossFade) {
            DrawableTransitionOptions().crossFade()
        } else {
            DrawableTransitionOptions()
        }
        val transition = Glide.with(context)
            .load(imageUrl)
            .transition(transitionOptions)

        requestListener?.let { transition.listener(it) }

        transition.into(this)
    }
}