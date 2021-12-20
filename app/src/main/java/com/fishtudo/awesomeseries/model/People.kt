package com.fishtudo.awesomeseries.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class People(
    var id: Int,
    var name: String,
    var image: Map<String, String>,
) : Parcelable