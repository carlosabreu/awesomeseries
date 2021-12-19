package com.fishtudo.awesomeseries.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Show(
    var id: Int,
    var url: String,
    var name: String,
    var type: String,
    var language: String,
    var image: Map<String, String>?,
    var summary: String,
    var schedule: Schedule,
    var genres: List<String>
) : Parcelable