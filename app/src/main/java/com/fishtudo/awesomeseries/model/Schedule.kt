package com.fishtudo.awesomeseries.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Schedule(
    var time: String,
    var days: List<String>
) : Parcelable