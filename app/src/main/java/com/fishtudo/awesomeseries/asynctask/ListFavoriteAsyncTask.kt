package com.fishtudo.awesomeseries.asynctask

import android.os.AsyncTask
import com.fishtudo.awesomeseries.repositories.database.dao.ShowDAO
import com.fishtudo.awesomeseries.repositories.database.entity.ShowEntity

class ListFavoriteAsyncTask(
    private val showDAO: ShowDAO,
    private val callback: (List<ShowEntity?>?) -> Unit
) :
    AsyncTask<Void, Void, List<ShowEntity?>?>() {

    override fun doInBackground(vararg p0: Void?) =
        showDAO.listAll()

    override fun onPostExecute(result: List<ShowEntity?>?) {
        super.onPostExecute(result)
        callback(result)
    }
}