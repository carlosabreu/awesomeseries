package com.fishtudo.awesomeseries.model

object Session {

    var logged: Boolean = false
        private set

    fun onLoginSucceed() {
        logged = true
    }
}