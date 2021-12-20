package com.fishtudo.awesomeseries.repositories

import com.fishtudo.awesomeseries.model.*
import com.fishtudo.awesomeseries.util.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvmazeApiInterface {

    @GET(SHOW_LIST)
    fun listShows(@Query("page") page: Int): Call<List<Show>>

    @GET(SEARCH_SHOW)
    fun searchShows(@Query("q") searchParameter: String): Call<List<SearchResult>>

    @GET(SEASONS_LIST)
    fun listSeasons(@Path("showId") showId: Int): Call<List<Season>>

    @GET(EPISODES_LIST)
    fun listEpisodes(@Path("seasonId") seasonId: Int): Call<List<Episode>>

    @GET(PEOPLE_LIST)
    fun listPeople(): Call<List<People>>
}