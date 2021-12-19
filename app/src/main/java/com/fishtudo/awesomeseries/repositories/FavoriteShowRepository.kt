package com.fishtudo.awesomeseries.repositories

import android.content.Context
import com.fishtudo.awesomeseries.asynctask.ListFavoriteAsyncTask
import com.fishtudo.awesomeseries.asynctask.SaveFavoriteAsyncTask
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.database.FavoriteDatabase
import com.fishtudo.awesomeseries.repositories.database.entity.ShowEntity

class FavoriteShowRepository {

    private var favorites: List<ShowEntity?>? = null

    fun save(context: Context, show: Show) {
        FavoriteDatabase.getInstance(context).showDAO?.let {
            SaveFavoriteAsyncTask(it, show) {
            }.execute()
        }
    }

    fun isFavorite(
        context: Context,
        show: Show,
        callback: (Boolean) -> Unit
    ) {
        if (favorites == null) {
            loadFavorites(context) {
                answerIfFavorite(show, callback)
            }
            return
        }
        answerIfFavorite(show, callback)
    }

    private fun loadFavorites(context: Context, callback: () -> Unit) {
        FavoriteDatabase.getInstance(context).showDAO?.let { showDao ->
            ListFavoriteAsyncTask(showDao) {
                this.favorites = it
                callback()
            }.execute()
        }
    }

    private fun answerIfFavorite(
        show: Show,
        callback: (Boolean) -> Unit
    ) {
        val filter = favorites?.filter { favorite -> favorite?.movieId == show.id }
        callback(filter?.size ?: 0 > 0)
    }
}