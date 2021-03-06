package com.fishtudo.awesomeseries.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fishtudo.awesomeseries.repositories.FavoriteShowRepository
import com.fishtudo.awesomeseries.ui.viewmodel.ListFavoriteViewModel

class ListFavoriteViewModelFactory(
    private val repository: FavoriteShowRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListFavoriteViewModel(repository) as T
    }
}