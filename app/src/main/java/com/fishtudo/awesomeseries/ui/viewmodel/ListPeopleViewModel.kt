package com.fishtudo.awesomeseries.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fishtudo.awesomeseries.model.People
import com.fishtudo.awesomeseries.repositories.Resource
import com.fishtudo.awesomeseries.repositories.TVMazeRepository

class ListPeopleViewModel(
    private val repository: TVMazeRepository
) : ViewModel() {

    fun listPeople(): LiveData<Resource<List<People>>> {
        return repository.listPeople()
    }
}