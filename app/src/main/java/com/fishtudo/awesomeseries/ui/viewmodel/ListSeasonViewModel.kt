package com.fishtudo.awesomeseries.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fishtudo.awesomeseries.model.Season
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.Resource
import com.fishtudo.awesomeseries.repositories.TVMazeRepository

class ListSeasonViewModel(
    private val repository: TVMazeRepository
) : ViewModel() {

    fun listSeasons(showId: Int): LiveData<Resource<List<Season>>> {
        return repository.listSeasons(showId)
    }
}