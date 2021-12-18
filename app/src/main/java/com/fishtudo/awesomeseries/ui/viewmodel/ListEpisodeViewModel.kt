package com.fishtudo.awesomeseries.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fishtudo.awesomeseries.model.Episode
import com.fishtudo.awesomeseries.repositories.Resource
import com.fishtudo.awesomeseries.repositories.TVMazeRepository

class ListEpisodeViewModel(
    private val repository: TVMazeRepository
) : ViewModel() {

    fun listEpisodeBySeason(seasonId: Int): LiveData<Resource<List<Episode>>> {
        return repository.listEpisodesBySeason(seasonId)
    }
}