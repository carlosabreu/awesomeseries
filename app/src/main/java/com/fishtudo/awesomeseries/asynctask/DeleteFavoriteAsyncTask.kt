package com.fishtudo.awesomeseries.asynctask

import android.os.AsyncTask
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.database.dao.ShowDAO
import com.fishtudo.awesomeseries.repositories.database.entity.ShowEntity

class DeleteFavoriteAsyncTask(
    private val showDAO: ShowDAO,
    private val show: Show,
    private val callback: () -> Unit
) :
    AsyncTask<Void, Void, Void?>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        showDAO.delete(ShowEntity.createShowEntityFromShow(show))
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        callback()
    }
}