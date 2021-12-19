package com.fishtudo.awesomeseries.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fishtudo.awesomeseries.model.Episode
import com.fishtudo.awesomeseries.model.SearchResult
import com.fishtudo.awesomeseries.model.Season
import com.fishtudo.awesomeseries.model.Show
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVMazeRepository(private val service: TvmazeApiInterface) {

    private val showLiveData = MutableLiveData<Resource<List<Show>>>()

    private val seasonLiveData = MutableLiveData<Resource<List<Season>>>()

    private val episodeLiveData = MutableLiveData<Resource<List<Episode>>>()

    fun listShowsByPage(page: Int): LiveData<Resource<List<Show>>> {
        val call: Call<List<Show>> = service.listShows(page)
        call.enqueue(object : Callback<List<Show>> {
            override fun onResponse(call: Call<List<Show>>, response: Response<List<Show>>) {
                showLiveData.value = Resource(response.body())
            }

            override fun onFailure(call: Call<List<Show>>, t: Throwable) {
                showLiveData.value = Resource(null, "Unable to connect to server")
            }
        })
        return showLiveData
    }

    fun searchShow(term: String): LiveData<Resource<List<Show>>> {
        val call: Call<List<SearchResult>> = service.searchShows(term)
        call.enqueue(object : Callback<List<SearchResult>> {
            override fun onResponse(
                call: Call<List<SearchResult>>,
                response: Response<List<SearchResult>>
            ) {
                response.body()?.let { searchResultList ->
                    showLiveData.value = Resource(searchResultList.map { it.show })
                }
            }

            override fun onFailure(call: Call<List<SearchResult>>, t: Throwable) {
                showLiveData.value = Resource(null, "Unable to connect to server")
            }
        })
        return showLiveData
    }

    fun listSeasons(showId: Int): LiveData<Resource<List<Season>>> {
        val call: Call<List<Season>> = service.listSeasons(showId)
        call.enqueue(object : Callback<List<Season>> {
            override fun onResponse(call: Call<List<Season>>, response: Response<List<Season>>) {
                seasonLiveData.value = Resource(response.body())
            }

            override fun onFailure(call: Call<List<Season>>, t: Throwable) {
                seasonLiveData.value = Resource(error = "Conection Error", data = null)
            }
        })
        return seasonLiveData
    }


    fun listEpisodesBySeason(page: Int): LiveData<Resource<List<Episode>>> {
        val call: Call<List<Episode>> = service.listEpisodes(page)
        call.enqueue(object : Callback<List<Episode>> {
            override fun onResponse(call: Call<List<Episode>>, response: Response<List<Episode>>) {
                episodeLiveData.value = Resource(response.body())
            }

            override fun onFailure(call: Call<List<Episode>>, t: Throwable) {
                seasonLiveData.value = Resource(error = "Conection Error", data = null)
            }
        })
        return episodeLiveData
    }
}