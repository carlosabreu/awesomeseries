package com.fishtudo.awesomeseries.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    var number: String,
    var name: String,
    var season: String,
    var image: Map<String, String>?,
    var summary: String,
) : Parcelable