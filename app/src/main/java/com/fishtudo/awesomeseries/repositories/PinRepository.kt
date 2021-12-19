package com.fishtudo.awesomeseries.repositories

import android.content.Context
import com.fishtudo.awesomeseries.repositories.dao.SharedPreferenceDAO
import com.fishtudo.awesomeseries.util.PIN_SHARED_PREFERENCE_NAME

class PinRepository {

    fun savePin(context: Context, text: String) {
        // Unsecure persistence it is just for this 5 day test
        // A much better way is to use Android keystore to cypher this pin before saving
        SharedPreferenceDAO().saveString(context, text, PIN_SHARED_PREFERENCE_NAME)
    }

    fun readPin(context: Context) =
        SharedPreferenceDAO().readString(context, PIN_SHARED_PREFERENCE_NAME)
}