package com.fishtudo.awesomeseries.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResult(
    var score: Double,
    var show: Show,
) : Parcelable