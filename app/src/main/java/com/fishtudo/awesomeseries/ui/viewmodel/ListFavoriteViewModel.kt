package com.fishtudo.awesomeseries.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.FavoriteShowRepository
import com.fishtudo.awesomeseries.repositories.Resource

class ListFavoriteViewModel(
    private val repository: FavoriteShowRepository
) : ViewModel() {

    fun listFavorites(context: Context): LiveData<Resource<List<Show>>> {
        return repository.listFavorites(context)
    }

    fun changeFavoritesStatus(context: Context, show: Show) {
        return repository.changeFavoritesStatus(context, show)
    }
}