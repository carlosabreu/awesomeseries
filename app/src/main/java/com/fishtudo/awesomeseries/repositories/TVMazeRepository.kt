package com.fishtudo.awesomeseries.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fishtudo.awesomeseries.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVMazeRepository(private val service: TvmazeApiInterface) {

    private val showLiveData = MutableLiveData<Resource<List<Show>>>()

    private val singleShowLiveData = MutableLiveData<Resource<Show>>()

    private val seasonLiveData = MutableLiveData<Resource<List<Season>>>()

    private val episodeLiveData = MutableLiveData<Resource<List<Episode>>>()

    private val peopleLiveData = MutableLiveData<Resource<List<People>>>()

    private val castCreditsLiveData = MutableLiveData<Resource<List<CastCredits>>>()

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

    fun requireShowById(id: List<Int>): LiveData<Resource<Show>> {
        id.forEach {
            callSingShow(it)
        }
        return singleShowLiveData
    }

    private fun callSingShow(id: Int) {
        val call: Call<Show> = service.singleShow(id)
        call.enqueue(object : Callback<Show> {
            override fun onResponse(call: Call<Show>, response: Response<Show>) {
                singleShowLiveData.value = Resource(response.body())
            }

            override fun onFailure(call: Call<Show>, t: Throwable) {
                singleShowLiveData.value = Resource(null, "Unable to connect to server")
            }
        })
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

    fun listPeople(): LiveData<Resource<List<People>>> {
        val call: Call<List<People>> = service.listPeople()
        call.enqueue(object : Callback<List<People>> {
            override fun onResponse(call: Call<List<People>>, response: Response<List<People>>) {
                peopleLiveData.value = Resource(response.body())
            }

            override fun onFailure(call: Call<List<People>>, t: Throwable) {
                peopleLiveData.value = Resource(error = "Conection Error", data = null)
            }
        })
        return peopleLiveData
    }

    fun listCastCredits(id: Int): LiveData<Resource<List<CastCredits>>> {
        val call: Call<List<CastCredits>> = service.listCastCredits(id)
        call.enqueue(object : Callback<List<CastCredits>> {
            override fun onResponse(
                call: Call<List<CastCredits>>,
                response: Response<List<CastCredits>>
            ) {
                castCreditsLiveData.value = Resource(response.body())
            }

            override fun onFailure(call: Call<List<CastCredits>>, t: Throwable) {
                castCreditsLiveData.value = Resource(error = "Conection Error", data = null)
            }
        })
        return castCreditsLiveData
    }
}