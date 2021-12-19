package com.fishtudo.awesomeseries.repositories.factories

import com.fishtudo.awesomeseries.repositories.TVMazeRepository
import com.fishtudo.awesomeseries.repositories.TvmazeApiInterface
import com.fishtudo.awesomeseries.util.TVMAZE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TVMazeRepositoryFactory {

    private val service: TvmazeApiInterface by lazy {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(TVMAZE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        retrofit.create(TvmazeApiInterface::class.java)
    }

    fun createRepository(): TVMazeRepository {
        return TVMazeRepository(service)
    }
}