package com.fishtudo.awesomeseries.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.Resource
import com.fishtudo.awesomeseries.repositories.TVMazeRepository

class ListShowViewModel(
    private val repository: TVMazeRepository
) : ViewModel() {

    fun listShowsByPage(page: Int): LiveData<Resource<List<Show>>> {
        return repository.listShowsByPage(page)
    }
}