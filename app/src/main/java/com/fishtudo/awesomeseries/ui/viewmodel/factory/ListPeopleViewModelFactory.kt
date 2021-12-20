package com.fishtudo.awesomeseries.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fishtudo.awesomeseries.repositories.TVMazeRepository
import com.fishtudo.awesomeseries.ui.viewmodel.ListPeopleViewModel

class ListPeopleViewModelFactory(
    private val repository: TVMazeRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListPeopleViewModel(repository) as T
    }
}