package com.fishtudo.awesomeseries.repositories.dao

import android.content.Context

class SharedPreferenceDAO {

    fun saveString(context: Context, string: String, sharedPreferenceName: String) {
        context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE).edit().apply {
            putString(sharedPreferenceName, string)
            apply()
        }
    }

    fun readString(context: Context, sharedPreferenceName: String): String =
        context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
            .getString(sharedPreferenceName, "") ?: ""
}