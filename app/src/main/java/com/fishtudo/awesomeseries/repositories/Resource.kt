package com.fishtudo.awesomeseries.repositories

class Resource<T>(
    val data: T?,
    val error: String? = null
)