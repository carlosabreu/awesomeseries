package com.fishtudo.awesomeseries.asynctask

import android.os.AsyncTask
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.database.dao.ShowDAO
import com.fishtudo.awesomeseries.repositories.database.entity.ShowEntity

class SaveFavoriteAsyncTask(
    private val showDAO: ShowDAO,
    private val show: Show,
    private val callback: () -> Unit
) :
    AsyncTask<Void, Void, Long>() {

    override fun doInBackground(vararg p0: Void?) =
        showDAO.save(ShowEntity.createShowEntityFromShow(show))

    override fun onPostExecute(result: Long?) {
        super.onPostExecute(result)
        callback()
    }
}