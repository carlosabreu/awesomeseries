package com.fishtudo.awesomeseries.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fishtudo.awesomeseries.model.CastCredits
import com.fishtudo.awesomeseries.repositories.Resource
import com.fishtudo.awesomeseries.repositories.TVMazeRepository

class ListCastCreditsViewModel(
    private val repository: TVMazeRepository
) : ViewModel() {

    fun listCastCredits(personId: Int): LiveData<Resource<List<CastCredits>>> {
        return repository.listCastCredits(personId)
    }
}