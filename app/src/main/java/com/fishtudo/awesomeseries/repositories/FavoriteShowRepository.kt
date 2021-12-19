package com.fishtudo.awesomeseries.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fishtudo.awesomeseries.asynctask.DeleteFavoriteAsyncTask
import com.fishtudo.awesomeseries.asynctask.ListFavoriteAsyncTask
import com.fishtudo.awesomeseries.asynctask.SaveFavoriteAsyncTask
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.database.FavoriteDatabase
import com.fishtudo.awesomeseries.repositories.database.entity.ShowEntity

class FavoriteShowRepository {

    private val favoritesLiveData = MutableLiveData<Resource<List<Show>>>()

    fun changeFavoritesStatus(context: Context, show: Show) {
        val found = favoritesLiveData.value?.data?.filter { it.id == show.id }?.size != 0

        FavoriteDatabase.getInstance(context).showDAO?.let {
            if (!found) {
                SaveFavoriteAsyncTask(it, show) {
                }.execute()
            } else {
                DeleteFavoriteAsyncTask(it, show) {
                }.execute()
            }
        }
        listFavorites(context)
    }

    fun listFavorites(context: Context): LiveData<Resource<List<Show>>> {
        loadFavorites(context) { showEntityList ->
            val mapNotNull = showEntityList?.mapNotNull {
                it?.createShow()
            }
            favoritesLiveData.value = Resource(mapNotNull)
        }
        return favoritesLiveData
    }

    private fun loadFavorites(context: Context, callback: (List<ShowEntity?>?) -> Unit) {
        FavoriteDatabase.getInstance(context).showDAO?.let { showDao ->
            ListFavoriteAsyncTask(showDao) {
                callback(it)
            }.execute()
        }
    }
}