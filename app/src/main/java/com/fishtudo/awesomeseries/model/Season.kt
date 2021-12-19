package com.fishtudo.awesomeseries.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Season(
    var id: Int,
    var name: String,
    var number: String,
) : Parcelable