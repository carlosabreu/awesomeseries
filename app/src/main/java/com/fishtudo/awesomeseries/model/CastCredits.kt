package com.fishtudo.awesomeseries.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CastCredits(
    var self: String,
    var _links: Map<String, Map<String, String>>,
) : Parcelable {
    fun getUrl(): String = _links["show"]?.get("href") ?: ""
}
