package com.fishtudo.awesomeseries.repositories

import com.fishtudo.awesomeseries.model.Episode
import com.fishtudo.awesomeseries.model.Season
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.util.EPISODES_LIST
import com.fishtudo.awesomeseries.util.SEASONS_LIST
import com.fishtudo.awesomeseries.util.SHOW_LIST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvmazeApiInterface {

    @GET(SHOW_LIST)
    fun listShows(@Query("page") page: Int): Call<List<Show>>

    @GET(SEASONS_LIST)
    fun listSeasons(@Path("showId") showId: Int): Call<List<Season>>

    @GET(EPISODES_LIST)
    fun listEpisodes(@Path("seasonId") seasonId: Int): Call<List<Episode>>
}