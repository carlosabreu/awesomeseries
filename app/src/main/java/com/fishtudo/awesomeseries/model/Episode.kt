package com.fishtudo.awesomeseries.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    var id: Int,
    var url: String,
    var number: String,
    var name: String,
) : Parcelable