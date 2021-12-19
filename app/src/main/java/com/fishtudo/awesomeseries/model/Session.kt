package com.fishtudo.awesomeseries.model

object Session {

    var logged: Boolean = false
        private set

    init {
        println("Init class invoked.")
    }

    fun onLoginSucceed() {
        logged = true
    }
}