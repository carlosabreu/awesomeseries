package com.fishtudo.awesomeseries.repositories

import android.content.Context
import com.fishtudo.awesomeseries.util.PIN_SHARED_PREFERENCE_NAME

class PinRepository {

    fun savePin(context: Context, text: String) {
        // Unsecure persistence it is just for this 5 day test
        // A much better way is to use Android keystore to cypher this pin before saving
        context.getSharedPreferences(PIN_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
            .apply {
                putString(PIN_SHARED_PREFERENCE_NAME, text)
                apply()
            }
    }

    fun readPin(context: Context) =
        context.getSharedPreferences(PIN_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            .getString(PIN_SHARED_PREFERENCE_NAME, "") ?: ""
}