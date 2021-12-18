package com.fishtudo.awesomeseries.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageUtil {

    fun downloadImage(context: Context, image: Map<String, String>, imageView: ImageView) {
        findProperImageUrl(image)?.let {
            Glide.with(context).load(it).into(imageView)
        }
    }

    private fun findProperImageUrl(imageMap: Map<String, String>) =
        imageMap[MEDIUM_IMAGE] ?: imageMap[ORIGINAL_IMAGE]
}